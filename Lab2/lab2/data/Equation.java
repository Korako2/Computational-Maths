package lab2.data;

import lab2.function.EquationFunction;
import lab2.function.EquationFunctionForDerivative;
import lab2.function.EquationFunctionForIteration;
import lombok.Getter;
import lombok.Setter;

public class Equation {
    @Setter
    private EquationFunction functionForBisection;
    @Setter
    private EquationFunctionForIteration functionForIterationMethod;
    @Setter
    private EquationFunctionForDerivative functionForDerivative;
    @Getter @Setter
    private double[] coefficients;
    @Getter
    private final int numberOfCoefficients;
    @Getter
    private final String equation;


    public Equation(String equation, int numberOfCoefficients) {
        this.equation = equation;
        this.numberOfCoefficients = numberOfCoefficients;
    }
    public double calculateForBisection(double x) {
        return functionForBisection.calculateForBisection(x, coefficients);
    }

    public double calculateForIterationMethod(double x) {
        return functionForIterationMethod.calculateForIterationMethod(x);
    }

    public double calculateTheDerivative(double x) {
        return functionForDerivative.calculateTheDerivative(x);
    }
}
