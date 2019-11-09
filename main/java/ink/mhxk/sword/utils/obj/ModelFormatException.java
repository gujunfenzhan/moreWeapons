package ink.mhxk.sword.utils.obj;

/**
 * Thrown if there is a problem parsing the model
 *
 * 鏉ヨ嚜鎷斿垁鍓戠殑寮�婧愪唬鐮�
 * https://github.com/flammpfeil/SlashBlade/tree/1.12.2
 *
 * @author cpw
 */
public class ModelFormatException extends RuntimeException {

    private static final long serialVersionUID = 2023547503969671835L;

    public ModelFormatException() {
        super();
    }

    public ModelFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelFormatException(String message) {
        super(message);
    }

    public ModelFormatException(Throwable cause) {
        super(cause);
    }
}