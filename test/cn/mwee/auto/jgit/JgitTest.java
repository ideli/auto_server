/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.jgit;

import static org.junit.Assert.*;

import cn.mwee.auto.deploy.util.AutoConsts;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.Test;

import java.util.Collection;

/**
 * @author mengfanyuan
 * 2016年7月6日下午2:50:11
 */
public class JgitTest {

	@Test
	public void test() {
		String REMOTE_URL = "http://git.9now.net:10080/devops/mw_auto.git";
		Collection<Ref> refs = null;
		CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("mengfanyuan","wt19892414");
		try {
			refs = Git.lsRemoteRepository()
                    .setHeads(true)
                    .setTags(true)
                    .setRemote(REMOTE_URL)
					.setCredentialsProvider(credentialsProvider)
                    .call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}

		for (Ref ref : refs) {
			System.out.println("Ref: " + ref.getName());
			System.out.println("Ref: " + ref.getLeaf());

			System.out.println("Ref: " + ref.getObjectId());

			System.out.println("Ref: " + ref.getPeeledObjectId());

			System.out.println("Ref: " + ref.getStorage());
			System.out.println("Ref: " + ref.getTarget());

			System.out.println(">>>>>>>>>>>>>>>>>>>>>>");
		}
	}
	
	@Test
	public void test2() {
		String str = "abc\n\n\n";
		char[] cs = str.toCharArray();
		for(char c : cs){
			System.out.println(0xffffffff);
		}
	}

	@Test
	public void test3() {
		System.out.println(AutoConsts.GroupType.PrepareGroup);
		System.out.println(AutoConsts.GroupType.PreGroup);
		System.out.println(AutoConsts.GroupType.PostGroup);
	}
}
