package tech.aistar.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 本类功能:
 *
 * @author cxylk
 * @date 2020/8/31 15:39
 */
public class JdbcTemplate {
    /**
     * 执行所有的DML操作
     * @param pcb
     */
    public static void executeUpdate(IPrepareCallback pcb){
        Connection conn=null;
        PreparedStatement pst=null;

        try {
            conn=JdbcUtil.getConnection();

            //String sql="";
            //pst=conn.preparedStatement(sql);
            //发送参数
            pst=pcb.handler(conn);

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst);
        }
    }

    /**
     * 执行的是一个DQL- 返回的是单个对象
     * @param pcb
     * @param rsc
     * @return
     */
//    public static Object executeQuery(IPrepareCallback pcb,IResultSetCallback rsc){
//        Connection conn=null;
//        PreparedStatement pst=null;
//        ResultSet rs=null;
//
//        Object obj=null;
//
//        try {
//            conn=JdbcUtil.getConnection();
//
//            //String sql="";
//            //pst..
//            //处理结果集
//
//            pst=pcb.handler(conn);//处理pst
//
//            rs=pst.executeQuery();
//
//            obj=rsc.executeQuery(rs);//处理结果集对象
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            JdbcUtil.close(conn,pst,rs);
//        }
//        return obj;
//    }

    /**
     * 查询单个
     * @param pcb
     * @param rsc
     * @param <T>
     * @return
     */
    public static <T> T executeQuery(IPrepareCallback pcb,IResultSetCallback<T> rsc){
        Connection conn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;

        T obj=null;

        try {
            conn=JdbcUtil.getConnection();

            //String sql="";
            //pst

            //处理结果集

            pst=pcb.handler(conn);//处理pst

            rs=pst.executeQuery();

            obj=rsc.executeQuery(rs);//处理结果集对象
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst,rs);
        }
        return obj;
    }

    /**
     * 查询多个
     * @param pcb
     * @param rsc
     * @param <T>
     * @return
     */
    public static <T> List<T> executeList(IPrepareCallback pcb, IResultListCallback<T> rsc){
        Connection conn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;

        List<T> obj=null;

        try {
            conn=SimplePools.getConnection();

            pst=pcb.handler(conn);//处理pst

            rs=pst.executeQuery();

            obj=rsc.executeQuery(rs);//处理结果集对象
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst,rs);
        }
        return obj;
    }

    public static void executeUpdate(String sql,Object... params){
        Connection conn=null;
        PreparedStatement pst=null;
        try {
            conn=SimplePools.getConnection();

            pst=conn.prepareStatement(sql);

            //解决发送参数的问题
            if(null!=params&&params.length>0){
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i+1,params[i]);
                }
            }
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst);
        }
    }
}
