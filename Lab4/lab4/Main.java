package lab4;

import lab4.algorithms.LeastSquaresMethod;
import lab4.data.ApproximatedFunction;
import lab4.data.ApproximationTypeFunc;
import lab4.data.DiscreteFunction;
import lab4.exceptions.WrongNumberOfCoefficients;

import java.util.Random;


public class Main {
    public static ApproximatedFunction[] approximatedFunctions = new ApproximatedFunction[]{
            new ApproximatedFunction("f(x) = ax + b", 2),
            new ApproximatedFunction("f(x) = ax^2 + bx + c", 3),
            new ApproximatedFunction("f(x) = a * lg(x) + b", 2),
            new ApproximatedFunction("f(x) = a * sin(x) + b", 2),
            new ApproximatedFunction("f(x) = a * e^x + b", 2),
            new ApproximatedFunction("f(x) = a * cos(x) + b", 2)
    };

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        approximatedFunctions[0].setFunctionForIntegration((x, coef) -> coef[0] * x + coef[1]);
        approximatedFunctions[1].setFunctionForIntegration((x, coef) -> coef[0] * Math.pow(x, 2) + coef[1] * x + coef[2]);
        approximatedFunctions[2].setFunctionForIntegration((x, coef) -> coef[0] * Math.log10(x) + coef[1]);
        approximatedFunctions[3].setFunctionForIntegration((x, coef) -> coef[0] * Math.sin(x) + coef[1]);
        approximatedFunctions[4].setFunctionForIntegration((x, coef) -> coef[0] * Math.pow(Math.E, x) + coef[1]);
        approximatedFunctions[5].setFunctionForIntegration((x, coef) -> coef[0] * Math.cos(x) + coef[1]);
        UserIO userIO = new UserIO();
        userIO.printWelcomeInformation();
        userIO.printInfoForEquation();
        int type = userIO.readType();
        double[] coefficients = userIO.readCoefficients(type, " ", 0);
        approximatedFunctions[type].setCoefficients(coefficients);
        double[] xArray = userIO.readXArray();
        double[] yArray = calculateYArray(xArray, approximatedFunctions[type]);
        DiscreteFunction discreteFunction = new DiscreteFunction(xArray, yArray, xArray.length, type, approximatedFunctions[type].getNumberOfCoefficients());

        ApproximationTypeFunc approximationTypeFunc = new ApproximationTypeFunc();
        switch (type) {
            case 0, 1:
                approximationTypeFunc.setFunction((x) -> x);
                break;
            case 2:
                approximationTypeFunc.setFunction(Math::log10);
                break;
            case 3:
                approximationTypeFunc.setFunction(Math::sin);
            case 4:
                approximationTypeFunc.setFunction((x) -> Math.pow(Math.E, x));
            case 5:
                approximationTypeFunc.setFunction(Math::cos);
            default:
        }

        LeastSquaresMethod leastSquaresMethod = new LeastSquaresMethod(approximationTypeFunc);
        double[] coeff = new double[0];
        try {
            coeff = leastSquaresMethod.findCoefficients(discreteFunction);
        } catch (WrongNumberOfCoefficients e) {
            System.out.println(e.getMessage());
        }
        ApproximatedFunction approximatedFunction = setApproximatedFunctions(coeff, type);
        approximatedFunction.setCoefficients(coeff);
        GraphDraftsman.setFunction(approximatedFunction, approximatedFunction.getEquation());
        DiscreteFunction cloneDiscreteFunction = new DiscreteFunction(discreteFunction.getXArray(),
                discreteFunction.getYArray(), discreteFunction.getNumberOfPoints(), type, discreteFunction.getNumberOfCoefficients());
        GraphDraftsman.setDiscreteFunction(cloneDiscreteFunction);
        removeLargestDeviation(discreteFunction, approximatedFunction);
        try {
            coeff = leastSquaresMethod.findCoefficients(discreteFunction);
        } catch (WrongNumberOfCoefficients e) {
            System.out.println(e.getMessage());
        }
        approximatedFunction = setApproximatedFunctions(coeff, type);
        approximatedFunction.setCoefficients(coeff);
        GraphDraftsman.setModifiedApproximatedFunction(approximatedFunction, approximatedFunction.getEquation());

        new GraphDraftsman().build();
    }

    private double[] calculateYArray(double[] xArray, ApproximatedFunction function) {
        double[] yArray = new double[xArray.length];
        for (int i = 0; i < xArray.length; i++) {
            if (Math.round(xArray.length / 2) == i) {
                yArray[i] = function.calculate(xArray[i]) + 3;
            } else {
                yArray[i] = function.calculate(xArray[i]) + generateNoise();
            }
        }
        return yArray;
    }

    private double generateNoise() {
        Random random = new Random();
        double rand = random.nextDouble() / 2;
        if (random.nextDouble() > 0.5) rand *= -1;
        return rand;
    }

    private ApproximatedFunction setApproximatedFunctions(double[] coeff, int type) {
        ApproximatedFunction approximatedFunction = null;
        switch (type) {
            case 0 -> {
                approximatedFunction = new ApproximatedFunction(String.format("%.3f", coeff[1]) + "x + " + String.format("%.3f", coeff[0]), 2);
                approximatedFunction.setFunctionForIntegration((x, coef) -> coef[1] * x + coef[0]);
            }
            case 1 -> {
                approximatedFunction = new ApproximatedFunction(String.format("%.3f", coeff[2]) + "x^2 + " + String.format("%.3f", coeff[1]) + "x +" + String.format("%.3f", coeff[0]), 3);
                approximatedFunction.setFunctionForIntegration((x, coef) -> coef[2] * x * x + coef[1] * x + coef[0]);
            }
            case 2 -> {
                approximatedFunction = new ApproximatedFunction(String.format("%.3f", coeff[1]) + "log(x) + " + String.format("%.3f", coeff[0]), 2);
                approximatedFunction.setFunctionForIntegration((x, coef) -> coef[1] * Math.log10(x) + coef[0]);
            }
            case 3 -> {
                approximatedFunction = new ApproximatedFunction(String.format("%.3f", coeff[1]) + "sin(x) + " + String.format("%.3f", coeff[0]), 2);
                approximatedFunction.setFunctionForIntegration((x, coef) -> coef[1] * Math.sin(x) + coef[0]);
            }
            case 4 -> {
                approximatedFunction = new ApproximatedFunction(String.format("%.3f", coeff[1]) + "e^x + " + String.format("%.3f", coeff[0]), 2);
                approximatedFunction.setFunctionForIntegration((x, coef) -> coef[1] * Math.pow(Math.E, x) + coef[0]);
            }
            case 5 -> {
                approximatedFunction = new ApproximatedFunction(String.format("%.3f", coeff[1]) + "cos(x) + " + String.format("%.3f", coeff[0]), 2);
                approximatedFunction.setFunctionForIntegration((x, coef) -> coef[1] * Math.cos(x) + coef[0]);
            }
            default -> {
            }
        }
        return approximatedFunction;
    }

    private void removeLargestDeviation(DiscreteFunction discreteFunction, ApproximatedFunction approximatedFunction) {
        double[] xArray = discreteFunction.getXArray();
        double[] yArray = discreteFunction.getYArray();
        double[] newXArray = new double[xArray.length - 1];
        double[] newYArray = new double[xArray.length - 1];
        double maxDeviation = 0;
        int indexOfMaxDeviation = 0;
        for (int i = 0; i < discreteFunction.getNumberOfPoints(); i++) {
            if (Math.abs(yArray[i] - approximatedFunction.calculate(xArray[i])) > maxDeviation) {
                maxDeviation = Math.abs(yArray[i] - approximatedFunction.calculate(xArray[i]));
                indexOfMaxDeviation = i;
            }
        }
        int j = 0;
        for (int i = 0; i < xArray.length - 1; i++) {
            if (i != indexOfMaxDeviation) {
                newXArray[i] = xArray[j];
                newYArray[i] = yArray[j];
                j++;
            } else {
                j++;
                newXArray[i] = xArray[j];
                newYArray[i] = yArray[j];
            }
        }
        discreteFunction.setNumberOfPoints(discreteFunction.getNumberOfPoints() - 1);
        discreteFunction.setXArray(newXArray);
        discreteFunction.setYArray(newYArray);
    }

    private void printArray(double[] array) {
        for (double v : array) {
            System.out.print(v + " ");
        }
    }
}