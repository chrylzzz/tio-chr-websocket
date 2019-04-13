package com.sdsoon.server;

import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.intf.Packet;
import org.tio.core.stat.IpStat;

/**
 * AioListener是处理消息的核心接口，它有两个子接口，
 * ClientAioListener和ServerAioListener，
 * 当用tio作tcp客户端时需要实现ClientAioListener，当用tio作tcp服务器时需要实现ServerAioListener，
 * <p>
 * Created By Chr on 2019/4/12.
 */
@Slf4j
public class ChrStatListener implements org.tio.core.stat.IpStatListener {

    public static final ChrStatListener chr = new ChrStatListener();


    @Override
    public void onExpired(GroupContext groupContext, IpStat ipStat) {
        //这里把统计数据 入库或者日志
//        if (log.isInfoEnabled()) {
//            log.info("可以把统计数据入库\r\n{}", Json.toFormatedJson(ipStat));
//        }
    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1, IpStat ipStat) throws Exception {

    }

    @Override
    public void onDecodeError(ChannelContext channelContext, IpStat ipStat) {

    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b, IpStat ipStat) throws Exception {

    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i, IpStat ipStat) throws Exception {

    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int i, IpStat ipStat) throws Exception {

    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, IpStat ipStat, long l) throws Exception {

    }
}
