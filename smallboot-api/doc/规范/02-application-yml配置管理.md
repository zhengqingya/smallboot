# application-yml配置管理

各中间件单独配置

### 示例

数据库配置 `application-db.yml`

然后在项目模块需要时引入

```yml
spring:
  profiles:
    include:
      - db
```

