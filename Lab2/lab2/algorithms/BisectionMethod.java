package lab2.algorithms;

import lab2.data.Answer;
import lab2.data.Equation;

public class BisectionMethod {
    public Answer solveTheEquation(Equation equation, double error, double leftBoundary, double rightBoundary) {
        if (!isRootInsideTheSegment(equation, leftBoundary, rightBoundary))
            return new Answer(null, null, 0,
                    "Корень на отрезке отсутствует или корней на отрезке несколько", false);
        if (equation.calculateForBisection(leftBoundary) == 0)
            return new Answer(leftBoundary, 0.0d, 0, "Корень на границе.", true);
        if (equation.calculateForBisection(rightBoundary) == 0)
            return new Answer(rightBoundary, 0.0d, 0, "Корень на границе.", true);

        int MAX_ITERATIONS = 200;
        int numberOfIterations = 0;
        double middle = 0;
        double func;
        double currentError;
        String message = "Все отлично!";
        boolean isCorrect = true;

        do {
            middle = (rightBoundary + leftBoundary) / 2.0d;
            func = equation.calculateForBisection(middle);
            numberOfIterations++;
            currentError = rightBoundary - leftBoundary;

            if (func == 0.0d) {
                currentError = 0.0d;
                message = "Корень попал ровно на середину одного из отрезков";
                break;
            }

            if (numberOfIterations == MAX_ITERATIONS) {
                message = "Достигнут лимит на количество итераций.";
                isCorrect = false;
                break;
            }

            if (isRootInsideTheSegment(equation, leftBoundary, middle)) rightBoundary = middle;
            else leftBoundary = middle;

        } while (currentError > error);
        return new Answer(middle, currentError, numberOfIterations, message, isCorrect);
    }

    private boolean isRootInsideTheSegment(Equation equation, double leftBoundary, double rightBoundary) {
        return equation.calculateForBisection(leftBoundary) * equation.calculateForBisection(rightBoundary) < 0;
    }
}


