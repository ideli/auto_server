/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.user;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.mwee.auto.auth.model.AuthUser;
import cn.mwee.auto.auth.service.IUserService;

/**
 * @author mengfanyuan
 * 2016年6月29日上午10:08:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext.xml",})
public class UserServiceTest extends AbstractJUnit4SpringContextTests  {

	@Resource
	private IUserService userService;
	/**
	 * Test method for {@link cn.mwee.auto.auth.service.user.impl.UserService#addUser(cn.mwee.auto.auth.model.AuthUser)}.
	 */
	@Test
	public void testAddUser() throws Exception {
		AuthUser user = new AuthUser();
		user.setUsername("mengfanyuan");
		user.setPassword("aaa");
		user.setCreateTime(new Date());
		user.setSalt("123");
		boolean result = userService.addUser(user) > 0;
		if (result) {
			System.out.println("user:"+user.getUsername() +",success");
		}
		System.out.println("userService's hashcode:"+userService.hashCode());
	}

	/**
	 * Test method for {@link cn.mwee.auto.auth.service.impl.UserService#updateUser(cn.mwee.auto.auth.model.AuthUser)}.
	 */
	@Test
	public void testUpdateUser() {
		AuthUser user = new AuthUser();
		user.setId(1);
		user.setUsername("frainmeng");
		user.setPassword("bbbb");
		user.setCreateTime(new Date());
		user.setSalt("4556");
		boolean result = userService.updateUser(user);
	}

	/**
	 * Test method for {@link cn.mwee.auto.auth.service.impl.UserService#delUser(java.lang.Integer)}.
	 */
	@Test
	public void testDelUser() {
		userService.delUser(1);
	}

	/**
	 * Test method for {@link cn.mwee.auto.auth.service.impl.UserService#select(java.lang.Integer)}.
	 */
	@Test
	public void testSelect() {
		AuthUser user = userService.select(2);
		System.out.println(user.getUsername());
	}

	/**
	 * Test method for {@link cn.mwee.auto.auth.service.impl.UserService#query(cn.mwee.auto.auth.model.AuthUser)}.
	 */
	@Test
	public void testQuery() {
		AuthUser user = new AuthUser();
		user.setUsername("%meng%");
		List<AuthUser> users = userService.query(user);
		for (AuthUser tmpUser:users) {
			System.out.println(tmpUser.getUsername());
		}
	}
	public void testUpdatePassword(){
		AuthUser user = new AuthUser();
		user.setId(2);
		user.setPassword("123456");
		try {
			userService.updatePassword("123456","123456");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
