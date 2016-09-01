package cn.mwee.auto.auth.deploy;

import cn.mwee.auto.deploy.service.IFlowManagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

/**
 * Created by huming on 16/7/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext.xml",})
public class TestConcurrentTask extends AbstractJUnit4SpringContextTests {

    @Resource
    private IFlowManagerService flowManagerService;


    @Test
    public void testInitFlowTasks() {
        boolean flag = flowManagerService.updateTaskStatus(2154,"SUCCESS");
        assertTrue(flag);
    }
}
