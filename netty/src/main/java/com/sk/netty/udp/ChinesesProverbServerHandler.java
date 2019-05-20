package com.sk.netty.udp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/5
 * Time: 16:03
 */
public class ChinesesProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	private static final String[] DICTIONARY = {
		"只要功夫深，铁杵磨成针。",
		"旧时王谢堂前燕， 飞入寻常百姓家。",
		"一寸光阴一寸金，寸金难买寸光阴。",
		"老骥伏枥，志在千里。壮士暮年，壮心不已。",
		"长风破浪会有时，直挂云帆济沧海。",
	};

	private String nextQuote() {
		int quotedId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
		return DICTIONARY[quotedId];
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		String req = packet.content().toString(CharsetUtil.UTF_8);
		System.out.println(req);
		if("谚语字段查询？".equals(req)) {
			ctx.writeAndFlush(new DatagramPacket(
				Unpooled.copiedBuffer("谚语查询结果： " + nextQuote(), CharsetUtil.UTF_8), packet.sender()));
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
		cause.printStackTrace();
	}
}
