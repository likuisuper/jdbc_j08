package tech.aistar.util;

import java.sql.*;

/**
 * 本类功能:jdbc工具类
 *
 * @author cxylk
 * @date 2020/8/27 15:13
 */
public class JdbcUtil {
    public static Connection getConnection() throws SQLException {
        //1.加载驱动
        String url="jdbc:mysql://localhost:3306/j08";
        String username="root";
        String password="root";

        Connection conn= DriverManager.getConnection(url,username,password);

        return conn;
    }

    public static void close(Connection conn, Statement st){
        if(null!=st){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(null!=conn){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn, Statement st, ResultSet rs){
        if(null!=rs){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(null!=st){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(null!=conn){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
