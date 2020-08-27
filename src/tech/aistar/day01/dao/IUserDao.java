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

    /**
     * 返回的是单个对象
     * @param username 唯一的
     * @param password
     * @return
     */
    User findByUserName(String username,String password);

    /**
     * PrepareStatement
     * 批量保存
     * @param users
     */
    void saveList(List<User> users);

    /**
     * 根据id来查询用户
     * @param id
     * @return
     */
    User getById(Integer id);

    /**
     * 根据id来删除
     * @param id
     */
    void delById(Integer id);

    /**
     * 根据用户的id来更新列
     * @param user
     */
    void update(User user);
}
