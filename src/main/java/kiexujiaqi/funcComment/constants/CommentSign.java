package kiexujiaqi.funcComment.constants;

public enum CommentSign {

    // 基础符号
    SPACE(" "),

    // 注释体组件
    TRIGGER("//"),
    BODY(" * "),
    BOTTOM(" */"),
    DOUBLE_SLASH("// "),

    // 文本前缀
    PARAM_SIGN("param:"),
    RETURN_SIGN("return:"),

    // 待忽略的数据类型
    GO_CONTEXT("context.Context"),
    GO_ERROR("error");

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
