package tech.aistar.day01.dao;

import org.junit.jupiter.api.Test;
import tech.aistar.day01.dao.impl.UserDaoImpl;
import tech.aistar.day01.entity.Gender;
import tech.aistar.day01.entity.User;

import java.util.Date;
import java.util.List;

/**
 * 本类功能:单元测试
 *
 * @author cxylk
 * @date 2020/8/27 13:43
 */
public class TestUserDao {
    IUserDao userDao=new UserDaoImpl();

    /**
     * 测试类中的每个测试方法使用@Test注解标注
     * 方法一定是public void 方法名 一定是空参的
     *
     * 双击选中testSave - 右击 -run
     */
    @Test
    public void testSave(){
        //1.模拟数据
        User user=new User("tom2","123",new Date(), Gender.M);
        //2.测试dao接口中的save方法
        userDao.save(user);
    }

    @Test
    public void testFindAll(){
        List<User> users=userDao.findAll();

        if(users.size()>0){
            users.forEach((e)-> System.out.println(e));
        }
    }
}
