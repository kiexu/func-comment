package kiexujiaqi.funcComment.extensions.service;

import kiexujiaqi.funcComment.beans.FuncDeclInfo;
import kiexujiaqi.funcComment.beans.FuncFieldInfo;
import kiexujiaqi.funcComment.beans.SuspectDeclResp;
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
        List<String> comments = new ArrayList<>();
        SuspectDeclResp declResp = outputCommentLines(suspectDecl);
        if (!declResp.isActivated()) {
            return comments;
        }
        List<String> rawComments = declResp.getRawComments();
        // 参数列表不为零则额外增加空行
        if (rawComments.size() != 0) {
            comments.add(CommentSign.DOUBLE_SLASH.getText());
        }
        comments.add(CommentSign.DOUBLE_SLASH.getText());
        for (String raw : rawComments) {
            comments.add(CommentSign.DOUBLE_SLASH.getText() + raw);
        }
        return comments;
    }

    /**
     * 尝试从表达式中解析注释列表
     *
     * @param suspectDecl 疑似表达式
     * @return 注释列表, 空代表无需添加
     */
    private static SuspectDeclResp outputCommentLines(String suspectDecl) {
        List<String> resList = new ArrayList<>();
        Optional<FuncDeclInfo> infoOpt = StringUtil.parseDeclStr(suspectDecl);
        if (!infoOpt.isPresent()) {
            return new SuspectDeclResp(false, new ArrayList<>());
        }
        FuncDeclInfo info = infoOpt.get();
        // 参数注释
        for (int i = 0; i < info.getParamInfoList().size(); i += 1) {
            FuncFieldInfo paramInfo = info.getParamInfoList().get(i);
            // 过滤掉第一个context入参
            if (i == 0 && CommentSign.GO_CONTEXT.getText().equals(paramInfo.getType())) {
                continue;
            }
            resList.add(buildCommentLine(CommentSign.PARAM_SIGN.getText(), paramInfo));
        }
        // 返回值注释
        // 由于error默认无需特殊注释, 无论几个返回值均只打印一行, 供用户自行选用
        List<FuncFieldInfo> retList = info.getReturnInfoList();
        if (!retList.isEmpty() && !(retList.size() == 1 && CommentSign.GO_ERROR.getText().equals(retList.get(0).getType()))) {
            resList.add(CommentSign.RETURN_SIGN.getText() + CommentSign.SPACE.getText());
        }
        return new SuspectDeclResp(true, resList);
    }

    /**
     * 组装单行注释
     * todo 变量添加超链接
     *
     * @param prefix 类型前缀
     * @param info   参数信息
     * @return 单行注释
     */
    private static String buildCommentLine(String prefix, FuncFieldInfo info) {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        builder.append(CommentSign.SPACE.getText());
        if (info.getFieldType() == FieldType.PARAM) {
            builder.append(info.getName());
        }
        return builder.toString();
    }
}
