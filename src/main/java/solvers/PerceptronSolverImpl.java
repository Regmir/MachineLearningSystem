/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solvers;


import controllers.ObjectsFromDBController;
import dataBaseManagement.model.ObjectFromDB;
import dataBaseManagement.service.ObjectService;
import dataBaseManagement.service.ObjectServiceImpl;
import mainIntefaces.BasicLearningAlgorythm;
import mainIntefaces.BasicTask;
import mainIntefaces.BasicSolver;
import mainIntefaces.DataBaseManagement;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import solvers.perceptronEntitys.Layer;
import solvers.perceptronEntitys.Neuron;

import javax.swing.text.LayeredHighlighter;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

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
       /* ObjectFromDB objToPersist = new ObjectFromDB();
        objToPersist.setName(this.name);
        objToPersist.setType("perceptron");
        byte[] parameters = SerializationUtils.serialize(this);
        objToPersist.setParameters(parameters);
        id = ObjectsFromDBController(objToPersist);*/
        return null;
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

    public PerceptronSolverImpl setPerceptron(Layer[] layers, String name){
        this.layerCount = layers.length;
        this.name = name;
        for (int i = 1; i < layerCount; i++){
            Neuron[] neurons = new Neuron[layers[i].getNeuronCount()];
            double[] weights = new double[layers[i].getNeuronCount()];
            for (double weight:weights) {
                weight = 1.0;
            }
            for (Neuron neuron:neurons) {
                neuron = new Neuron();
                neuron.setWeight(weights);
            }
            layers[i].setNeurons(neurons);
        }
        return this;
    }

    public ObjectFromDB prepareObjectFromDB(){
        ObjectFromDB objToPersist = new ObjectFromDB();
        objToPersist.setName(this.name);
        objToPersist.setType("perceptron");
        byte[] parameters = SerializationUtils.serialize(this);
        objToPersist.setParameters(parameters);
        return  objToPersist;
    }
}
