package lab2.data;

import lab2.function.SystemFunction;
import lombok.Getter;
import lombok.Setter;

public class SystemOfEquations {
    private final SystemFunction[] functions;
    @Getter
    @Setter
    private double[][] coefficients;
    @Getter
    private final int[] numberOfCoefficients;
    @Getter
    private final String[] system;

    public SystemOfEquations(SystemFunction function1, SystemFunction function2,
                             int[] numberOfCoefficients, String[] system) {
        functions = new SystemFunction[]{function1, function2};
        this.numberOfCoefficients = numberOfCoefficients;
        this.system = system;
    }

    public double calculateFirstEquation(double x, double y) {
        return functions[0].calculate(x, y, coefficients[0]);
    }

    public double calculateSecondEquation(double x, double y) {
        return functions[1].calculate(x, y, coefficients[1]);
    }

}
