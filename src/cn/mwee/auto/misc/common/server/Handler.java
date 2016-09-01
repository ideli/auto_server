package cn.mwee.auto.misc.common.server;

import cn.mwee.auto.misc.common.server.resp.Resp;


public interface Handler {

	 public Resp handleRequest(NettyHttpRequest request);
	 
}
