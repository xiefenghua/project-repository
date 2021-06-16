package com.codemachine.platform.main;

import com.codemachine.platform.model.TableConfig;
import com.codemachine.platform.tool.db.DbConn;

/**
 * @author <a href="mailto:Guanyp@hiwitech.com">Guanyp</a>
 * @date 2020/12/10
 */
public class RunEntrance {

    /**
     * 生成文件的路径 必须填写(本地工程的路径)
     */
    public static final String CODE_PATH = "D:\\workspace\\xxqa-server-v2\\";
    /**
     * 模块名前缀
     */
    public static final String MODULE_PREFIX = "xxqa-";

    /**
     * 初始化配置
     */
    public static void initConfig() {
        /*********数据库链接***********/
        DbConn.DATABASE_TYPE = "MYSQL";
        DbConn.URL = "jdbc:mysql://rm-bp1103v82bws9yn2r4o.mysql.rds.aliyuncs.com:3306/xxqa_dev?autoCommit=true&useUnicode=true&autoReconnect=true" +
                "&allowMultiQueries=true";
        DbConn.USERNAME = "xxqa";
        DbConn.PASSWORD = "xxqa_2020!@#";
        DbConn.TABLE_SCHEMA = "xxqa_dev";

        /***包名,适配不同的项目****/
        CodeMeachine.PACKAGE_NAME = "com.security.im.usercenter";
        /***适配不同的模版引擎 freemaker 或 velocity****/
        CodeMeachine.TEMPLATE_ENGINE = "freemaker";
        /***服务名称****/
        CodeMeachine.SERVER_NAME = "usercenter";
        // 初始化参数
        initParam();
    }


    public static void main(String[] args) {
        /***初始化配置文件***/
        initConfig();

        /*******1.配置功能信息********/
        //模块名称
        CodeMeachine.MODULE_NAME = "sm";
        String tableName = "sm_operator";

        // 参数说明  表名；功能名称；是否新页面编辑
        TableConfig table = new TableConfig(tableName, "区域编码表", false);

        /********2.需要生成 <<EX扩展代码>> EX扩展代码时 配置字段信息,否则不需要********/
        //1.获得字段初始化配置
        CodeMeachine.generateConfig(tableName);

        /** ----------------以下配置为自动生成------------ **/
        // 设置显示字段
        table.setViewFields(new String[]{});
        // 设置新增字段
        table.setAddFields(new String[]{});
        // 导出的字段不设置不导出
        table.setExportFields(new String[]{});
        /** ----------------字段属性配置------------ **/


        /** ----------------字段属性配置------------ **/
        /** ----------------以上配置为自动生成------------ **/

        //3.代码生成
        generateBase(tableName);
        //修改表字段的时候需要注释,新表首次需要放开
        generateEx(table);
    }

    /**
     * 生成扩展-不可以覆盖生成
     *
     * @param table
     */
    public static void generateEx(TableConfig table) {
        if (table == null) {
            throw new RuntimeException("table 参数不能为空");
        }
        /****************** 根据自己的实际需要生成为对应的文件(说明请查看:readme文件) ***********************/
        CodeMeachine.FILE_PATH = CODE_PATH + MODULE_PREFIX + CodeMeachine.SERVER_NAME;
        CodeMeachine.generateDOFile(table);
        CodeMeachine.generateMapperExFile(table);
        CodeMeachine.generateQueryFile(table);
        CodeMeachine.generateRequestFile(table);
        CodeMeachine.generateServiceFile(table);
    }

    /**
     * 生成基础对象-可以覆盖生成
     * D:/workspace/workspace_git/avic-backend
     *
     * @param tableName 表名
     */
    public static void generateBase(String tableName) {
        CodeMeachine.FILE_PATH = CODE_PATH + MODULE_PREFIX + CodeMeachine.SERVER_NAME;
        CodeMeachine.generateBeanFile(tableName);
    }

    /**
     * 此方法中参数无需调整
     */
    public static void initParam() {
    }
}
