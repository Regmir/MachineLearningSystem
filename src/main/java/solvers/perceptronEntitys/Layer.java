package solvers.perceptronEntitys;

public class Layer {

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
}
