package lab1.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@AllArgsConstructor
public class Answer {
    private double[] approximations;
    private double[] errors;

    private int numberOfSolutions;

    private int numberOfIterations;

    public void printAnswers() {
        for (int i = 0; i < numberOfSolutions; i++) {
            System.out.println("x" + (i + 1) + " = " + approximations[i] + "; погрешность = " + errors[i]);
        }
        System.out.println("Количество итераций: " + numberOfIterations);
    }
}
