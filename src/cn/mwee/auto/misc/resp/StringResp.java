package cn.mwee.auto.misc.resp;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNonStringKeyAsString;
import java.nio.charset.Charset;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import com.alibaba.fastjson.serializer.SerializerFeature;
import cn.mwee.auto.misc.common.server.RestProcesser;
import cn.mwee.auto.misc.common.server.resp.Resp;

public class StringResp extends Resp {

	private static final long serialVersionUID = 1L;

	public static final SerializerFeature[] JSON_SERIAL_FEATURES = {WriteMapNullValue, WriteNonStringKeyAsString};
	public String sr;
	public String contentType;

	
	public StringResp() {
	}
	
	/**
	 * 默认contentType xml
	 * @param sr
	 */
	public StringResp(String sr) {
		this.sr = sr;
		contentType=RestProcesser.CONTENT_TYPE_XML;
	}

	public StringResp(String sr,String contentType) {
		this.sr = sr;
		this.contentType=contentType;
	}
	
	@Override
	public ChannelBuffer toJson(){
		return ChannelBuffers.copiedBuffer(sr, Charset.forName("UTF-8"));
	}
	
	@Override
	public String contentType()
	{
		return contentType;
	}
}
