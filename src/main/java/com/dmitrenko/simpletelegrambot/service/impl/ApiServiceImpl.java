package com.dmitrenko.simpletelegrambot.service.impl;

import com.dmitrenko.simpletelegrambot.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    @Override
    public BotApiMethod<?> handle(Update update) {
        if (update.getMessage() == null) log.info("Get update is null : {}\", update", update);

        return update.hasCallbackQuery()
            ? handleCallbackQuery(update.getCallbackQuery())
            : handleMessage(update.getMessage());
    }

    private BotApiMethod<?> handleCallbackQuery(CallbackQuery query) {
        return new SendMessage(String.valueOf(query.getMessage().getChatId()), "inlineKeyboardButton");
    }

    private BotApiMethod<?> handleMessage(Message message) {
        return switch (message.getText()) {
            case "/start" -> getSelectAmountKeyboard(message);
            case "/menu" -> new SendMessage(String.valueOf(message.getChatId()), "Menu");
            default -> throw new IllegalArgumentException();
        };
    }

    private SendMessage getSelectAmountKeyboard(Message message) {
        var oneHundredKeyboardButton = new InlineKeyboardButton();
        oneHundredKeyboardButton.setText("100");
        oneHundredKeyboardButton.setCallbackData("100");

        var fiveHundredKeyboardButton = new InlineKeyboardButton();
        fiveHundredKeyboardButton.setText("200");
        fiveHundredKeyboardButton.setCallbackData("200");

        var oneThousandKeyboardButton = new InlineKeyboardButton();
        oneThousandKeyboardButton.setText("300");
        oneThousandKeyboardButton.setCallbackData("300");

        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(List.of(List.of(oneHundredKeyboardButton, fiveHundredKeyboardButton, oneThousandKeyboardButton)));

        var callbackMessage = new SendMessage(String.valueOf(message.getChatId()), "Let's rock");
        callbackMessage.setReplyMarkup(inlineKeyboardMarkup);
        return callbackMessage;
    }
}
