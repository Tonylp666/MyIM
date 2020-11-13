package com.tonylp.client.console.impl;

import com.tonylp.client.console.ConsoleCommand;
import com.tonylp.server.utils.SessionUtil;
import io.netty.channel.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {
    private static final Log LOG = LogFactory.getLog(ConsoleCommandManager.class);
    private Map<String,ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("register", new RegisterConsoleCommand());
        consoleCommandMap.put("login", new LoginConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("sendMsgToUser", new SendMsgToUserConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("listGroupMembers", new ListGroupMembersConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
        consoleCommandMap.put("quitGroup", new QuitGroupConsoleCommand());
        consoleCommandMap.put("addFriend", new AddFriendConsoleCommand());
        consoleCommandMap.put("sendMsgToGroup", new SendMsgToGroupConsoleCommand());
        consoleCommandMap.put("removeFriend", new RemoveFriendConsoleCommand());
        consoleCommandMap.put("listFriend", new ListFriendConsoleCommand());
        consoleCommandMap.put("syncMessage", new SyncMsgConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        String operationType = scanner.next();

        if (!SessionUtil.hasLogin(channel)){
            // todo : goto login/register
            return ;
        }

        ConsoleCommand consoleCommand = consoleCommandMap.get(operationType);

        if (consoleCommand != null){
            consoleCommand.exec(scanner,channel);
        }else{
            LOG.error("无法识别[" + operationType + "]指令，请重新输入!");
        }

    }
}
