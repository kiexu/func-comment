package kiexujiaqi.funcComment.beans;

import java.util.List;

// 待分析的次行解析结果
public class SuspectDeclResp {

    // 是否启动注释
    private boolean activated;

    // 未带前缀的注释
    private List<String> rawComments;

    public SuspectDeclResp(boolean activated, List<String> rawComments) {
        this.activated = activated;
        this.rawComments = rawComments;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public List<String> getRawComments() {
        return rawComments;
    }

    public void setRawComments(List<String> rawComments) {
        this.rawComments = rawComments;
    }
}
