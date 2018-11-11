package solvers.perceptronEntitys;

import java.io.Serializable;

public class Neuron implements Serializable {

    public double dEdz;

    private double[] weight;

    public double lastNET;

    public double LastState;

    public double[] getWeight() {
        return weight;
    }

    public void setWeight(double[] weight) {
        this.weight = weight;
    }

    public double calculate(double[] input, ActivationFunction function){
        double result = 0.0;
        for(int i = 0; i < weight.length; i++){
            result = result + weight[i] * input[i];
        }
        lastNET = result;
        result = Calculator.calculateFunction(result,function);
        LastState = result;
        return result;
    }

    public int getWeightCount(){
        return  weight.length;
    }
}
