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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class PerceptronSolverImpl implements BasicSolver, Serializable{

    private int layerCount;

    private Layer[] layers;

    public int getLayerCount() {
        return layerCount;
    }

    public void setLayerCount(int layerCount) {
        this.layerCount = layerCount;
    }

    public Layer[] getLayers() {
        return layers;
    }

    public void setLayers(Layer[] layers) {
        this.layers = layers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    private String name;

    private BigInteger id;

    @Override
    public double solve(double[] input) {
        double[] params = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            params[i] = input[i];
        }
        for (int i = 1; i < layerCount; i++){
            params = layers[i].calculate(params);
        }
        return params[0];
    }

    public double[] solveAll (ArrayList<double[]> input) {
        double[] result = new double[input.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = solve(input.get(i));
        return result;
    }

    @Override
    public void learn(BasicLearningAlgorythm algorythm, BasicTask task) {
       /* PerceptronSolverImpl newSolver = (PerceptronSolverImpl)*/ algorythm.learn(this,task);
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

    public PerceptronSolverImpl setPerceptron(Layer[] layers, String name){
        this.layerCount = layers.length;
        this.name = name;
        for (int i = 1; i < layerCount; i++){
            Neuron[] neurons = new Neuron[layers[i].getNeuronCount()];
            double[] weights = new double[layers[i-1].getNeuronCount()];
            for (double weight:weights) {
                weight = 1.0;
            }
            for (Neuron neuron:neurons) {
                neuron = new Neuron();
                neuron.setWeight(weights);
            }
            layers[i].setNeurons(neurons);
        }
        this.layers = layers;
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

    public static PerceptronSolverImpl parsePerceptron(ObjectFromDB objectFromDB){
        PerceptronSolverImpl perceptronSolver = null;
        if (objectFromDB.getType().equals("perceptron"))
            perceptronSolver = (PerceptronSolverImpl)SerializationUtils.deserialize(objectFromDB.getParameters());
        return perceptronSolver;
    }
}
