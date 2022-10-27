package com.dmitrenko.simpletelegrambot.config;

import com.dmitrenko.simpletelegrambot.exception.TelegramBotApiExeption;
import com.dmitrenko.simpletelegrambot.service.ApiService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Slf4j
@Getter
@Setter
public class TelegramBotApi extends SpringWebhookBot {
    private String botPath;
    private String botUsername;
    private String botToken;

    private ApiService apiService;

    public TelegramBotApi(SetWebhook setWebhook, ApiService apiService) {
        super(setWebhook);
        this.apiService = apiService;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return apiService.handle(update);
        } catch (Exception e) {
            log.error("Fail handle for update: {}", update.toString());
            throw new TelegramBotApiExeption(e.getMessage());
        }
    }
}
