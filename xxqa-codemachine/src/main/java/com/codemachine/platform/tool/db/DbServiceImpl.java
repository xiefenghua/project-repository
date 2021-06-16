package com.codemachine.platform.tool.db;

import com.codemachine.platform.model.FieldBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbServiceImpl implements DbService {

    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String sqlstr = null;

    /**
     * 根据表名获取所有的列信息
     *
     * @param tableName
     * @return
     */
    @Override
    public List<FieldBean> getAllColums(String tableName) {
        if (DbConn.DATABASE_TYPE == "MYSQL") {
            return getAllColumsForMysql(tableName);
        }
        return getAllColumsForOracle(tableName);
    }

    /**
     * 根据表名获取所有的列信息(oracle数据库)
     *
     * @param tableName
     * @return
     */
    public List<FieldBean> getAllColumsForOracle(String tableName) {
        tableName = tableName.toUpperCase();

        ArrayList<FieldBean> returnList = new ArrayList<FieldBean>();

        DbConn dbConn = new DbConn();

        try {
            this.conn = dbConn.getConnection();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT CC.COLUMN_NAME, ");
            sb.append("       CC.COMMENTS, ");
            sb.append("       TC.DATA_TYPE, ");
            sb.append("       TC.DATA_PRECISION, ");
            sb.append("       TC.DATA_LENGTH, ");
            sb.append("       UCC.COLUMN_NAME P_COLUMN_NAME");
            sb.append("  FROM ALL_COL_COMMENTS CC ");
            sb.append(" INNER JOIN USER_TAB_COLUMNS TC ");
            sb.append("    ON TC.TABLE_NAME = CC.TABLE_NAME ");
            sb.append("   AND TC.COLUMN_NAME = CC.COLUMN_NAME ");
            sb.append("  LEFT JOIN USER_CONSTRAINTS UC ");
            sb.append("    ON UC.TABLE_NAME = CC.TABLE_NAME ");
            sb.append("   AND UC.CONSTRAINT_TYPE = 'P' ");
            sb.append("  LEFT JOIN USER_CONS_COLUMNS UCC ");
            sb.append("    ON UCC.CONSTRAINT_NAME = UC.CONSTRAINT_NAME ");
            sb.append(" WHERE CC.TABLE_NAME = '" + tableName + "' ");
            sb.append("   AND CC.OWNER = '" + DbConn.USERNAME.toUpperCase()
                    + "' ");

            this.sqlstr = sb.toString();
            this.st = conn.createStatement();
            this.rs = this.st.executeQuery(sqlstr);

            while (rs.next()) {
                // 数据库字段名
                String columName = rs.getString("COLUMN_NAME");
                // bean字段名
                String proName = columName;//convertField(columName);
                // 数据类型
                String dataType = getTypeName(rs.getString("DATA_TYPE"));
                // 字段描述
                String columDesc = rs.getString("COMMENTS");
                // 字段长度
                String dataPrecision = rs.getString("DATA_PRECISION");
                String dataLength = rs.getString("DATA_LENGTH");

                FieldBean fieldBean = new FieldBean();
                fieldBean.setColumnName(columName);
                fieldBean.setFieldName(columName.toLowerCase());
                fieldBean.setProName(proName);
                fieldBean.setProType(dataType);
                fieldBean.setIsPrimary(columName.equals(rs
                        .getString("P_COLUMN_NAME")));

                // 设置字段描述
                if (columDesc != null) {
                    fieldBean.setColumnDesc(columDesc);
                } else {
                    fieldBean.setColumnDesc("");
                }

                // 设置字段的长度
                if (Long.class.getSimpleName().equals(dataType)
                        && dataPrecision != null) {
                    fieldBean.setColumnLength(Long.parseLong(dataPrecision));
                } else if (String.class.getSimpleName().equals(dataType)
                        && dataLength != null) {
                    fieldBean.setColumnLength(Long.parseLong(dataLength));
                } else {
                    fieldBean.setColumnLength(-1L);
                }

                returnList.add(fieldBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeALL(conn, st, rs, pst);
        }
        return returnList;
    }

    /**
     * 根据表名获取所有的列信息(mysql数据库)
     *
     * @param tableName
     * @return
     */
    public List<FieldBean> getAllColumsForMysql(String tableName) {
        tableName = tableName.toUpperCase();

        ArrayList<FieldBean> returnList = new ArrayList<FieldBean>();

        DbConn dbConn = new DbConn();

        try {
            this.conn = dbConn.getConnection();

            StringBuilder sb = new StringBuilder();
            sb.append("SELECT TC.COLUMN_NAME, ");
            sb.append("       TC.COLUMN_COMMENT COMMENTS, ");
            sb.append("       TC.DATA_TYPE, ");
            sb.append("       TC.COLUMN_TYPE DATA_PRECISION, ");
            sb.append("       TC.COLUMN_TYPE DATA_LENGTH, ");
            sb.append("       UC.COLUMN_NAME P_COLUMN_NAME ");
            sb.append("  FROM information_schema.COLUMNS TC ");
            sb.append("  LEFT JOIN information_schema.KEY_COLUMN_USAGE UC ");
            sb.append("    ON UPPER(UC.TABLE_NAME) = UPPER(TC.TABLE_NAME) ");
            sb.append("   AND UPPER(UC.CONSTRAINT_NAME) = 'PRIMARY' ");
            sb.append(" WHERE UPPER(TC.TABLE_NAME) = '" + tableName + "' ");
            sb.append("   AND UPPER(TC.TABLE_SCHEMA) = '"
                    + DbConn.TABLE_SCHEMA.toUpperCase() + "' ");
            sb.append("   AND UPPER(UC.CONSTRAINT_SCHEMA) = '"
                    + DbConn.TABLE_SCHEMA.toUpperCase() + "' ");

            this.sqlstr = sb.toString();
            this.st = conn.createStatement();
            this.rs = this.st.executeQuery(sqlstr);

            while (rs.next()) {
                // 数据库字段名
                String columName = rs.getString("COLUMN_NAME");
                // bean字段名
                String proName = convertField(columName);
                // 数据类型
                String dataType = rs.getString("DATA_TYPE");
                // 数据库数据对应的类型
                String dbDataType = rs.getString("DATA_TYPE");
                // 字段描述
                String columDesc = rs.getString("COMMENTS");
                // 字段长度
                String dataLength = rs.getString("DATA_LENGTH");

                FieldBean fieldBean = new FieldBean();
                fieldBean.setColumnName(columName);
                fieldBean.setFieldName(columName.toLowerCase());
                fieldBean.setProName(proName);

                fieldBean.setIsPrimary(columName.equals(rs
                        .getString("P_COLUMN_NAME")));

                // 设置字段描述
                if (columDesc != null) {
                    fieldBean.setColumnDesc(columDesc);
                } else {
                    fieldBean.setColumnDesc("");
                }

                // 设置字段的长度
                if (!"datetime".equals(dataType) && !"date".equals(dataType) && !"timestamp".equals(dataType)
                        && !"decimal".equals(dataType) && !"text".equals(dataType) && !"longtext".equals(dataType)) {
                    int begin = dataLength.indexOf("(") + 1;
                    int end = dataLength.indexOf(")");
                    // System.out.println(tableName + "=======" + dataLength);
                    fieldBean.setColumnLength(Long.parseLong(dataLength
                            .substring(begin, end)));
                } else {
                    fieldBean.setColumnLength(0L);
                }

                if (fieldBean.getColumnLength() == 20 && "bigint".equals(dataType) && (dataType.indexOf("id") != -1 || dataType.indexOf("Id") != -1)) {
                    dataType = "String";
                    dbDataType = "typeHandler";
                } else if ("archive".equals(columName)) {
                    dataType = "Boolean";
                    dbDataType = "BIT";
                } else if ("date".equals(dataType) || "time".equals(dataType) || "datetime".equals(dataType) || "timestamp".equals(dataType)) {
                    dataType = "Date";
                    dbDataType = "TIMESTAMP";
                } else if ("varchar".equals(dataType)
                        || "char".equals(dataType) || "text".equals(dataType) || "longtext".equals(dataType)) {
                    if ("text".equals(dataType)) {
                        dbDataType = "CLOB";
                    } else {
                        dbDataType = "VARCHAR";
                    }
                    dataType = "String";
                } else if ("tinyint".equals(dataType)) {
                    if (fieldBean.getColumnLength() == 1) {
                        dataType = "Boolean";
                    } else {
                        dataType = "Integer";
                    }
                    dbDataType = "BIT";
                } else if ("int".equals(dataType) || "smallint".equals(dataType)) {
                    dataType = "Integer";
                    dbDataType = "INTEGER";
                } else if ("bigint".equals(dataType)) {
                    dataType = "Long";
                    dbDataType = "INTEGER";
                } else if ("decimal".equals(dataType)) {
                    dataType = "BigDecimal";
                    dbDataType = "DECIMAL";
                }

                fieldBean.setProType(dataType);
                fieldBean.setDataBaseProType(dbDataType);

                returnList.add(fieldBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeALL(conn, st, rs, pst);
        }
        return returnList;
    }

    /**
     * 根据表名获取所有的列信息
     *
     * @param userName
     * @return
     */
    public List<String> getAllTables(String userName) {

        if (DbConn.DATABASE_TYPE == "ORACLE") {
            return getAllTablesForOracle(userName);
        } else if (DbConn.DATABASE_TYPE == "MYSQL") {
            return getAllTablesForMysql(userName);
        }
        return new ArrayList<String>();
    }

    /**
     * 根据表名获取所有的列信息(oracle数据库)
     *
     * @param userName
     * @return
     */
    public List<String> getAllTablesForOracle(String userName) {

        ArrayList<String> returnList = new ArrayList<String>();

        DbConn dbConn = new DbConn();

        try {
            this.conn = dbConn.getConnection();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT TABLE_NAME FROM information_schema.ALL_TABLES WHERE OWNER = '"
                    + userName.toUpperCase() + "' ");

            this.sqlstr = sb.toString();
            this.st = conn.createStatement();
            this.rs = this.st.executeQuery(sqlstr);

            while (rs.next()) {
                // 数据库表名
                String tableName = rs.getString("TABLE_NAME");
                returnList.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeALL(conn, st, rs, pst);
        }
        return returnList;
    }

    /**
     * 根据表名获取所有的列信息(mysql数据库)
     *
     * @param tableSchema
     * @return
     */
    public List<String> getAllTablesForMysql(String tableSchema) {

        ArrayList<String> returnList = new ArrayList<String>();

        DbConn dbConn = new DbConn();

        try {
            this.conn = dbConn.getConnection();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT TABLE_NAME FROM information_schema.TABLES WHERE UPPER(TABLE_SCHEMA) = '"
                    + tableSchema.toUpperCase() + "' ");

            this.sqlstr = sb.toString();
            this.st = conn.createStatement();
            this.rs = this.st.executeQuery(sqlstr);

            while (rs.next()) {
                // 数据库表名
                String tableName = rs.getString("TABLE_NAME");
                returnList.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.closeALL(conn, st, rs, pst);
        }
        return returnList;
    }

    /**
     * 把数据库中的字段转换为变量类型 如（user_id ----> userId）
     *
     * @param field
     * @return
     */
    public String convertField(String field) {
        return field;
//        // 分隔符
//        char separator = '_';
//        // 转化为小写
//        String variable = field.toLowerCase();
//
//        if (variable.indexOf(separator) > -1) {
//            char[] varArray = variable.toCharArray();
//            for (int i = 0; i < varArray.length; i++) {
//                if (varArray[i] == separator && i < varArray.length - 1) {
//                    varArray[i + 1] = Character.toUpperCase(varArray[i + 1]);
//                }
//            }
//            variable = new String(varArray).replaceAll("_", "");
//        }
//
//        return variable;
    }

    /**
     * 获取字符串型的类型名
     *
     * @param type
     * @return
     */
    private String getTypeName(String type) {

        String typeName = String.class.getSimpleName();

        if ("VARCHAR2".equals(type)) {
            typeName = String.class.getSimpleName();
        } else if ("NUMBER".equals(type)) {
            typeName = Long.class.getSimpleName();
        } else if ("DATE".equals(type)) {
            typeName = Date.class.getSimpleName();
        } else {
            typeName = String.class.getSimpleName();
        }
        return typeName;

    }

}
