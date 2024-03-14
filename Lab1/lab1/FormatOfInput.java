package lab1;

import lombok.Getter;

public enum FormatOfInput {
    CONSOLE (0),
    FILE (1),
    GENERATE (2);
    @Getter
    private final int format;

    FormatOfInput(int format) {
        this.format = format;
    }
}
