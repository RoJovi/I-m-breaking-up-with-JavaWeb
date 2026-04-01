## yml配置文件



```yaml
#定义对象/Map
user:
 name: 我
 age: 99

#定义数组/List/Set集合
hobby:
 - java
 - game
  
```

* 缩进对齐表示层级结构
* -和:后面都有空格

* '0123456'防止误识别八进制



## 前后端接口

* 根据原型＋需求

* REST风格url ....../users/1
  * GET
  * DELETE
  * POST
  * PUT
  
* 前端→代理→后端集群

  nginx反向代理配置



## 工程搭建结构

* controller
  * 控制层类（请求处理类）`@RestController`(=`@Controller`+`@ResponseBody`响应前端,

  * `@RequestMapping("/depts")`指定公共访问路径
  
    * 查询
    
      ```java
      @GetMapping //请求路径&请求方式
      public Result list(){
      	return Result.success(deptList);
      }
      
      @GetMapping("/{id}")  //根据id查询
      public Result getInfo(@PathVariable Integer id){	//注解路径参数绑定给形参
      	return Result.success(emp);
      }
      ```
    
    * 删除
    
      `delete(Integer id)`方法
    
      批量删除参数用集合（需要`@RequestParam`标识）/数组接收
    
    * 新增
    
      ```java
      @PostMapping 
      public Result add(@RequestBody Dept dept){	//用实体对象接收JSON格式的请求参数
      	return Result.success();
      }
      ```
    
    * 修改：和新增一样需要接收JSON格式的请求
    
      `@PutMapping`
  
* mapper

  * 方法命名不和业务功能挂钩，见名生意

* pojo	

  * 数据实体类

  * `Result`类返回封装结果

    ```java
    public class Result{
        private Integer code;  //1成功 0失败
        private String msg;
        private Object data;
    }
    ```

* service
  * 接口
  * 实现类`@Service`

* exception

  * 全局异常处理器——统一返回结果Result与前端连接

    ```java
    @RestControllerAdvice	//相当于@ControllerAdvice和@ResponseBody
    public class GlobalExceptionHandler{
        @ExceptionHandler
        public Result HException(Exception e){	//根据继承关系从下至上捕捉错误
            log.error("有人在代码里下毒！",e);
            return Result.error("有刺客！");
        }
    }
    ```

* utils

  * JwtUtils

* filter
  * filter类（`@WebFilter(urlPatterns="/*")`）连接servlet包（引导类需要`@ServletComponentScan`支持）下的filter接口
  
* Interceptor

  * Interceptor类（`@Component`）连接HandlerInterceptor接口

* config放配置类
  * `@Configuration` WebConfig implements WebMvcConfigurer
    * `addInterceptors`
      * `registry.addInterceptor(new DemoInterceptor).addPathPatterns("/**");`    //注册拦截器并指定路径



## Mybatis设定

`camel`命名映射开关（yml中设置）

#### XML映射

##### 基础配置

* 同包同名（目录用/才能形成包结构）
* 标签`<mapper namespace = "">`接全限定名
* id与方法名一致
* 返回类型一致
  * `<resultType>`代表封装对象
  * `<resultMap>`自定义结果级，手搓封装，用于无法一一对应的情况
    * `<id column = 字段 property = 属性/> `
    * 其它用`<result column>`标签
    * `<collection property="list" ofType="集合泛型">`

##### 动态SQL`<foreach>`

 获取主键：将生成的主键封装给对象的id

```sql
@Option(useGeneratedKeys = true,keyProperty = "id")	
```

* collection集合名称
* item遍历元素
* separator分隔符号
* open开始拼接片段
* close结束拼接片段 

##### if动态优化

* `<set>`去除多余的","
* `<if test ="username != null">`优化修改时机



## LogBack

* `@Slf4j`注解后生成log对象

* 日志有级别，常用`info`记录,`debug`



## 多表

#### 关联方式

* [ ] 物理外键
* [x] 逻辑外键

#### 分类

* 一对多
  * 在多的表创建关联
* 一对一
* 多对多
  * 创建中间表

#### 查询

* 内连接

  * 隐式

    ```sql
    select * from table1 t1,table2 t2 where t1.table2_id = t2.id;
    ```

  * 显示

    `inner join table2 on`

* 外连接

  * 左外连接

    `left join table2 on`

  * 右外连接

    `right join table2 on`

* 子查询

  * 标量子查询

    `where a = (select语句)`

  * 列子查询

    `where a in (select语句)`

  * 行子查询

    `where (a,b) = (select语句)`

  * 表子查询

    本质是消除无效笛卡尔积`from table , (select group by语句) where`

    

## 事务管理

Service层一个方法多次操控数据库：`@Transaction`

* `(rollbackFor = {Exception.class})`
* `(propagation = Propagation.ReQUIRES_NEW)`创建新事物，排除外层事务的回滚影响

```yaml
#配置事务管理日志级别
logging:
  level:
    org.springframework.jdbc.support.JdbcTransactionManager: debug
```

#### 四大特性ACID

* 原子性
* 一致性
* 隔离性
* 持久性



## 文件上传

`MultipartFile file`对象

#### 本地存储

* 获取文件名

  `file.getOriginalFilename()`

* 转存

   `file.transferTo(new File("D:/images/"+originalFilename))`

#### 防撞名

* `UUID.randomUUID().toString()`
* 获取文件类型`substring`,`lastIndexOd(".")`等API

#### 配置文件大小

yml中multipart -> 点击max



## 登录认证token令牌

存储登录标记→（统一拦截）请求→获取登录标记【会话技术】→登录校验→响应

#### 会话技术

* 同一次会话，多次请求，会共享数据

* 客户端Cookie

  * HTTP协议支持 
    * 响应头，cookie自动保存于浏览器本地
    * 请求头，cookie自动携带至服务器端
  * 但是App用不了
  * 不安全
  * 前后端不能跨域（协议、IP、端口号）

* 服务端Session

  * 底层基于cookie
  * session存于服务器（集群服务器不好用）

* 令牌技术（主流JWT）

  * 组成

    * Header（json数据进行Base64编码）：令牌类型、签名算法
    * Payload（同上）：自定义信息、默认信息
    * Signature：机密校验

  * 生成

    ```java
    //业务层自定义信息
    Map<String,Object>dataMap = new HashMap<>();	//Map集合
    dataMap.put("id",1);
    dataMap.put("username","admin");
    ```
    
    ```java
    //静态方法
    return Jwts.builder()
    				.signWith(SignatureAlgorithm.HS256,"(密钥)")//加密算法
    				.addClaims(dataMap)	//添加自定义信息
    				.setExpiration(new Date(System.currentTimeMillis()+3600))	//过期时刻
    				.compact();	//生成令牌 
    ```
    
  * 解析
  
    ```java
    //静态方法
    String token = "";
    return Jwts.parser()
        	   .setSigningKey("")	//如果密钥错了会报错，用try-catch
        	   .parseClaimsJws(token)	//解析令牌
        	   .getBody;	//获取信息
    ```

#### 统一拦截

* 用于登录校验、统一编码处理、敏感字符处理

* 顺序：Filter1→Filter2（按照命名顺序确定1/2）→（SpringBoot资源）（Interceptor→资源）

​    （响应）           ←                                                       ←                                                            ←

##### 过滤器Filter

* 初始化方法`init`

* **`doFilter`方法拦截到请求**

  ```java
  HttpServletRequest request = (HttpServletRequest) servletRequest;	//强转回去
  HttpServletResponse response = (HttpServletResponse) servletResponse;
  
  String requestURI = request.getRequestURI();	//获取请求路径
  String token = request.getHeader("token");	//获取令牌
  
  if(requestURI.contains("login")){
  	filterChain.doFilter(request,response);	//放行
      return;
  }
  
  response.setStatus(HttpServletResponse.401)	//返回错误信息
  return;
  ```

* 销毁方法`destroy`

* 执行流程

  * 放行前Filter内逻辑
  * 放行访问相关资源
  * 放行后回到Filter接着执行后半部分逻辑
* 拦截路径

  * /*
  * /login
  * /emp/*（目录拦截）

	##### 拦截器Interceptor

* 动态拦截控制器方法，只拦截Spring环境资源
* **`preHandle`目标资源方法执行前**
  * 返回值为boolen值
  * 参数为不需要转化的HttpServletRequest和HttpServletResponse

* `postHandle`目标资源方法执行后
* `afterCompletion`视图渲染完成

* Interceptor拦截路径
  * .excludePathPatterns("/login")排除路径
  * /*是一级路径
  * /**是目录路径