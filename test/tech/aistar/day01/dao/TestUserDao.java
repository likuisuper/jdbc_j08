package tech.aistar.day01.dao;

import org.junit.jupiter.api.Test;
import tech.aistar.day01.dao.impl.UserDaoImpl;
import tech.aistar.day01.entity.Gender;
import tech.aistar.day01.entity.User;

import java.util.ArrayList;
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
        User user=new User("tom6","123",new Date(), Gender.M);
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

    @Test
    public void testFindByUserName(){
        //User user=userDao.findByUserName("tom2","123ffdss' or '1'='1");
        User user=userDao.findByUserName("tom3","123");
        System.out.println(user);
    }

    @Test
    public void testSaveList(){
        User user=new User("tom1","123",new Date(),Gender.M);
        User user2=new User("tom3","123",new Date(),Gender.F);
        User user3=new User("tom4","123",new Date(),Gender.F);
        User user4=new User("tom5","123",new Date(),Gender.M);

        List<User> users=new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        userDao.saveList(users);
    }

    @Test
    public void testGetById(){
        System.out.println(userDao.getById(1));
    }

    @Test
    public void testDelById(){
        userDao.delById(6);
    }

    @Test
    public void testUpdate(){
        User user=userDao.getById(1);
        user.setUsername("admin");
        user.setPassword("success");
        user.setBirthday(new Date());

        userDao.update(user);

        //不推荐 - 手动模拟数据 - 一定给定id值
//        User user1=new User("tt","tt",new Date(),Gender.M);
//        user1.setId(1);
//
//        userDao.update(user1);
    }
}
