package tech.aistar.day01.dao.impl;

import tech.aistar.day01.dao.IUserDao;
import tech.aistar.day01.entity.Gender;
import tech.aistar.day01.entity.User;
import tech.aistar.util.JdbcUtil;

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
//        Connection conn = null;
//        Statement st = null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            String url = "jdbc:mysql://localhost:3306/j08";
//            String username = "root";
//            String password = "root";
//
//            conn = DriverManager.getConnection(url, username, password);
//            st = conn.createStatement();
//
//            String birthday = String.format("%tF", user.getBirthday());
//            String sql = "insert into tbl_user(username,password,birthday,sex) values('"+user.getUsername()+"','"+user.getPassword()+"','"+birthday+"','"+user.getGender().toString()+"')";
//
//            int count = st.executeUpdate(sql);
//
//            System.out.println("成功插入" + count + "行");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (null != st) {
//                try {
//                    st.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (null != conn) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        Connection conn=null;
        PreparedStatement pst=null;

        try {
            conn=JdbcUtil.getConnection();
            String sql="insert into tbl_user(username,password,birthday,sex) values(?,?,?,?)";

            //把带有占位符的sql语句发送到DB-Server端进行预编译
            pst=conn.prepareStatement(sql);

            //设置参数
            pst.setString(1,user.getUsername());
            pst.setString(2,user.getPassword());
            pst.setDate(3,new java.sql.Date(user.getBirthday().getTime()));
            pst.setString(4,user.getGender().toString());

            //真正发送参数到DB-Server端，但是sql语句不需要编译了
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users=new ArrayList<>();
        Connection conn=null;
        Statement st=null;
        ResultSet rs=null;

        try {
            //加载驱动
            conn=JdbcUtil.getConnection();

            //获取语句对象
            st=conn.createStatement();

            //发送sql语句
            rs=st.executeQuery("select * from tbl_user");

            //处理结果集
            while(rs.next()){
                //每读取一行，就创建一个对象
                User user=new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));

                //rs.getDate方法返回的是java.sql.Date
                // 实体类中的birthday是java.util.Date
                //java.sql.Date extends java.util.Date
                user.setBirthday(rs.getDate(4));

                //将字符串转换成枚举类型
                Gender gender=Enum.valueOf(Gender.class,rs.getString(5));
                user.setGender(gender);

                //将对象添加到集合中
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,st,rs);
        }
        return users;

//        List<User> users=new ArrayList<>();
//        Connection conn=null;
//        PreparedStatement pst=null;
//        ResultSet rs=null;
//        try {
//            conn=JdbcUtil.getConnection();
//            String sql="select * from tbl_user";
//            pst=conn.prepareStatement(sql);
//            rs=pst.executeQuery();
//            while(rs.next()){
//                User user=new User();
//                user.setId(rs.getInt(1));
//                user.setUsername(rs.getString(2));
//                user.setPassword(rs.getString(3));
//
//                user.setBirthday(rs.getDate(4));
//                Gender gender=Enum.valueOf(Gender.class,rs.getString(5));
//                user.setGender(gender);
//                users.add(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            JdbcUtil.close(conn,pst,rs);
//        }
//        return users;
    }

    @Override
    public User findByUserName(String uname, String pwd) {
//        User user = null;
//
//        Connection conn = null;
//        Statement st = null;
//        ResultSet rs = null;
//
//        //Class.forName("com.mysql.cj.jdbc.Driver");
//        String url = "jdbc:mysql://localhost:3306/j08";
//        String username = "root";
//        String password = "root";
//
//        try {
//            conn = DriverManager.getConnection(url, username, password);
//
//            //3.创建语句对象
//            st=conn.createStatement();
//
//            String sql="select * from tbl_user where username='"+uname+"' and password='"+pwd+"'";
//
//            System.out.println(sql);
//            //4.发送sql语句
//            rs=st.executeQuery(sql);
//
//            //5.处理结果集对象 - 由于username是单个记录
//            if(rs.next()){
//                user=new User();
//
//                Integer id=rs.getInt(1);
//                String unames=rs.getString(2);
//                String pwds=rs.getString(3);
//
//                Date birthday=rs.getDate(4);
//                String sex=rs.getString(5);
//
//                user.setId(id);
//                user.setUsername(unames);
//                user.setPassword(pwds);
//                user.setBirthday(birthday);
//
//                Gender gender=Enum.valueOf(Gender.class,sex);
//                user.setGender(gender);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            JdbcUtil.close(conn,st,rs);
//        }
//        return user;

        //使用PreparedStatement防止SQL注入
        User user=null;
        Connection conn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;

        try {
            conn=JdbcUtil.getConnection();
            String sql="select * from tbl_user where username=? and password=?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,uname);
            pst.setString(2,pwd);
            rs=pst.executeQuery();
            if(rs.next()){
                user=new User();

                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setBirthday(rs.getDate(4));
                Gender gender=Enum.valueOf(Gender.class,rs.getString(5));
                user.setGender(gender);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst,rs);
        }
        return user;
    }

    @Override
    public void saveList(List<User> users) {
        Connection conn=null;

        PreparedStatement pst=null;

        String url="jdbc:mysql://localhost:3306/j08";
        String username="root";
        String password="root";

        try {
            conn=DriverManager.getConnection(url,username,password);
            String sql="insert into tbl_user(username,password,birthday,sex) values(?,?,?,?)";
            //创建预编译语句对象
            //背后 - 把带有占位符的sql语句发送到DB-server端进行预编译了
            pst=conn.prepareStatement(sql);

            //设置参数 - 同构的sql,只是参数不一样
            for (User user : users) {
                pst.setString(1,user.getUsername());
                pst.setString(2,user.getPassword());
                //java.util.Date->java.sql.Date
                pst.setDate(3,new java.sql.Date(user.getBirthday().getTime()));
                pst.setString(4,user.getGender().toString());

                //真正发送参数DB-server端，但是sql语句不需要编译了
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            JdbcUtil.close(conn,pst);
        }

    }

    @Override
    public User getById(Integer id) {
        User user=null;
        Connection conn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;

        try {
            conn=JdbcUtil.getConnection();
            String sql="select * from tbl_user where id=?";
            pst=conn.prepareStatement(sql);
             pst.setInt(1,id);

            rs=pst.executeQuery();

            if(rs.next()){
                user=new User();

                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setBirthday(rs.getDate(4));
                user.setGender(Enum.valueOf(Gender.class,rs.getString(5)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst,rs);
        }
        return user;

//        User user=null;
//        Connection conn=null;
//        PreparedStatement pst=null;
//
//        ResultSet rs=null;
//
//        try {
//            conn=JdbcUtil.getConnection();
//            String sql="select * from tbl_user where id=?";
//            pst=conn.prepareStatement(sql);
//            pst.setInt(1,id);
//            rs=pst.executeQuery();
//            if(rs.next()){
//                user=new User();
//                user.setId(rs.getInt(1));
//                user.setUsername(rs.getString(2));
//                user.setPassword(rs.getString(3));
//                user.setBirthday(rs.getDate(4));
//                Gender gender=Enum.valueOf(Gender.class,rs.getString(5));
//                user.setGender(gender);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            JdbcUtil.close(conn,pst,rs);
//        }
//        return user;
    }

    @Override
    public void delById(Integer id) {
        Connection conn=null;
        PreparedStatement pst=null;

        try {
            conn=JdbcUtil.getConnection();
            String sql="delete from tbl_user where id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst);
        }

//        Connection conn=null;
//        PreparedStatement pst=null;

//        try {
//            conn=JdbcUtil.getConnection();
//            String sql="delete from tbl_user where id=?";
//            pst=conn.prepareStatement(sql);
//            pst.setInt(1,id);
//            pst.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            JdbcUtil.close(conn,pst);
//        }
    }

    @Override
    public void update(User user) {
        Connection conn=null;
        PreparedStatement pst=null;

        try {
            conn=JdbcUtil.getConnection();
            String sql="update tbl_user set username=?,password=?,birthday=?,sex=? where id=?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,user.getUsername());
            pst.setString(2,user.getPassword());
            pst.setDate(3, new java.sql.Date(user.getBirthday().getTime()));
            pst.setString(4,user.getGender().toString());
            pst.setInt(5,user.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst);
        }

//        Connection conn=null;
//        PreparedStatement pst=null;
//
//        try {
//            conn=JdbcUtil.getConnection();
//            String sql="update tbl_user set username=?,password=?,birthday=?,sex=? where id=?";
//            pst=conn.prepareStatement(sql);
//            pst.setString(1,user.getUsername());
//            pst.setString(2,user.getPassword());
//            pst.setDate(3,new java.sql.Date(user.getBirthday().getTime()));
//            pst.setString(4,user.getGender().toString());
//            pst.setInt(5,user.getId());
//
//            pst.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            JdbcUtil.close(conn,pst);
//        }
    }
}
