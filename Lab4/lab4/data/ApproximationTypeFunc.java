package lab4.data;

import lab4.function.ApproximationFunction;
import lombok.Setter;

public class ApproximationTypeFunc {
    @Setter
    private ApproximationFunction function;

    public double calculate(double x) {
        return function.calculate(x);
    }

}
