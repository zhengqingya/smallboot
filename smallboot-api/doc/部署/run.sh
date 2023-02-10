####################################
# @description 运行docker镜像
# @params $? => 代表上一个命令执行后的退出状态: 0->成功,1->失败
#         ${1} => 脚本第1个参数: 要运行的名称
# @example => sh run.sh
# @author zhengqingya
# @date 2023/2/4 12:49 PM
####################################

# 在执行过程中若遇到使用了未定义的变量或命令返回值为非零，将直接报错退出
set -eu

# 获取脚本第一个参数
APP=${1}

# 删除旧容器
docker ps -a | grep ${APP} | awk '{print $1}' | xargs -i docker stop {} | xargs -i docker rm {}

# 删除旧镜像
docker images | grep -E ${APP} | awk '{print $3}' | uniq | xargs -I {} docker rmi --force {}

# 运行
docker-compose -p smallboot up -d ${APP}
