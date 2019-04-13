package com.sdsoon.server;

import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Objects;

/**
 * Created By Chr on 2019/4/12.
 */
@Slf4j
public class ChrWsMsgHandler implements IWsMsgHandler {

    public static final ChrWsMsgHandler chr =
            new ChrWsMsgHandler();

    /**
     * 握手时走这个方法，业务可以在这里获取cookie，request参数等
     */
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String clientIp = httpRequest.getClientIp();
        String myName = httpRequest.getParam("name");
        Tio.bindUser(channelContext, myName);

        log.info("收到来自{}的ws握手包\r\n{}", clientIp, httpRequest.toString());
        return httpResponse;

    }

    /**
     * 握手之后走这个方法
     *
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @throws Exception
     */
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {

        //绑定到群组，后面-有群发
        Tio.bindGroup(channelContext, Const.GROUP_ID);
        //得到所有的群组
        int count = Tio.getAllChannelContexts(channelContext.groupContext).getObj().size();

        String msg = "{name:'admin',message:'" + channelContext.userid + " 进来了，共【" + count + "】人在线" + "'}";
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, ChrServerConfig.CHARSET);
        //群发,包装数据
        Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);
    }

    /**
     * 字节消息（binaryType = arraybuffer）过来后会走这个方法
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 当客户端发close flag时，会走这个方法
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        Tio.remove(channelContext, "receive close flag");
        return null;
    }

    /*
     * 字符消息（binaryType = blob）过来后会走这个方法
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();

        //获得http握手包
        HttpRequest httpRequest = wsSessionContext.getHandshakeRequest();

        if (log.isInfoEnabled()) {

            log.debug("握手包{}", httpRequest);
        }
        log.info("收到ws消息:{}", text);
        if (Objects.equals("心跳内容", text)) {
            return null;
        }

        //
        String msg = "{name:'" + channelContext.userid + "',message:'" + text + "'}";
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, ChrServerConfig.CHARSET);
        //群发
        Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);
        //返回值是要发送给客户端的内容，一般都是返回null
        return null;

    }
}
