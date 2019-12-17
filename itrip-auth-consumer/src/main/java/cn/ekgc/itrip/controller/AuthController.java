package cn.ekgc.itrip.controller;

import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.pojo.vo.Dto;
import cn.ekgc.itrip.transport.UserTransport;
import cn.ekgc.itrip.util.JWTUtil;
import cn.ekgc.itrip.util.MD5Util;
import cn.ekgc.itrip.util.UserUtil;
import feign.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>认证子项目控制器</b>
 * @author Arthur
 * @version 3.1.0 2019-12-12
 * @since 3.1.0
 */
@RestController("authController")
@RequestMapping("/auth")
public class AuthController extends BaseController {
    @Autowired
    private UserTransport userTransport;
    @RequestMapping(value = "api/ckusr",method = RequestMethod.GET)
    public Dto<User> checkUserCodeForRegistry(String name)throws Exception{
        // 创建数据返回对象
        Dto<User> result = new Dto<User>();
        // 判断所提交的邮箱/手机号码是否符合格式要求
        if (UserUtil.checkUserCodePattern(name)){
        //在格式正确的情况下，进行唯一性校验
            User user = userTransport.getUserByUserCode(name);
            if(user == null){
                //此事该注意信息可用
                result.setSuccess("true");
                return result;
            }
    }
        //如果格式正确，那么在校验是否唯一
        result.setSuccess("false");
        result.setMsg("该邮箱地址或者手机号格式不正确，或者被占用");
    return result;
    }

    /**
     * <b>使用邮箱注册新用户</b>
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/api/doregister", method = RequestMethod.POST)
    public Dto<User> registryUserByEmail(@RequestBody User user) throws Exception {
        Dto<User> result = new Dto<User>();
        //1.校验用户所填写的信息是否有效， 包含游湖的Email地址和密码
        if (user.getUserCode()!= null && !"".equals(user.getUserCode().trim())
        && user.getUserPassword() != null && !"".equals(user.getUserPassword())){
           //校验用户的Email地址是否正确
            if (UserUtil.checkUserCodePattern(user.getUserCode())){
            //校验用户的唯一性
                if(userTransport.getUserByUserCode(user.getUserCode()) == null){
                    //对于数据进行MD5加密
                    user.setUserPassword(MD5Util.encrypt(user.getUserPassword()));
                    if(userTransport.saveUserByEmail(user)){
                        result.setSuccess("true");
                        result.setMsg("请在30分钟内登录您的邮箱进行查看激活码");
                    }else {
                        result.setSuccess("false");
                        result.setMsg("系统错误，请联系管理员阿文：18149335688");
                    }
                }else {
                    result.setSuccess("false");
                    result.setMsg("该Email地址已被占用，请直接登录");
                }
            }else {
                result.setSuccess("false");
                result.setMsg("请填写正确的Email地址");
            }
        } else {
            result.setSuccess("false");
            result.setMsg("请填写您的Mail地址和密码");
        }
        // 2、对于用户的登录密码进行MD5加密
        // 3、保存用户数据，并且在业务层保存成功的是否使用邮箱功能发送激活码，并且将激活码设定到redis中保存，并且设定有效时间
        return result;
    }

    /**
     * <b>根据用户的userCode和userPassword进行用户信息登录</b>
     * @param name
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/api/dologin",method = RequestMethod.POST)
    public Dto<User> doLoginUser(String name,String password)throws Exception {
        //创建返回数据DTO对象
        Dto<User> resultDTO = new Dto<>();
        //校验用户提供的用户名的密码正确有效
        if (name != null && !"".equals(name.trim()) && password != null && !"".equals(password.trim())){
        //对于用户的密码进行加密
            password = MD5Util.encrypt(password);
            //使用登录名和密码进行登录
            User user = userTransport.doLoginUser(name,password);
            if(user != null) {
                //对于前后端的分离来说，登陆成功之后，不再使用，或者是很少使用HTTPSession绑定用户信息
                // 对于用户的信息进行进一步Token
                String json = JWTUtil.cteateToken(user.getId());
                //将对应得Token绑定到response的header的部分
                response.setHeader("Authorization", json);
                //至此登陆成功
                resultDTO.setSuccess("true");

                resultDTO.setData(user);
                return resultDTO;
                }
            }
        resultDTO.setSuccess("false");
        resultDTO.setMsg("登陆失败，请确认登录信息后再次进行登录");
        return resultDTO;
        }
}
