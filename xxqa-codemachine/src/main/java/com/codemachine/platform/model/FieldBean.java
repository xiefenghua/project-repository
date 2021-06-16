package com.codemachine.platform.model;

/**
 * 对象基本信息
 *
 * @author wuzhu
 */
public class FieldBean extends FieldBeanEx {
    private String fieldName; // 数据库原字段名
    private String firstUpperCaseProName;// 第一个字母大些的变量名
    private String proName; // 变量名
    private String proType; // 变量类型
    private String dataBaseProType;// 数据库类型
    private String columnName;// 数据库字段名称
    private String columnDesc = "";// 字段描述
    private Boolean isPrimary = false;// 是否是主键
    private Long columnLength;// 字段的长度

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnDesc() {
        return columnDesc.replaceAll("\r", "").replaceAll("\t", "").replaceAll("\n", "");
    }

    public void setColumnDesc(String columnDesc) {
        this.columnDesc = columnDesc;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Long getColumnLength() {
        if (columnLength == -1L) {
            return 0L;
        }
        return columnLength;
    }

    public void setColumnLength(Long columnLength) {
        this.columnLength = columnLength;
    }

    public String getDataBaseProType() {
        return dataBaseProType;
    }

    public void setDataBaseProType(String dataBaseProType) {
        this.dataBaseProType = dataBaseProType;
    }

    public String getFirstUpperCaseProName() {
        if (firstUpperCaseProName == null || firstUpperCaseProName == "") {
            return proName.substring(0, 1).toUpperCase() + proName.substring(1);
        }
        return firstUpperCaseProName;
    }

    public void setFirstUpperCaseProName(String firstUpperCaseProName) {
        this.firstUpperCaseProName = firstUpperCaseProName;
    }
}
