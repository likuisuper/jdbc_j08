package tech.aistar.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 本类功能:读取配置文件,然后根据key来获取值
 *
 * java.util.Properteis[C] extends java.util.Hashtable[C] implements Map[C]
 *
 * @author cxylk
 * @date 2020/8/31 9:37
 */
public class PropUtil {
    //将.properties文件加载到内存中 -> 映射到Properties对象中
    private static Properties prop=new Properties();

    //通过静态代码块来加载属性配置文件
    static{
        //准备属性文件的字节输入流
        //InputStream in=new FileInputStream("");
        InputStream in=Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("tech/aistar/util/config/db.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getConfigValue(String key){
        return prop==null?null:prop.getProperty(key);
    }

}
