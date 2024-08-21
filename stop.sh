#! /bin/bash


APP_NAME=alarmy.*[.].ar

ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}' | while read pid ;do kill -9 $pid;done

if [ $? -ne 0 ]
then
   echo [error] : $0
   exit -1
fi

echo [stop] : $APP_NAME
