package cn.ekgc.itrip.service;

import cn.ekgc.itrip.pojo.entity.User;

/**
 * <b>用户信息业务层接口</b>
 * @author awen
 * @version 3.1.1 2019-12-12
 * @since 3.1.1
 */
public interface UserService {
    /**
     * <b>通过userCode查询User对象</b>
     * @param userCode
     * @return
     * @throws Exception
     */
    User getUserByUserCode(String userCode) throws Exception;

    /**
     * <b>保存用户新，保存成功后，根据用户的userCode类型进行相关验证发送工作</b>
     * @param user
     * @return
     * @throws Exception
     */
    boolean saveUser(User user)throws Exception;

    User doLoginUser(String userCode, String userPassword)throws Exception;
}