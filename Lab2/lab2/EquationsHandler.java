package lab2;

import lab2.exceptions.NotConvergeException;
import lombok.AllArgsConstructor;
import static lab2.Main.equations;
@AllArgsConstructor
public class EquationsHandler {
    private final double leftBoundary;
    private final double rightBoundary;

    public void prepareForSimpleIteration(int type) throws NotConvergeException{

        if (type == 0) setFirstFunctionForDerivative();
        if (type == 1) setSecondFunctionForDerivative();
        if (type == 2) setThirdFunctionForDerivative();
    }

    private void setFirstFunctionForDerivative() throws NotConvergeException {
        double a = equations[0].getCoefficients()[0];
        double b = equations[0].getCoefficients()[1];
        double c = equations[0].getCoefficients()[2];
        double d = equations[0].getCoefficients()[3];
        if (c != 0) {
            equations[0].setFunctionForDerivative((x) -> (-3 * a * pow(x, 2) - 2 * b * x) / (c));
            if (isConverges(0)) {
                equations[0].setFunctionForIterationMethod((x) -> (-a * pow(x, 3) - b * pow(x, 2) - d) / (c));
                return;
            }
        }
        if (b > 0) {
            equations[0].setFunctionForDerivative((x) -> (-3 * a * pow(x, 2) * pow(b, 0.5) -  pow(b, 0.5) * c) / (2 * b * pow(-a * pow(x, 3) - c * x - d, 0.5)));
            if (isConverges(0)) {
                equations[0].setFunctionForIterationMethod((x) -> pow((-a * pow(x, 3) - c * x - d) / (b), 0.5) );
                return;
            }
        }
        if (a != 0) {
            equations[0].setFunctionForDerivative((x) -> (-2 * b * x - c) / (3 *
                    Math.cbrt(a * pow(b,2) * pow(x, 4) + a * pow(c, 2) * pow(x, 2) + a * pow(d, 2) +
                            2 * a * b * c * pow(x,3) + 2 * a * b * d * pow(x, 2) + 2 * a * c * d * x)));
            if (isConverges(0)) {
                equations[0].setFunctionForIterationMethod((x) -> Math.cbrt((-b * pow(x, 2) - c * x - d) / a));
                return;
            }
        }
        throw new NotConvergeException("Условие сходимости не выполнено.");

    }

    private void setSecondFunctionForDerivative() throws NotConvergeException {
        double a = equations[1].getCoefficients()[0];
        double b = equations[1].getCoefficients()[1];
        if (a != 0) {
            equations[1].setFunctionForDerivative((x) -> (Math.cbrt(b)) / (3 * Math.cbrt(a) * Math.cbrt(pow(Math.E, x))));
            if (isConverges(1)) {
                equations[1].setFunctionForIterationMethod((x) -> Math.cbrt((-b * pow(Math.E, -x)) / (a)));
                return;
            }
        }
        throw new NotConvergeException("Условие сходимости не выполнено.");
    }

    private void setThirdFunctionForDerivative() throws NotConvergeException {
        double a = equations[2].getCoefficients()[0];
        double b = equations[2].getCoefficients()[1];
        double c = equations[2].getCoefficients()[2];
        if (b != 0) {
            equations[2].setFunctionForDerivative((x) -> (-a * Math.cos(x))/ b);
            if (isConverges(2)) {
                equations[2].setFunctionForIterationMethod((x) -> (-a * Math.sin(x) - c) / (b));
                return;
            }
        }
        if (a != 0) {
            equations[2].setFunctionForDerivative((x) -> (-b * Math.abs(a)) / (Math.sqrt(pow(a, 2) -
                    pow(b, 2) * pow(x, 2) - 2 * b * c * x - pow(c, 2) *a)));
            if (isConverges(2)) {
                equations[2].setFunctionForIterationMethod((x) -> Math.asin((-b * x - c) / (a)));
                return;
            }
        }
        throw new NotConvergeException("Условие сходимости не выполнено.");
    }
    private boolean isConverges(int type) {
      //  System.out.println("левая:" + equations[type].calculateTheDerivative(leftBoundary));
       // System.out.println("правая: " + equations[type].calculateTheDerivative(rightBoundary));
        return Math.abs(equations[type].calculateTheDerivative(leftBoundary)) < 1 && Math.abs(equations[type].calculateTheDerivative(rightBoundary)) < 1;
    }

    private double pow(double a, double b) {
        return Math.pow(a, b);
    }


}
