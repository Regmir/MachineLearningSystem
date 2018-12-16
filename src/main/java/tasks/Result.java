/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;


import antlr.StringUtils;
import dataBaseManagement.model.ObjectFromDB;
import mainIntefaces.BasicTask;
import mainIntefaces.DataBaseManagement;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Acer
 */
public class Result implements  Serializable {

    double test;

    double learn;

    int id;

    String name;

    String solver;

    String algo;

    public String getSolver() {
        return solver;
    }

    public void setSolver(String solver) {
        this.solver = solver;
    }

    public String getAlgo() {
        return algo;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }

    public double getTest() {
        return test;
    }

    public void setTest(double test) {
        this.test = test;
    }

    public double getLearn() {
        return learn;
    }

    public void setLearn(double learn) {
        this.learn = learn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Result makeResult(double learn, double test, String solver, String algo, String name) {
        this.learn = learn;
        this.name = name;
        this.test = test;
        this.solver = solver;
        this.algo= algo;
        return  this;
    }

    public ObjectFromDB prepareObjectFromDB(){
        ObjectFromDB objToPersist = new ObjectFromDB();
        objToPersist.setName(this.name);
        objToPersist.setType("result");
        byte[] parameters = SerializationUtils.serialize(this);
        objToPersist.setParameters(parameters);
        return  objToPersist;
    }

    public static Result parseResult(ObjectFromDB objectFromDB){
        Result result = null;
        if (objectFromDB.getType().equals("result"))
            result = (Result) SerializationUtils.deserialize(objectFromDB.getParameters());
        return result;
    }
}
