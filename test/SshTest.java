import cn.mwee.auto.misc.common.util.SSHManager;
import org.junit.Test;

/**
 * Created by huming on 16/7/6.
 */
public class SshTest {


    /**
     * Test of sendCommand method, of class SSHManager.
     */
    @Test
    public void testSendCommand()
    {
        System.out.println("sendCommand");

        /**
         * YOU MUST CHANGE THE FOLLOWING
         * FILE_NAME: A FILE IN THE DIRECTORY
         * USER: LOGIN USER NAME
         * PASSWORD: PASSWORD FOR THAT USER
         * HOST: IP ADDRESS OF THE SSH SERVER
         **/
        String command = "sh /root/test.sh";
        String userName = "root2";
        String password = "";
        String connectionIP = "rabbitmq";
        SSHManager instance = new SSHManager(userName, "/Users/huming/.ssh/id_rsa",connectionIP,null,null,1);
        String errorMessage = instance.connect();

        if(errorMessage != null)
        {
            System.out.println(errorMessage);
        }

        String expResult = "FILE_NAME\n";
        // call sendCommand for each command and the output
        //(without prompts) is returned
        String result = instance.sendCommand(command,0);
        System.out.println("----result----");
        System.out.println(result);

        if(result.contains("MW_SUCCESS"))
        {
            System.out.println("SUCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCESSSSSSSS");
        }

        // close only after all commands are sent
        instance.close();
    }

}
