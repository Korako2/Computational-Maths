package lab2.algorithms;
import lab2.data.Answer;
import lab2.data.Equation;

public class SimpleIterationMethod {
    public Answer solveTheEquation(Equation equation, double error, double leftBoundary, double rightBoundary, double approximation) {
        double currentError;
        int MAX_ITERATIONS = 250;
        int numberOfIterations = 0;
        double oldX = approximation;
        double x;
        String message = "Все отлично!";
        boolean isCorrect = true;
        do {
            x = equation.calculateForIterationMethod(oldX);
            currentError = Math.abs(x - oldX);
            numberOfIterations++;
            oldX = x;
            if (leftBoundary > x || x > rightBoundary) {
                message = "Выход за пределы диапазона";
                isCorrect = false;
                break;
            }
            if (numberOfIterations == MAX_ITERATIONS) {
                message = "Достигнут лимит на количество итераций.";
                isCorrect = false;
                break;
            }
        } while (currentError > error);
        if (Double.isNaN(x)) {
            message = "К сожалению, не удалось вычистить корень.";
            isCorrect = false;
        }
        return new Answer(x, currentError, numberOfIterations, message, isCorrect);
    }
}

