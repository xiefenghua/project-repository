package com.codemachine.platform.tool.db;

import java.sql.*;

public class DbConn {

    // 定义一个连接对象
    private Connection conn = null;
    // 定义连接数据库的url资源
    public static String URL = "";
    // 定义连接数据库的用户名和密码
    public static String USERNAME = "";
    public static String PASSWORD = "";
    public static String DATABASE_TYPE = "";// 数据库类型:ORACLE,MYSQL
    public static String TABLE_SCHEMA = "";// 数据库名称(mysql使用)

    // 加载数据库连接驱动
    public Connection getConnection() {
        try {
            if (DATABASE_TYPE == "MYSQL") {
                Class.forName("com.mysql.jdbc.Driver");
            } else {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            }

            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 关闭数据库
    public void closeALL(Connection conn, Statement st, ResultSet rs,
                         PreparedStatement pst) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
