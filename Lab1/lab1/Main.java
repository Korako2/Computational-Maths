package lab1;

import lab1.algorithms.GaussSeidelMethod;
import lab1.data.Answer;
import lab1.data.Matrix;
import lab1.exceptions.DiagonalDominanceException;

public class Main {
    public static void main(String[] args) {
        UserIO userIO = new UserIO();
        userIO.printWelcomeInformation();
        Matrix matrix = userIO.readMatrix();
        if (matrix == null) System.exit(0);
        double error = userIO.readError();
        GaussSeidelMethod gaussSeidelMethod = new GaussSeidelMethod();
        try {
            Answer answer = gaussSeidelMethod.solveTheSystem(matrix, error);
            if (answer != null) answer.printAnswers();
        } catch (DiagonalDominanceException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);

    }

}