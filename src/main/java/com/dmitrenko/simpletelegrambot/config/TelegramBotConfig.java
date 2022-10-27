package com.dmitrenko.simpletelegrambot.config;

import com.dmitrenko.simpletelegrambot.service.ApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
public class TelegramBotConfig {

    @Value("${telegram.bot.path}")
    private String botPath;
    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botPath).build();
    }

    @Bean
    public TelegramBotApi springWebhookBot(SetWebhook setWebhook, ApiService apiService) {
        var botApi = new TelegramBotApi(setWebhook, apiService);
        botApi.setBotPath(botPath);
        botApi.setBotUsername(botUsername);
        botApi.setBotToken(botToken);
        return botApi;
    }
}
