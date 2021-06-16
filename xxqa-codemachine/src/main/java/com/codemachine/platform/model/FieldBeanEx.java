package com.codemachine.platform.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象扩展信息
 *
 * @author wuzhu
 */
public class FieldBeanEx {
    /**
     * 数据库字段名称
     */
    private String fieldName;
    /**
     * 页面显示名称 默认跟数据库一致
     */
    private String viewName;
    /**
     * 是否允许为空
     */
    private Boolean canNull;
    /**
     * 是否允许重复
     */
    private Boolean canRepeat = true;
    /**
     * 最小长度
     */
    private Integer minLength;
    /**
     * 最大长度 默认为数据库的长度
     */
    private Integer maxLength;
    /**
     * 是否可以修改
     */
    private Boolean canEdit = true;
    /**
     * 日期的格式，根据maxLength确定
     */
    private String dateFormat;
    /**
     * 枚举值范围
     */
    private List<EnumBean> enumList;

    public FieldBeanEx() {
    }

    public FieldBeanEx(String fieldName, String viewName, Boolean canNull, Boolean canRepeat, Integer minLength, Integer maxLength) {
        this.fieldName = fieldName;
        this.viewName = viewName;
        this.canNull = canNull;
        this.canRepeat = canRepeat;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Boolean getCanNull() {
        if (canNull != null) {
            return canNull;
        }

        if (minLength != null && minLength > 0) {
            return false;
        }
        return true;
    }

    public void setCanNull(Boolean canNull) {
        this.canNull = canNull;
    }

    public Boolean getCanRepeat() {
        return canRepeat;
    }

    public void setCanRepeat(Boolean canRepeat) {
        this.canRepeat = canRepeat;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public List<EnumBean> getEnumList() {
        if (enumList == null) {
            return new ArrayList<EnumBean>();
        }
        return enumList;
    }

    public void setEnumList(List<EnumBean> enumList) {
        this.enumList = enumList;
    }

    /**
     * 格式说明 name@value|name@value  例如：0@否|1@是
     *
     * @param enumStr
     * @return
     */
    public FieldBeanEx setEnumList(String enumStr) {
        this.enumList = new ArrayList<EnumBean>();
        if (enumStr == null || enumStr == "") {
            return this;
        }
        for (String str : enumStr.split("\\|")) {
            enumList.add(new EnumBean(str.split("@")[1], str.split("@")[0]));
        }
        return this;
    }

    public Boolean getHasEnum() {
        return this.getEnumList() != null && !this.getEnumList().isEmpty();
    }

    public String getDateFormat() {
        if (dateFormat != null && dateFormat != "") {
            return dateFormat;
        }

        if (getMaxLength() != null) {
            switch (this.maxLength) {
                case 4:
                    return "yyyy";
                case 6:
                    return "yyyyMM";
                case 8:
                    return "yyyyMMdd";
                case 7:
                    return "yyyy-MM";
                case 10:
                    return "yyyy-MM-dd";
                case 16:
                    return "yyyy-MM-dd HH:mm";
                default:
                    return "yyyy-MM-dd HH:mm:ss";
            }
        }
        return dateFormat;
    }

    public String getTableDateFormat() {
        if (getDateFormat() == null || getDateFormat() == "") {
            return "yyyy_MM_dd_HH_mm_ss";
        }
        return getDateFormat().replaceAll("-", "_").replaceAll(" ", "_").replaceAll(":", "_");
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
