#!/bin/sh

PID=`ps  | grep java | awk '{print $1}'`
PID2=`ps -ef | grep "run/usr/bin/python3 /usr/bin/flask run --host=0.0.0.0 --host=0.0.0.0" | awk '{print $2}'`
if [ -n "$PID" ]
then
  echo "=====spring is running at" $PID "Shutdown spring now"
  echo "=====flask is running at" $PID2 "Shutdown flask now"

  sudo kill -9 $PID
  sudo kill -9 $PID2
else
  echo "=====spring isn't running====="
fi
