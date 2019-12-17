package cn.ekgc.itrip.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.mail.imap.protocol.ID;
import sun.security.x509.CertificateAlgorithmId;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    public static String cteateToken(Long userId){
        //获得对于Token进行加密的算法对对象
        Algorithm algorithm = Algorithm.HMAC256(Constanutil.AUTH_SECRET);
        //创建JWt创建者Builder，注意的是，在3.8.3中，取消JWTBuilder对象，变成JWTCreator.Builder对象
        JWTCreator.Builder builder = JWT.create();
        //设定对于生成的json的加密方法；
        //设定有效载荷信息
        Map<String,Object> headerMap = new HashMap<String, Object>();
        headerMap.put("alg", "hmac256");
        headerMap.put("type", "jwt");
        //设定签名头部信息
        builder.withHeader(headerMap);
        //设定有效载荷信息
        builder.withClaim("id", userId);
        //进性加密，获得最终的json
        String jwtJson = builder.sign(algorithm);

        return jwtJson;
    }

    /**
     * <b>对于给定的Token进行校验和加密，获的用户ID</b>
     * @param token
     * @return
     */
    public static Long decryptToken(String token){
        //设定加密是所有的算法构成
        Algorithm algorithm = Algorithm.HMAC256(Constanutil.AUTH_SECRET);
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            //如果能够进行成功的解密，那么说明此时所提供的Token的json是真实有效的
            DecodedJWT decodedJWT = JWT.decode(token);
            // 通过解密后的JWT对象获得相应的载荷数据
            Long id =decodedJWT .getClaim("id").asLong();
            if (id != null && id > 0) {
                return id;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1L;
    }
    public static void main(String[] args){
    System.out.println(decryptToken("eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJpZCI6MjMyMzIxMn0.lqVkBDwTS3mAbKw17QYgK87LWcLvsxLbyv88EOOohmU"));
    }
}
