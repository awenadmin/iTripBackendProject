package cn.ekgc.itrip.transport.impl;

import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.service.UserService;
import cn.ekgc.itrip.transport.UserTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <b>用户信息传输层接口实现类</b>
 * @author Arthur
 * @version 3.1.1 2019-12-12
 * @since 3.1.1
 */
@RestController("userTransport")
@RequestMapping("/user")
public class UserTransportImpl implements UserTransport {
    @Autowired
    private UserService userService;

    /**
     * <b>通过userCode查询User对象</b>
     * @param userCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/code", method = RequestMethod.POST)
    public User getUserByUserCode(@RequestParam String userCode) throws Exception {
        return userService.getUserByUserCode(userCode);
    }

    /**
     * <b>保存用户信息</b>
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean saveUserByEmail(@RequestBody User user) throws Exception {

        return userService.saveUser(user);
    }

    /**
     * <b>使用userCode和userPassword进行用户信息登录</b>
     * @param userCode
     * @param userPassword
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User doLoginUser(@RequestParam String userCode,@RequestParam String userPassword) throws Exception {
        return userService.doLoginUser(userCode, userPassword);
    }
}