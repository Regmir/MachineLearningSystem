/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solvers;


import dataBaseManagement.model.ObjectFromDB;
import dataBaseManagement.service.ObjectServiceImpl;
import mainIntefaces.BasicLearningAlgorythm;
import mainIntefaces.BasicTask;
import mainIntefaces.BasicSolver;
import mainIntefaces.DataBaseManagement;
import org.apache.commons.lang3.SerializationUtils;
import solvers.perceptronEntitys.Layer;

import javax.swing.text.LayeredHighlighter;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author Acer
 */
public class PerceptronSolverImpl implements BasicSolver, DataBaseManagement, Serializable{

    private int layerCount;

    private Layer[] layers;

    private String name;

    private BigInteger id;

    @Override
    public BigInteger solve(Object[] input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void learn(BasicLearningAlgorythm algorythm, BasicTask task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] getParamters() {
        Object[] parameters = new Object[2];
        parameters[1]=layerCount;
        parameters[2]=layers;
        return parameters;
    }

    @Override
    public void setParameters(Object[] parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigInteger writeToDataBase() {
        ObjectServiceImpl service = new ObjectServiceImpl();
        ObjectFromDB objToPersist = new ObjectFromDB();
        objToPersist.setName(this.name);
        objToPersist.setType("solver");
        byte[] parameters = SerializationUtils.serialize(this);
        objToPersist.setParameters(parameters);
        id = service.addObject(objToPersist);
        return id;
    }


    @Override
    public PerceptronSolverImpl readFromDatabase(BigInteger objectID) {
        ObjectServiceImpl service = new ObjectServiceImpl();
        ObjectFromDB objectFromDB = service.getObjectById(objectID);
        PerceptronSolverImpl perceptronSolver = null;
        if (objectFromDB.getType().equals("Perceptron"))
            perceptronSolver = (PerceptronSolverImpl)SerializationUtils.deserialize(objectFromDB.getParameters());
        return perceptronSolver;
    }
    
}
