package com.zhengqing.common.netty.model;

import com.zhengqing.common.netty.enums.NettyMsgCmdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 消息基类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/2/26 11:12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NettyMsgBase<T> {

    /**
     * 消息指令
     */
    private NettyMsgCmdType cmd;

    /**
     * 推送消息体
     */
    private T data;

}
