package solvers.perceptronEntitys;

import java.io.Serializable;

public class Neuron implements Serializable {

    private double[] weight;

    public double[] getWeight() {
        return weight;
    }

    public void setWeight(double[] weight) {
        this.weight = weight;
    }
}
