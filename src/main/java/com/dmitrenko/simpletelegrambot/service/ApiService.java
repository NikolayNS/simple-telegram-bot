package com.dmitrenko.simpletelegrambot.service;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ApiService {

    BotApiMethod<?> handle(Update update);
}
