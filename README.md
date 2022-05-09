#  基于SpringBoot的个人博客2.0
 **我的个人博客项目第二版 未完成 ，代码重构中ing** 
目前已完成后台的部分和部分前台。 

看完如果觉得还行，给个star鼓励一下吧👍

接口文档地址  http://localhost/swagger-ui.html

1.  **后台接口restful风格** 

2.  **响应式页面支持移动版** 
3.  **集成swagger接口文档** 
4.  **完善的代码注释** 
5.   **策略模式+redis+自定义注解实现的接口幂等性校验** 

6.  **难度适中，非常适合专注后端的同学学习** 

前端是移动端适配的，使用ajax请求后台数据，然后用jq渲染。
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
抛弃了thymeleaf模板，前端使用完全的ajax请求获取数据，（/pages/下的html文件）
达到前后端分离的效果。

*  **全局处理异常** 
*  **AOP自定义注解：日志打印，登录校验，幂等性校验** 
*  **策略模式 + 自定义注解 + redis 实现的幂等性校验。** 

*  **全局返回统一封装的json数据格式** 

 **{
    code:200,
    msg: "String",
    data: 数据
}** 

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
# 3.文件结构
![输入图片说明](assets/%E6%96%87%E4%BB%B6%E7%BB%93%E6%9E%84.png)
# 4.预览图：
## 前台：
### 主页
![输入图片说明](assets/%E4%B8%BB%E9%A1%B5.png)
### 文章浏览页面
![输入图片说明](blogPage.png)
### 分类页面
![输入图片说明](assets/%E5%88%86%E7%B1%BB%E9%A1%B5.png)
### 标签页面
![输入图片说明](assets/tagsPage.png)

## 后台：
### 博文管理页面
![输入图片说明](assets/%E5%90%8E%E5%8F%B0%E6%96%87%E7%AB%A0%E7%AE%A1%E7%90%86.png)