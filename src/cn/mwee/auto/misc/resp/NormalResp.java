package cn.mwee.auto.misc.resp;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import cn.mwee.auto.misc.common.server.resp.Resp;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;

public class NormalResp extends Resp{
	public static final SerializerFeature[] JSON_SERIAL_FEATURES = {WriteMapNullValue, WriteNonStringKeyAsString};
	private static final long serialVersionUID = 1L;
	public NormalReturn sr;

	public NormalResp() {
	}

	public NormalResp(NormalReturn sr) {
		this.sr = sr;
	}

	@Override
	public ChannelBuffer toJson(){
		String json = JSONObject.toJSONString(sr, JSON_SERIAL_FEATURES);
		return ChannelBuffers.copiedBuffer(json, Charset.forName("UTF-8"));
	}

}
