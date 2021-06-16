package com.codemachine.platform.tool.data;

import com.codemachine.platform.main.CodeMeachine;
import com.codemachine.platform.model.FieldBean;
import com.codemachine.platform.model.FieldBeanEx;
import com.codemachine.platform.model.TableConfig;
import com.codemachine.platform.tool.db.DbConn;
import com.codemachine.platform.tool.db.DbService;
import com.codemachine.platform.tool.db.DbServiceImpl;

import java.text.SimpleDateFormat;
import java.util.*;

public class DataServiceImpl implements DataService {

    /**
     * 根据表名获取模板要用的Map型数据 (带有列信息)
     *
     * @param tableName
     * @return
     */
    @Override
    public Map<String, Object> getDbTemplateData(String tableName,
                                                 String classPre, TableConfig tableConfig) {
        DbService dbService = new DbServiceImpl();
        List<FieldBean> fieldBeanList = dbService.getAllColums(tableName);
        // 主键对象
        FieldBean periMaryBean = null;

        Map<String, Object> templateData = new HashMap<String, Object>(fieldBeanList.size());
        templateData.put("hasArchive", false);
        List<FieldBeanEx> needAddList = new ArrayList<FieldBeanEx>();

        for (FieldBean bean : fieldBeanList) {
            if (bean.getIsPrimary()) {
                periMaryBean = bean;
            }
            if ("archive".equals(bean.getColumnName())) {
                templateData.put("hasArchive", true);
            }

            // 赋值扩展属性
            if (tableConfig != null) {
                for (FieldBeanEx fieldBeanEx : tableConfig.getFieldList()) {
                    if (fieldBeanEx.getFieldName().equals(bean.getColumnName())) {
                        bean.setViewName(fieldBeanEx.getViewName());
                        bean.setCanNull(fieldBeanEx.getCanNull());
                        bean.setCanRepeat(fieldBeanEx.getCanRepeat());
                        bean.setMinLength(fieldBeanEx.getMinLength());
                        if (fieldBeanEx.getMaxLength() == null) {
                            bean.setMaxLength(bean.getColumnLength().intValue());
                        } else {
                            bean.setMaxLength(fieldBeanEx.getMaxLength());
                        }
                        bean.setEnumList(fieldBeanEx.getEnumList());
                        bean.setFieldName(fieldBeanEx.getFieldName());
                    } else {
                        needAddList.add(new FieldBeanEx(bean.getColumnName(), bean.getColumnDesc(), true, true, 0, bean.getColumnLength().intValue()));
                    }
                }

                // 设置字段是否可以修改
                for (String fieldName : tableConfig.getCanNotEditFields()) {
                    if (fieldName != null && fieldName != "" && fieldName.equals(bean.getColumnName())) {
                        bean.setCanEdit(false);
                    }
                }
            }
        }

        if (tableConfig != null && tableConfig.getNeedProcField()) {
            tableConfig.setNeedProcField(false);
            tableConfig.getFieldList().addAll(needAddList);
        }

        templateData.put("databaseType", DbConn.DATABASE_TYPE);
        templateData.put("moduleName", CodeMeachine.MODULE_NAME);
        templateData.put("packageName", CodeMeachine.PACKAGE_NAME);
        templateData.put("tempEngine", CodeMeachine.TEMPLATE_ENGINE);
        templateData.put("tableConfig", tableConfig);
        templateData.put("tableName", tableName);
        templateData.put("noModuleTableName", tableName.substring(tableName.indexOf("_") + 1).toLowerCase());
        templateData.put("properties", fieldBeanList);
        templateData.put("classPre", classPre);
        templateData.put("firstLowerClassPre", classPre.substring(0, 1)
                .toLowerCase() + classPre.substring(1));
        templateData.put("periMaryBean", periMaryBean);
        templateData.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        templateData.put("year", new SimpleDateFormat("yyyy").format(new Date()));

        Map<String, FieldBean> fieldMap = new HashMap<String, FieldBean>(fieldBeanList.size());
        for (FieldBean bean : fieldBeanList) {
            fieldMap.put(bean.getColumnName(), bean);
        }
        // 可以根据字段key获得字段对象
        templateData.put("fieldMap", fieldMap);
        return templateData;
    }
}
