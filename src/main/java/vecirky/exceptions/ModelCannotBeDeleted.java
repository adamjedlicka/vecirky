package vecirky.exceptions;

/**
 * Model cannot be deleted for some reason. Mostly it's because of integrity constraints.
 */
public class ModelCannotBeDeleted extends Exception {

    private static final long serialVersionUID = 9111554385577941819L;

    public ModelCannotBeDeleted(String message) {
        super(message);
    }

}
