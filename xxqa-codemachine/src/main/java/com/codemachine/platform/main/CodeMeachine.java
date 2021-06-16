package com.codemachine.platform.main;

import com.codemachine.platform.model.FieldBean;
import com.codemachine.platform.model.TableConfig;
import com.codemachine.platform.tool.data.DataService;
import com.codemachine.platform.tool.data.DataServiceImpl;
import com.codemachine.platform.tool.db.DbService;
import com.codemachine.platform.tool.db.DbServiceImpl;
import com.codemachine.platform.tool.freeMaker.FreeMakerUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author hiwi base
 */
public class CodeMeachine {
    /**
     * 生成文件的路径
     */
    public static String FILE_PATH = "";
    /**
     * 模块名称
     */
    public static String MODULE_NAME = "";
    /**
     * 包名
     */
    public static String PACKAGE_NAME = "";

    /**
     * 服务名
     */
    public static String SERVER_NAME = "";
    /**
     * 模版引擎
     */
    public static String TEMPLATE_ENGINE = "freemaker";

    private static FreeMakerUtil freeMakerUtil = new FreeMakerUtil();
    private static DataService dataService = new DataServiceImpl();

    public static String getJavaPath() {
        return FILE_PATH + "/src/main/java/" + PACKAGE_NAME.replaceAll("\\.", "\\/");
    }

    public static String getBeanPath() {
        return getJavaPath() + "/model/db/" + CodeMeachine.MODULE_NAME + "/";
    }

    public static String getVoPath() {
        return getJavaPath() + "/common/vo/" + CodeMeachine.MODULE_NAME + "/";
    }

    public static String getDOPath() {
        return getJavaPath() + "/model/dao/" + CodeMeachine.MODULE_NAME + "/";
    }

    public static String getQueryPath() {
        return getJavaPath() + "/model/request/query/" + CodeMeachine.MODULE_NAME + "/";
    }

    public static String getRequestPath() {
        return getJavaPath() + "/model/request/" + CodeMeachine.MODULE_NAME + "/";
    }

    public static String getControllerPath() {
        return getJavaPath() + "/web/controller/" + CodeMeachine.MODULE_NAME + "/";
    }

    public static String getServicePath() {
        return getJavaPath() + "/service/" + CodeMeachine.MODULE_NAME + "/";
    }

    public static String getServiceImplPath() {
        return getJavaPath() + "/service/" + CodeMeachine.MODULE_NAME + "/impl/";
    }

    public static String getMapperJavaPath() {
        return getJavaPath() + "/dao/mapper/" + CodeMeachine.MODULE_NAME + "/";
    }

    public static String getMapperXmlPath() {
        return FILE_PATH + "/src/main/resources/mybatis/mapper/" + CodeMeachine.MODULE_NAME + "/";
    }

    public static String getFtlPath() {
        return FILE_PATH + "/src/main/resources/templates/" + CodeMeachine.MODULE_NAME + "/";
    }

    /**
     * 生成Bean文件
     *
     * @param tableName
     */
    public static void generateBeanFile(String tableName) {
        String path = getBeanPath();
        mkdir(path);
        String fileName = getClassNameByTableName(tableName, "");
        generateFileWithDb("beanModel.ftl", tableName,
                fileName, path, null);
    }

    /**
     * 生成Bean文件
     *
     * @param tableName
     */
    public static void generateMapperFile(String tableName) {
        String path = getMapperJavaPath();
        mkdir(path);

        String fileName = getClassNameByTableName(tableName, "Mapper.java");
        generateFileWithDb("mapperJavaModel.ftl", tableName,
                fileName, path, null);

        fileName = getClassNameByTableName(tableName, "SqlProvider.java");
        generateFileWithDb("mapperProviderJavaModel.ftl", tableName,
                fileName, path, null);
    }

    /**
     * 生成Bean文件
     *
     * @param table
     */
    public static void generateMapperExFile(TableConfig table) {
        String tableName = table.getTable();
        String path = getMapperXmlPath();
        mkdir(path);
        String fileName = getClassNameByTableName(tableName, "");
        fileName = getClassNameByTableName(tableName, "Mapper.xml");
        Map<String, Object> templateData = generateFileWithDb(
                "mapperXmlModel.ftl", tableName, fileName,
                path, table);

        path = getMapperJavaPath();
        mkdir(path);
        fileName = getClassNameByTableName(tableName, "Mapper.java");
        generateFileWithDb("mapperExJavaModel.ftl", tableName,
                fileName, path, table);
    }

    /**
     * 生成DO文件
     *
     * @param table
     */
    public static void generateDOFile(TableConfig table) {
        String path = getDOPath();
        mkdir(path);
        String fileName = getClassNameByTableName(table.getTable(), "DO.java");

        generateFileWithDb("doModel.ftl", table.getTable(), fileName,
                path, table);
    }

    /**
     * 生成Query文件
     *
     * @param table
     */
    public static void generateQueryFile(TableConfig table) {
        String path = getQueryPath();
        mkdir(path);
        String fileName = getClassNameByTableName(table.getTable(), "Query.java");

        generateFileWithDb("queryModel.ftl", table.getTable(),
                fileName, path, table);
    }

    /**
     * 生成Request文件
     *
     * @param table
     */
    public static void generateRequestFile(TableConfig table) {
        String path = getRequestPath();
        mkdir(path);
        String fileName = getClassNameByTableName(table.getTable(), "Request.java");

        generateFileWithDb("requestModel.ftl", table.getTable(),
                fileName, path, table);
    }

    /**
     * 生成Service文件
     *
     * @param table
     */
    public static void generateServiceFile(TableConfig table) {
        String tableName = table.getTable();

        String servicePath = getServicePath();
        mkdir(servicePath);
        String serviceFileName = getClassNameByTableName(tableName,
                "Service.java");
        generateFileWithDb("serviceModel.ftl", tableName,
                serviceFileName, servicePath, table);

        String serviceImplPath = getServiceImplPath();
        mkdir(serviceImplPath);
        String serviceImplFileName = getClassNameByTableName(tableName,
                "ServiceImpl.java");
        generateFileWithDb("serviceImplModel.ftl", tableName,
                serviceImplFileName, serviceImplPath, table);
    }

    /**
     * 生成Controller文件
     *
     * @param table
     */
    public static void generateControllerFile(TableConfig table) {
        String controllerPath = getControllerPath();
        mkdir(controllerPath);
        String controllerFileName = getClassNameByTableName(table.getTable(),
                "Controller.java");
        generateFileWithDb("controllerModel.ftl", table.getTable(),
                controllerFileName, controllerPath, table);
    }

    /**
     * 生成Ftl文件
     *
     * @param table
     */
    public static void generateFtlFile(TableConfig table) {
        String tableName = table.getTable();

        String ftlPath = getFtlPath();
        mkdir(ftlPath);

        String formFtlPath = getFtlPath() + "form/";
        mkdir(formFtlPath);

        String ftlFileName = getClassNameByTableName(tableName, ".html");
        ftlFileName = ftlFileName.substring(0, 1).toLowerCase()
                + ftlFileName.substring(1);
        generateFileWithDb("ftlListModel.ftl", tableName, ftlFileName,
                ftlPath, table);

        if (!table.getNewPageEdit()) {
            // 弹层
            String formFtlFileName = getClassNameByTableName(tableName, "Form.html");
            formFtlFileName = formFtlFileName.substring(0, 1).toLowerCase()
                    + formFtlFileName.substring(1);
            generateFileWithDb("ftlEditModel.ftl", tableName, formFtlFileName,
                    formFtlPath, table);
        } else {
            // 新页面
            String pageFormFtlFileName = getClassNameByTableName(tableName, "PageForm.html");
            pageFormFtlFileName = pageFormFtlFileName.substring(0, 1).toLowerCase()
                    + pageFormFtlFileName.substring(1);
            generateFileWithDb("ftlEditPageModel.ftl", tableName, pageFormFtlFileName,
                    formFtlPath, table);
        }
    }

    /**
     * 根据不同模板生成文件 （包含列数据）
     *
     * @param templateName
     * @param tableName
     * @param fileName
     * @param path
     */
    public static Map<String, Object> generateFileWithDb(String templateName,
                                                         String tableName,
                                                         String fileName,
                                                         String path,
                                                         TableConfig tableConfig) {
        String classPre = getClassPreByTableName(tableName);
        tableName = tableName.toUpperCase();

        Map<String, Object> templateData = dataService.getDbTemplateData(
                tableName, classPre, tableConfig);
        freeMakerUtil.generateFile(templateName, templateData, path + fileName);

        System.out.println(path + fileName + " 生成完成......");

        return templateData;
    }

    /**
     * 创建文件夹
     *
     * @param generatePath
     */
    public static void mkdir(String generatePath) {

        if (!new File(generatePath).isDirectory()) {
            try {
                new File(generatePath).mkdirs();
            } catch (Exception e) {
                System.out.println("指定路径不对...");
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成实体名称
     *
     * @param tableName
     * @return
     */
    public static String getClassPreByTableName(String tableName) {
        String[] splits = tableName.split("_");
        String classPre = "";
        for (int i = 0; i < splits.length; i++) {
            String s = splits[i].toLowerCase();
            if (i > 0) {
                classPre += s.substring(0, 1).toUpperCase() + s.substring(1);
            }
        }
        return classPre;
    }

    /**
     * 生成类名
     *
     * @param tableName
     * @param suffix
     * @return
     */
    public static String getClassNameByTableName(String tableName, String suffix) {
        String[] splits = tableName.split("_");
        String fileName = "";
        for (int i = 0; i < splits.length; i++) {
            String s = splits[i].toLowerCase();
            if (i > 0) {
                fileName += s.substring(0, 1).toUpperCase() + s.substring(1);
            }
        }
        if (suffix != null && !"".equals(suffix)) {
            return fileName + suffix;
        }
        return fileName + ".java";
    }

    /**
     * 生成配置信息
     *
     * @param tableName
     */
    public static void generateConfig(String tableName) {
        DbService dbService = new DbServiceImpl();
        List<FieldBean> fieldBeanList = dbService.getAllColums(tableName);

        String allFieldStr = "";
        for (FieldBean fieldBean : fieldBeanList) {
            if ("id".equals(fieldBean.getColumnName())
                    || "archive".equals(fieldBean.getColumnName())
                    || "createTime".equals(fieldBean.getColumnName())
                    || "updateTime".equals(fieldBean.getColumnName())
                    || "opId".equals(fieldBean.getColumnName())
                    || "opTime".equals(fieldBean.getColumnName())) {
                continue;
            }
            allFieldStr += "\"" + fieldBean.getColumnName() + "\", ";
        }

        // 过滤最后一个，号
        allFieldStr = allFieldStr.substring(0, allFieldStr.length() - 2);

        ///System.out.println("        /** ------注意：调整设置成功后请，请更新至code-config/项目名称/项目名称.txt中，并及时提交服务器，以便下次修改------ **/");
        ///System.out.println("");
        System.out.println("        /** ----------------以下配置为自动生成------------ **/");
        ///System.out.println("");
        ///System.out.println("        /** ----------------功能全局配置------------ **/");
        System.out.println("        // 设置查询条件");
        System.out.println("table.setSearchFields(new SearchField[]{new SearchField(\"\",\"\")});");
        System.out.println("        // 设置显示字段");
        System.out.println("table.setViewFields(new String[]{" + allFieldStr + "});");
        System.out.println("        // 设置新增字段");
        System.out.println("table.setAddFields(new String[]{" + allFieldStr + "});");
        System.out.println("        // 设置不能修改字段");
        System.out.println("table.setCanNotEditFields(new String[]{});");
        System.out.println("        // 导出的字段不设置不导出");
        System.out.println("table.setExportFields(new String[]{});");
        ///System.out.println("        /** ----------------功能全局配置------------ **/");


        System.out.println("        /** ----------------字段属性配置------------ **/");
        System.out.println("        // 参数说明：参数说明：字段名,页面显示名称,是否允许为空,是否可以重复,最小长度(数字时为最大最小值),最大长度");
        for (FieldBean fieldBean : fieldBeanList) {
            if ("id".equals(fieldBean.getColumnName())
                    || "archive".equals(fieldBean.getColumnName())
                    || "createTime".equals(fieldBean.getColumnName())
                    || "updateTime".equals(fieldBean.getColumnName())) {
                continue;
            }
            StringBuilder sb = new StringBuilder("table.addField(new FieldBeanEx(");
            sb.append("\"" + fieldBean.getColumnName()).append("\"" + ", ");
            sb.append("\"" + fieldBean.getColumnDesc()).append("\"" + ", ");
            sb.append("true, ");
            sb.append("true, ");
            sb.append("0, ");
            sb.append(fieldBean.getColumnLength());
            if (fieldBean.getColumnLength() == 1) {
                sb.append(").setEnumList(\"\"));");
            } else {
                sb.append("));");
            }
            System.out.println(sb.toString());
        }
        System.out.println("        /** ----------------字段属性配置------------ **/");

        System.out.print("");
        System.out.println("        /** ----------------以上配置为自动生成------------ **/");
    }
}
