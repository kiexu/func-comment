package kiexujiaqi.funcComment.extensions.service;

import kiexujiaqi.funcComment.beans.FuncDeclInfo;
import kiexujiaqi.funcComment.beans.FuncFieldInfo;
import kiexujiaqi.funcComment.enums.FieldType;
import kiexujiaqi.funcComment.constants.CommentSign;
import kiexujiaqi.funcComment.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TriggerEnterHandlerDelegateService {

    /**
     * 包装注释列表
     *
     * @param suspectDecl 疑似表达式
     * @return 注释列表, 空代表无需添加
     */
    public static List<String> buildCommentLines(String suspectDecl) {
        List<String> rawComments = outputCommentLines(suspectDecl);
        List<String> comments = new ArrayList<>();
        // 参数列表不为零则额外增加空行
        if (rawComments.size() != 0) {
            comments.add(CommentSign.BODY.getText());
        }
        comments.add(CommentSign.BODY.getText());
        for (String raw : rawComments) {
            comments.add(CommentSign.BODY.getText() + raw);
        }
        comments.add(CommentSign.BOTTOM.getText());
        return comments;
    }

    /**
     * 尝试从表达式中解析注释列表
     *
     * @param suspectDecl 疑似表达式
     * @return 注释列表, 空代表无需添加
     */
    private static List<String> outputCommentLines(String suspectDecl) {
        List<String> resList = new ArrayList<>();
        Optional<FuncDeclInfo> infoOpt = StringUtil.parseDeclStr(suspectDecl);
        if (!infoOpt.isPresent()) {
            return resList;
        }
        FuncDeclInfo info = infoOpt.get();
        // 参数注释
        for (int i = 0; i < info.getParamInfoList().size(); i += 1) {
            FuncFieldInfo paramInfo = info.getParamInfoList().get(i);
            resList.add(buildCommentLine(CommentSign.PARAM_SIGN.getText(), paramInfo, info.getMaxParamTypeLength()));
        }
        // 返回值注释
        for (int j = 0; j < info.getReturnInfoList().size(); j += 1) {
            FuncFieldInfo paramInfo = info.getReturnInfoList().get(j);
            resList.add(buildCommentLine(CommentSign.RETURN_SIGN.getText(), paramInfo, info.getMaxParamTypeLength()));
        }
        return resList;
    }

    /**
     * 组装单行注释
     *
     * @param prefix        类型前缀
     * @param info          参数信息
     * @param maxTypeLength 最大类型长度
     * @return 单行注释
     */
    private static String buildCommentLine(String prefix, FuncFieldInfo info, int maxTypeLength) {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        builder.append(info.getType());
        for (int i = info.getType().length(); i < maxTypeLength; i += 1) {
            // 缩进对齐
            builder.append(CommentSign.SPACE.getText());
        }
        builder.append(CommentSign.SPACE.getText());
        if (info.getFieldType() == FieldType.PARAM) {
            builder.append(info.getName());
        }
        return builder.toString();
    }
}
