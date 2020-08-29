package tech.aistar.service.impl;

import tech.aistar.dao.IOrderDao;
import tech.aistar.dao.impl.OrderDaoImpl;
import tech.aistar.pojo.dto.PageBean;
import tech.aistar.pojo.entity.Ord;
import tech.aistar.service.IOrderService;

import java.util.List;

/**
 * 本类功能:service层专门用来进行业务逻辑的处理 - 调用dao层
 *
 * @author cxylk
 * @date 2020/8/29 14:57
 */
public class OrderServiceImpl implements IOrderService {
    private IOrderDao orderDao=new OrderDaoImpl();
    @Override
    public PageBean<Ord> list(Double total, Integer pageNow, Integer pageSize) {
        //创建一个分页的对象
        PageBean<Ord> pageBean=new PageBean<>();

        //1.封装的真实的分页的数据
        List<Ord> ordList=orderDao.findByPages(total,pageNow,pageSize);
        //绑定分页的数据
        pageBean.setDatas(ordList);
        //2.封装pageNow - 当前页
        pageBean.setPageNow(pageNow);
        //3.封装每页显示的条数
        pageBean.setPageSize(pageSize);

        //4.总的条数
        Integer rows=Integer.valueOf(String.valueOf(orderDao.getRows(total)));
        pageBean.setRows(rows);

        //5.封装总的页数
        Integer pageCounts=rows/pageSize;

        if(rows%pageSize!=0){
            pageCounts++;
        }
        pageBean.setPageCounts(pageCounts);

        return pageBean;
    }
}
