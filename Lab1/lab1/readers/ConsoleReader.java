package lab1.readers;

import lab1.data.Matrix;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class ConsoleReader {
    Scanner scanner;

    public Matrix readUserInput() {
        int numberOfEquations = readNumberOfEquations();
        printInformation();
        return readMatrix(numberOfEquations);
    }

    public int readNumberOfEquations() throws InputMismatchException {
        int numberOfEquations = 0;
        System.out.print("Введите количество неизвестных в уравнении большее 0 и не превышающее 20: ");
        if (scanner.hasNext()) {
            while (true) {
                try {
                    numberOfEquations = scanner.nextInt();
                    if (isCorrectAmount(numberOfEquations)) break;
                    print("Введите целое число больше 0 и меньше или равное 20: ");
                } catch (InputMismatchException e) {
                    print("Неверный формат ввода, введите целое число больше 0 и меньше или равное 20: ");
                    scanner.next();
                }
            }
        }
        return numberOfEquations;
    }

    private Matrix readMatrix(int matrixSize) {
        Matrix matrix = new Matrix(matrixSize);
        scanner.nextLine();
        for (int i = 0; i < matrixSize; i++) {
            matrix.setRow(i, readRow(matrixSize, i));
        }
        return matrix;
    }

    private ArrayList<Double> readRow(int matrixSize, int i) {
        ArrayList<Double> row = new ArrayList<>();
        boolean isWrongRow = true;
        while (isWrongRow) {
            print((i + 1) + ": ");
            try {
                String[] matrixRow;
                matrixRow = scanner.nextLine().trim().replace(",", ".").split(" ");
                if (matrixRow.length == matrixSize + 1) {
                    isWrongRow = addElements(matrixRow, row, i);
                } else {
                    println("Неверное количество элементов в строке. Введите " + (matrixSize + 1) + " элементов в формате: a" + i + "1 a" + i + "2 ... a" + i + "n b" + i);
                }
            } catch (NoSuchElementException e) {
                println("Неверный формат ввода. Введите " + (matrixSize + 1) + " элементов в формате: a" + i + "1 a" + i + "2 ... a" + i + "n b" + i);
                isWrongRow = true;
            }
        }
        return row;
    }

    private boolean addElements(String[] matrixRow, ArrayList<Double> row, int i) {
        boolean isWrongRow = true;
        try {
            for (String element : matrixRow) {

                double value = Double.parseDouble(element);
                row.add(value);
                isWrongRow = false;
            }
        } catch (NumberFormatException e) {
            println("Неверный формат данных. Введите числа в формате:  a" + i + "1 a" + i + "2 ... a" + i + "n b" + i);
            isWrongRow = true;
            row.clear();
        }
        return isWrongRow;
    }

    private boolean isCorrectAmount(int numberOfEquations) {
        return 1 <= numberOfEquations && numberOfEquations <= 21;
    }

    private void printInformation() {
        println("Введите коэффициенты уравнений построчно через пробел.");
        println("Пример ввода:");
        println("a11 a12 a13 ... a1n b1");
        println("a21 a22 a23 ... a2n b2");
        println("......................");
        println("an1 an2 an3 ... ann bn");
    }

    private void print(String s) {
        System.out.print(s);
    }

    private void println(String s) {
        System.out.println(s);
    }

}
