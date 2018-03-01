package com.itfan.client.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ChatMessage
 * 消息封装
 *
 * @author: ralap
 * @date: created at 2017/11/12 13:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    public static final int TYPE_POINT = 1;
    public static final int TYPE_GROUP = 0;
    public static final int TYPE_ROBOT= 2;

    private String sender;
    private String reciver;
    private String content;
    private Date senderDate;
    /*
    消息类型 0：群发，1.点对点
     */
    private int type;
}
