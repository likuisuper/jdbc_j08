package tech.atistar.service;

import org.junit.jupiter.api.Test;
import tech.aistar.pojo.dto.PageBean;
import tech.aistar.pojo.entity.Ord;
import tech.aistar.service.IOrderService;
import tech.aistar.service.impl.OrderServiceImpl;

import java.util.List;

/**
 * 本类功能:
 *
 * @author cxylk
 * @date 2020/8/29 15:19
 */
public class TestOrderService {
    IOrderService orderService=new OrderServiceImpl();

    @Test
    public void testList(){
        PageBean<Ord> pageBean=orderService.list(105.0d,2,4);

        System.out.println("总的条数:"+pageBean.getRows());

        System.out.println("总的页数:"+pageBean.getPageCounts());

        System.out.println("当前页:"+pageBean.getPageNow());

        System.out.println("每页显示条数:"+pageBean.getPageSize());

        List<Ord> datas=pageBean.getDatas();

        if(null!=datas&&datas.size()>0){
            for (Ord data : datas) {
                System.out.println(data);
            }
        }
    }
}
