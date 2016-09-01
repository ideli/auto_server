package cn.mwee.auto.util;

import cn.mwee.auto.misc.common.util.Utilities;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by huming on 16/8/11.
 */
public class TestAddBatchZoneIps {

    public static void main(String[] args) {

        String ips= "127.0.0.1,, 192.168.1.10;10.0.21.13   10.0.21.135 1x.12.121 p.2 sa";

        String[] arrays = StringUtils.split(ips,",|;| ");

        for (String a : arrays)
        {
            if(Utilities.isIpAddress(a))
            {
                System.out.println("ip address ->" +a);
            }
            else
            {
                System.err.println("not ip " +a);
            }
        }

    }

}
