package tech.aistar.day03;

import tech.aistar.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 本类功能:
 *
 * @author cxylk
 * @date 2020/8/31 18:02
 */
public class RSDemo {
    public static void main(String[] args) {
        Connection conn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;

        try {
            conn= JdbcUtil.getConnection();
            String sql="select * from s_dept";
            //为了使用pst来得到一个可滚动的，可更新的结果集对象
            pst=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs=pst.executeQuery();//默认停留在标题行

            //直接定位到最后一行
            rs.last();
            //第一行
            rs.first();

            //绝对定位到第N行
            rs.absolute(5);

            //相对定位
            //如果是正数，向下走
            //如果是负数，向上走
            rs.relative(-2);

            System.out.println(rs.getInt(1));

            //获取最后一行的行标
            System.out.println(rs.getRow());

            //通过rs光标来进行对行记录的直接修改操作
            rs.updateString(2,"cto");

            //提交
            rs.updateRow();

            //rs.deleteRow();
            //rs.updateRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst,rs);
        }
    }
}
