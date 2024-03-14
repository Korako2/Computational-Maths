package lab1.algorithms;

import lab1.data.Answer;
import lab1.data.Matrix;
import lab1.exceptions.DiagonalDominanceException;

import java.util.ArrayList;

public class GaussSeidelMethod {
    public Answer solveTheSystem(Matrix matrix, double error) throws ArithmeticException, DiagonalDominanceException {
        ArrayList<ArrayList<Double>> matrixCopy = (ArrayList<ArrayList<Double>>) matrix.getMatrix().clone();
        matrixCopy = leadToDiagonalDominance(matrixCopy);
        if (!isDiagonalDominance(matrixCopy))
            throw new DiagonalDominanceException("Матрицу невозможно привести к диагональному преобладанию.");
        prepareForIterations(matrixCopy);
        double[] approximations = new double[matrixCopy.size()];
        double currentError = Double.MAX_VALUE;
        double[] oldApproximations = new double[matrixCopy.size()];
        double[] errors = new double[matrixCopy.size()];
        int numberOfIterations = 0;
        while (currentError > error) {
            for (int i = 0; i < matrixCopy.size(); i++) {
                approximations[i] = matrixCopy.get(i).get(matrixCopy.size());
                for (int j = 0; j < matrixCopy.size(); j++) {
                    if (i != j) {
                        approximations[i] += approximations[j] * matrixCopy.get(i).get(j);
                    }
                }
            }
            currentError = updateError(matrixCopy, approximations, oldApproximations, errors, currentError);
            numberOfIterations++;
        }
        return new Answer(approximations, errors, matrixCopy.size(), numberOfIterations);
    }

    private void prepareForIterations(ArrayList<ArrayList<Double>> matrix) {
        divByTheDiagElement(matrix);
        transferringElements(matrix);
    }

    private void divByTheDiagElement(ArrayList<ArrayList<Double>> matrix) {
        double diagonalElement;
        for (int i = 0; i < matrix.size(); i++) {
            diagonalElement = matrix.get(i).get(i);
            for (int j = 0; j < matrix.size() + 1; j++) {
                matrix.get(i).set(j, matrix.get(i).get(j) / diagonalElement);
            }
        }
    }

    private void transferringElements(ArrayList<ArrayList<Double>> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                if (j != i) {
                    matrix.get(i).set(j, matrix.get(i).get(j) * (-1));
                }
            }
        }
    }

    private double updateError(ArrayList<ArrayList<Double>> matrixCopy, double[] approximations, double[] oldApproximations,
                               double[] errors, double currentError) {
        double maxError = 0;
        for (int i = 0; i < matrixCopy.size(); i++) {
            double err = Math.abs(approximations[i] - oldApproximations[i]);
            errors[i] = err;
            if (err > maxError) {
                maxError = err;
            }
        }
        System.arraycopy(approximations, 0, oldApproximations, 0, matrixCopy.size());
        if (maxError < currentError) currentError = maxError;
        return currentError;
    }

    private ArrayList<ArrayList<Double>> leadToDiagonalDominance(ArrayList<ArrayList<Double>> matrixCopy) throws DiagonalDominanceException {
        int matrixSize = matrixCopy.size();
        boolean[] swaps = new boolean[matrixCopy.size()];
        ArrayList<ArrayList<Double>> newMatrix = (ArrayList<ArrayList<Double>>) matrixCopy.clone();
        for (int i = 0; i < matrixSize; i++) {
            double max = 0;
            byte indexOfMax = 0;
            for (byte j = 0; j < matrixSize; j++) {
                if (Math.abs(matrixCopy.get(i).get(j)) > Math.abs(max)) {
                    max = matrixCopy.get(i).get(j);
                    indexOfMax = j;
                }
            }
            if (!swaps[indexOfMax]) {
                newMatrix.set(indexOfMax, matrixCopy.get(i));
                swaps[indexOfMax] = true;
            } else throw new DiagonalDominanceException("Матрицу невозможно привести к диагональному преобладанию.");
        }
        return newMatrix;
    }

    private boolean isDiagonalDominance(ArrayList<ArrayList<Double>> matrixCopy) {
        double sumOfRow;
        double diagonalElement;
        boolean isDiagonalDominance = true;
        boolean flag = false;
        int matrixSize = matrixCopy.size();
        for (int i = 0; i < matrixSize; i++) {
            sumOfRow = 0;
            for (int j = 0; j < matrixSize; j++) {
                if (i != j) sumOfRow += Math.abs(matrixCopy.get(i).get(j));
            }
            diagonalElement = matrixCopy.get(i).get(i);
            if (Math.abs(diagonalElement) < sumOfRow) isDiagonalDominance = false;
            if (Math.abs(diagonalElement) > sumOfRow) flag = true;
        }
        return isDiagonalDominance && flag;
    }

}
