package com.hnzy.per.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class Str16Encoder extends ProtocolEncoderAdapter {

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		System.out.println("######编码######");
		byte[] bytes = (byte[]) message;

		IoBuffer buffer = IoBuffer.allocate(256);
		buffer.setAutoExpand(true);

		buffer.put(bytes);
		buffer.flip();
      //服务器--客户端
		out.write(buffer);
		out.flush();
		buffer.free();

	}
	
	
}
