package tech.aistar.day01.dao;

import tech.aistar.day01.entity.User;

import java.util.List;

/**
 * 本类功能:user实体类的持久层的dao接口
 *
 * @author cxylk
 * @date 2020/8/27 12:03
 */
public interface IUserDao {
    /**
     * 保存一个用户信息
     * @param user
     */
    void save(User user);

    /**
     * 查询所有的数据
     * @return 集合对象
     */
    List<User> findAll();
}
