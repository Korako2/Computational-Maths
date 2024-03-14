package lab2.algorithms;

import lab2.data.AnswerFromSystem;
import lab2.data.SystemOfEquations;


public class NewtonMethod {
    public AnswerFromSystem solveTheSystem(SystemOfEquations system, double[] approximations, double error) throws ArithmeticException {
        int numberOfIterations = 0;
        int MAX_ITERATIONS = 200;
        double x;
        double y;
        double oldX = approximations[0];
        double oldY = approximations[1];
        String message = "Все отлично!";
        boolean isCorrect = true;

        do {
            x = oldX;
            y = oldY;
            numberOfIterations++;
            double[] coefficients = calcCoefficients(x, y, system);
            oldX = x + coefficients[0];
            oldY = y + coefficients[1];

            if (numberOfIterations == MAX_ITERATIONS) {
                message = "Достигнут лимит на количество итераций.";
                isCorrect = false;
                break;
            }
        } while (isErrorsOverThreshold(x, y, oldX, oldY, error));

        if (Double.isNaN(oldX) || Double.isNaN(oldY)) {
            message = "К сожалению, не удалось вычистить корни системы";
            isCorrect = false;
        }

        return new AnswerFromSystem(oldX, oldY, oldX - x, oldY - y, numberOfIterations, message, isCorrect);
    }

    private double[] calcCoefficients(double x, double y, SystemOfEquations system) {
        double[] coefficients = new double[2];

        double[] derivativesX = calcDerivativeByX(system, x, y);
        double[] derivativesY = calcDerivativeByY(system, x, y);
        double func1 = system.calculateFirstEquation(x, y);
        double func2 = system.calculateSecondEquation(x, y);

        double det = derivativesY[1] * derivativesX[0] - derivativesX[1] * derivativesY[0];
        if (det != 0) {
            coefficients[0] = -(func1 * derivativesY[1] - func2 * derivativesY[0]) / det;
            coefficients[1] = -(func2 * derivativesX[0] - func1 * derivativesX[1]) / det;
        } else {
            throw new ArithmeticException("Определитель Якобиана равен 0. Решение невозможно.");
        }

        return coefficients;
    }

    private double[] calcDerivativeByX(SystemOfEquations system, double x, double y) {
        double h = 0.000001;
        double[] derivatives = new double[2];
        derivatives[0] = (system.calculateFirstEquation(x + h, y) - system.calculateFirstEquation(x, y)) / h;
        derivatives[1] = (system.calculateSecondEquation(x + h, y) - system.calculateSecondEquation(x, y)) / h;
        return derivatives;
    }

    private double[] calcDerivativeByY(SystemOfEquations system, double x, double y) {
        double h = 0.000001;
        double[] derivatives = new double[2];
        derivatives[0] = (system.calculateFirstEquation(x, y + h) - system.calculateFirstEquation(x, y)) / h;
        derivatives[1] = (system.calculateSecondEquation(x, y) - system.calculateSecondEquation(x, y + h)) / h;
        return derivatives;
    }

    private boolean isErrorsOverThreshold(double x, double y, double oldX, double oldY, double error) {
        return Math.abs(x - oldX) > error || Math.abs(y - oldY) > error;
    }
}
