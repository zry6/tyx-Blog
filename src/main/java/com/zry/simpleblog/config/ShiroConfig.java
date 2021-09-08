package com.zry.simpleblog.config;

import com.zry.simpleblog.realm.ShiroRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zry
 * @ClassName ShiroConfig.java
 * @Description TODO
 * @createTime 2021年08月29日
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("manager") DefaultWebSecurityManager manager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);
        Map<String,String> map = new HashMap<>();
        map.put("/admin/login","anon");
        map.put("/admin/**","authc");
        map.put("/","anon");
        factoryBean.setFilterChainDefinitionMap(map);
        //设置登录页面
        factoryBean.setLoginUrl("/admin/login");
        factoryBean.setSuccessUrl("/admin/index");

        //未授权页面
//        factoryBean.setUnauthorizedUrl("/unauth");
        return factoryBean;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public DefaultWebSecurityManager manager(@Qualifier("shiroRealm") ShiroRealm Realm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(Realm);
        return manager;
    }

    @Bean
    public ShiroRealm shiroRealm(){
        return new ShiroRealm();
    }

}
