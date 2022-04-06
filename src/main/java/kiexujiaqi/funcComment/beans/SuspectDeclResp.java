package kiexujiaqi.funcComment.beans;

import lombok.Data;

import java.util.List;

/**
 * 待分析的次行解析结果
 */
@Data
public class SuspectDeclResp {

    // 是否启动注释
    private boolean activated;

    // 未带前缀的注释
    private List<String> rawComments;

    public SuspectDeclResp(boolean activated, List<String> rawComments) {
        this.activated = activated;
        this.rawComments = rawComments;
    }
}
