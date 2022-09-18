package lyc.compiler.model;

import java.io.Serial;

public class InvalidIntegerException extends CompilerException{

    @Serial
    private static final long serialVersionUID = -3138875452688398489L;

    public InvalidIntegerException(String message) {
        super(message);
    }
}
