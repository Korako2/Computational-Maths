package lab5;

import lab5.algorithms.EulerMethod;
import lab5.algorithms.LeastSquaresMethod;
import lab5.data.*;
import lab5.exceptions.WrongNumberOfCoefficients;


public class Main {
    public static DifferentialEquation[] differentialEquations = new DifferentialEquation[]{
            new DifferentialEquation("y' = ax + b", 2),
            new DifferentialEquation("y' = a/x", 1),
            new DifferentialEquation("y' = a * cos(x)", 1)
    };


    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        differentialEquations[0].setFunction((x, y, coef) -> coef[0] * x + coef[1]);
        differentialEquations[0].setSolutionOfDifferentialEquation((x, coef) -> (coef[0] / 2) * x * x + coef[1] * x + coef[2]);

        differentialEquations[1].setFunction((x, y, coef) -> coef[0] / x);
        differentialEquations[1].setSolutionOfDifferentialEquation((x, coef) -> coef[0] * Math.log(x) + coef[1]);

        differentialEquations[2].setFunction((x, y, coef) -> coef[0] *  Math.cos(x));
        differentialEquations[2].setSolutionOfDifferentialEquation((x, coef) -> coef[0] * Math.sin(x) + coef[1]);



        UserIO userIO = new UserIO();
        userIO.printWelcomeInformation();
        userIO.printInfoForEquation();
        int type = userIO.readType();
        double[] coefficients = userIO.readCoefficients(type, " ", 0);

        differentialEquations[type].setCoefficients(coefficients);
        differentialEquations[type].setCoefficientsForCalculatingC(coefficients);

        double x0 = userIO.readValue("Введите начальное значение x0:");
        double y0 = userIO.readValue("Введите начальное значение y0: ");
        double lastX = userIO.readValue("Введите границу решения дифференциального уравненения: ");
        double h = userIO.readValue("Введите шаг для метода от 0,01 до 1:");
        while (h > 1 || h < 0.01) {
            h = userIO.readValue("Введите шаг для метода от 0,01 до 1:");
        }
        double C = calculateFreeTerm(x0, y0, type);
        coefficients = copyOfCoefficients(coefficients, C);
        differentialEquations[type].setSolutionCoefficients(coefficients);
        setSolutionFunctionDescription(type, coefficients);

        EulerMethod eulerMethod = new EulerMethod();
        AnswerFromEulerMethod answer = eulerMethod.solveEquation(differentialEquations[type], x0, y0, h, lastX);
        DiscreteFunction discreteFunction = new DiscreteFunction(answer.getXArray(), answer.getYArray(), answer.getYArray().length, type, differentialEquations[type].getNumberOfCoefficients() + 1);

        ApproximationTypeFunc approximationTypeFunc = new ApproximationTypeFunc();
        switch (type) {
            case 0 -> approximationTypeFunc.setFunction((x) -> x);
            case 1 -> approximationTypeFunc.setFunction(Math::log);
            case 2 -> approximationTypeFunc.setFunction(Math::sin);
            default -> {
            }
        }
        LeastSquaresMethod leastSquaresMethod = new LeastSquaresMethod(approximationTypeFunc);
        double[] coeff = new double[0];
        try {
            coeff = leastSquaresMethod.findCoefficients(discreteFunction);
        } catch (WrongNumberOfCoefficients e) {
            System.out.println(e.getMessage());
        }

        ApproximatedFunction approximatedFunction = setApproximatedFunctions(coeff, type);
        approximatedFunction.setCoefficients(coeff);
        System.out.println("Аналитическое решение: " + differentialEquations[type].getSolutionFunctionDescription());
        System.out.println("Решение методом Эйлера: " + approximatedFunction.getEquation());
        GraphDraftsman.setFunction(differentialEquations[type], differentialEquations[type].getSolutionFunctionDescription());
        GraphDraftsman.setBounds(x0, lastX);
        GraphDraftsman.setApproximatedFunction(approximatedFunction, approximatedFunction.getEquation());
        new GraphDraftsman().build();
    }
    private void setSolutionFunctionDescription(int type, double[] coef) {
        switch (type) {
            case 0 ->
                    differentialEquations[0].setSolutionFunctionDescription("Аналитическое решение: " + String.format("%.3f", coef[0] / 2) + " x^2 + " + String.format("%.3f", coef[1]) + "x + " + String.format("%.3f", coef[2]));
            case 1 ->
                    differentialEquations[1].setSolutionFunctionDescription("Аналитическое решение: " + String.format("%.3f", coef[0]) + " ln(x) + " + String.format("%.3f", coef[1]));
            case 2 ->
                    differentialEquations[2].setSolutionFunctionDescription("Аналитическое решение: " + String.format("%.3f", coef[0]) + " sin(x) + " + String.format("%.3f", coef[1]));
            default -> {
            }
        }
    }
    private double[] copyOfCoefficients(double[] coefficients, double C) {
        double[] copiedCoefficients = new double[coefficients.length + 1];
        System.arraycopy(coefficients, 0, copiedCoefficients, 0, coefficients.length);
        copiedCoefficients[coefficients.length] = C;
        return copiedCoefficients;
    }

    private double calculateFreeTerm(double x0, double y0, int type) {
        double C = 0;
        switch (type) {
            case 0 -> {
                differentialEquations[0].setFunctionForCalculatingC((x, y, coef) -> y - ((coef[0] / 2) * x * x + coef[1] * x));
                C = differentialEquations[0].calculateC(x0, y0);
            }
            case 1 -> {
                differentialEquations[1].setFunctionForCalculatingC((x, y, coef) -> y - coef[0] * Math.log(x));
                C = differentialEquations[1].calculateC(x0, y0);
            }
            case 2 -> {
                differentialEquations[2].setFunctionForCalculatingC((x, y, coef) -> (y - coef[0] * Math.sin(x)));
                C = differentialEquations[2].calculateC(x0, y0);
            }
            default -> {
            }
        }
        return C;
    }

    private ApproximatedFunction setApproximatedFunctions(double[] coeff, int type) {
        ApproximatedFunction approximatedFunction = null;
        switch (type) {
            case 0 -> {
                approximatedFunction = new ApproximatedFunction("Метод Эйлера: "  + String.format("%.3f", coeff[2]) + " x^2 + " + String.format("%.3f", coeff[1]) + " x +" + String.format("%.3f", coeff[0]), 3);
                approximatedFunction.setFunctionForIntegration((x, coef) -> coef[2] * x * x + coef[1] * x + coef[0]);
            }
            case 1 -> {
                approximatedFunction = new ApproximatedFunction("Метод Эйлера: "  + String.format("%.3f", coeff[1]) + " ln(x) + " + String.format("%.3f", coeff[0]), 2);
                approximatedFunction.setFunctionForIntegration((x, coef) -> coef[1] * Math.log(x) + coef[0]);
            }
            case 2 -> {
                approximatedFunction = new ApproximatedFunction("Метод Эйлера: "  + String.format("%.3f", coeff[1]) + " sin(x) + " + String.format("%.3f", coeff[0]), 2);
                approximatedFunction.setFunctionForIntegration((x, coef) -> coef[1] * Math.sin(x) + coef[0]);
            }
            default -> {
            }
        }
        return approximatedFunction;
    }
    private void printArray(double[] array) {
        for (double v : array) {
            System.out.print(v + " ");
        }
    }
}