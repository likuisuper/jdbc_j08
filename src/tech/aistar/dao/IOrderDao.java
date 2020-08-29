package tech.aistar.dao;

import tech.aistar.pojo.entity.Ord;

import java.util.List;

/**
 * 本类功能:订单的dao接口
 *
 * @author cxylk
 * @date 2020/8/28 19:26
 */
public interface IOrderDao {
    /**
     * 分页查询
     * @param total 价格
     * @param pageNow 当前页
     * @param pageSize 总的页数
     * @return
     */
    List<Ord> findByPages(Double total,Integer pageNow,Integer pageSize);

    /**
     * 根据total条件得到的总的条数
     * 本质删可以直接使用上面findByPages得到的集合.size();
     * 集合.size()->性能很低
     * @param total
     * @return
     */
    long getRows(Double total);
}
