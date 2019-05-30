package org.learn;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class IOServer {

	public static void main(String[] args) throws InterruptedException {

		   EventLoopGroup bossGroup = new NioEventLoopGroup();
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	            ServerBootstrap b = new ServerBootstrap();
	            b.group(bossGroup, workerGroup)
	              .channel(NioServerSocketChannel.class)
	              .childHandler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {
	                @Override
	                public void initChannel(io.netty.channel.socket.SocketChannel ch)
	                  throws Exception {
	                    ch.pipeline().addLast(
	                      new ServerInboundHandler());
	                }

	            }).option(ChannelOption.SO_BACKLOG, 128)
	              .childOption(ChannelOption.SO_KEEPALIVE, true);

	            ChannelFuture f = b.bind(8080).sync();
	            f.channel().closeFuture().sync();
	        } finally {
	            workerGroup.shutdownGracefully();
	            bossGroup.shutdownGracefully();
	        }

	}
}
