package com.example.mysse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final SseEmitters sseEmitters;

    private List<ChatMessage> chatMessages = new ArrayList<>();


    public record WriteMessageRequest(String authorName, String content) {
    }

    public record WriteMessageResponse(long id) {
    }

    @GetMapping("/room")
    public String showRoom() {
        return "chat/room";
    }


    @ResponseBody
    @PostMapping("/writeMessage")
    public RsData<WriteMessageResponse> writeMessage(@RequestBody WriteMessageRequest req) {
        ChatMessage message = new ChatMessage(req.authorName(), req.content());

        chatMessages.add(message);

        sseEmitters.noti("chat__messageAdded");

        return new RsData<>("S-1", "메시지가 작성되었습니다.", new WriteMessageResponse(message.getId()));
    }

    public record MessagesRequest(Long fromId) {
    }

    public record MessagesResponse(List<ChatMessage> messages, long count) {
    }

    @ResponseBody
    @GetMapping("/messages")
    public RsData<MessagesResponse> messages(MessagesRequest req) {
        List<ChatMessage> messages = chatMessages;

        if (req.fromId != null) {
            int index = IntStream.range(0, messages.size())
                    .filter(i -> chatMessages.get(i).getId() == req.fromId)
                    .findFirst()
                    .orElse(-1);

            if (index != -1) {
                messages = messages.subList(index + 1 , messages.size());
            }
        }

        log.info("req : {}", req);

        return new RsData<>("S-1", "성공.", new MessagesResponse(messages, messages.size()));
    }
}
