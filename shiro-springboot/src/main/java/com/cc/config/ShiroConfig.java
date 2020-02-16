package com.cc.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig{

    //shiroFilterFactoryBean:3
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
        //内置过滤器
        /*
             anno: 无需认证就可以访问
             authc: 必须认证才能访问
             user：必须拥有 记住我 功能才能用
             perms:拥有对某个资源的权限才能访问
             role: 拥有某个角色权限才能访问

             map.put("/user/add", "authc");
             map.put("/user/update", "authc");
         */
        //拦截
        Map<String, String> map = new LinkedHashMap<>();

        //授权,正常的情况下，没有授权会跳转到未授权页面
        map.put("/user/add","perms[user:add]");
        map.put("/user/update","perms[user:update]");

        map.put("/user/*", "authc");
        bean.setFilterChainDefinitionMap(map);

        //设置登录的请求
        bean.setLoginUrl("/toLogin");
        //设置未授权页面
        bean.setUnauthorizedUrl("/noauth");

        return bean;
    }


    //DefaultWebSecurityManager:2
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联userReaml
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建 realm对象，需要自定义类:1
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }


    //整合ShiroDialect:用来整合 shiro thymeleaf
//    @Bean
//    public ShiroDialect getShiroDialect() {
//        return new ShiroDialect();
//    }

}
