package lab2;

import lombok.Getter;

public enum FormatOfInput {
    EQUATION (0),
    SYSTEM (1),
    EXIT (2);
    @Getter
    private final int format;
    FormatOfInput(int format) {
        this.format = format;
    }
}
