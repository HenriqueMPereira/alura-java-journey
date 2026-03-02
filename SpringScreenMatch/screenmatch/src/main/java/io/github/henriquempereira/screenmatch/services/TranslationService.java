package io.github.henriquempereira.screenmatch.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    private final ChatClient chatClient;

    // O Spring Boot injeta o Builder automaticamente para você
    public TranslationService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String obterTraducao(String texto) {
        return chatClient.prompt()
                .user("Traduza para o português o seguinte texto: " + texto)
                .call()
                .content();
    }
}