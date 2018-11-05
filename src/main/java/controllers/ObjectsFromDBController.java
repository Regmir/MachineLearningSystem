package controllers;

import dataBaseManagement.model.ObjectFromDB;
import dataBaseManagement.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import solvers.PerceptronSolverImpl;
import solvers.perceptronEntitys.ActivationFunction;
import solvers.perceptronEntitys.Layer;
import tasks.TaskImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ObjectsFromDBController {
    private ObjectService objectService;

    @Autowired(required = true)
    @Qualifier(value = "objService")
    public void setObjcectService(ObjectService objectService) {
        this.objectService = objectService;
    }

    @RequestMapping(value = "objects", method = RequestMethod.GET)
    public String listObjects(Model model){
        model.addAttribute("obj", new ObjectFromDB());
        model.addAttribute("listObjects", this.objectService.getAll());
        return "objects";
    }

    @RequestMapping(value = "/objects/add", method = RequestMethod.POST)
    public String addObj(@ModelAttribute("obj") ObjectFromDB obj){
            this.objectService.addObject(obj);
        return "redirect:/objects";
    }

    @RequestMapping(value = "/perceptron/add", method = RequestMethod.POST)
    public String addPerceptron(@RequestParam ("neurons") Integer[] neurons,
                                @RequestParam ("func") String[] func,
                                @RequestParam ("name") String name, Model model){
        Layer[] layers = new Layer[neurons.length];
        for (int i = 0; i<neurons.length; i++){
            layers[i] = new Layer();
            layers[i].setNeuronCount(neurons[i]);
            if(i > 0)
                layers[i].setActivationFunction(func[i-1].equals("linear") ? ActivationFunction.LINEAR : ActivationFunction.SIGMOID);
        }
        PerceptronSolverImpl perceptronSolver = new PerceptronSolverImpl();
        perceptronSolver.setPerceptron(layers,name);
        ObjectFromDB objectFromDB = perceptronSolver.prepareObjectFromDB();
        BigInteger id = this.objectService.addObject(objectFromDB);
        objectFromDB = this.objectService.getObjectById(id);
        perceptronSolver = PerceptronSolverImpl.parsePerceptron(objectFromDB);
        model.addAttribute("perceptronSolver",perceptronSolver);
        return "showPerceptron";
    }

    @RequestMapping(value = "/task/add", method = RequestMethod.POST)
    public String addTask(@RequestParam ("name") String name,
                          @RequestParam ("file") MultipartFile file, Model model){
        File convFile = null;
        try { convFile = new File( file.getOriginalFilename());
            file.transferTo(convFile);} catch (Exception e) {}
        TaskImpl task = new TaskImpl();
        try {
            task.parseFile(convFile,name);
        } catch (Exception e) {}
        ObjectFromDB objectFromDB = task.prepareObjectFromDB();
        BigInteger id = this.objectService.addObject(objectFromDB);
        objectFromDB = this.objectService.getObjectById(id);
        task = TaskImpl.parseTask(objectFromDB);
        model.addAttribute("task",task);
        return "showTask";
    }

    @RequestMapping("objectsfromdbdata/{id}")
    public String objData(@PathVariable("id") BigInteger id, Model model){
        model.addAttribute("obj", this.objectService.getObjectById(id));
        return "objectsfromdbdata";
    }

    @RequestMapping("/remove/{id}")
    public String removeObj(@PathVariable("id") BigInteger id, Model model){
        this.objectService.removeObject(id);
        return "redirect:/objects";
    }

    @RequestMapping("/createPerceptron")
    public String cp( Model model){
        return "createPerceptron";
    }

    @RequestMapping("/createTask")
    public String ct( Model model){
        return "createTask";
    }
}
