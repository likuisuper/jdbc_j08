package tech.aistar.day01.dao;

import tech.aistar.day01.entity.User;

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
}
