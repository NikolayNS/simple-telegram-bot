package com.dmitrenko.simpletelegrambot.exception;

public class TelegramBotApiExeption extends RuntimeException {

    public TelegramBotApiExeption() {
        super();
    }

    public TelegramBotApiExeption(String message) {
        super(message);
    }
}
