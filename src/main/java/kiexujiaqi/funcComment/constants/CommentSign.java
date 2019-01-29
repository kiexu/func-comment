package kiexujiaqi.funcComment.constants;

public enum CommentSign {

    // 基础符号
    SPACE(" "),

    // 注释体组件
    PREFIX("/**"),
    BODY(" * "),
    BOTTOM(" */"),

    // 文本前缀
    PARAM_SIGN("param: "),
    RETURN_SIGN("return: ");

    private String text;

    CommentSign(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
