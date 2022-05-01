#  基于SpringBoot的个人博客2.0

 **我的个人博客项目第二版，代码重构中。** 
目前已完成后台的部分。 


1.  **后台接口restful风格** 

2.  **集成swagger接口文档** 
3.  **完善的代码注释** 
4.  **非常适合专注后端的同学学习** 

前端使用ajax请求后台数据，然后用jq渲染。
达到前后端分离的目的。（因为不会vue  大哭~）

## 1.涉及技术及工具

|  后端 |    |
|---|---|
|  核心框架 | SpringBoot  |
|  持久层 |  mybatis-plus |
|  MVC | Spring MVC  |
| 辅助插件  | Lombok  |
| 数据库  |  MySQL8.0 |
| 中间件  | redis  |
| 接口文档  | swagger  |

前端主要是html页面：
- semantic-UI + layer
- JS框架：Jquery
插件：
- css动画：animate     
- Markdown 编辑器：Editor.md
- 代码高亮（Prism）
- 目录生成（Tocbot）
- 二维码生成（qrcode.js）
- 还有其它有意思的插件 ...

## 2.整体设计与功能
请求接口restful风格+Swagger接口文档
抛弃了thymeleaf模板，前端使用完全的ajax请求获取数据，（/static/下的html文件）
达到前后端分离的效果。

*  **全局处理异常** 
*  **AOP自定义注解：日志打印，登录校验，幂等性校验** 
*  **策略模式 + 自定义注解 + redis 实现的幂等性校验。** 

*  **全局返回统一封装的json数据格式** 
{
    code:200,
    massage: "String",
    obj: 数据
}

* ...

### 主要功能

博客前台
- 博客展示
- 博客分类
- 博客标签
- 时间线
- 博客留言
- 留言邮件通知

博客后台
- 管理员登录
- 写文章
- 修改文章
- 删除文章
- 标签管理
- 分类管理
- 个人信息管理

## 3.文件结构
```
blog
─src
    ├─main
       ├─java
       │  └─com
       │      └─zry
       │          ├─comment
       │          │  ├─annotations
       │          │  ├─aop
       │          │  │  ├─aspect
       │          │  │  └─exception
       │          │  │      └─handler
       │          │  ├─config
       │          │  ├─idempotent
       │          │  │  └─strategy
       │          │  │      └─impl
       │          │  ├─interceptor
       │          │  ├─respBean
       │          │  └─utils
       │          ├─controller
       │          │  └─admin
       │          ├─dto
       │          ├─entity
       │          ├─mapper
       │          └─service
       │              └─impl
       └─resources
           ├─i18n
           ├─mapper
           ├─static
```

# 预览图：
前台：
![输入图片说明](%E4%B8%BB%E9%A1%B5.png)
后台：
![输入图片说明](image.png)
