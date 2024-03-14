package lab4.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class DiscreteFunction {
    @Setter
    private double[] xArray;
    @Setter
    private double[] yArray;
    @Setter
    private int numberOfPoints;
    private int type;
    private final int numberOfCoefficients;

}
