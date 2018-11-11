package solvers.perceptronEntitys;

public class Calculator {

    static double alpha = 1.0;

    public static double getAlpha() {
        return alpha;
    }

    public static void setAlpha(double alpha) {
        Calculator.alpha = alpha;
    }

    public static double calculateError(double[] ethalon, double[] out){
        double result = 0.0;
        for (int i = 0; i < ethalon.length; i++){
            result = result + (ethalon[i] - out[i]) * (ethalon[i] - out[i]);
        }
        result = result / 2.0;
        return result;
    }

    public static double calculatePartialDerivativeByIndex(double[] ethalon, double[] out, int index)
    {
        return out[index] - ethalon[index];
    }

    public static double calculateFunction(double x, ActivationFunction function){
        double result = 0.0;
        if (function == ActivationFunction.SIGMOID) {
            result = (1.0 / (1.0 + Math.exp(-1.0 * alpha * x)));
        }
        return result;
    }

    public static double calculateFunctionDerivative(double x, ActivationFunction function){
        double result = 0.0;
        if (function == ActivationFunction.SIGMOID) {
            result = alpha * calculateFunction(x,ActivationFunction.SIGMOID) * (1 - calculateFunction(x,ActivationFunction.SIGMOID));
        }
        return result;
    }
}
