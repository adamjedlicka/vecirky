package vecirky.exceptions;

/**
 * Model is invalid and cannot be stored in the database. Probably some required field is missing.
 */
public class ModelIsInvalid extends Exception {

    private static final long serialVersionUID = -5374361357237303353L;

    public ModelIsInvalid(String message) {
        super(message);
    }

}
