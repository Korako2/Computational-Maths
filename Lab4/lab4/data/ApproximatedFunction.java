package lab4.data;

import lab4.function.Function;
import lombok.Getter;
import lombok.Setter;

public class ApproximatedFunction {
    @Setter
    private Function functionForIntegration;
    @Getter @Setter
    private double[] coefficients;
    @Getter
    private final int numberOfCoefficients;
    @Getter
    private final String equation;


    public ApproximatedFunction(String equation, int numberOfCoefficients) {
        this.equation = equation;
        this.numberOfCoefficients = numberOfCoefficients;
    }
    public double calculate(double x) {
        return functionForIntegration.calculate(x, coefficients);
    }

}
