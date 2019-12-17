package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.dao.UserDao;
import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.service.UserService;
import cn.ekgc.itrip.util.Constanutil;
import cn.ekgc.itrip.util.SystemCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>用户信息业务层接口实现类</b>
 * @author awen
 * @version 3.1.1 2019-12-12
 * @since 3.1.1
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private JavaMailSender mailSender;

    /**
     * <b>通过userCode查询User对象</b>
     * @param userCode
     * @return
     * @throws Exception
     */
    public User getUserByUserCode(String userCode) throws Exception {
        // 封装查询参数
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("userCode", userCode);

        // 进行查询
        List<User> userList = userDao.findUserByQuery(queryMap);

        // 对于得到的结果进行判断
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }
    /**
     * <b>保存用户新，保存成功后，根据用户的userCode类型进行相关验证发送工作</b>
     * @param user
     * @return
     * @throws Exception
     */
    public boolean saveUser(User user) throws Exception {
       try {
            //讲述数据保存到数据库
           userDao.saveUser(user);
           //产生激活码
           String activeCode = SystemCodeUtil.cteateActiveCode();
           //将激活码保存到aedis中
           redisTemplate.opsForValue().set("activeCode", activeCode, Constanutil.ACTIVE_TIMEOUT);
            //发送邮件到用户邮箱
           SimpleMailMessage mailMessage =new SimpleMailMessage();
           mailMessage.setFrom(Constanutil.MAIL_FROM);
           mailMessage.setTo("329234355@qq.com");
           mailMessage.setSubject("爱旅行，爱乔佳：账户激活码");
           mailMessage.setText("您的激活码是"+activeCode+"请在"+Constanutil.ACTIVE_TIMEOUT+"分钟内登录系统，输入本验证码激活您的账户");
            mailSender.send(mailMessage);
            return  true;
       } catch (Exception e){
           e.printStackTrace();
       }
        return false;
    }

    @Override
    public User doLoginUser(String userCode, String userPassword)throws Exception {
        //绑定查询Map集合
        Map<String,Object> queryMap = new HashMap<String,Object>();
        queryMap.put("userCode", userCode);
        queryMap.put("userpassword", userPassword);
        //用户处于激活状态
        queryMap.put("activated", 1);
        //进行查询
        List<User> userList = userDao.findUserByQuery(queryMap);
        if (userList != null && userList.size()>0){
            return userList.get(0);
        }
        return null;
    }
}
