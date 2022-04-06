package kiexujiaqi.funcComment.utils;

import kiexujiaqi.funcComment.beans.FuncDeclInfo;
import kiexujiaqi.funcComment.constants.RegexSign;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串相关处理
 */
public class StringUtil {

    // 匹配参数字符串
    // todo 能否直接捕获数组元素?
    private static final String FUNC_PATTERN = String.format("^func\\s*(?:\\(.*\\))?\\s*(?<%s>[^\\s\\(]+)\\s*\\((?<%s>[\\s\\S]*?)\\)\\s*(?<%s>(?:\\([\\s\\S]+\\))|[^\\s]*)\\s*\\{\\s*$",
            RegexSign.FUNC_NAME_GROUP.getText(), RegexSign.PARAM_GROUP.getText(), RegexSign.RETURN_GROUP.getText());

    // 匹配换行
    private static final String ENTER_PATTERN = "^\\s\\*[\\s\\S]*$";

    /**
     * 判断是否触发注释
     *
     * @param suspectText 待判定文本
     * @param suffix      后缀文本
     * @return 是否触发
     * @deprecated
     */
    @Deprecated
    public static boolean hasSuffix(String suspectText, String suffix) {
        if (StringUtils.isBlank(suspectText) || StringUtils.isBlank(suffix)) {
            return false;
        }
        if (!suspectText.endsWith(suffix)) {
            return false;
        }
        for (int i = 0; i < suspectText.length() - suffix.length(); i += 1) {
            if (suspectText.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * 提取trim()后的参数列表与返回值(列表)字符串
     *
     * @param decl 表达式
     * @return (参数, 返回值)
     */
    public static Optional<FuncDeclInfo> parseDeclStr(String decl) {
        Matcher matcher = matchDeclStr(decl);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        FuncDeclInfo info = new FuncDeclInfo(
                matcher.group(RegexSign.FUNC_NAME_GROUP.getText()),
                matcher.group(RegexSign.PARAM_GROUP.getText()),
                matcher.group(RegexSign.RETURN_GROUP.getText())
        );
        if (!info.tryParse()) {
            return Optional.empty();
        }
        return Optional.of(info);
    }

    /**
     * 正则解析表达式
     *
     * @param decl 表达式
     * @return Matcher
     */
    public static Matcher matchDeclStr(String decl) {
        return Pattern.compile(FUNC_PATTERN).matcher(decl);
    }

    /**
     * 判断给定字符串是否为注释体结构
     *
     * @param line 一行字符串
     * @return 是否为注释体
     */
    public static boolean notCommentBody(String line) {
        return !Pattern.matches(ENTER_PATTERN, line);
    }
}
