package tech.atistar.dao.impl;

import org.junit.jupiter.api.Test;
import tech.aistar.dao.IOrderDao;
import tech.aistar.dao.impl.OrderDaoImpl;
import tech.aistar.pojo.entity.Ord;

import java.util.List;

/**
 * 本类功能:
 *
 * @author cxylk
 * @date 2020/8/29 14:09
 */
public class TestOrderDao {
    IOrderDao orderDao=new OrderDaoImpl();

    @Test
    public void testFindByPages(){
        //total为null的时候,按照顺序分页，分3页，每页4条
        //total不为null的时候，按照total分页，第一页就是满足大于total条件的数据，可能不是连续的
        List<Ord> ordList=orderDao.findByPages(105.0d,3,4);

        if(null!=ordList&&ordList.size()>0){
            for (Ord ord : ordList) {
                System.out.println(ord);
            }
        }else{
            System.out.println("null");
        }
    }

    @Test
    public void testGetRows(){
        long count=orderDao.getRows(105.0);
        System.out.println(count);
    }
}
