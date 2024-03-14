package lab5.algorithms;

import lab5.data.AnswerFromKramerMethod;
import lab5.data.DiscreteFunction;
import lab5.data.ApproximationTypeFunc;
import lab5.data.Matrix;
import lab5.exceptions.WrongNumberOfCoefficients;


import java.util.ArrayList;

public class LeastSquaresMethod {

    private final ApproximationTypeFunc approximationTypeFunc;
    public LeastSquaresMethod(ApproximationTypeFunc function) {
        approximationTypeFunc = function;
    }
    public double[] findCoefficients(DiscreteFunction function) throws WrongNumberOfCoefficients{
        int numberOfCoefficients = function.getNumberOfCoefficients();
        if (numberOfCoefficients < 2 || numberOfCoefficients > 3)
            throw new WrongNumberOfCoefficients("К сожалению, пока что данный метод не умеет решать уравнения с таким количеством неизвестных");
        Double[] sumsOfX = new Double[5];
        Double[] sumsOfFreeTerms = new Double[numberOfCoefficients];
        Matrix matrix = new Matrix(numberOfCoefficients);
        calculateSumOfX(function, sumsOfX);
        calculateSumOfFreeTerms(function, sumsOfFreeTerms);

        if (numberOfCoefficients == 2) {
            matrix.setRow(0, new ArrayList<>(){{add(sumsOfX[0]); add(sumsOfX[1]); add(sumsOfFreeTerms[0]);}});
            matrix.setRow(1, new ArrayList<>(){{add(sumsOfX[1]); add(sumsOfX[2]); add(sumsOfFreeTerms[1]);}});
        } else if (numberOfCoefficients == 3) {
            matrix.setRow(0, new ArrayList<>(){{add(sumsOfX[0]); add(sumsOfX[1]); add(sumsOfX[2]); add(sumsOfFreeTerms[0]);}});
            matrix.setRow(1, new ArrayList<>(){{add(sumsOfX[1]); add(sumsOfX[2]); add(sumsOfX[3]); add(sumsOfFreeTerms[1]);}});
            matrix.setRow(2, new ArrayList<>(){{add(sumsOfX[2]); add(sumsOfX[3]); add(sumsOfX[4]); add(sumsOfFreeTerms[2]);}});
        }

        KramerMethod kramerMethod = new KramerMethod();
        AnswerFromKramerMethod answer = kramerMethod.solveTheSystem(matrix);
        return answer.getApproximations();
    }
    private void calculateSumOfX(DiscreteFunction function, Double[] sumsOfX) {
        int numberOfCoefficients = function.getNumberOfCoefficients();
            for (int i = 0; i < 5; i++) {
                sumsOfX[i] = 0.0;
                for (int j = 0; j < function.getNumberOfPoints(); j++) {
                    sumsOfX[i] += Math.pow(approximationTypeFunc.calculate(function.getXArray()[j]), i);
                }
                if (numberOfCoefficients == 2 && i == 2) break;
            }
    }
    private void calculateSumOfFreeTerms(DiscreteFunction function, Double[] sumsOfFreeTerms) {
        int numberOfCoefficients = function.getNumberOfCoefficients();
        for (int i = 0; i < numberOfCoefficients; i++) {
            sumsOfFreeTerms[i] = 0.0;
            for (int j = 0; j < function.getNumberOfPoints(); j++) {
                sumsOfFreeTerms[i] += Math.pow(approximationTypeFunc.calculate(function.getXArray()[j]), i) * function.getYArray()[j];
            }
        }
    }
}

