package kiexujiaqi.funcComment.beans;

import kiexujiaqi.funcComment.enums.FieldType;

import java.util.StringJoiner;

/**
 * 参数信息
 */
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

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FuncFieldInfo.class.getSimpleName() + "[", "]")
                .add("type='" + type + "'")
                .add("name='" + name + "'")
                .toString();
    }
}
