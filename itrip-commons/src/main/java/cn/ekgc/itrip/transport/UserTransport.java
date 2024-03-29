package cn.ekgc.itrip.transport;

import cn.ekgc.itrip.pojo.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;

/**
 * <b>用户信息传输层接口</b>
 * @author awen
 * @version 3.1.1 2019-12-12
 * @since 3.1.1
 */
@FeignClient(name = "itrip-biz-provider")
@RequestMapping("/user")
public interface UserTransport {
    /**
     * <b>通过userCode查询User对象</b>
     * @param userCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/code", method = RequestMethod.POST)
   User getUserByUserCode(@RequestParam String userCode) throws Exception;

    /**
     * <b>保存用户信息</b>
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    boolean saveUserByEmail(@RequestBody User user) throws Exception;

    /**
     * <b>使用userCode和userPassword进行用户信息登录</b>
     * @param userCode
     * @param userPassword
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    User doLoginUser(@RequestParam String userCode, @RequestParam String userPassword)throws  Exception;
}