package lab4;

import lab4.data.ApproximatedFunction;

import static lab4.Main.approximatedFunctions;
import java.util.*;

public class UserIO {
    private final Scanner scanner;

    public UserIO() {
        scanner = new Scanner(System.in);
    }

    public void printWelcomeInformation() {
        println("Аппроксимация методом наименьших квадратов");
    }

    public void printInfoForEquation() {
        println("Выберитите тип функции, которую вы хотите аппроксимировать");
        int number = 0;
        for (ApproximatedFunction approximatedFunction : approximatedFunctions) {
            println(approximatedFunction.getEquation() + " - введите " + number);
            number++;
        }
    }
    public int readType() {
        int type = 0;
        if (scanner.hasNext()) {
            while (true) {
                try {
                    type = scanner.nextInt();
                    if (isCorrectType(type)) break;
                    println("Введите целое число из предложенных выше: ");
                } catch (InputMismatchException e) {
                    println("Неверный формат ввода, введите целое число из предложенных выше:");
                    scanner.next();
                }
            }
        }
        return type;
    }

    public double[] readCoefficients(int type, String message, int numberOfEquation) {
        println("Введите коэффициенты"+ message +"уравнения через пробел в формате: a b c ...:" );
        int numberOfCoefficients = approximatedFunctions[type].getNumberOfCoefficients();
        double[] coefficients = new double[numberOfCoefficients];
        boolean isWrongRow = true;
        if (scanner.hasNext()) {
            if (numberOfEquation == 0) scanner.nextLine();
            while (isWrongRow) {
                try {
                    String[] row;
                    row = scanner.nextLine().trim().replace(",", ".").split(" ");
                    if (row.length == numberOfCoefficients) {
                        for (int i = 0; i < numberOfCoefficients; i++) {
                            coefficients[i] = Double.parseDouble(row[i]);
                        }
                        isWrongRow = false;
                    } else {
                        println("Неверное количество элементов в строке. Введите " + (numberOfCoefficients) + " коэффициентов.");
                    }
                } catch (NoSuchElementException e) {
                    println("Неверный формат ввода. Введите " + (numberOfCoefficients) + " коэффициентов.");
                    isWrongRow = true;
                } catch (NumberFormatException e) {
                    println("Неверный формат данных. Введите числа в формате: a b c ...");
                    isWrongRow = true;
                }
            }
        }
        return coefficients;
    }

    public double[] readXArray() {
        println("Введите количество точек: ");
        int amount = 0;
        if (scanner.hasNext()) {
            while (true) {
                try {
                    amount = scanner.nextInt();
                    if (isCorrectAmount(amount)) break;
                    println("Введите целое число.");
                } catch (InputMismatchException e) {
                    println("Неверный формат ввода, введите целое число.");
                    scanner.next();
                }
            }
        }
        double[] xArray = new double[amount];
        for (int i = 0; i < amount; i++) {
            println("Введите " + (i + 1) + " точку:");
            xArray[i] = readValue();
        }
        return xArray;
    }

    private double readValue() {
        double value = 1;
        boolean isCorrectValue;
        do {
            isCorrectValue = true;
            try {
                value = scanner.nextDouble();
            } catch (NoSuchElementException e) {
                println("Неверный формат данных, повторите ввод:");
                isCorrectValue = false;
            }
        } while (!isCorrectValue);

        return value;
    }

    private boolean isCorrectType(double type) {
        return (0 <= type && type < approximatedFunctions.length);
    }

    private boolean isCorrectAmount(double amount) { return (2 <= amount && amount <= 100); }
    private void println(String s) {
        System.out.println(s);
    }


}
