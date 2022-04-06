package kiexujiaqi.funcComment.beans;

import kiexujiaqi.funcComment.enums.FieldType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class FuncDeclInfo {

    // 参数名
    private String funcName;

    // 参数字符串
    private String paramStr;

    // 返回值字符串
    private String returnStr;

    // 参数属性(类型->参数名)列表
    private List<FuncFieldInfo> paramInfoList = new ArrayList<>();

    // 返回值属性(类型->参数名[可选])列表
    private List<FuncFieldInfo> returnInfoList = new ArrayList<>();

    // 最大参数类型长度
    private int maxParamTypeLength;

    // 最大返回值类型长度
    private int maxReturnTypeLength;

    public FuncDeclInfo(String funcName, String paramStr, String returnStr) {
        this.funcName = funcName;
        this.paramStr = paramStr;
        this.returnStr = returnStr;
    }

    /**
     * 尝试解析正则提取后的字符串
     *
     * @return 是否成功
     */
    public boolean tryParse() {
        // 参数字符串解析
        if (!StringUtils.isBlank(paramStr)) {
            String[] parts = paramStr.split(",");
            for (String part : parts) {
                String[] innerParts = part.trim().split("\\s+");
                if (innerParts.length != 2) {
                    return false;
                }
                if (maxParamTypeLength < innerParts[1].length()) {
                    maxParamTypeLength = innerParts[1].length();
                }
                paramInfoList.add(new FuncFieldInfo(FieldType.PARAM, innerParts[1], innerParts[0]));
            }
        }
        // 返回值解析
        if (!StringUtils.isBlank(returnStr)) {
            // 若存在括号, 则分割解析
            if (returnStr.startsWith("(") && returnStr.endsWith(")")) {
                String actualReturnStr = returnStr.substring(1, returnStr.length() - 1);
                String[] parts = actualReturnStr.split(",");
                for (String part : parts) {
                    String[] innerParts = part.trim().split("\\s+");
                    switch (innerParts.length) {
                        case 1:
                            // (string, error)形式
                            if (maxReturnTypeLength < innerParts[0].length()) {
                                maxReturnTypeLength = innerParts[0].length();
                            }
                            returnInfoList.add(new FuncFieldInfo(FieldType.RETURN, innerParts[0], ""));
                            break;
                        case 2:
                            // (resp string, err error)形式
                            if (maxReturnTypeLength < innerParts[1].length()) {
                                maxReturnTypeLength = innerParts[1].length();
                            }
                            returnInfoList.add(new FuncFieldInfo(FieldType.RETURN, innerParts[1], innerParts[0]));
                            break;
                        default:
                            return false;
                    }

                }
            } else {
                // 不存在括号, 简单检查后放入结果
                if (returnStr.split(",").length > 1) {
                    return false;
                }
                returnInfoList.add(new FuncFieldInfo(FieldType.RETURN, returnStr.trim(), ""));
            }

        }
        return true;
    }
}
