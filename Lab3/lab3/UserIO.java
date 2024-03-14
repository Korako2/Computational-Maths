package lab3;

import lab3.data.IntegratedFunction;

import static lab3.Main.integratedFunctions;
import java.util.*;

public class UserIO {
    private Scanner scanner;

    public UserIO() {
        scanner = new Scanner(System.in);
    }

    public void printWelcomeInformation() {
        println("Численное интегрирование методом трапеций.");
        println("Если на интервале интегрирования будет устранимый разрыв первого рода, то для его устранения будет " +
                "использовано среднее от значений функции в двух соседних точках f(x-e), f(x+e)");
    }

    public double[] readBoundaries() {
        double[] boundaries = new double[2];
        println("Введите левую границу интервала интегрирования:");
        boundaries[0] = readValue();
        println("Введите правую границу интервала интегрирования:");
        boundaries[1] = readValue();
        while (Math.abs(boundaries[0] - boundaries[1]) > 10000) {
            println("Слишком большой интервал интегнирования.");
            println("Введите левую границу интервала интегрирования:");
            boundaries[0] = readValue();
            println("Введите правую границу интервала интегрирования:");
            boundaries[1] = readValue();
        }
        return boundaries;

    }


    public double readError() {
        println("Введите погрешность вычислений от 0,00001 до 1:");
        double error = 0;
        scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            while (true) {
                try {
                    error = scanner.nextDouble();
                    if (isCorrectError(error)) break;
                    println("Неверный формат ввода данных. Введите число от 0,00001 до 1");
                } catch (InputMismatchException e) {
                    println("Неверный формат ввода данных. Введите число от 0,00001 до 1 через запятую");
                    scanner.next();
                }
            }
        }
        return error;
    }

    public void printInfoForEquation() {
        println("Выберите тип уравнения, которое хотите решить");
        int number = 0;
        for (IntegratedFunction integratedFunction : integratedFunctions) {
            println(integratedFunction.getEquation() + " - введите " + number);
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

    public double[] readCoefficients(int type, String message, boolean isSystem, int numberOfEquation) {
        println("Введите коэффициенты"+ message +"уравнения через пробел в формате: a b c ...:" );
        int numberOfCoefficients = integratedFunctions[type].getNumberOfCoefficients();
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
        return (0 <= type && type < integratedFunctions.length);
    }
    private boolean isCorrectError(double error) {
        return (0.00001 <= error && error <= 1);
    }
    private void println(String s) {
        System.out.println(s);
    }


}
