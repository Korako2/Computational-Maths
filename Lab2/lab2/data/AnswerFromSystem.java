package lab2.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AnswerFromSystem {
    private double x;
    private double y;
    private double xError;
    private double yError;
    private int numberOfIterations;
    private String errorMassage;
    private boolean isCorrect;
    public void printAnswer() {
        if (isCorrect) {
            System.out.println("x = " + x);
            System.out.println("y = " + y);
            System.out.println("Количество итераций: " + numberOfIterations);
            System.out.println("Погрешности: ");
            System.out.println("Для x = " + xError);
            System.out.println("Для y = " + yError);
            System.out.println(errorMassage);
        } else System.out.println(errorMassage);

    }
}
