package lab5.algorithms;

import lab5.data.AnswerFromEulerMethod;
import lab5.data.DifferentialEquation;

public class EulerMethod {
    public AnswerFromEulerMethod solveEquation(DifferentialEquation differentialEquation, double x0, double y0, double h, double lastX) {
        int numberOfSteps = (int) ((lastX - x0) / h);
        double[] xArray = new double[numberOfSteps + 1];
        double[] yArray = new double[numberOfSteps + 1];
        xArray[0] = x0;
        yArray[0] = y0;
        double x = x0;
        double y = y0;
        for (int i = 0; i < numberOfSteps; i++) {
            y = y + h * differentialEquation.calculateFunction(x, y);
            x += h;
            xArray[i + 1] = x;
            yArray[i + 1] = y;
        }

        return new AnswerFromEulerMethod(xArray, yArray);
    }
}

