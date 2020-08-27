package tech.aistar.day01;

import java.sql.*;

/**
 * 本类功能:jdbc六大编程步骤
 *
 * @author cxylk
 * @date 2020/8/27 10:27
 */
public class HelloJdbc {
    public static void main(String[] args) {
        //1.加载驱动 - jdbc4.0规范开始，可以省略不写
        Connection conn=null;
        Statement st=null;
        ResultSet rs=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2.获取连接对象Connection
            //2-1.准备三个连接对象
            String url="jdbc:mysql://localhost:3306/j08";
            String username="root";
            String password="root";

            conn= DriverManager.getConnection(url,username,password);

            //3.创建语句对象Statement
            //作用 - 负责将sql语句发送到数据库端,由Db在server端对sql语句进行编译和解释
            //ResultSet executeQuery(String sql);//用来执行DQL语句
            //Int executeUpdate(String sql)//执行DML语句
            st=conn.createStatement();

            //4."执行"-SQL,执行的是DQL,返回一个结果集对象
            //4-1 准备sql语句
            String sql="select id,first_name,salary from s_emp";
            rs=st.executeQuery(sql);

            //5.处理结果集对象
            //ResultSet结果集对象的本质是游标，默认是停留在标题行，通过boolean next();//光标向下移动一行，下一行没有则返回false
            while(rs.next()){
                //分别获取查询列的数据，一列一列进行获取

                //鼓励用列的序号获取，性能稍微比通过列的名称高

                //调用get方法，但是一定要是db表中列的数据类型
                int id=rs.getInt(1);//根据查询列在查询语句的序号的位置获取

                //根据列的标题进行获取，一定要注意，如果列取了别名，通过别名获取
                String firstName=rs.getString("first_name");//可读性强一点
                double salary=rs.getDouble(3);

                System.out.printf("编号:[%d],名字:[%s],工资:[%.2f]\n",id,firstName,salary);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //6.释放资源 - 关闭对象的
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
}
