package lab2;

import lab2.algorithms.BisectionMethod;
import lab2.algorithms.NewtonMethod;
import lab2.algorithms.SimpleIterationMethod;
import lab2.data.Answer;
import lab2.data.AnswerFromSystem;
import lab2.data.Equation;
import lab2.data.SystemOfEquations;
import lab2.exceptions.NotConvergeException;


public class Main {
    public static Equation[] equations = new Equation[] {
            new Equation("ax^3 + bx^2 + cx + d = 0", 4),
            new Equation("ax^3 + b * e^{-x} = 0", 2),
            new Equation("a * sin(x) + b * x + c", 3)
    };

    public static SystemOfEquations[] systems = new SystemOfEquations[] {
            new SystemOfEquations((x, y, coef) -> coef[0] * x + coef[1] * y + coef[2],
                    (x, y, coef) -> coef[0] * x * x + coef[1] * y * y + coef[2],
                    new int[]{3, 3}, new String[] {"a_1 * x + b_1 * y + c_1 = 0", "a_2 * x^2 + b_2 * y^2 + c_2 = 0"}),
            new SystemOfEquations((x, y, coef) -> coef[0] * x + coef[1] * Math.log10(x) + coef[2] * y * y + coef[3],
                    (x, y, coef) -> coef[0] * x * x + coef[1] * x * y + coef[2] * x + coef[3],
                    new int[]{4, 4}, new String[] {"a_1 * x + b_1 * lg(x) + c_1 * y^2 + d_1 = 0", "a_2 * x^2 + b_2 * x * y + c_2 * x + d_2 = 0"}),
    };

    public static void main(String[] args) {
        new Main().run();
    }
    private void run() {
        equations[0].setFunctionForBisection((x, coef) -> coef[0] * Math.pow(x, 3) + coef[1] * Math.pow(x, 2) + coef[2] * x + coef[3]);
        equations[1].setFunctionForBisection((x, coef) -> coef[0] * Math.pow(x, 3) + coef[1] * Math.pow(Math.E, -x));
        equations[2].setFunctionForBisection((x, coef) -> coef[0] * Math.sin(x) + coef[1] * x + coef[2]);
        UserIO userIO = new UserIO();
        userIO.printWelcomeInformation();
        int format = userIO.readInputFormat();
        if (format == FormatOfInput.EQUATION.getFormat()) {
            userIO.printInfoForEquation();
            int type = userIO.readType();
            double[] coefficients = userIO.readCoefficients(type, " ", false, 0);
            equations[type].setCoefficients(coefficients);
            double[] boundaries = userIO.readBoundaries();
            double error = userIO.readError();
            double[] approximation = userIO.readApproximations(format);
            solveTheEquation(type, boundaries, error, approximation[0]);
        }

        if (format == FormatOfInput.SYSTEM.getFormat()) {
            userIO.printInfoForSystem();
            int type = userIO.readType();
            double[][] coefficients = new double[][]{userIO.readCoefficients(type, " первого ", true, 0),
                    userIO.readCoefficients(type, " второго ", true, 1)};
            systems[type].setCoefficients(coefficients);
            double[] approximations = userIO.readApproximations(format);
            double error = userIO.readError();
            solveTheSystem(type, approximations, error);
        }

        if (format == FormatOfInput.EXIT.getFormat()) {
            System.exit(0);
        }

    }
    private void solveTheEquation(int type, double[] boundaries, double error, double approximation) {
        double leftBoundary = boundaries[0];
        double rightBoundary = boundaries[1];
        EquationsHandler equationsHandler = new EquationsHandler(leftBoundary, rightBoundary);
        try {
            equationsHandler.prepareForSimpleIteration(type);
        } catch (NotConvergeException e) {
            System.out.println("------- Метод простой итерации -------");
            System.out.println(e.getMessage());
            System.out.println("Решить уравнение методом простой итерации невозможно.");
            BisectionMethod bisectionMethod = new BisectionMethod();
            System.out.println("------- Метод деления попалам -------");
            bisectionMethod.solveTheEquation(equations[type], error, leftBoundary, rightBoundary).printAnswer();
            System.exit(0);
        }
        System.out.println("------- Метод простой итерации -------");
        SimpleIterationMethod simpleIterationMethod = new SimpleIterationMethod();
        Answer answerFromIterationMethod = simpleIterationMethod.solveTheEquation(equations[type], error, leftBoundary, rightBoundary, approximation);
        answerFromIterationMethod.printAnswer();
        System.out.println("------- Метод деления попалам -------");
        BisectionMethod bisectionMethod = new BisectionMethod();
        Answer answerFromBisectionMethod = bisectionMethod.solveTheEquation(equations[type], error, leftBoundary, rightBoundary);
        answerFromBisectionMethod.printAnswer();
        if (answerFromBisectionMethod.isCorrect() && answerFromIterationMethod.isCorrect())
            System.out.println("\nРазница между методами: " + Math.abs(answerFromIterationMethod.getAnswer() - answerFromBisectionMethod.getAnswer()));

    }

    private void solveTheSystem(int type, double[] approximations, double error) {
        NewtonMethod newtonMethod = new NewtonMethod();
        try {
            AnswerFromSystem  answerFromSystem = newtonMethod.solveTheSystem(systems[type], approximations, error);
            answerFromSystem.printAnswer();
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}