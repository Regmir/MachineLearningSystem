package controllers;

import dataBaseManagement.model.ObjectFromDB;
import dataBaseManagement.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import solvers.perceptronEntitys.ActivationFunction;
import solvers.perceptronEntitys.Layer;

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
                                @RequestParam ("func") String[] func, Model model){
        List<Layer> layers = new ArrayList<Layer>();
        for (int i = 0; i<neurons.length; i++){
            Layer layer = new Layer();
            layer.setNeuronCount(neurons[i]);
            if(i > 0)
                layer.setActivationFunction(func[i-1].equals("Ф1") ? ActivationFunction.FUNC : ActivationFunction.FUNC2);
            layers.add(layer);
        }
        model.addAttribute("layers",layers);
        return "showPerceptron";
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
        //this.objectService.removeObject(id);
        return "createPerceptron";
    }
}
