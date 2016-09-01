#!/bin/sh
function check() {
  if [ $1 != 0 ];then
    echo "exec fail"
    exit 1
  fi
}
PROJECT_NAME=mw_auto
gradle clean && gradle installDist -x Test
check $?
cp -r conf/ build/install/"$PROJECT_NAME"/bin/conf/
cd build/install/
tar cvzf  "$PROJECT_NAME".tar.gz "$PROJECT_NAME"/
mv -f "$PROJECT_NAME".tar.gz ../../
echo "MW_SUCCESS"