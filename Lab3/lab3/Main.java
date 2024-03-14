package lab3;

import lab3.algorithms.TrapezoidalMethod;
import lab3.data.Answer;
import lab3.data.IntegratedFunction;


public class Main {
    public static IntegratedFunction[] integratedFunctions = new IntegratedFunction[] {
            new IntegratedFunction("f(x) = ax^3 + bx^2 + cx + d", 4),
            new IntegratedFunction("f(x) = ax^3 + b * e^{-x} = 0", 2),
            new IntegratedFunction("f(x) = a * sin(x) + b * x + c", 3),
            new IntegratedFunction("f(x) = (ax^2 + b) / (cx + d)", 4),
            new IntegratedFunction("f(x) = ax * e ^ {-x}", 1)
    };

    public static void main(String[] args) {
        new Main().run();
    }
    private void run() {
        integratedFunctions[0].setFunctionForIntegration((x, coef) -> coef[0] * Math.pow(x, 3) + coef[1] * Math.pow(x, 2) + coef[2] * x + coef[3]);
        integratedFunctions[1].setFunctionForIntegration((x, coef) -> coef[0] * Math.pow(x, 3) + coef[1] * Math.pow(Math.E, -x));
        integratedFunctions[2].setFunctionForIntegration((x, coef) -> coef[0] * Math.sin(x) + coef[1] * x + coef[2]);
        integratedFunctions[3].setFunctionForIntegration((x, coef) -> (coef[0] * x * x + coef[1]) / (coef[2] * x + coef[3]));
        integratedFunctions[4].setFunctionForIntegration((x, coef) -> (coef[0] * x * Math.pow(Math.E, -x)));
        UserIO userIO = new UserIO();
        userIO.printWelcomeInformation();
        userIO.printInfoForEquation();
        int type = userIO.readType();
        double[] coefficients = userIO.readCoefficients(type, " ", false, 0);
        integratedFunctions[type].setCoefficients(coefficients);
        double[] boundaries = userIO.readBoundaries();
        double error = userIO.readError();
        solveTheEquation(type, boundaries, error);


    }
    private void solveTheEquation(int type, double[] boundaries, double error) {
        double leftBoundary = boundaries[0];
        double rightBoundary = boundaries[1];
        TrapezoidalMethod trapezoidalMethod = new TrapezoidalMethod();
        Answer answer = trapezoidalMethod.calculateTheIntegral(integratedFunctions[type], leftBoundary, rightBoundary, error);
        answer.printAnswer();
    }

}