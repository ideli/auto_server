#!/bin/sh
PROJECT_NAME=mw_auto
PROCESS_NAME="${PROJECT_NAME}-1.0-SNAPSHOT"
echo "stopping..."
pkill -f "$PROCESS_NAME"
sleep 5
if test $(pgrep -f "$PROCESS_NAME"|wc -l) -ne 0
then
pkill -9 -f "$PROCESS_NAME"
fi
echo "stop ok"
echo "MW_SUCCESS"
