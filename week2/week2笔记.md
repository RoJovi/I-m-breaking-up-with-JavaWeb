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
* 前端→代理→后端集群

## 工程搭建结构

* controller
  * 控制层类（请求处理类）`@RestController`(=`@Controller`+`@ResponseBody`响应前端

    ```java
    @GetMapping("/depts")  #请求路径&请求方式
    public Result list(){
    	return Result.success(deptList);
    }
    ```
  
    
  
* mapper

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

## Mybatis数据封装

`camel`命名映射开关



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

    

## 动态SQL`<foreach>`

 获取主键：将生成的主键封装给对象的id

```sql
@Option(useGeneratedKeys = true,keyProperty = "id")	
```

* collection集合名称

* item遍历元素

* separator分隔符号

* open开始拼接片段

* close结束拼接片段 

  

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