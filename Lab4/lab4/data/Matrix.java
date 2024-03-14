package lab4.data;

import lombok.Getter;

import java.util.ArrayList;

public class Matrix {
    @Getter
    private final ArrayList<ArrayList<Double>> matrix;
    @Getter
    private final int matrixSize;

    public Matrix(int matrixSize) {
        matrix = new ArrayList<>();
        this.matrixSize = matrixSize;
    }

    public void setRow(int i, ArrayList<Double> row) {
        matrix.add(i, row);
    }

    public void printMatrix() {
        for (ArrayList<Double> i : matrix) {
            for (Double j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    public double getElement(int i, int j) {
        return matrix.get(i).get(j);
    }


}
