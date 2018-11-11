package solvers.perceptronEntitys;

import java.io.Serializable;

public class Layer implements Serializable {

    private int neuronCount;

    private ActivationFunction activationFunction;

    private Neuron[] neurons;

    public int getNeuronCount() {
        return neuronCount;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public void setNeuronCount(int neuronCount) {
        this.neuronCount = neuronCount;
    }

    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    public void setNeurons(Neuron[] neurons) {
        this.neurons = neurons;
    }

    public double[] calculate (double[] input){
        double[] output = new double[neuronCount];
        for(int i = 1; i < neurons.length; i++) {
            output[i] = neurons[i].calculate(input,activationFunction);
        }
        return  output;
    }
}
