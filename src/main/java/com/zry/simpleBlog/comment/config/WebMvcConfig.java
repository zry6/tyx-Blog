package com.zry.simpleBlog.comment.config;


import com.zry.simpleBlog.comment.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置 fastJson 处理 null
 *
 * @author zry
 * @create 2022-04-09 17:31
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 功能描述: 开放静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        "classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"
        //重写这个方法，映射静态资源文件
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/pages/")
        ;
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    /**
     * 功能描述: 加入自定义拦截器
     *
     * @param
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor())
                //拦截路径
                .addPathPatterns("/admin/*.html")
                .excludePathPatterns("/admin/login.html") ;

        super.addInterceptors(registry);
    }

    /**
     * 负责页面的拦截
     * 将拦截器加入spring容器
     */
    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor();
    }

}
