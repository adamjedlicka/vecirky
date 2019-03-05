package vecirky.exceptions;

/**
 * Model was not found in the databse.
 */
public class ModelNotFound extends Exception {

    private static final long serialVersionUID = -6390971708701218894L;

    public ModelNotFound(String message) {
        super(message);
    }

}
