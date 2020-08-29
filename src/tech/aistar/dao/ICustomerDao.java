package tech.aistar.dao;

import tech.aistar.pojo.dto.CustomerDTO;
import tech.aistar.pojo.entity.Customer;

import java.util.List;

/**
 * 本类功能:顾客的dao接口
 *
 * @author cxylk
 * @date 2020/8/28 19:26
 */
public interface ICustomerDao {

    /**
     * 保存一个客户的同时,保存这个客户拥有的所有的订单信息 - [级联插]
     * 级联DML操作的话 - 同时成功，同时失败
     * @param c
     */
    void saveCascade(Customer c);

    /**
     * 查询所有的客户信息,如果这个客户拥有订单信息,那么也要加载出这个客户的订单信息(级联查)
     * 级联查询
     * @return
     */
    List<Customer> findAllCascade();

    /**
     * 级联查询 - 第二种方法(偶然和巧合性)
     * @return
     */
    List<Customer> ListCascade();

    /**
     * 根据客户id进行删除,如果这个客户拥有订单信息
     * 也要将该客户的订单信息全部删除(级联删)
     * 级联删除
     * @param id
     */
    void delByIdCascade(Integer id);

    /**
     * 统计每个客户对应的订单个数
     * 统计每个客户拥有的订单个数-> id cname,订单数量 -> 返回出去.
     * @return
     */
    List<CustomerDTO> countOrders();
}
