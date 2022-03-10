# spring-boot-blog-master

#### 介绍
一个简单 的个人博客系统（基于李仁密小而美博客）
本项目长期维护。
我的网站：  http://www.tyux.top/

#### 软件架构
    基于thymeleaf模板
    前端 
        UI框架：semantic-ui框架
        css动画 Animate
        markdown
    后端
        核心框架：SpringBoot 2.5.4
        安全框架：shiro  1.7.1
        持久层框架：Mybatis 2.2.0
        java版本：JDK8
## 效果：http://www.tyux.top/
#### 安装教程
在application.yml文件修改数据库密码和用户名

mysql是高版本需要手动修改application.yml中的
com.mysql.jdbc.Driver属性为 spring.datasource.driver-class-name
并导入数据库数据。

在idea中运行
引入好依赖。
就可以启动项目了。

#### 使用说明
因为我一开始跟着学的时候没有设置留言板，导致后面添加的时候就不方便。所以我直接用t_blog表存放留言板数据了。
（🤣这点不是很好，应该将他分离出来。）

application-dev.yml开发环境。
application-pro.yml为生产环境。


