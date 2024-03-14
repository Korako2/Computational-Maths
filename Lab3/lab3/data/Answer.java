package lab3.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor

public class Answer {
    @Getter
    private Double answer;
    private Double error;
    private int numberOfIterations;
    private String errorMassage;
    @Getter
    private boolean isCorrect;
    public void printAnswer() {
        if (isCorrect) {
            System.out.println("Значение интеграла: " + answer);
            System.out.println("Количество итераций: " + numberOfIterations);
            System.out.println("Погрешность: " + error);
            System.out.println(errorMassage);
        }
        else System.out.println(errorMassage);
    }
}
