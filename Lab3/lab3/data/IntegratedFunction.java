package lab3.data;

import lab3.function.Function;
import lombok.Getter;
import lombok.Setter;

public class IntegratedFunction {
    @Setter
    private Function functionForIntegration;
    @Getter @Setter
    private double[] coefficients;
    @Getter
    private final int numberOfCoefficients;
    @Getter
    private final String equation;


    public IntegratedFunction(String equation, int numberOfCoefficients) {
        this.equation = equation;
        this.numberOfCoefficients = numberOfCoefficients;
    }
    public double calculate(double x) {
        return functionForIntegration.calculate(x, coefficients);
    }

}
