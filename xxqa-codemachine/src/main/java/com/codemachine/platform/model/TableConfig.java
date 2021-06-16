package com.codemachine.platform.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象扩展信息
 *
 * @author wuzhu
 */
public class TableConfig {
    /**
     * 表名
     */
    private String table;
    /**
     * 页面显示的标题
     */
    private String viewTitle;
    /**
     * 查询条件
     */
    private SearchField[] searchFields;
    /**
     * 显示字段
     */
    private String[] viewFields;
    /**
     * 新增字段
     */
    private String[] addFields;
    /**
     * 不能修改字段
     */
    private String[] canNotEditFields;
    /**
     * 导出字段
     */
    private String[] exportFields;

    /**
     * 新页面编辑 默认为fasle
     */
    private Boolean newPageEdit = false;
    /**
     * 字段是否需要处理
     */
    private Boolean needProcField = true;
    /**
     * 是否需要导出
     */
    private Boolean needExport;

    private List<FieldBeanEx> fieldList;

    public TableConfig(String table, String viewTitle) {
        this.table = table;
        this.viewTitle = viewTitle;
    }

    public TableConfig(String table, String viewTitle, Boolean newPageEdit) {
        this.table = table;
        this.viewTitle = viewTitle;
        this.newPageEdit = newPageEdit;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getViewTitle() {
        return viewTitle;
    }

    public void setViewTitle(String viewTitle) {
        this.viewTitle = viewTitle;
    }

    public Boolean getNewPageEdit() {
        return newPageEdit;
    }

    public void setNewPageEdit(Boolean newPageEdit) {
        this.newPageEdit = newPageEdit;
    }

    public void addField(FieldBeanEx fieldBeanEx) {
        if (this.fieldList == null) {
            this.fieldList = new ArrayList<FieldBeanEx>();
        }
        this.fieldList.add(fieldBeanEx);
    }

    public List<FieldBeanEx> getFieldList() {
        if (fieldList == null) {
            fieldList = new ArrayList<FieldBeanEx>();
        }
        return fieldList;
    }

    public void setFieldList(List<FieldBeanEx> fieldList) {
        this.fieldList = fieldList;
    }

    public SearchField[] getSearchFields() {
        if (searchFields == null) {
            return new SearchField[]{};
        }
        return searchFields;
    }

    public void setSearchFields(SearchField[] searchFields) {
        if (searchFields == null) {
            this.searchFields = new SearchField[]{};
            return;
        }
        List<SearchField> list = new ArrayList<SearchField>();
        // 清空为空的数据
        for (SearchField searchField : searchFields) {
            if (searchField.getFieldName() != null && searchField.getFieldName() != "") {
                list.add(searchField);
            }
        }
        this.searchFields = list.toArray(new SearchField[]{});
    }

    public String[] getViewFields() {
        return viewFields;
    }

    public void setViewFields(String[] viewFields) {
        this.viewFields = viewFields;
    }

    public String[] getAddFields() {
        return addFields;
    }

    public void setAddFields(String[] addFields) {
        this.addFields = addFields;
    }

    public String[] getCanNotEditFields() {
        if (canNotEditFields == null) {
            return new String[]{};
        }
        return canNotEditFields;
    }

    public void setCanNotEditFields(String[] canNotEditFields) {
        if (canNotEditFields == null) {
            this.canNotEditFields = new String[]{};
            return;
        }
        List<String> list = new ArrayList<String>();
        // 清空为空的数据
        for (String str : canNotEditFields) {
            if (str != null && str != "") {
                list.add(str);
            }
        }
        this.canNotEditFields = list.toArray(new String[]{});
    }

    public String[] getExportFields() {
        return exportFields;
    }

    public void setExportFields(String[] exportFields) {
        this.exportFields = exportFields;
    }

    public Boolean getNeedExport() {
        if (exportFields == null || exportFields.length == 0) {
            return false;
        }
        return true;
    }

    public void setNeedExport(Boolean needExport) {
        this.needExport = needExport;
    }

    public Boolean getHasEnum() {
        for (FieldBeanEx fieldBeanEx : getFieldList()) {
            if (fieldBeanEx.getHasEnum()) {
                return true;
            }
        }
        return false;
    }

    public Boolean getNeedProcField() {
        return needProcField;
    }

    public void setNeedProcField(Boolean needProcField) {
        this.needProcField = needProcField;
    }
}
