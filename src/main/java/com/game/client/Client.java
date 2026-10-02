package com.game.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    private final String host;
    private final int port;
    static ChannelFuture future;

    public Client(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bs = new Bootstrap();
            bs.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringDecoder())
                                    .addLast(new ClientHandler());
                        }
                    });
            future = bs.connect().sync();
            if (Config.FIGHT_WORD != null) {
                future.channel().writeAndFlush(Unpooled.copiedBuffer(Config.FIGHT_WORD, CharsetUtil.UTF_8));
            }
            else {
                LOGGER.info("战斗贺词被跳过");
            }
            future.channel().closeFuture().sync();
        }
        finally {
            group.shutdownGracefully().sync();
        }
    }

    @ChannelHandler.Sharable
    class ClientHandler extends SimpleChannelInboundHandler<String> {
        private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            logger.info("服务器发送了：{}", msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.error("客户端处理出现异常", cause);
            ctx.close();
        }
    }
}

