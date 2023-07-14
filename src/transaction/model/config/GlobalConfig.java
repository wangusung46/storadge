package transaction.model.config;

/**
 *
 * @author Khanza
 */
public class GlobalConfig {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "GlobalConfig{" + "value=" + value + '}';
    }

}
