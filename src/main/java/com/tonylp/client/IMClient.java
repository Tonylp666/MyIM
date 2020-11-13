package com.tonylp.client;

import com.tonylp.client.console.impl.ConsoleCommandManager;
import com.tonylp.client.console.impl.LoginConsoleCommand;
import com.tonylp.server.utils.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class IMClient {
    private static final Log LOG = LogFactory.getLog(IMClient.class);

    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void clientStart(){
        NioEventLoopGroup worlerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(worlerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                    }
                });
        connect(bootstrap,HOST,PORT,MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host,port).addListener(future ->{
            if (future.isSuccess()){
                LOG.info(new Date() + " : 连接成功，启动控制台线程......");
                Channel channel = ((ChannelFuture) future).channel();
                // todo: 启动控制终端线程，开发阶段使用。后期如果有时间开发安卓、ios 客户端。
                startConsoleThread(channel);
            }else if (retry == 0){
                LOG.error("重试次数已经用完，连接失败！");
            }else{
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                LOG.info(new Date() + " : 连接失败，第 "+ order + " 次重连......");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(()->{
            while (!Thread.interrupted()){
                if (!SessionUtil.hasLogin(channel)){
                    loginConsoleCommand.exec(scanner,channel);
                } else {
                    consoleCommandManager.exec(scanner,channel);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        clientStart();
    }
}
