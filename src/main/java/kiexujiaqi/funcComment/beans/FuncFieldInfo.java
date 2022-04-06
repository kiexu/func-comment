package kiexujiaqi.funcComment.beans;

import kiexujiaqi.funcComment.enums.FieldType;
import lombok.Data;

/**
 * 参数信息
 */
@Data
public class FuncFieldInfo {

    // 字段类型
    private FieldType fieldType;

    // 类型名
    private String type;

    // 名称名
    private String name;

    public FuncFieldInfo(FieldType fieldType, String type, String name) {
        this.fieldType = fieldType;
        this.type = type;
        this.name = name;
    }
}
