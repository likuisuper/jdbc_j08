package tech.aistar.util;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 本类功能:连接池工具类
 *
 * @author cxylk
 * @date 2020/8/31 16:12
 */
public class SimplePools {
    private static BasicDataSource bds=new BasicDataSource();

    //静态代码块进行连接池相关参数的配置
    static{
        bds.setUrl(PropUtil.getConfigValue("url"));
        bds.setUsername(PropUtil.getConfigValue("user"));
        bds.setPassword(PropUtil.getConfigValue("password"));

        bds.setInitialSize(Integer.valueOf(PropUtil.getConfigValue("initialSize")));
        bds.setMaxIdle(Integer.valueOf(PropUtil.getConfigValue("maxIdleTime")));
        bds.setMaxActive(Integer.valueOf(PropUtil.getConfigValue("maxActive")));
        bds.setMaxWait(Long.valueOf(PropUtil.getConfigValue("maxWait")));
    }

    public static Connection getConnection() throws SQLException {
        return bds.getConnection();
    }
}
