package controllers;

import dataBaseManagement.model.ObjectFromDB;
import dataBaseManagement.service.ObjectService;
import learningAlgorythms.BackPropagationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import solvers.PerceptronSolverImpl;
import solvers.perceptronEntitys.ActivationFunction;
import solvers.perceptronEntitys.Calculator;
import solvers.perceptronEntitys.Layer;
import tasks.Result;
import tasks.TaskImpl;

import java.io.*;
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

    @RequestMapping(value = "/perceptron/addOrEdit/", method = RequestMethod.POST)
    public String addPerceptron(@RequestParam ("id") String oldid,
                                @RequestParam ("neurons") Integer[] neurons,
                                @RequestParam ("func") String[] func,
                                @RequestParam ("name") String name,
                                @RequestParam ("flag") String flag, Model model){
        Layer[] layers = new Layer[neurons.length];
        for (int i = 0; i<neurons.length; i++){
            layers[i] = new Layer();
            layers[i].setNeuronCount(neurons[i]);
            if(i > 0)
                layers[i].setActivationFunction(func[i-1].equals("linear") ? ActivationFunction.LINEAR : ActivationFunction.SIGMOID);
        }
        PerceptronSolverImpl perceptronSolver = new PerceptronSolverImpl();
        perceptronSolver.setPerceptron(layers,name);
        PerceptronSolverImpl oldperceptronSolver = PerceptronSolverImpl.parsePerceptron(this.objectService.getObjectById(new BigInteger(oldid)));
        ObjectFromDB objectFromDB = perceptronSolver.prepareObjectFromDB();
        objectFromDB.setId(new BigInteger(oldid));
        if (flag.equals("new")) {
            BigInteger id = this.objectService.addObject(objectFromDB);
            objectFromDB = this.objectService.getObjectById(id);
            perceptronSolver = PerceptronSolverImpl.parsePerceptron(objectFromDB);
            model.addAttribute("perceptronSolver", perceptronSolver);
        }
        if (flag.equals("old")) {
            this.objectService.updateObject(objectFromDB,oldperceptronSolver.getName());
            objectFromDB = this.objectService.getObjectById(new BigInteger(oldid));
            perceptronSolver = PerceptronSolverImpl.parsePerceptron(objectFromDB);
            model.addAttribute("perceptronSolver", perceptronSolver);
        }
        return "showPerceptron";
    }

    @RequestMapping(value = "/task/add", method = RequestMethod.POST)
    public String addTask(@RequestParam ("name") String name,
                          @RequestParam ("file") MultipartFile file, Model model){
       /* File convFile = null;
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
        model.addAttribute("task",task);*/
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "Files");
            if (!dir.exists())
                dir.mkdirs();

            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath()
                    + File.separator + name);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "showTask";
    }

    @RequestMapping(value = "/algo/add", method = RequestMethod.POST)
    public String addTask(@RequestParam ("name") String name,
                          @RequestParam ("speed") double speed,
                          @RequestParam ("iter")  int iter, Model model){
        BackPropagationImpl backPropagation = new BackPropagationImpl();
        backPropagation.setIterations(iter);
        backPropagation.setSpeed(speed);
        backPropagation.setName(name);
        ObjectFromDB objectFromDB = backPropagation.prepareObjectFromDB();
        BigInteger id = this.objectService.addObject(objectFromDB);
        objectFromDB = this.objectService.getObjectById(id);
        backPropagation = BackPropagationImpl.parseAlgo(objectFromDB);
        model.addAttribute("algo",backPropagation);
        return "showBackPropagation";
    }

    @RequestMapping(value = "/algo/addOrEdit/", method = RequestMethod.POST)
    public String addTask(@RequestParam ("id") BigInteger oldid,
                          @RequestParam ("name") String name,
                          @RequestParam ("speed") double speed,
                          @RequestParam ("flag") String flag,
                          @RequestParam ("iter")  int iter, Model model){
        BackPropagationImpl backPropagation = new BackPropagationImpl();
        backPropagation.setIterations(iter);
        backPropagation.setSpeed(speed);
        backPropagation.setName(name);
        ObjectFromDB objectFromDB = backPropagation.prepareObjectFromDB();
        objectFromDB.setId(oldid);
        backPropagation.setId(oldid);
        BackPropagationImpl oldBackPropagation = BackPropagationImpl.parseAlgo(this.objectService.getObjectById(oldid));
        if (flag.equals("new")) {
            BigInteger id = this.objectService.addObject(objectFromDB);
            objectFromDB = this.objectService.getObjectById(id);
            backPropagation = BackPropagationImpl.parseAlgo(objectFromDB);
            model.addAttribute("algo",backPropagation);
        }
        if (flag.equals("old")) {
            this.objectService.updateObject(objectFromDB,oldBackPropagation.getName());
            objectFromDB = this.objectService.getObjectById(oldid);
            backPropagation = BackPropagationImpl.parseAlgo(objectFromDB);
            model.addAttribute("algo", backPropagation);
        }
        return "showBackPropagation";
    }

    @RequestMapping("show/objectsfromdbdata/{id}")
    public String objData(@PathVariable("id") BigInteger id, Model model){
        ObjectFromDB objectFromDB = this.objectService.getObjectById(id);
        if (objectFromDB.getType().equals("task")) {
            TaskImpl task = TaskImpl.parseTask(objectFromDB);
            model.addAttribute("task",task);
            return "showTask";
        }
        if (objectFromDB.getType().equals("perceptron")) {
            PerceptronSolverImpl perceptronSolver = PerceptronSolverImpl.parsePerceptron(objectFromDB);
            model.addAttribute("perceptronSolver",perceptronSolver);
            return "showAndChangePerceptron";
        }
        if (objectFromDB.getType().equals("backpropagation")) {
            BackPropagationImpl algo = BackPropagationImpl.parseAlgo(objectFromDB);
            algo.setId(id);
            model.addAttribute("algo",algo);
            return "showAndChangeBackPropagation";
        }
        return  "objectsfromdbdata";
    }

    @RequestMapping("/remove/{id}")
    public String removeObj(@PathVariable("id") BigInteger id, Model model){
        String type = this.objectService.getObjectById(id).getType();
        this.objectService.removeObject(id);
        return "redirect:/show/"+type;
    }

    @RequestMapping("/createPerceptron")
    public String cp( Model model){
        return "createPerceptron";
    }

    @RequestMapping("/createTask")
    public String ct( Model model){
        return "createTask";
    }

    @RequestMapping("/createAlgo")
    public String ca( Model model){
        return "createBackPropagation";
    }

    @RequestMapping("/createLearn")
    public String cl( Model model){
        List<ObjectFromDB> solvers = this.objectService.getByType("perceptron");
        List<ObjectFromDB> tasks = this.objectService.getByType("task");
        List<ObjectFromDB> algo = this.objectService.getByType("backpropagation");
        model.addAttribute("solvers", solvers);
        model.addAttribute("tasks", tasks);
        model.addAttribute("algo", algo);
        return "createLearn";
    }

    @RequestMapping("/show/{type}")
    public String showByType(@PathVariable("type") String type, Model model){
        List<ObjectFromDB> object = objectService.getByType(type);
        model.addAttribute("objects",object);
        model.addAttribute("type",type);
        if (type.equals("task")){
        model.addAttribute("name","Задачи");
        }
        if (type.equals("perceptron")) {
        model.addAttribute("name","Решатели");
        }
        if (type.equals("backpropagation")) {
        model.addAttribute("name","Алгоритмы");
        }
        return "showObjectsByType";
    }

    @RequestMapping("/result")
    public String showResult(Model model){
        List<ObjectFromDB> object = this.objectService.getByType("result");
        List<Result> res = new ArrayList<Result>();
        for (ObjectFromDB obj : object) {
            res.add(Result.parseResult(obj));
        }
        model.addAttribute("objects",res);
        return "showResult";
    }

    @RequestMapping(value = "/learn", method = RequestMethod.POST)
    public String learn( @RequestParam ("solver") String solver,
                        @RequestParam ("task") String task,
                        @RequestParam ("algo")  String algo, Model model){
        BackPropagationImpl backPropagation = BackPropagationImpl.parseAlgo(this.objectService.getObject(algo,"backpropagation"));
        PerceptronSolverImpl perceptronSolver = PerceptronSolverImpl.parsePerceptron(this.objectService.getObject(solver,"perceptron"));
        TaskImpl truetask = TaskImpl.parseTask(this.objectService.getObject(task,"task"));
        perceptronSolver.learn(backPropagation,truetask);
        model.addAttribute("errlearn",backPropagation.getLasterror());
        double[] realOutput = perceptronSolver.solveAll(truetask.getXtest(20));
        double err = Calculator.calculateErrorPercent(truetask.getYtest(20),realOutput);
        model.addAttribute("errtest",err);
        Result result = new Result();
        result.setName("Solver: "+solver+" Algo: "+algo);
        result.setTest(err);
        result.setLearn(backPropagation.getLasterror());
        result.setAlgo(algo);
        result.setSolver(solver);
        this.objectService.addObject(result.prepareObjectFromDB());
        return "testpage";
    }
}
