package kiexujiaqi.funcComment.beans;

import kiexujiaqi.funcComment.enums.FieldType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class FuncDeclInfo {

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

    public FuncDeclInfo(String paramStr, String returnStr) {
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
                    if (innerParts.length != 2) {
                        return false;
                    }
                    if (maxReturnTypeLength < innerParts[1].length()) {
                        maxReturnTypeLength = innerParts[1].length();
                    }
                    returnInfoList.add(new FuncFieldInfo(FieldType.RETURN, innerParts[1], innerParts[0]));
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

    public String getParamStr() {
        return paramStr;
    }

    public void setParamStr(String paramStr) {
        this.paramStr = paramStr;
    }

    public String getReturnStr() {
        return returnStr;
    }

    public void setReturnStr(String returnStr) {
        this.returnStr = returnStr;
    }

    public List<FuncFieldInfo> getParamInfoList() {
        return paramInfoList;
    }

    public void setParamInfoList(List<FuncFieldInfo> paramInfoList) {
        this.paramInfoList = paramInfoList;
    }

    public List<FuncFieldInfo> getReturnInfoList() {
        return returnInfoList;
    }

    public void setReturnInfoList(List<FuncFieldInfo> returnInfoList) {
        this.returnInfoList = returnInfoList;
    }

    public int getMaxParamTypeLength() {
        return maxParamTypeLength;
    }

    public void setMaxParamTypeLength(int maxParamTypeLength) {
        this.maxParamTypeLength = maxParamTypeLength;
    }

    public int getMaxReturnTypeLength() {
        return maxReturnTypeLength;
    }

    public void setMaxReturnTypeLength(int maxReturnTypeLength) {
        this.maxReturnTypeLength = maxReturnTypeLength;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FuncDeclInfo.class.getSimpleName() + "[", "]")
                .add("paramStr='" + paramStr + "'")
                .add("returnStr='" + returnStr + "'")
                .add("paramInfoList=" + paramInfoList)
                .add("returnInfoList=" + returnInfoList)
                .toString();
    }
}
