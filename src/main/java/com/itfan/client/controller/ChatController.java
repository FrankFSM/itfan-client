package com.itfan.client.controller;


import com.itfan.client.domain.ChatMessage;
import com.itfan.client.domain.ItfanUser;
import com.itfan.client.service.TLService;
import com.itfan.client.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ChatController
 *
 * @author: ralap
 * @date: created at 2017/11/12 12:55
 */
@Controller
@Slf4j
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private TLService tlService;

    @Autowired
    private UserService userService;

    @MessageMapping("/sendToAll")
    @SendTo("/groupChat/chat")
    public ChatMessage say(Principal principal, ChatMessage message) throws InterruptedException {

        log.info("-------------SENT START-------------");

        ChatMessage responsetCaht = new ChatMessage(principal.getName(), "all",
                message.getContent(),
                new Date(), ChatMessage.TYPE_GROUP);
        log.info("-------------SENT END-------------");

        return responsetCaht;
    }

    @MessageMapping("/sendToPoint")
    public void handleChat(Principal principal, ChatMessage message) {
        log.info("-------------SENT START-------------");

        String sender;
        String reciver;
        String content;

        if ("robot".equals(message.getReciver())) {
            sender = "robot";
            reciver = principal.getName();
            content = tlService.getInfo(message.getContent());
        } else {
            sender = principal.getName();
            reciver = message.getReciver();
            content = message.getContent();

        }

        {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setContent(content);
            chatMessage.setSender(sender);
            messagingTemplate
                    .convertAndSendToUser(reciver, "/pointChat/chat", chatMessage);
        }
        log.info("-------------SENT START-------------");

    }

    @RequestMapping("/chat")
    public String chat(Principal principal, HttpServletRequest request) {

        List<ItfanUser> all = new ArrayList<>();
        ItfanUser groupUser = new ItfanUser();
        groupUser.setUserName("群聊");

        ItfanUser robot = new ItfanUser();
        robot.setUserName("小R [机器人]");
        all.add(groupUser);
        all.add(robot);

        List<ItfanUser> friendList = userService.getAll();

        for (ItfanUser model : friendList) {
            if (principal.getName().equals(model.getUserName())) {
                friendList.remove(model);
                break;
            }
        }
        all.addAll(friendList);
        request.setAttribute("friendList", all);
        return "chat";

    }
}
