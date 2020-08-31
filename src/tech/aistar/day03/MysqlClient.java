package tech.aistar.day03;

import tech.aistar.util.JdbcUtil;

import java.sql.*;
import java.util.Scanner;

/**
 * 本类功能:mysql客户端
 *
 * @author cxylk
 * @date 2020/8/31 10:59
 */
public class MysqlClient {
    public static void main(String[] args) {
        createSql();
    }
    public static void createSql(){
        //1.创建一个StringBuilder用来拼接sql语句
        StringBuilder sb=new StringBuilder();
        //2.获取键盘输入
        Scanner sc=new Scanner(System.in);
        System.out.print("mysql> ");
        while(true){
            String sql=sc.nextLine();
            sb.append(sql+" ");

            //判断sql是否结束了
            if(sql.trim().endsWith(";")){
                //System.out.println(sb);

                client(sb.deleteCharAt(sb.length()-2).toString().trim());

                //清空一下
                sb.delete(0,sb.capacity());
                System.out.print("mysql> ");
            }
            else{
                System.out.print("     -> ");
            }
        }
    }

    private static void client(String sql) {
        Connection conn=null;
        Statement st=null;
        ResultSet rs=null;

        try {
            conn= JdbcUtil.getConnection();
            st=conn.createStatement();

            //判断sql是DML还是DQL
            boolean flag=st.execute(sql);
            if(flag){//DQL - 返回true
                //拿到结果集对象 - 结果集元数据
                rs=st.getResultSet();
                //获取结果集元数据
                ResultSetMetaData rsmd=rs.getMetaData();
                //得到查询的个数
                int cols=rsmd.getColumnCount();
                //遍历它
                for (int i = 1; i <=cols ; i++) {
                    //获取列的名称
                    String colName=rsmd.getColumnName(i);
                    System.out.printf("%-12s",colName);
                }
                System.out.println();

                //输出每一行每列的数据
                while(rs.next()){
                    for (int i = 1; i <=cols ; i++) {
                        String colValue=rs.getString(i);
                        System.out.printf("%-12s",colValue);
                    }
                    System.out.println();
                }
            }else{
                //执行非DQL语句
                //只需要拿到受影响的行数即可
                int rows=st.getUpdateCount();

                System.out.println("Query OK, "+rows+" row affected (0.00 sec)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,st,rs);
        }
    }
}
