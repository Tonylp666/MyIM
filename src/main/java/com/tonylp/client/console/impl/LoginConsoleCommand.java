package com.tonylp.client.console.impl;

import com.tonylp.client.console.ConsoleCommand;
import com.tonylp.server.common.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

    }
}
