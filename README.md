# OurBlog项目总结
多人博客系统

项目地址：后端：[gqd000/OurBlog: 多人博客系统 (github.com)](https://github.com/gqd000/OurBlog)

​	前端：[1664635775/BlogDemo (github.com)](https://github.com/1664635775/BlogDemo)

## TIPS

- 强行关闭甲骨文服务器防火墙：

  `bash <(curl -sL www.wqzs.vip/zy/.sh/open_oracle_cloud_firewall.sh)`

- mysql服务器时间不同步解决：`useTimezone=true&serverTimezone=Asia/Shanghai`；

- 前端JS传回来用雪花算法生成ID时，要传字符串类型，否则JS会爆Number；

- 前后端交互跨域问题：

  后端添加`CorsConfig`类继承`WebMvcConfigurer`：

  ```java
  @Configuration
  public class CorsConfig implements WebMvcConfigurer {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
          //添加映射路径
          registry.addMapping("/**")
                  //是否发送Cookie
                  .allowCredentials(true)
                  //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
                  .allowedOrigins("*")
                  .allowedOriginPatterns("*")
                  //放行哪些请求方式
                  .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                  //.allowedMethods("*") //或者放行全部
                  //放行哪些原始请求头部信息
                  .allowedHeaders("*")
                  //暴露哪些原始请求头部信息
                  .exposedHeaders("*");
      }
  }
  ```

- 创建实体类时，要实现序列号接口，即`implements Serializable`，才能存入Redis缓存。

- 注意Redis缓存删除时间。


