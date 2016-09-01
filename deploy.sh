#!/bin/sh
./build.sh

#10.0.21.135 G3

TARGET_IPS="10.0.21.135";

TARGET_DIR="/opt/service/";

SERVICE_NAME="mw_auto";

echo "start to deploy ${TARGET_IPS}"

if [ "$1" == "full" ]; then
    scp "${SERVICE_NAME}".tar.gz root@"${TARGET_IPS}":"${TARGET_DIR}"
    ssh root@"${TARGET_IPS}" "cd ${TARGET_DIR};rm -rf ${SERVICE_NAME}/;tar xvzf ${SERVICE_NAME}.tar.gz"
else
    scp build/libs/* root@"${TARGET_IPS}":${TARGET_DIR}${SERVICE_NAME}/lib/
fi

ssh root@"${TARGET_IPS}" "source /etc/profile;${TARGET_DIR}${SERVICE_NAME}/bin/conf/deploy/restart.sh"