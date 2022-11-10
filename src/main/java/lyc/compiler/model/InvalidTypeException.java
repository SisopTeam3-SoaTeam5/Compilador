package lyc.compiler.model;

import java.io.Serial;

public class InvalidTypeException extends CompilerException{

    @Serial
    private static final long serialVersionUID = -3138875452688398489L;

    public InvalidTypeException(String message) {
        super(message);
    }
}
