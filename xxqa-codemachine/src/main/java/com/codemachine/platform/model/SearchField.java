package com.codemachine.platform.model;

/**
 * 查询条件
 *
 * @author wuzhu
 */
public class SearchField {
    /**
     * 数据库字段名称
     */
    private String fieldName;
    /**
     * 查询条件符号 默认为等于  支持：=;>;<;like;>=;<=;!=
     */
    private String condMark = "=";
    /**
     * 查询显示字段名称
     */
    private String searchName;

    public SearchField(String fieldName, String condMark) {
        this.fieldName = fieldName;
        this.condMark = condMark;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getCondMark() {
        return condMark;
    }

    public void setCondMark(String condMark) {
        this.condMark = condMark;
    }

    public String getSearchName() {
        if (this.searchName != null && this.searchName != "") {
            return searchName;
        }

        String str = "";
        if (condMark == "like") {
            str = "Like";
        } else if (condMark == ">") {
            str = "Gt";
        } else if (condMark == "<") {
            str = "Lt";
        } else if (condMark == "!=") {
            str = "NotEq";
        } else if (condMark == "<=") {
            str = "LtEq";
        } else if (condMark == ">=") {
            str = "GtEq";
        }
        return "sear" + str + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
}
