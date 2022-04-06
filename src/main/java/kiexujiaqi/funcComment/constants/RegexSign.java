package kiexujiaqi.funcComment.constants;

public enum RegexSign {

    FUNC_NAME_GROUP("name"),
    PARAM_GROUP("params"),
    RETURN_GROUP("returns");

    private String text;

    RegexSign(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
