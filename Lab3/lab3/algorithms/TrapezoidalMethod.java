package lab3.algorithms;

import lab3.data.Answer;
import lab3.data.IntegratedFunction;

public class TrapezoidalMethod {
    public Answer calculateTheIntegral(IntegratedFunction integratedFunction, double leftBoundary, double rightBoundary,
                                       double error) {
        double oldSum = 0;
        double currentError = 0;
        int numberOfIterations = 0;
        long numberOfSegments = Math.round((rightBoundary - leftBoundary) / 0.5);
        double integrationStep;
        double eps = 0.000000001;
        String errorMessage = "Все отлично!";
        boolean isCorrect = true;
        do {
            double x = leftBoundary;
            double currentSum = 0;
            integrationStep = (rightBoundary - leftBoundary) / numberOfSegments;
            numberOfIterations++;
            double y;
            for (int i = 0; i < numberOfSegments - 1; i++) {
                x += integrationStep;
                y = integratedFunction.calculate(x);
                if (Double.isNaN(y))
                    currentSum += (integratedFunction.calculate(x - eps) + integratedFunction.calculate(x + eps)) / 2;
                else currentSum += y;
            }
            currentSum += addOnBoundaries(integratedFunction, leftBoundary, rightBoundary, eps);
            if (Double.isInfinite(currentSum) || Double.isNaN(currentSum)) {
                errorMessage = "Посчитать интеграл не удалось :(";
                isCorrect = false;
                break;
            }
            currentSum *= integrationStep;
            currentError = Math.abs(currentSum - oldSum);
            oldSum = currentSum;
            numberOfSegments = numberOfSegments * 2;
        } while (currentError > error);

        return new Answer(oldSum, currentError, numberOfIterations, errorMessage, isCorrect);
    }

    private double addOnBoundaries(IntegratedFunction integratedFunction, double leftBoundary,
                                   double rightBoundary, double eps) {
        double sum = 0;
        if (Double.isNaN(integratedFunction.calculate(leftBoundary)))
            sum += integratedFunction.calculate(leftBoundary + eps) / 2;
        else sum += integratedFunction.calculate(leftBoundary) / 2;
        if (Double.isNaN(integratedFunction.calculate(rightBoundary)))
            sum += integratedFunction.calculate(rightBoundary - eps) / 2;
        else sum += integratedFunction.calculate(rightBoundary) / 2;
        return sum;

    }

}
