package tech.aistar.service;

import tech.aistar.pojo.dto.PageBean;
import tech.aistar.pojo.entity.Ord;

/**
 * 本类功能:订单的业务逻辑接口
 *
 * @author cxylk
 * @date 2020/8/29 14:48
 */
@FunctionalInterface
public interface IOrderService {
    PageBean<Ord> list(Double total,Integer pageNow,Integer pageSize);
}
