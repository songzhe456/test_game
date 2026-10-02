package com.game.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class Server {
    private static ChannelFuture channelFuture;
    private final int port;
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public Server(int port){
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            LOGGER.info("服务器已启动");
            serverBootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringEncoder())
                                    .addLast(new ServerHandler());
                        }
                    });

            channelFuture = serverBootstrap.bind().sync();
            LOGGER.info("正在监听{}",channelFuture.channel().localAddress());

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            LOGGER.error("服务器出现异常：", e);
        }
        finally {
            group.shutdownGracefully().sync();
        }
    }

    public static ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    class ServerHandler extends ChannelInboundHandlerAdapter{
        private static String msg;
        private static ByteBuf in;
        private static String content;
        Logger logger = LoggerFactory.getLogger(ServerHandler.class);
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            in = (ByteBuf) msg;
            content = in.toString(CharsetUtil.UTF_8);
            in.release();
            logger.info("已接收客户端传来的{}",content);
            new GameRoll().run();

            ctx.writeAndFlush("服务器已接收" + content);
        }

        public static String getContent() {
            return content;
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.error("服务器处理出现异常",cause);
            ctx.close();
        }
        public static void setMsg(String msg) {
            ServerHandler.msg = msg;
        }

        public static String getMsg() {
            return msg;
        }
    }
}