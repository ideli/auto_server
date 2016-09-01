#!/bin/sh
PRGDIR=`dirname $0`
"$PRGDIR"/stop.sh
"$PRGDIR"/start.sh
echo "MW_SUCCESS"