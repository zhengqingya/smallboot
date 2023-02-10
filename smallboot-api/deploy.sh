####################################
# @description 构建docker镜像
# @params $? => 代表上一个命令执行后的退出状态: 0->成功,1->失败
# @example => deploy.sh
# @author zhengqingya
# @date 2023/2/2 14:55
####################################

# 在执行过程中若遇到使用了未定义的变量或命令返回值为非零，将直接报错退出
set -eu

# 删除旧jar包
rm -rf docker/*.jar

# 打包
mvn clean install -Dmaven.test.skip=true

# 移动jar包
mv app/target/app.jar docker

# 构建docker镜像
cd docker
docker build -f Dockerfile --build-arg JAVA_OPTS="-XX:+UseG1GC" -t "registry.cn-hangzhou.aliyuncs.com/zhengqingya/smallboot-api:prod" . --no-cache
docker push registry.cn-hangzhou.aliyuncs.com/zhengqingya/smallboot-api:prod

# 删除jar包
rm -rf *.jar