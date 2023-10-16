# 环境部署

> 组件环境部署可参考 https://gitee.com/zhengqingya/docker-compose

```shell
# portainer
docker run -d -p 9000:9000 --restart=always --name portainer -v /var/run/docker.sock:/var/run/docker.sock registry.cn-hangzhou.aliyuncs.com/zhengqing/portainer-ce:2.16.2


# 当前目录下所有文件赋予权限(读、写、执行)
chmod -R 777 ./redis
chmod -R 777 ./rabbitmq

# 组件部署
docker-compose -f ./docker-compose.yml -p smallboot up -d mysql redis rabbitmq nginx_file

# 启用rabbitmq延时插件
# Linux
docker exec rabbitmq /bin/bash -c 'rabbitmq-plugins enable rabbitmq_delayed_message_exchange'
# Windows
docker exec -it rabbitmq /bin/bash
rabbitmq-plugins enable rabbitmq_delayed_message_exchange


# 服务部署 -- 根据自己的条件去启动
docker-compose -p smallboot up -d api
docker-compose -p smallboot up -d web
```

---

### Redis

```shell
# 连接redis
docker exec -it redis redis-cli -a 123456
```

### Nginx

前端部署访问后端接口需配置 [nginx.conf](./nginx/conf/nginx.conf) 中的 `/prod-api/`

```shell
# nginx修改配置后重载
nginx -s reload
```

### java

```shell
# 原生手动运行
nohup java -jar -Xmx500M -Xms500M -XX:+UseG1GC -Dspring.profiles.active=prod -Dserver.port=20011   app.jar >> app.log 2>&1 &
```

### web

前端线上访问需要配置nginx

```
http {
  underscores_in_headers on;     # 允许在HTTP请求头中使用下划线（underscore）字符
  server {
    proxy_set_header Host $host;  # 设置代理请求头中的主机名（Host）为客户端请求的主机名
    proxy_set_header X-Real-IP $remote_addr; # 将客户端的真实 IP 地址作为 X-Real-IP 请求头发送给后端服务器
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for; # 在请求头中添加 X-Forwarded-For 字段，用于追踪经过的代理服务器地址
    proxy_set_header User-Agent $http_user_agent; # 将客户端的 User-Agent 请求头字段传递给后端服务器
    
    location ^~ /prod-api/ {
        proxy_pass   http://ip:端口/;
    }
  }
}
```