package com.tcray.rayrpc.core.handler;

import com.alibaba.fastjson.JSONArray;
import com.tcray.rayrpc.core.meta.MessageType;
import com.tcray.rayrpc.core.meta.RpcContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author lirui
 * 命令处理器
 * 功能: 定义命令处理器接口
 */
public interface CommandHandler {

    /**
     * 处理命令
     * @param rpcContext
     * @param channelCtx
     * @param jsonArray
     */
    void processCommand(RpcContext rpcContext, ChannelHandlerContext channelCtx, JSONArray jsonArray);

    /**
     * 返回消息类型
     * @return
     */
    MessageType msgType();

}
