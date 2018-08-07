# comment-service
评论服务
### 编写缘由：建立自己的墙内插件
网站是jekyll平台搭建的，纯静态网站，就需要第三方插件，通过js来增加评论功能。
国内评论系统大多数停止服务了，有少数就比较麻烦需要备案，认证等一些，就没有去找那些可用好用的服务了；国外的因为**互联网国情**就不采用了。

于是就有了自己来搭建一套评论服务也正好来练练手，也为了熟悉之前未接触过的一些业务等。

最开始规划当前从最简单开始，版本是一代一代迭代升级的。

---
### 插件使用
[使用教程][1]

---
### 最基本需求功能
> * 帖子发表评论
> * 帖子评论展示
> * 回复评论
> * 接口限流、IP限流及监控
> * 帖子关键字过滤监控
---

### 服务端开发
自身是用Java语言进行后端开发，所以这里就基于springBoot2.0搭建一套敏捷的服务。

主要用到的技术和框架以及依赖服务
>* spring-boot-2
>* spring-boot-starter-webflux
>* mysql
>* Guave的RateLimiter限流策略
>* 基于redis的IP访问计数限流监控IP策略
>* 全局异常处理:GlobalExceptionHandler
>* application多环境配置

---
### 待开发功能
> * 第三方登陆账号后回复
> * 评论内容过滤关键字
> * 站点监控拉黑

---
### 开发日记
#### 已完成
2018.08.02  新增接口调用ip黑名单，黑名单无法进行接口调用

2018.08.03  新增filter,过滤ip黑名单，取消接口调用。

2018.08.04  新增redis

2018.08.05  新增mail通知新的评论

2018.08.06  新增jasypt加密（待验证安全问题）

2018.08.07  新增接口限流和ip限流（GuaveRateLimiter 和 Redis）

            

[1]: https://www.shanbing.top/2018/07/26/%E8%87%AA%E5%B7%B1%E5%8A%A8%E6%89%8B%E7%BC%96%E5%86%99jekyll%E8%AF%84%E8%AE%BA%E6%8F%92%E4%BB%B6.html#%E6%8F%92%E4%BB%B6%E4%BD%BF%E7%94%A8
