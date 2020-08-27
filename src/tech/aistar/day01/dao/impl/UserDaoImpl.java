package tech.aistar.day01.dao.impl;

import tech.aistar.day01.dao.IUserDao;
import tech.aistar.day01.entity.Gender;
import tech.aistar.day01.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<User> findAll() {
        List<User> users=new ArrayList<>();
        Connection conn=null;
        Statement st=null;
        ResultSet rs=null;

        //1.加载驱动 - 省略。。。
        //2.获取conn对象
        String url="jdbc:mysql://localhost:3306/j08";
        String username="root";
        String password="root";

        try {
            conn=DriverManager.getConnection(url,username,password);

            //3.创建语句对象
            st=conn.createStatement();

            //4.发送sql语句
            rs=st.executeQuery("select * from tbl_user");

            //5.处理结果集对象
            while(rs.next()){
                //1.每读取一行，创建一个对象
                User user=new User();

                Integer id=rs.getInt(1);
                String uname=rs.getString(2);
                String pwd=rs.getString(3);

                //rs.getDate方法返回的是java.sql.Date
                //实体类中的birthday是java.util.Date
                //java.sql.Date extends java.util.Date
                Date birthday=rs.getDate(4);

                String sex=rs.getString(5);

                user.setId(id);
                user.setUsername(uname);
                user.setPassword(pwd);
                user.setBirthday(birthday);

                //把枚举类型转换成字符串类型
                Gender gender=Enum.valueOf(Gender.class,sex);

                user.setGender(gender);

                //将各个user对象存储到users集合中去
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
        return users;
    }
}
