package tech.aistar.dao.impl;

import tech.aistar.dao.ICustomerDao;
import tech.aistar.pojo.dto.CustomerDTO;
import tech.aistar.pojo.entity.Customer;
import tech.aistar.pojo.entity.Ord;
import tech.aistar.util.JdbcUtil;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * 本类功能:顾客接口的实现类
 *
 * @author cxylk
 * @date 2020/8/28 19:31
 */
public class CustomerDaoImpl implements ICustomerDao {
    @Override
    public void saveCascade(Customer c) {
        Connection conn=null;

        //一个pst对象只能编译一条语句
        PreparedStatement pst=null;

        ResultSet rstKey=null;

        try {
            conn= JdbcUtil.getConnection();

            //设置手动提交
            conn.setAutoCommit(false);

            String sql="insert into customer(cname,phone) values(?,?)";
            //不加第二个参数的话，下面获取主键列就会报错
            pst=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,c.getCname());
            pst.setString(2,c.getPhone());

            pst.executeUpdate();

            Set<Ord> ordSet=c.getOrders();
            if(null!=ordSet&&ordSet.size()>0) {
                rstKey = pst.getGeneratedKeys();
                rstKey.next();
                Integer cid=rstKey.getInt(1);
//              System.out.println(cid);

                //上一条语句已经执行完了，把他关闭
                pst.close();
                //======
                //继续执行下一条语句
                pst = conn.prepareStatement("insert into ord(ordno,total,create_date,customer_id) values (?,?,?,?)");

                //遍历订单信息
                for (Ord ord : ordSet) {
                    pst.setString(1,ord.getOrdno());;
                    pst.setDouble(2,ord.getTotal());
                    pst.setDate(3,new java.sql.Date(ord.getCreateDate().getTime()));
                    pst.setInt(4,cid);
                    pst.executeUpdate();
                }
            }
            //手动提交事务(不然插不进去)
            conn.commit();
        } catch (Exception e) {
            if(null!=conn){
                try {
                    //只要出现异常，让事务回滚
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst,rstKey);
        }
    }

    @Override
    public List<Customer> findAllCascade() {
        List<Customer> customers=new ArrayList<>();
        Connection conn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;

        //创建一个Map集合
        Map<Integer,Customer> maps=new HashMap<>();

        try {
            conn=JdbcUtil.getConnection();
            String sql="select c.id cid,c.cname cname,c.phone,o.* from customer c\n" +
                    "left join ord o on c.id=o.customer_id";
            pst=conn.prepareStatement(sql);

            rs=pst.executeQuery();

            while(rs.next()){
                //1.获取客户cid
                Integer cid=rs.getInt(1);

                Customer c=null;

                //判断Maps集合中是否已经包含了该客户
                if(maps.containsKey(cid)){
                    //根据key获取customer对象 - 集合中已经包含的
                    c=maps.get(cid);
                }else{
                    //创建一个新的客户对象
                    c=new Customer();
                    c.setId(cid);
                    c.setCname(rs.getString(2));
                    c.setPhone(rs.getString(3));
                }
                //2.其余所有的属性
                Integer oid=rs.getInt(4);

                //判断该客户是否存在订单信息
                String ordno=rs.getString(5);
                if(null!=ordno){
                    Ord o=new Ord();
                    o.setId(oid);
                    o.setOrdno(rs.getString(5));
                    o.setTotal(rs.getDouble(6));
                    o.setCreateDate(rs.getDate(7));

                    //把订单的信息绑定到customer对象
                    Set<Ord> ords=null;
                    //如果该客户有订单
                    if(maps.containsKey(cid)){
                        ords=c.getOrders();
                    }else{
                        ords=new HashSet<>();
                    }
                    ords.add(o);

                    c.setOrders(ords);
                }

                maps.put(cid,c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst,rs);
        }
        //把maps集合中的所有的value全部获取到ArrayList中去
        return new ArrayList<>(maps.values());
    }

    @Override
    public List<Customer> ListCascade() {
        List<Customer> customers=new ArrayList<>();
        Connection conn=null;
        PreparedStatement pst=null;
        ResultSet rs1=null;
        ResultSet rs2=null;

        String sql="select * from customer";
        try {
            conn=JdbcUtil.getConnection();
            pst=conn.prepareStatement(sql);

            rs1=pst.executeQuery();

            while(rs1.next()){
                Customer c=new Customer();
                Integer cid=rs1.getInt(1);
                c.setId(cid);
                c.setCname(rs1.getString(2));
                c.setPhone(rs1.getString(3));

                //立即去查询ord表 - customer_id
                String sql2="select * from ord where customer_id=?";

                pst=conn.prepareStatement(sql2);

                pst.setInt(1,cid);
                rs2=pst.executeQuery();

                Set<Ord> ords=new HashSet<>();

                while(rs2.next()){
                    Ord o=new Ord();
                    o.setId(rs2.getInt(1));
                    o.setOrdno(rs2.getString(2));
                    o.setTotal(rs2.getDouble(3));
                    o.setCreateDate(rs2.getDate(4));

                    ords.add(o);
                }

                //把订单绑定到c
                c.setOrders(ords);

                customers.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(null,null,rs2);
            JdbcUtil.close(conn,pst,rs1);
        }
        return customers;
    }

    @Override
    public void delByIdCascade(Integer id) {
        Connection conn=null;
        PreparedStatement pst=null;

        try {
            conn=JdbcUtil.getConnection();

            //设置手动提交
            conn.setAutoCommit(false);

            //先删除多的一方
            String sql="delete from ord where customer_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            pst.executeUpdate();

            //关闭已经执行完的pst对象
            pst.close();

            //再删除少的一方
            sql="delete from customer where id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,id);

            pst.executeUpdate();

            conn.commit();
        } catch (Exception e) {
            if(null!=conn){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<CustomerDTO> countOrders() {
        List<CustomerDTO> dtos=new ArrayList<>();

        Connection conn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;

        try {
            conn=JdbcUtil.getConnection();
            String sql="select c.id,c.cname,count(o.id) from customer c " +
                    "left join ord o on c.id = o.customer_id group by c.id,c.cname";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                CustomerDTO dto=new CustomerDTO();
                dto.setId(rs.getInt(1));
                dto.setCname(rs.getString(2));
                dto.setCount(rs.getLong(3));
                dtos.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst,rs);
        }
        return dtos;
    }
}
