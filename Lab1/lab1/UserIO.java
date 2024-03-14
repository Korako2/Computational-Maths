package lab1;

import lab1.data.Matrix;
import lab1.readers.ConsoleReader;
import lab1.readers.FileReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class UserIO {
    private Scanner scanner;

    public UserIO() {
        scanner = new Scanner(System.in);
    }

    public void printWelcomeInformation() {
        println("Решение систем линейных алгебраических уравнений методом Гаусса-Зейделя");
        println("Вы уверены, что оно вам нужно? Если нет, то нажмите сочетание клавиш Ctrl+C");
    }

    public Matrix readMatrix() {
        int format = readInputFormat();
        if (format == FormatOfInput.CONSOLE.getFormat()) {
            ConsoleReader consoleReader = new ConsoleReader(scanner);
            return consoleReader.readUserInput();
        }
        if (format == FormatOfInput.GENERATE.getFormat()) {
            return generateRandomData(new ConsoleReader(scanner).readNumberOfEquations());
        }
        if (format == FormatOfInput.FILE.getFormat()) {
            setFileStreamForScanner();
            FileReader fileReader = new FileReader(scanner);
            int numberOfEquations = scanner.nextInt();
            if (1 <= numberOfEquations && numberOfEquations <= 21) {
                return fileReader.readMatrixFromFile(numberOfEquations);
            }
            else {
                System.out.println("Неверное количество уравнений в файле.");
            }
        }
        return null;
    }
    private int readInputFormat() {
        println("Выберите формат ввода данных: 0 - ввод через консоль, 1 - ввод через файл, 2 - генерация случайной матрицы");
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

    private boolean isCorrectError(double error) {
        return (0.000001 <= error && error <= 0.1);
    }
    private boolean isCorrectFormat(int format) {
        return (0 <= format && format <= 2);
    }

    private Matrix generateRandomData(int size) {
        Matrix matrix = new Matrix(size);
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            matrix.setRow(i, new ArrayList<>());
            double currentSum = 0;
            for (int j = 0; j < size + 1; j++) {
                matrix.getMatrix().get(i).add(rand.nextDouble() * 10);
                currentSum += Math.abs(matrix.getElement(i, j));
            }
            matrix.getMatrix().get(i).set(i, currentSum + rand.nextDouble() * 10);
        }
        matrix.printMatrix();
        return matrix;
    }

    public void setFileStreamForScanner() {
        boolean isWrongFilePath = true;
        scanner.nextLine();
        while (isWrongFilePath) {
            try {
                println("Введите полный путь до файла:");
                if (scanner.hasNext()) {
                    String fileName = scanner.nextLine();
                    FileInputStream fileInputStream = new FileInputStream(fileName);
                    scanner = new Scanner(fileInputStream);
                    isWrongFilePath = false;
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Неверный путь до файла, повторите ввод.");
            }
        }
    }
    private void println(String s) {
        System.out.println(s);
    }
}
