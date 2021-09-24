# spring-boot-blog-master

#### 介绍
一个简单 的个人博客系统（基于李仁密小而美博客）
本项目长期维护。

#### 软件架构
    
    前端
    核心框架：

        thymeleaf
        UI框架：semantic-ui框架(感觉这个框架快完蛋了)
        css动画 Animate
        markdown
    后端
        核心框架：SpringBoot 2.5.4
        安全框架：shiro  1.7.1
        Token 认证：jwt
        持久层框架：Mybatis 2.2.0
        java版本：JDK8
#### 安装教程

在application.yml文件修改数据库密码和用户名
你需要 jdk1.8和mysql6及以上版本
mysql如果低于此版本需要手动修改application.yml中的
spring.datasource.driver-class-name 属性为 com.mysql.jdbc.Driver

在idea中运行
引入好依赖。
就可以启动项目了。

#### 使用说明
因为我一开始跟着学的时候没有设置留言板，导致后面添加的时候就不方便。所以我直接用t_blog表存放留言板数据了。。
（🤣这点不是很好，应该将他分离出来。）
application-dev.yml开发环境。
application-pro.yml为生产环境。

#### 效果图
![输入图片说明](https://images.gitee.com/uploads/images/2021/0913/181019_6f536d41_9428709.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0924/101457_a55bc6f2_9428709.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0924/160003_2634eba2_9428709.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0924/101541_1cf3fff7_9428709.png "屏幕截图.png")
#### 致谢
项目开发过程中参考了很多大佬的博客，还有B站李仁密博客视频。

