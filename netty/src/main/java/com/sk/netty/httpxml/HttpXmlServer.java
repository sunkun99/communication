package com.sk.netty.httpxml;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/4
 * Time: 18:15
 */
public class HttpXmlServer {
	public void run(final int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
						ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65535));
						ch.pipeline().addLast("xml-decoder", new HttpXmlRequestDecoder(Order.class, true));
						ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
						ch.pipeline().addLast("xml-encoder", new HttpXmlResponseEncoder()) ;
						ch.pipeline().addLast("xmlServerHandler", new HttpXmlServerHandler()) ;
					}
				});
			ChannelFuture future = b.bind(new InetSocketAddress(port)).sync();
			System.out.println("HTTP订购服务器启动，网址是：http://localhost:" + port);
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 2222;
		if(args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		new HttpXmlServer().run(port);
	}
}
