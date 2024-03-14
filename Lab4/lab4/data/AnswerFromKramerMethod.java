package lab4.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AnswerFromKramerMethod {
    @Getter
    private double[] approximations;

    private int numberOfSolutions;


    public void printAnswers() {
        for (int i = 0; i < numberOfSolutions; i++) {
            System.out.println("x" + (i + 1) + " = " + approximations[i]);
        }
    }
}
