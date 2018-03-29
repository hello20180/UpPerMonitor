package com.hnzy.per.socket.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class Str16CodecFactory implements ProtocolCodecFactory {
	
	private Str16Encoder encoder;
	private Str16Decoder decoder;

	public Str16CodecFactory(){
		encoder=new Str16Encoder();
		decoder=new Str16Decoder();
	} 
	
	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
