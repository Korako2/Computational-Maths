package lab5;

import lab5.data.DifferentialEquation;

import static lab5.Main.differentialEquations;
import java.util.*;

public class UserIO {
    private final Scanner scanner;

    public UserIO() {
        scanner = new Scanner(System.in);
    }

    public void printWelcomeInformation() {
        println("Решение задачи Коши методом Эйлера");
    }

    public void printInfoForEquation() {
        println("Выберитите тип дифференциального уравнения, который вы хотите решить");
        int number = 0;
        for (DifferentialEquation differentialEquation : differentialEquations) {
            println(differentialEquation.getEquation() + " - введите " + number);
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
        int numberOfCoefficients = differentialEquations[type].getNumberOfCoefficients();
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


    public double readValue(String massage) {
        System.out.println(massage);
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
        return (0 <= type && type < differentialEquations.length);
    }

    private void println(String s) {
        System.out.println(s);
    }


}
