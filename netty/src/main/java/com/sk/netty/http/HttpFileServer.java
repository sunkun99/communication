package com.sk.netty.http;

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
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/5/31
 * Time: 12:52
 *
 * 简单的http服务器, 页面显示当前某个指定目录的文件系统的目录,可下载文件
 *
 */
public class HttpFileServer {
	private static final String DEFAULT_URL = "/netty/src";

	public void run(final int port, final String url) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			//设置主从线程池
			b.group(bossGroup, workerGroup)
				//指定通道channel的类型，由于是服务端，故而是NioServerSocketChannel
				.channel(NioServerSocketChannel.class)
				//SocketChannel的处理器
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						//负责把字节解码成Http请求
						ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
						/*
						 * 它负责把多个HttpMessage组装成一个完整的Http请求或者响应。
						 * 到底是组装成请求还是响应，则取决于它所处理的内容是请求的内容，还是响应的内容。
						 * 这其实可以通过Inbound和Outbound来判断，对于Server端而言，在Inbound 端接收请求，在Outbound端返回响应。
						 */
						ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
						//当Server处理完消息后，需要向Client发送响应。那么需要把响应编码成字节，再发送出去。故添加HttpResponseEncoder处理器。
						ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
						//该通道处理器主要是为了处理大文件传输的情形。大文件传输时，需要复杂的状态管理，而ChunkedWriteHandler实现这个功能。
						ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
						ch.pipeline().addLast("filerServerHandler", new HttpFileServerHandler(url));
					}
				});
			ChannelFuture future = b.bind("localhost", port).sync();
			System.out.println("HTTP文件目录服务器启动，网址是：" + "http://localhost:" + port + url);
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 1111;
		if(args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		String url = DEFAULT_URL;
//		if(args.length > 1) {
//			url = args[1];
			new HttpFileServer().run(port, url);
//		}
	}
}
