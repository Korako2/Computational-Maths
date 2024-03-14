package lab5.data;

import lab5.function.Function;
import lab5.function.SolutionFunction;
import lombok.Getter;
import lombok.Setter;

public class DifferentialEquation {
    @Setter
    private Function function;
    @Setter @Getter
    private SolutionFunction solutionOfDifferentialEquation;
    @Setter Function functionForCalculatingC;
    @Getter @Setter
    private double[] coefficients;
    @Getter @Setter
    private double[] solutionCoefficients;
    @Getter @Setter
    private double[] coefficientsForCalculatingC;
    @Getter
    private final int numberOfCoefficients;
    @Getter
    private final String equation;
    @Setter @Getter
    private String solutionFunctionDescription;

    public DifferentialEquation(String equation, int numberOfCoefficients) {
        this.equation = equation;
        this.numberOfCoefficients = numberOfCoefficients;
    }
    public double calculateFunction(double x, double y) {
        return function.calculate(x, y, coefficients);
    }

    public double calculateSolutionFunction(double x) {
        return solutionOfDifferentialEquation.calculate(x, solutionCoefficients);
    }

    public double calculateC(double x, double y) {
        return functionForCalculatingC.calculate(x, y, coefficientsForCalculatingC);
    }
}
