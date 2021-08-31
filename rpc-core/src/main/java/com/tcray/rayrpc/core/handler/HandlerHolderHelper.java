package com.tcray.rayrpc.core.handler;

import com.tcray.rayrpc.core.meta.MessageType;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author lirui
 * 根据消息类型查询命令处理器
 */
public class HandlerHolderHelper {

    /**
     * 查找对应的消息处理类
     * @param messageType
     * @return
     */
    public static CommandHandler findHandler(MessageType messageType) {
        ServiceLoader<CommandHandler> commandHandlers = ServiceLoader.load(CommandHandler.class);
        Iterator<CommandHandler> iterator = commandHandlers.iterator();
        while (iterator.hasNext()) {
            CommandHandler commandHandler =  iterator.next();
            if (messageType.equals(commandHandler.msgType())) {
                return commandHandler;
            }
        }
        return null;
    }
}
