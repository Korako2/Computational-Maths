package lab4.algorithms;

import lab4.data.AnswerFromKramerMethod;
import lab4.data.Matrix;


public class KramerMethod {
    public AnswerFromKramerMethod solveTheSystem(Matrix matrix)  {
        double det;
        double[] approximations = new double[matrix.getMatrixSize()];
        if (matrix.getMatrixSize() == 1) {
            det = matrix.getElement(0,0);
            approximations[0] = matrix.getElement(0, 1) / det;
        }
        if (matrix.getMatrixSize() == 2) {
            det = matrix.getElement(0, 0) * matrix.getElement(1, 1) - matrix.getElement(1, 0 ) * matrix.getElement(0, 1);
            approximations[0] = (matrix.getElement(0, 2) * matrix.getElement(1, 1) - matrix.getElement(1, 2) * matrix.getElement(0, 1)) / det;
            approximations[1] = (matrix.getElement(0, 0) * matrix.getElement(1, 2) - matrix.getElement(1, 0) * matrix.getElement(0, 2)) / det;
        }
        if (matrix.getMatrixSize() == 3) {
            det = matrix.getElement(0, 0) * matrix.getElement(1, 1) * matrix.getElement(2, 2) +
                    matrix.getElement(0, 1) * matrix.getElement(1, 2) * matrix.getElement(2, 0) +
                    matrix.getElement(0, 2) * matrix.getElement(1, 0) * matrix.getElement(2, 1) -
                    matrix.getElement(2, 0) * matrix.getElement(1, 1) * matrix.getElement(0,2 ) -
                    matrix.getElement(0, 0) * matrix.getElement(1, 2) * matrix.getElement(2, 1) -
                    matrix.getElement(0, 1) * matrix.getElement(1, 0) * matrix.getElement(2, 2);
            approximations[0] = (matrix.getElement(0, 3) * matrix.getElement(1, 1) * matrix.getElement(2, 2) +
                    matrix.getElement(0, 1) * matrix.getElement(1, 2) * matrix.getElement(2, 3) +
                    matrix.getElement(0, 2) * matrix.getElement(1, 3) * matrix.getElement(2, 1) -
                    matrix.getElement(2, 3) * matrix.getElement(1, 1) * matrix.getElement(0,2 ) -
                    matrix.getElement(0, 3) * matrix.getElement(1, 2) * matrix.getElement(2, 1) -
                    matrix.getElement(0, 1) * matrix.getElement(1, 3) * matrix.getElement(2, 2)) / det;
            approximations[1] = (matrix.getElement(0, 0) * matrix.getElement(1, 3) * matrix.getElement(2, 2) +
                    matrix.getElement(0, 3) * matrix.getElement(1, 2) * matrix.getElement(2, 0) +
                    matrix.getElement(0, 2) * matrix.getElement(1, 0) * matrix.getElement(2, 3) -
                    matrix.getElement(2, 0) * matrix.getElement(1, 3) * matrix.getElement(0,2 ) -
                    matrix.getElement(0, 0) * matrix.getElement(1, 2) * matrix.getElement(2, 3) -
                    matrix.getElement(0, 3) * matrix.getElement(1, 0) * matrix.getElement(2, 2)) / det;
            approximations[2] = (matrix.getElement(0, 0) * matrix.getElement(1, 1) * matrix.getElement(2, 3) +
                    matrix.getElement(0, 1) * matrix.getElement(1, 3) * matrix.getElement(2, 0) +
                    matrix.getElement(0, 3) * matrix.getElement(1, 0) * matrix.getElement(2, 1) -
                    matrix.getElement(2, 0) * matrix.getElement(1, 1) * matrix.getElement(0,3) -
                    matrix.getElement(0, 0) * matrix.getElement(1, 3) * matrix.getElement(2, 1) -
                    matrix.getElement(0, 1) * matrix.getElement(1, 0) * matrix.getElement(2, 3)) / det;
        }
        return new AnswerFromKramerMethod(approximations, matrix.getMatrixSize());
    }



}
