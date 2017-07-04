package com.sk.netty.httpxml;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/4
 * Time: 17:08
 */
public class HttpXmlClient {
	public void connect(int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast("http-decoder", new HttpResponseDecoder());
						ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
						ch.pipeline().addLast("xml-decoder", new HttpXmlResponseDecoder(Order.class, true));
						ch.pipeline().addLast("http-encoder", new HttpRequestEncoder());
						ch.pipeline().addLast("xml-encoder", new HttpXmlRequestEncoder());
						ch.pipeline().addLast("xmlClientHandler", new HttpXmlClientHandler());
					}
				});
			ChannelFuture f = b.connect(new InetSocketAddress(port)).sync();
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 8080;
		if(null != args && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch(Exception e) {

			}
		}
		new HttpXmlClient().connect(port);
	}
}
