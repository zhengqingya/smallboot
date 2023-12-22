### 使用示例命令

此版本提供出一个`JAVA_OPTS`去设置jar的运行参数

```shell
# 打包镜像 -f:指定Dockerfile文件路径 --no-cache:构建镜像时不使用缓存
docker build -f Dockerfile --build-arg JAVA_OPTS="-XX:+UseG1GC" -t "registry.cn-hangzhou.aliyuncs.com/zhengqingya/smallboot-api:prod" . --no-cache

# 推送镜像
docker push registry.cn-hangzhou.aliyuncs.com/zhengqingya/smallboot-api:prod

# 拉取镜像
docker pull registry.cn-hangzhou.aliyuncs.com/zhengqingya/smallboot-api:prod

# 运行
docker run -d -p 888:888 --name smallboot-api -e JAVA_OPTS="-Xmx100M -Xms100M" registry.cn-hangzhou.aliyuncs.com/zhengqingya/smallboot-api:prod

# 删除旧容器
docker ps -a | grep smallboot-api | grep dev | awk '{print $1}' | xargs -i docker stop {} | xargs -I docker rm {}

# 删除旧镜像
docker images | grep -E smallboot-api | grep dev | awk '{print $3}' | uniq | xargs -I {} docker rmi --force {}
```
