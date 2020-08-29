package tech.aistar.dao.impl;

import tech.aistar.dao.IOrderDao;
import tech.aistar.pojo.entity.Ord;
import tech.aistar.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 本类功能:
 *
 * @author cxylk
 * @date 2020/8/29 13:38
 */
public class OrderDaoImpl implements IOrderDao {
    @Override
    public List<Ord> findByPages(Double total, Integer pageNow, Integer pageSize) {
        List<Ord> ords=new ArrayList<>();

        Connection conn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;

        try {
            conn= JdbcUtil.getConnection();
            String sql="select * from ord where 1=1";//没有条件就查所有的
            //有条件就查这个
            if(null!=total){
                sql+=" and total>?";
            }
            sql+=" limit ?,?";

            pst=conn.prepareStatement(sql);
            if(null!=total){
                pst.setDouble(1,total);
                pst.setInt(2,(pageNow-1)*pageSize);
                pst.setInt(3,pageSize);
            }else{
                pst.setInt(1,(pageNow-1)*pageSize);
                pst.setInt(2,pageSize);
            }

            rs=pst.executeQuery();
            while(rs.next()){
                Ord ord=new Ord();
                ord.setId(rs.getInt(1));
                ord.setOrdno(rs.getString(2));
                ord.setTotal(rs.getDouble(3));
                ord.setCreateDate(rs.getDate(4));

                //==如果是双向关联的话(这题是单向关联就不需要)
                //把订单的客户信息也要绑定进去
                //Integer cid=rs.getInt(5);
                //调用根据cid得到客户对象的方法

                ords.add(ord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst,rs);
        }
        return ords;
    }

    @Override
    public long getRows(Double total) {
        Connection conn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        long count=0L;
        try {
            conn=JdbcUtil.getConnection();
            String sql="select count(*) from ord where 1=1";
            if(null!=total){
                sql+=" and total>?";
                pst=conn.prepareStatement(sql);
                pst.setDouble(1,total);
            }else{
                pst=conn.prepareStatement(sql);
            }
            rs=pst.executeQuery();
            if(rs.next()){
                count=rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
