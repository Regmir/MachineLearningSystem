package controllers;

import dataBaseManagement.model.ObjectFromDB;
import dataBaseManagement.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;

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
}
