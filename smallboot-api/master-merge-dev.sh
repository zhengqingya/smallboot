#!/bin/bash

####################################
# @description git合并dev分支到master分支
# @example => sh master-merge-dev.sh
# @author zhengqingya
# @date 2021/7/27 9:38 下午
####################################

# 在执行过程中若遇到使用了未定义的变量或命令返回值为非零，将直接报错退出
set -eu

echo 'start ...'

git checkout dev
git pull
git checkout master
git pull
git merge dev
git push
git checkout dev

echo 'end ...'
