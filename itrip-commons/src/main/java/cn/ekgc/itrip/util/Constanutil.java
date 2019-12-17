package cn.ekgc.itrip.util;

import com.netflix.discovery.converters.Converters;

import java.util.Properties;

/**
 * <b>系统常量类工具</b>
 * @author awen
 * @version 3.1.1 2019-12-13
 * @since 3.1.1
 */
public class Constanutil {
    private static final Properties props = new Properties();
    static{
        try {
            props.load(Constanutil.class.getClassLoader().getResourceAsStream("itrip.properties"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static final Long ACTIVE_TIMEOUT =Long.parseLong(props.getProperty("active.code.timeout"));
    public static final String MAIL_FROM =props.getProperty("mail.from");
    public static final String AUTH_SECRET = props.getProperty("auth.secret");
}
