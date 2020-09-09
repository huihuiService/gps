#!/bin/sh

JAVA_CMD=java

APP_NAME=gps-msg-handler
APP_VERSION=0.0.1-SNAPSHOT
APP_PROFILE=local

APP_HOME=$(cd `dirname $0`; pwd)
# CUSTOM_JAVA_OPTS
CUSTOM_OPTS=-Dlogging.config=config/log4j2.xml
# APP_MAINCLASS=
APP_JAR=lib/${APP_NAME}-${APP_VERSION}.jar
APP_PARAMS="--spring.profiles.active=${APP_PROFILE}"

CLASSPATH="$CLASSPATH:$APP_HOME/config"
for i in "$APP_HOME"/lib/*; do
   CLASSPATH="$CLASSPATH":"$i"
done

MemoryArgs="-Xmx1024M"

VmArgs="-XX:-UseGCOverheadLimit -XX:+UseConcMarkSweepGC -XX:GCTimeRatio=19 -Xloggc:gc.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime -XX:HeapDumpPath=./java_pid.hprof -XX:+HeapDumpOnOutOfMemoryError"

pidfile=$APP_HOME/proj.pid

psid=0

checkpid() {
   javaps=`cat $pidfile 2>/dev/null`
   if [ -n "$javaps" ]; then
      psid=`echo $javaps`
   else
      psid=0
   fi
}

start() {
   checkpid

   if [ $psid -ne 0 ]; then
      echo "WARN: $APP_NAME already started! (pid=$psid)"
   else
      echo -n "Starting $APP_NAME..."
      nohup $JAVA_CMD $VmArgs $MemoryArgs -cp $CLASSPATH $CUSTOM_OPTS -jar $APP_JAR $APP_PARAMS >/dev/null 2>&1 &
      echo $! > $pidfile
      checkpid
      if [ $psid -ne 0 ]; then
         echo " (pid=$psid) [OK]"
      else
         echo " [Failed]"
      fi
   fi
}

stop() {
   checkpid

   if [ $psid -ne 0 ]; then
      echo -n "Stopping $APP_NAME... (pid=$psid) "
      tried=0
      while [ $tried -lt 30 ]
      do
        kill -15 $psid 2>/dev/null
        if [ $? -ne 0 ]; then
            rm $pidfile
            echo "[OK]"
            return
        fi
        sleep 1
        echo -n "..."
        tried=`expr $tried + 1`
      done
      kill -15 $psid 2>/dev/null
      if [ $? -ne 0 ]; then
        rm $pidfile
        echo "[OK]"
      else
        echo "[Failed]"
      fi
   else
      echo "WARN: $APP_NAME is not running!"
   fi
}

console() {
   checkpid

   if [ $psid -ne 0 ]; then
      echo "WARN: $APP_NAME already started! (pid=$psid)"
   else
      $JAVA_CMD $VmArgs $MemoryArgs -cp $CLASSPATH $CUSTOM_OPTS -jar $APP_JAR $APP_PARAMS
   fi
}

status() {
   checkpid

   if [ $psid -ne 0 ];  then
      echo "$APP_NAME is running! (pid=$psid)"
   else
      echo "$APP_NAME is not running!"
   fi
}

case "$1" in
   'start')
      start
      ;;
   'stop')
     stop
     ;;
   'restart')
     stop
     start
     ;;
   'console')
     console
     ;;
   'status')
     status
     ;;
   *)
     echo "Usage: $0 {start|stop|restart|console|status}"
     exit 1
esac
exit 0
