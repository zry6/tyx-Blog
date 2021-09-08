package com.zry.simpleblog.realm;

import com.zry.simpleblog.entity.User;
import com.zry.simpleblog.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @author zry
 * @ClassName ShiroRealm.java
 * @Description TODO
 * @createTime 2021年08月29日
 */
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.把AuthenticationToken 转化为 UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //2.从UsernamePasswordToken 中获取数据 username
        String username = usernamePasswordToken.getUsername();
        //3.调用数据库方法进行数据对比
        User user = userService.checkUser(username);

        //4.若用户不存在，则可以抛出UnknownAccountException 异常
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }else {
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        }
    }
}
