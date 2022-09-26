package com.bitcamp221.didabara.websoket;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;


    @MessageMapping("/chat/message")
    public void enter(ChatMessage message) {

        if (message.getType().equals(ChatMessage.MessageType.ENTER)) {
            message.setMessage(message.getSender() + "님이 입장하였습니다.");
        }
        sendingOperations.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);

    }

    @MessageMapping("/alarm")
    public void alert(ChatMessage message) {
        if (message.getType().equals(ChatMessage.MessageType.ALERT)) {
            message.setMessage(message.getSender() + "님이 회원님에 카테고리에 입장하셧습니다");
        }
        sendingOperations.convertAndSend("/topic/invite/" + message.getNickname(), message);

    }


}
