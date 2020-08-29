package tech.atistar.dao.impl;

import org.junit.jupiter.api.Test;
import tech.aistar.dao.ICustomerDao;
import tech.aistar.dao.impl.CustomerDaoImpl;
import tech.aistar.pojo.dto.CustomerDTO;
import tech.aistar.pojo.entity.Customer;
import tech.aistar.pojo.entity.Ord;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 本类功能:
 *
 * @author cxylk
 * @date 2020/8/29 8:46
 */
public class TestCustomerDao {
    ICustomerDao dao=new CustomerDaoImpl();
    @Test
    public void testSaveCascade(){
        Customer customer=new Customer("admin","92480");
        //dao.saveCascade(customer);

        Set<Ord> ordSet=new HashSet<>();

        for (int i = 0; i < 20; i++) {
            Ord oo=new Ord(Double.valueOf(100+i),new Date());
            ordSet.add(oo);
        }
        customer.setOrders(ordSet);
        dao.saveCascade(customer);
    }

    @Test
    public void testFindAllCascade(){
        //List<Customer> customers=dao.findAllCascade();
        List<Customer> customers=dao.ListCascade();

        if(null!=customers&&customers.size()>0){
            for (Customer c : customers) {
                System.out.println(c);

                Set<Ord> ords=c.getOrders();
                if(null!=ords&&ords.size()>0){
                    System.out.println("\t订单信息:>");
                    for (Ord ord : ords) {
                        System.out.println("\t"+ord);
                    }
                }
            }
        }
    }

    @Test
    public void testDelByIdCascade(){
        dao.delByIdCascade(7);
    }

    @Test
    public void testCountOrders(){
        dao.countOrders().forEach(System.out::println);
    }
}
