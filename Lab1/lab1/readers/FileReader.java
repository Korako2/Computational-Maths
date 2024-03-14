package lab1.readers;

import lab1.data.Matrix;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

@AllArgsConstructor
public class FileReader {
    private Scanner scanner;

    public Matrix readMatrixFromFile(int size) {
        Matrix matrix = new Matrix(size);
        boolean isCorrectData = true;
        scanner.nextLine();
        for (int i = 0; i < size; i++) {
            matrix.setRow(i, new ArrayList<>());
            try {
                String[] matrixRow;
                matrixRow = scanner.nextLine().trim().replace(",", ".").split(" ");
                if (matrixRow.length != size + 1) {
                    System.out.println("Количество коэффициентов в строке " + (i + 1) +
                            " некорректно, исправьте данные в файле");
                    isCorrectData = false;
                    break;
                }
                for (String number : matrixRow) {
                    matrix.getMatrix().get(i).add(Double.parseDouble(number));
                }
            } catch (NoSuchElementException e) {
                System.out.println("Неверный формат данных! Исправьте данные в файле");
                isCorrectData = false;
                break;
            }
        }
        if (isCorrectData && size != 0) {
            matrix.printMatrix();
            return matrix;
        }
        return null;
    }
}
