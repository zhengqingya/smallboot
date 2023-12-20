# maven父子工程统一版本管理

#### 1、微服务中父工程`pom.xml`声明某一依赖并指定版本

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.seata</groupId>
            <artifactId>seata-spring-boot-starter</artifactId>
            <version>1.5.2</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

#### 2、子工程`pom.xml`引用依赖，无需再指定版本号，即实现全局统一版本管理

```
<dependencies>
    <dependency>
        <groupId>io.seata</groupId>
        <artifactId>seata-spring-boot-starter</artifactId>
        <!--            <version>1.5.2</version>-->
    </dependency>
</dependencies>
```
