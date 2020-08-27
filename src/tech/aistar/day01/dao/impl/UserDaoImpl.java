package tech.aistar.day01.dao.impl;

import tech.aistar.day01.dao.IUserDao;
import tech.aistar.day01.entity.Gender;
import tech.aistar.day01.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * 本类功能:DAO的实现层
 *
 * @author cxylk
 * @date 2020/8/27 12:05
 */
public class UserDaoImpl implements IUserDao {
    @Override
    public void save(User user) {
        Connection conn = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/j08";
            String username = "root";
            String password = "root";

            conn = DriverManager.getConnection(url, username, password);
            st = conn.createStatement();

            String birthday = String.format("%tF", user.getBirthday());
            String sql = "insert into tbl_user(username,password,birthday,sex) values('"+user.getUsername()+"','"+user.getPassword()+"','"+birthday+"','"+user.getGender().toString()+"')";

            int count = st.executeUpdate(sql);

            System.out.println("成功插入" + count + "行");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != st) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        User user=new User("admin","123",new Date(), Gender.F);
        IUserDao dao=new UserDaoImpl();
        dao.save(user);
    }
}
