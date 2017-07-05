package com.sk.netty.filedownload;

import com.sk.netty.http.HttpFileServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/5
 * Time: 18:30
 */
public class FileServer {
	public void run(int port) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 100)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
						ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
						ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
						ch.pipeline().addLast(new FileServerHandler());
					}
				});
			ChannelFuture f = b.bind(port).sync();
			System.out.println("Strat file server at port :" + port);
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		int port = 3333;
		if(args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}
		new FileServer().run(port);
	}
}
