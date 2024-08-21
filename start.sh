#! /bin/bash


APP_NAME=alarmy-0.0.1-SNAPSHOT
APP_DIR=/home/ec2-user
APP_BUILD_FILE=$APP_NAME.war


if [ ! -d $LOG_DIR ];then
    sudo mkdir -p $LOG_DIR
    sudo chown ec2-user:ec2-user $LOG_DIR
fi

cd $APP_DIR

nohup java \
-jar $APP_DIR/$APP_BUILD_FILE \
--logging.file.path=$APP_DIR/log/service --spring.profiles.active=$SPRING_PROFILE \
> /dev/null 2>&1 &

sleep 1

ps -ef | grep $APP_NAME | grep -v grep

echo [start] : $APP_NAME
