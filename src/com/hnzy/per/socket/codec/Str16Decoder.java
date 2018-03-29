package com.hnzy.per.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


public class Str16Decoder extends ProtocolDecoderAdapter {
	
	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		int limit = in.limit();
		byte[] bytes = new byte[limit];
    	//客户端-服务器
		in.get(bytes);
		
		out.write(bytes);
	}

	
}
