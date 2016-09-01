#!/bin/sh
echo "starting... please wait"
PROJECT_NAME=mw_auto
PROCESS_NAME="${PROJECT_NAME}-1.0-SNAPSHOT"
PRGDIR=`dirname $0`
cd $PRGDIR
cd ../../
nohup ./"$PROJECT_NAME" >/dev/null 2>&1 &
sleep 20
if test $(pgrep -f "$PROCESS_NAME"|wc -l) -eq 0
then
echo "start failed"
else
echo "start ok"
fi
echo "MW_SUCCESS"
