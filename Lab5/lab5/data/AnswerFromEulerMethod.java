package lab5.data;

import lombok.Getter;

public class AnswerFromEulerMethod {
    @Getter
    private final double[] xArray;
    @Getter
    private final double[] yArray;
    public AnswerFromEulerMethod(double[] xArray, double[] yArray) {
        this.xArray = xArray;
        this.yArray = yArray;
    }

}
