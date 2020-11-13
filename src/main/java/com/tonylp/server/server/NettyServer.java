package com.tonylp.server.server;

import com.tonylp.server.common.codec.Spliter;
import com.tonylp.server.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

public class NettyServer {
    private static final Log LOG = LogFactory.getLog(NettyServer.class);

    public static final int PORT = 9889;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public void run(int port){
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        // 设置辅助类
        final ServerBootstrap bootstrap = new ServerBootstrap();
        try{
            bootstrap.group(bossGroup,workerGroup);
            // 设置 socket 工厂
            bootstrap.channel(NioServerSocketChannel.class)
                    // 对应的是tcp/ip协议listen函数中的backlog参数,
                    // 函数listen(int socketfd,int backlog)用来初始化服务端可连接队列
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 对应于套接字选项中的SO_KEEPALIVE，用于设置TCP连接，
                    // 这个选项用于测试链接的状态。每两小时发送一个活动探测报文。
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 对应于套接字选项中的TCP_NODELAY,该参数的使用与Nagle算法有关,
                    // Nagle算法是将小的数据包组装为更大的帧然后进行发送，而不是输入一次发送一次,
                    // 因此在数据包不足的时候会等待其他数据的到了，组装成大的数据包进行发送，
                    // 虽然该方式有效提高网络的有效负载，但是却造成了延时
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {

                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            // 空闲检测
                            ch.pipeline().addLast(new IMIdleStateHandler());
                            // 基于帧的拆包器
                            ch.pipeline().addLast(new Spliter());
                            //
                            ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                            ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                            ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                            ch.pipeline().addLast(AuthHandler.INSTANCE);
                            ch.pipeline().addLast(IMHandler.INSTANCE);

                        }
                    });

            bind(bootstrap,port);
        }
        finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
        }
    }

    private static void bind(final ServerBootstrap bootstrap,final int port){
        bootstrap.bind(port).addListener(future -> {
           if (future.isSuccess()){
               LOG.info(new Date() + " port[ " + port + "]bind success.");
           }else{
               LOG.error(new Date() + "Exeception occured in NettyServer, port[ " + port + "]bind faild.");
           }
        });
    }

    public static void main(String[] args) {

    }
}
