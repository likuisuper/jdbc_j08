# JDBC

jdbc - java database connectivity - java数据库互连 - 使用Java语言编写的程序和DB数据库进行连接.

由于DB(数据库产品,比如mysql,sqlserver,oracle)是属于不同的DB厂商的.早期,java语言尚未火的时候,DB

厂商对java支持不是特别友好.***导致问题是:java语言要想连接不同的DB,就得写一套不同的代码***.导致代码的

可拓展性比较低.java语言就不能在不同的DB之间来回切换,切换的成本比较高,有可能就是需要重写一套代码.

***在没有JDBC技术之前:***

~~~java
MS厂商 - SqlServer
public class SqlServerConnection{
  //..
  void closeSqlServerConnection();
}

Oracle厂商 - mysql,oracle
public class MySqlConnection{
  //...
  void closeMySqlConnection();
}
~~~

由于java语言彻底火了之后,各大DB厂商开始"巴结".没有一个统一的标准.**sun公司就开始制定一系列的标准(接口),**让各大DB厂商去实现这个标准[数据库的驱动jar包 - **驱动实现类] - mysql-connector-java-5.1.47.jar**

**JDBC技术属**于JavaEE体系,***作用 - 为访问不同的DB,提供统一的途径.***(写一套代码,来和不同的DB进行交互,在DB之间的切换,修改的成本降低最低)

~~~java
sun开始制定标准.
public interface Driver{//顶级的驱动接口

}

//ms - sqlserver - 实现这个接口
public class SqlServerDriver implements Driver{
  
}

//oracle - mysql - 实现这个接口
public class MysqlDriver implements Driver{
  
}

//使用接口编程
Driver driver = Drivers.getInstance();//反射工厂
~~~

~~~java
java - app1     -  j  - mysql
java - app2     -  d  - oracle
java - app3     -  b  - sqlserver
java - app4     -  c  - 其他的DB产品
~~~



# 核心API

package:java.sql包

## Driver[I]

简介:每个驱动程序类必须实现的接口。在JDBC4.x规范开始之前,进行JDBC编程的第一步需要把DB驱动类加载内存中.但是从JDBC4.x规范开始,此行代码可以省略不写.

~~~mysql
Class.forName("com.mysql.jdbc.Driver");//mysql8.x之前
class.forName("com.mysql.cj.jdbc.Driver");//mysql8.0驱动jar
~~~



## DriverManager[C]

驱动管理类.用于管理一组JDBC驱动程序的基本服务。

> static Connection getConnection(String url,String user,String password);
>
> * user - 连接DB的用户名 - root
>
> * password - 连接DB的密码 - root
>
> * url - 不同的数据库的url是不一样的.
>
>   mysql - jdbc:mysql://localhost:3306/j08
>
>   oralce - jdbc:oracle:thin:@localhost:1521:ORCl
>
>   主协议:次协议://数据库主机的ip地址:端口号/数据库名称



## Connection[I]

与DB产生一次会话[连接].成功获取connection对象,等同于和数据库之间建立起连接.

* void close();//每一根连接在最终使用完毕之后,需要释放,属于一个昂贵的资源,比较占内存的.
* Statement createStatement();获取语句对象
* PreparedStatement prepareStatement(String sql);//创建预编译语句对象



## Statement[I]

语句对象 - 作用:负责将sql语句发送到mysql-server端,由mysql-server端对sql进行编译和解释.

* void close();//关于语句对象
* int executeUpdate(String sql);//传入的sql必须是DML(insert,update,delete),返回的是受影响的行数.
* ResultSet executeQuery(String sql);//传入的sql是一个DQL(select),返回一个结果集对象



## PreparedStatement[I]

预编译语句对象 - 提前将可能带有参数占位符号的sql语句提前发送到DB-server端进行预编译的操作

* void close();
* int executeUpdate();
* ResultSet executeQuery();
* 发送参数的方法set类型(参数1-占位符号的位置,参数2);



应用场景 - 特别适合解决批量的同构的sql问题.

### 和statement区别

* statement需要进行繁琐的sql的硬拼接 - 容易sql注入的问题

  一个statement可以发送多条sql语句

  ~~~java
  for(User user:users){}
    String sql = "insert into tbl_user(username,password,birthday,sex) values('"+user.getUsername()+"','"+user.getPassword()+"','"+bt+"','"+user.getGender().toString()+"')";
  
  	st.executeUpdate(sql);//每执行一次,sql都要进行编译一次.
  }
  
  -- 执行几次,就编译几次!
  ~~~

* preparedstatement可以在sql中运用占位符?

  一个pst对象只能编译一条sql语句

  ~~~java
  String sql = "insert into tbl_user(username,password,birthday,sex) values(?,?,?,?)";
  //1. 获取预编译语句对象
  //2. 把这条带有占位符好的sql语句提前到DB端进行预编译问题.
  PreparedStatement pst = conn.prepareStatement(sql);
  
  //3. 发送参数 - 后期只要将参数发送到DB-server端即可,不需要对sql再次进行编译了.
  ~~~

  

## ResultSet[I]

结果集对象 - 如果执行的是DQL,才需要处理结果集对象,如果执行的是DML操作,不需要处理结果集的.

ResultSet表面的意思就是用来保存select语句查询的结果.它的本质是一个游标.光标.

默认是指向标题行(有效数据的第一行的上方).

* void close();//关闭
* boolean next();//光标向下移动一行.如果下一行仍有数据,返回true,否则返回false.
* get类型(String 列的序号);//类型和mysql中的列的数据类型保持一致.
* get类型(String 列的标题);



# 关闭顺序的注意点

rs -> st -> conn



# JDBC六大步骤体验

~~~java
package tech.aistar.day01;

import java.sql.*;

/**
 * 本类用来演示:jdbc六大编程步骤 - 查询s_emp表中的id,first_name,salary数据进行控制台输出
 *
 * @author: success
 * @date: 2020/8/27 10:09 上午
 */
public class HelloJdbc {
    public static void main(String[] args) {
        //1. 加载驱动 - jdbc4.0规范开始,可以省略不写
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //2. 获取连接对象Connection;
            //2-1. 准备三个连接参数
            String url = "jdbc:mysql://localhost:3306/j08?useSSL=false";
            String username = "root";
            String password = "root";

            conn = DriverManager.getConnection(url,username,password);

            //3. 创建语句对象Statement
            //作用 - 负责将sql语句发送到数据库的服务器端,由Db在server端对sql语句进行编译和解释
            //ResultSet executeQuery(String sql);//用来执行DQL语句
            //int executeUpdate(String sql;//执行DML语句
            st = conn.createStatement();

            //4. "执行"-SQL,执行的是DQL,返回一个结果集对象
            //4-1. 准备sql语句
            String sql = "select id,first_name,salary from s_emp";
            rs = st.executeQuery(sql);

            //5. 处理结果集对象
            //ResultSet结果集对象的本质是游标,默认是停留在标题行,通过boolean next();//光标向下移动一行.如果下一行没有,则返回false
            while(rs.next()){
                //分别获取查询列的数据,一列一列进行获取.

                //鼓励大家使用列的序号获取,性能稍微比通过列的名称获取好.

                //调用get方法,但是一定要住db表中列的数据类型
                int id = rs.getInt(1);//根据查询列在查询语句中的序号的位置获取,从1开始.

                //根据列的标题进行获取,和一定要注意,如果列取了别名,通过别名获取.
                String firstName = rs.getString("first_name");//可读性强一点.

                double salary = rs.getDouble(3);

                System.out.printf("编号:[%d],名字:[%s],工资:[%.2f]\n",id,firstName,salary);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.释放资源 - 关闭对象的.
            if(null!=rs){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            if(null!=st){
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            if(null!=conn){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
~~~



# 具体编程

目标 - 掌握单张表的jdbc的CRUD操作.

## 创建表

~~~mysql
drop table tbl_user;
create table tbl_user(
	id int(7) primary key auto_increment,
  username varchar(20) not null unique,
  password varchar(20) not null default '123456',
  birthday date default '1990-01-01',
  sex varchar(1) comment 'f代表女,m代表男'
);
~~~



## 创建实体类

~~~java
User.java Gender.java
~~~



## 设计DAO

DAO层 - Data Access Object - 数据访问层 - 数据持久层 - 和DB进行交互的一层,不涉及到任何的业务操作.

dao - 设计crud等一些基础的接口.



## DAO的实现类

实现了DAO接口的方法.



## 单元测试类

完整的项目中应该是包含单元测试 - dao层接口中方法的测试,service层接口中方法的测试,控制层方法的测试[postman测试]

* 使用的是***junit4或者junit5***[idea版本高于2017版本].
* maven - ***testng测试插件.***

***右击工程 - test文件夹 - mark directory as - Test Sources Root,目录结构要和src中的测试的包的结构高度保持一致.***



# Sql注入的问题

Statement使用方式其中一个缺点就是由于它的***sql参数是硬拼接,***所以会***导致sql注入的问题***.在一条有效的sql语句后面拼接一些不合法的一些参数,引起一些问题.



# 作业

简介:1:n的案例

~~~mysql
tbl_customer(id,cname,phone);

tbl_ord(id,ordno,total,create_date,customer_id)
~~~

~~~mysql
drop table ord;
drop table customer;
create table customer(
	id int(7) primary key auto_increment,
  cname varchar(20) not null,
  phone varchar(11) not null unique
);
create table ord(
	id int(7) primary key auto_increment,
  ordno varchar(60) not null unique,
  total double(11,2),
  create_date datetime default now(),
  customer_id int(7)
);
~~~



创建实体类 - 对象与对象之间的关系

Customer.java以及Order.java



Dao层

~~~java
ICustomerDao.java
  - 查询所有的客户信息,如果这个客户拥有订单信息,那么也要加载出这个客户的订单信息(级联查)
  - 根据客户id进行删除,如果这个客户拥有订单信息,也要将该客户的订单信息全部删除(级联删)
  - 统计每个客户拥有的订单个数-> id cname,订单数量 -> 返回出去.
  - 保存一个客户的同时,保存这个客户拥有的所有的订单信息 - [级联插]

IOrderDao.java
  - 多条件组合查询- total,create_date
    a. 当俩个条件都没有传值的时候,则查所有的订单信息
    b. 当只有total传值但是create_date没有传值的时候,根据total查询
    c. 当create_date传值,但是total没有传值,根据create_date传值.
~~~

实现层以及每个单元测试方法.



# JDBC - 事务

简介:jdbc事务->jdbc连接是哪个数据库.jdbc事务就是底层的数据库的事务.

* jdbc事务默认是自动提交的.

* jdbc事务何时开启? - 当成功获取Connection对象的时候

* jdbc事务何时结束? - 如果自动 - 自动结束,手动 - 手动conn.commit();

* 如何手动提交事务 - conn.setAutoCommit(false);

* jdbc中的事务是属于**编程性事务(在java程序代码中出现关于事务的语句)** - 事务代码和程序代码耦合在一块.

  未来学习的spring中支持声明式事务[底层AOP思想 - ***动态代理[jdk动态代理***和cglib动态代理]]

* ***目前阶段jdbc事务只能在dao层写.***



## 相关事务操作

* conn.setAutoCommit(false);
* conn.rollback();//事务的回滚
* conn.commit();//事务的提交



# DTO对象

Data Transfer Object - 数据传输对象.

PO DAO DO BO DTO VO

当需要使用第三方对象来存储查询的结果,那么这个对象暂时把它叫做DTO对象.

***DTO对象中的属性>=实体类中的属性的.***

* dao层 - 和DB交互的层 - 90%的方法的参数都是可以直接使用entity包下的类

  void save(Customer c); Customer getById(Integer id);

  最后才会考虑DTO对象.

* service层 - 业务逻辑层,开发的主要的核心一层.不会和DB进行交互.

  必须调用dao层代码的.方法的参数和返回类型优先使用DTO对象了.

  经过业务逻辑处理之后的数据,不一定有表或者实体类对应着.

  ~~~java
  public void save(CustomerDTO dto){
    //调用dao层...
    //数据校验... -> dao->save
    
    //bean的转换->  dto对象转换成entity对象.
    //浅拷贝 - 深拷贝.
    
    //dao-> save(Customer c);
  }
  ~~~

  

# 带条件分页查询

* dao层设计

  ~~~java
  List<Ord> findByPages(Double total,Integer pageNow,Integer pageSize);
  total - 订单的价格,没有传入就查所有.
  pageNow - 当前页
  pageSize - 每页显示的条数.
    
  运用limit语句 - limit (pageNow-1)*pageSize,pageSize;
  ~~~

* service层设计

  ~~~java
  分页出来的元素 - 网页上需要展示的和分页相关的属性
  1 - datas核心的数据
    
  2 - 显示当前页
    
  3- 总的页数
    
  4 - 每页显示条数
    
  5 - 总的条数.
  ~~~

## 回顾早上级联删除

~~~java
ICustomerDao.java
void delById(Integer cid);//只要删除客户信息.
实现类:delete from customer where id = ?;


IOrdDao.java
void delById(Integer customer_id);
实现类:delete from ord where customer_id = ?;

ICustomerSerivce.java
  
实现类:
public void delByIdCacade(Integer cid){
   orderDao.delById(cid);
  
   customerDao.delById(cid);
}
原因是因为我们只要编程性事务.没有办法去控制service事务的.
spring声明式事务 - 控制service层事务的.
  
  
级联查询
ICustomerDao.java
List<Customer> findAll();//select * from customer;

IOrderDao.java
List<Ord> findByCustomer(Integer cid);//select * from ord where customer_id=?

ICustomerService.java

public List<Customer> listAll(){
  List<Customer> list = customerDao.findAll();
  
  for(Customer c:list){
      List<Ord> ords = ordDao.findByCustomer(c.getId());
      if(null!=ords && ords.size()>0){
        c.setOrds(ords);
      }
  }
  
  return list;
}
~~~



# 元数据(metadata)

元数据 - 描述元对象的数据.

* DatabaseMetaData - 获取底层数据库的元信息[url,driver,user,数据库的版本号等].
* ResultSetMetaData - 结果集元数据 - 描述ResultSet结果集对象的对象
  * 如何获取结果集元数据对象?
    * 通过java.sql.ResultSet提供的ResultSetMetaData getMetaData();
    * int getColumnCount();//获取查询列的个数.
    * String getColumnLabel(int i);//根据序号[从1开始],获取查询列的名称.
    * String getColumnName(int i);

补充:

PreparedStatement

* executeUpdate();//执行DML操作
* executeQuery();//执行的是DQL操作
* boolean execute();执行任意sql语句.如果传入的sql是DQL,返回true.否则返回false



## 完成mysql客户端

~~~mysql
启动程序 ->

mysql> select * from
    -> s_emp;
mysql> 
~~~



## 重新认识Resultset

本质上是指向行记录的光标.

~~~mysql
//通过stmt对象来得到一个可滚动的,可更新的一个结果集对象.
Statement stmt = con.createStatement(
                                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
~~~



# 批处理操作

批量保存订单信息.

~~~java
{
        Connection conn = null;
        PreparedStatement pst = null;

        String sql = "insert into ord(ordno,total,create_date,customer_id) values(?,?,?,?)";
        try {
            conn = JdbcUtil.getConnection();

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql);

            //定义一个计数器
            int count = 0;

            for (Ord ord : ords) {
                pst.setString(1,ord.getOrdno());
                pst.setDouble(2,ord.getTotal());
                pst.setDate(3,new java.sql.Date(ord.getCreateDate().getTime()));
                pst.setInt(4,ord.getCustomer().getId());
                //pst.executeUpdate();//将这一组参数发送到DB-server端.

                pst.addBatch();//将这一组参数放入到批处理的队列中去.
                count++;

                if(count==10000){
                    pst.executeBatch();//执行,将很多组的参数全部发送到DB-server端;
                    pst.clearBatch();//清空一下队列.
                    count=0;
                }
            }

            if(count!=0){
                pst.executeBatch();
                pst.clearBatch();
            }

            conn.commit();

        } catch (Exception e) {
            if(null!=conn){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            JdbcUtil.close(conn,pst);
        }
    }
~~~

~~~properties
url=jdbc:mysql://localhost:3306/j08?useSSL=false&rewriteBatchedStatements=true
~~~



# 连接池原理 - 笔试

数据库连接池的基本思想就是为数据库连接建立一个“缓冲池”。预先在缓冲池中放入一定数量的连接(***初始连接数量***)，当需要建立数据库连接时，只需从“缓冲池”中取出一个，使用完毕之后再放回去。我们可以通过设定连接池最大连接数来防止系统无尽的与数据库连接。更为重要的是我们可以通过连接池的管理机制监视数据库的连接的数量?使用情况，为系统开发?测试及性能调整提供依据。

* 连接池是什么?
* 连接池的初始的连接数应该设置多少合适 - 根据业务来决定.
* 连接池可以存放的连接最大数量是多少? - 最大活动数
* 最大等待时间
* ...

在没有连接池之前:我们每次调用dao层中的方法的时候,都会频繁进行连接的获取和连接的释放.会进行频繁的和磁盘进行IO交互.



手写连接池:单例模式(连接池肯定是一个重量级的对象,创建和销毁的成本比较高.一个连接池对应一个数据源)

​                    JDK动态代理 - 代理的是接口



## c3p0

第三方连接池配置,未来会切换到阿里的druid - 实现数据源的监控的功能 - 监控的web页面.



### 相关配置.



























