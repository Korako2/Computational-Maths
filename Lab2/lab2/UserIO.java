package lab2;

import lab2.data.Equation;
import lab2.data.SystemOfEquations;

import static lab2.Main.equations;
import static lab2.Main.systems;

import java.util.*;

public class UserIO {
    private Scanner scanner;

    public UserIO() {
        scanner = new Scanner(System.in);
    }

    public void printWelcomeInformation() {
        println("Решение систем нелинейных уравнений и систем нелинейных уравнений.");
        println("Вы уверены, что оно вам нужно? Если нет, то нажмите сочетание клавиш Ctrl+C");
    }

    public double[] readBoundaries() {
        double[] boundaries = new double[2];
        println("Введите левую границу интервала:");
        boundaries[0] = readValue();
        println("Введите правую границу интервала:");
        boundaries[1] = readValue();
        return boundaries;

    }

    public double[] readApproximations(int format) {
        double[] approximations = new double[2];
        println("Введите приближение для x:");
        approximations[0] = readValue();
        if (format == FormatOfInput.SYSTEM.getFormat()) {
            println("Введите приближение для y:");
            approximations[1] = readValue();
        }
        return approximations;
    }
    public int readInputFormat() {
        println("Выберите что вы хотите решить: 0 - нелинейное уравнение, 1 - систему нелинейных уравнений, 2 - выход");
        int format;
        while (true) {
            try {
                format = scanner.nextInt();
                if (isCorrectFormat(format)) break;
                println("Неверный формат ввода данных. Выберите формат ввода данных, введя 0, 1 или 2:");
            } catch (InputMismatchException e) {
                println("Неверный формат ввода данных. Введите число 0, 1 или 2:");
                scanner.next();
            }
        }
        return format;
    }

    public double readError() {
        println("Введите погрешность вычислений от 0,000001 до 0,1:");
        double error = 0;
        scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            while (true) {
                try {
                    error = scanner.nextDouble();
                    if (isCorrectError(error)) break;
                    println("Неверный формат ввода данных. Введите число от 0,000001 до 0,1");
                } catch (InputMismatchException e) {
                    println("Неверный формат ввода данных. Введите число от 0,000001 до 0,1 через запятую");
                    scanner.next();
                }
            }
        }
        return error;
    }

    public void printInfoForEquation() {
        println("Выберите тип уравнения, которое хотите решить");
        int number = 0;
        for (Equation equation: equations) {
            println(equation.getEquation() + " - введите " + number);
            number++;
        }
    }
    public void printInfoForSystem() {
        println("Выберите тип системы, которую хотите решить");
        int number = 0;
        for (SystemOfEquations system: systems) {
            println("Введите " + number + ":");
            println(system.getSystem()[0]);
            println(system.getSystem()[1]);
            println("------------------------------");
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
        int numberOfCoefficients;
        if (isSystem) numberOfCoefficients = systems[type].getNumberOfCoefficients()[numberOfEquation];
        else numberOfCoefficients = equations[type].getNumberOfCoefficients();
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
        return (0 <= type && type < equations.length);
    }
    private boolean isCorrectError(double error) {
        return (0.000001 <= error && error <= 0.1);
    }
    private boolean isCorrectFormat(int format) {
        return (0 <= format && format <= 2);
    }
    private void println(String s) {
        System.out.println(s);
    }


}
