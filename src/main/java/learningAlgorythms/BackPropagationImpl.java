/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learningAlgorythms;



import dataBaseManagement.model.ObjectFromDB;
import mainIntefaces.BasicLearningAlgorythm;
import mainIntefaces.BasicSolver;
import mainIntefaces.BasicTask;
import mainIntefaces.DataBaseManagement;
import org.apache.commons.lang3.SerializationUtils;
import solvers.PerceptronSolverImpl;
import solvers.perceptronEntitys.Calculator;
import tasks.TaskImpl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author Acer
 */
public class BackPropagationImpl implements BasicLearningAlgorythm, Serializable {

    String name;

    int iterations;

    double speed;

    double lastError;

    BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public double getLasterror() {
        return lastError;
    }

    public void setLasterror(double lasterror) {
        this.lastError = lasterror;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public Object[] getParamters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setParameters(Object[] parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BasicSolver learn(BasicSolver solver, BasicTask task) {
        PerceptronSolverImpl perceptronSolver = (PerceptronSolverImpl) solver;
        TaskImpl data = (TaskImpl) task;
        ArrayList<double[]> X = data.getXlearn(20);
        double[] Y = data.getYlearn(20);
        int recordscount = X.size();
        double[] out = new double[Y.length];
        for (int i = 0; i < X.size(); i++){
            out[i] = perceptronSolver.solve(X.get(i));
        }
        double currentError = Calculator.calculateError(Y,out);
        double lastError = 0.0;
        int epochNumber = 0;
        do
        {
            int currentIndex = 0;
            do
            {
                double[][][] nablaWeights = new double[perceptronSolver.getLayerCount()][][];
                double[][] nablaBiases = new double[perceptronSolver.getLayerCount()][];

                for (int i = 1; i < perceptronSolver.getLayerCount(); i++)
                {
                    nablaBiases[i] = new double[perceptronSolver.getLayers()[i].getNeuronCount()];
                    nablaWeights[i] = new double[perceptronSolver.getLayers()[i].getNeuronCount()][];
                    for (int j = 0; j < perceptronSolver.getLayers()[i].getNeuronCount(); j++)
                    {
                        nablaBiases[i][j] = 0;
                        nablaWeights[i][j] = new double[perceptronSolver.getLayers()[i].getNeurons()[j].getWeightCount()];
                        for (int k = 0; k < perceptronSolver.getLayers()[i].getNeurons()[j].getWeightCount(); k++)
                        {
                            nablaWeights[i][j][k] = 0;
                        }
                    }
                }
                //process one batch
                for (int inBatchIndex = currentIndex;  inBatchIndex < recordscount; inBatchIndex++)
                {
                    //forward pass
                    //double[] realOutput = network.ComputeOutput(data[trainingIndices[inBatchIndex]].Input);
                    double[] realOutput = perceptronSolver.solveAll(X);

                    //backward pass, error propagation
                    //last layer
                    for (int j = 0; j < perceptronSolver.getLayers()[perceptronSolver.getLayerCount() - 1].getNeurons().length; j++)
                    {
                        perceptronSolver.getLayers()[perceptronSolver.getLayerCount() - 1].getNeurons()[j].dEdz =
                                Calculator.calculatePartialDerivativeByIndex(Y, realOutput, j) *
                                        Calculator.calculateFunctionDerivative(perceptronSolver.getLayers()[perceptronSolver.getLayerCount() - 1].getNeurons()[j].lastNET,
                                                perceptronSolver.getLayers()[perceptronSolver.getLayerCount() - 1].getActivationFunction());

                        /*nablaBiases[network.Layers.Length - 1][j] += _config.LearningRate *
                                network.Layers[network.Layers.Length - 1].Neurons[j].dEdz;*/

                        for (int i = 0; i < perceptronSolver.getLayers()[perceptronSolver.getLayerCount() - 1].getNeurons()[j].getWeightCount(); i++)
                        {
                            nablaWeights[perceptronSolver.getLayerCount() - 1][j][i] +=
                                    speed*(perceptronSolver.getLayers()[perceptronSolver.getLayerCount() - 1].getNeurons()[j].dEdz*
                                            (perceptronSolver.getLayerCount() > 1 ?
                                                    perceptronSolver.getLayers()[perceptronSolver.getLayerCount() - 1 - 1].getNeurons()[i].LastState :
                                                    X.get(inBatchIndex)[i]));
                                           /* +
                                            _config.RegularizationFactor *
                                                    network.Layers[network.Layers.Length - 1].Neurons[j].Weights[i]
                                                    / data.Count);*/
                        }
                    }

                    //hidden layers
                    for (int hiddenLayerIndex = perceptronSolver.getLayerCount() - 2; hiddenLayerIndex >= 1; hiddenLayerIndex--)
                    {
                        for (int j = 0; j < perceptronSolver.getLayers()[hiddenLayerIndex].getNeurons().length; j++)
                        {
                            perceptronSolver.getLayers()[hiddenLayerIndex].getNeurons()[j].dEdz = 0;
                            for (int k = 0; k < perceptronSolver.getLayers()[hiddenLayerIndex + 1].getNeurons().length; k++)
                            {
                                perceptronSolver.getLayers()[hiddenLayerIndex].getNeurons()[j].dEdz +=
                                        perceptronSolver.getLayers()[hiddenLayerIndex + 1].getNeurons()[k].getWeight()[j]*
                                                perceptronSolver.getLayers()[hiddenLayerIndex + 1].getNeurons()[k].dEdz;
                            }
                            perceptronSolver.getLayers()[hiddenLayerIndex].getNeurons()[j].dEdz *=
                                    Calculator.calculateFunctionDerivative(perceptronSolver.getLayers()[hiddenLayerIndex].getNeurons()[j].lastNET,
                                            perceptronSolver.getLayers()[hiddenLayerIndex].getActivationFunction());

                            nablaBiases[hiddenLayerIndex][j] += speed*
                                    perceptronSolver.getLayers()[hiddenLayerIndex].getNeurons()[j].dEdz;

                            for (int i = 0; i < perceptronSolver.getLayers()[hiddenLayerIndex].getNeurons()[j].getWeightCount(); i++)
                            {
                                nablaWeights[hiddenLayerIndex][j][i] += speed * (
                                        perceptronSolver.getLayers()[hiddenLayerIndex].getNeurons()[j].dEdz *
                                                (hiddenLayerIndex > 1 ? perceptronSolver.getLayers()[hiddenLayerIndex - 1].getNeurons()[i].LastState : X.get(inBatchIndex)[i])
                                                /*+
                                                _config.RegularizationFactor * network.Layers[hiddenLayerIndex].Neurons[j].Weights[i] / data.Count*/
                                );
                            }
                        }
                    }
                }

                //update weights and bias
                for (int layerIndex = 1; layerIndex < perceptronSolver.getLayerCount(); layerIndex++)
                {
                    for (int neuronIndex = 0; neuronIndex < perceptronSolver.getLayers()[layerIndex].getNeuronCount(); neuronIndex++)
                    {
                        //perceptronSolver.getLayers()[layerIndex].getNeurons()[neuronIndex].Bias -= nablaBiases[layerIndex][neuronIndex];
                        for (int weightIndex = 0; weightIndex < perceptronSolver.getLayers()[layerIndex].getNeurons()[neuronIndex].getWeightCount(); weightIndex++)
                        {
                            perceptronSolver.getLayers()[layerIndex].getNeurons()[neuronIndex].getWeight()[weightIndex] -=
                                    nablaWeights[layerIndex][neuronIndex][weightIndex];
                        }
                    }
                }

                currentIndex += 1;
            } while (currentIndex < recordscount-1);

            //recalculating error on all data
            //real error
            currentError = 0;
            double[] realOutput = perceptronSolver.solveAll(X);
            currentError += Calculator.calculateError(Y, realOutput);
            currentError *= 1d/recordscount;
            this.lastError = currentError;
            //regularization term
            /*if (Math.Abs(_config.RegularizationFactor - 0d) > Double.Epsilon)
            {
                double reg = 0;
                for (int layerIndex = 0; layerIndex < network.Layers.Length; layerIndex++)
                {
                    for (int neuronIndex = 0; neuronIndex < network.Layers[layerIndex].Neurons.Length; neuronIndex++)
                    {
                        for (int weightIndex = 0; weightIndex < network.Layers[layerIndex].Neurons[neuronIndex].Weights.Length; weightIndex++)
                        {
                            reg += network.Layers[layerIndex].Neurons[neuronIndex].Weights[weightIndex] *
                                    network.Layers[layerIndex].Neurons[neuronIndex].Weights[weightIndex];
                        }
                    }
                }
                currentError += _config.RegularizationFactor * reg / (2 * data.Count);
            }*/

            epochNumber++;
        } while (epochNumber < iterations /*&&
                currentError > MinError &&
                Math.Abs(currentError - lastError) > MinErrorChange*/);
        return perceptronSolver;
    }

    public ObjectFromDB prepareObjectFromDB(){
        ObjectFromDB objToPersist = new ObjectFromDB();
        objToPersist.setName(this.name);
        objToPersist.setType("backpropagation");
        byte[] parameters = SerializationUtils.serialize(this);
        objToPersist.setParameters(parameters);
        return  objToPersist;
    }

    public static BackPropagationImpl parseAlgo(ObjectFromDB objectFromDB){
        BackPropagationImpl backPropagation = null;
        if (objectFromDB.getType().equals("backpropagation"))
            backPropagation = (BackPropagationImpl)SerializationUtils.deserialize(objectFromDB.getParameters());
        return backPropagation;
    }
}
