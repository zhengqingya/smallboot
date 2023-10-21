#!/bin/bash

####################################
# @description api运行脚本
# @params $? => 代表上一个命令执行后的退出状态: 0->成功,1->失败
#         $1 => 脚本第一个参数-操作类型(start|stop|restart)
# @example => sh run_api.sh restart
# @author zhengqingya
# @date 2021/7/17 3:59 下午
####################################


# 在执行过程中若遇到使用了未定义的变量或命令返回值为非零，将直接报错退出
# set -eu

# 检查参数个数
if [ "${#}" -lt 1 ]; then
	echo "\033[41;37m 脚本使用示例: sh run_api.sh start(运行)|stop(停止)|restart(重启)  \033[0m"
	exit
fi

# SOFT_DIR='/home/soft'

# 获取脚本第一个参数
APP_OPT=${1}
# 端口
APP_PORT=20011
# 名称
APP_NAME=app
# jar名 | war名
APP_JAR=${APP_NAME}.jar
# 程序根目录
APP_JAR_HOME=.
# 日志名
APP_LOG_NAME=jenkins
# 日志根目录
APP_LOG_HOME=.
# 程序运行参数
JAVA_OPTS=" -Xmx1000M -Xms1000M -XX:+UseG1GC -Dspring.profiles.active=prod -Dserver.port=${APP_PORT}  "

echo "本次操作服务名：[${APP_NAME}]"
echo "本次操作选择：[${APP_OPT}]"


# 停止
function stop(){
  echo "<-------------------------------------->"
  echo "[${APP_NAME}] ... stop ..."
  # 查看该jar进程
  pid=`ps -ef | grep ${APP_JAR} | grep -v 'grep' | awk '{print $2}'`
  echo "[${APP_NAME}] pid="${pid}
  # 存在则kill,不存在打印一下吧
  if [ "${pid}" ]; then
    kill -9 ${pid}
      # 检查kill是否成功
      if [ "$?" -eq 0 ]; then
          echo "[${APP_NAME}] stop success"
      else
          echo "[${APP_NAME}] stop fail"
      fi
  else
    echo "[${APP_NAME}] 进程不存在"
  fi
}


# 运行
function start(){
  echo "<-------------------------------------->"
  echo "[${APP_NAME}] ... start ..."
  cd ${APP_JAR_HOME}
  echo "当前路径:`pwd`"
  # 赋予可读可写可执行权限
  chmod 777 ${APP_JAR}
  echo "启动命令: nohup java -jar  ${JAVA_OPTS} ${APP_JAR} >> ${APP_LOG_HOME}/${APP_NAME}.log 2>&1 &"
  nohup java -jar ${JAVA_OPTS}  ${APP_JAR} >> ${APP_LOG_HOME}/${APP_NAME}.log 2>&1 &
  if [ "$?" -eq 0 ]; then
    echo "[${APP_NAME}] start success"
  else
    echo "[${APP_NAME}] start fail"
  fi
}


# 重启
function restart(){
  echo "<-------------------------------------->"
  echo "[${APP_NAME}] ... restart ..."
	stop
	start
}


# 多分支条件判断执行参数
case "${APP_OPT}" in
	"stop")
		stop
		;;
	"start")
		start
		;;
	"restart")
		restart
		;;
	*)
	echo "\033[41;37m 提示:不支持参数 命令 -> ${APP_OPT} \033[0m"
	;;
esac
