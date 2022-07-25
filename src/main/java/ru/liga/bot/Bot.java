package ru.liga.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.model.Anketa;
import ru.liga.repository.AnketaRepository;
import ru.liga.repository.impl.AnketaImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bot extends TelegramLongPollingBot {
    @Autowired
    AnketaRepository anketaRepository;

    SendMessage sendMessage = new SendMessage();
    AnketaImpl anketaImpl = new AnketaImpl();
    List<Anketa> anketas = anketaImpl.allAnkets();
    String listString = new String();
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            switch (update.getMessage().getText()) {
                case "/start":
                    //здесь добавить проверку на созданность анкеты
                    sendMsg(message, "Давайте заполним анкету! Как Вас величать?");
                    System.out.println(message.getText());
                    break;
                case "Меню":
                    KeyBoard.getMenuKeyBoard(sendMessage);
                    sendMsg(message, "Переходим в основное меню. Выберите команду для дальнейших действий");
                    System.out.println(message.getText());
                    break;
                case "Поиск":
                    KeyBoard.getQueryKeyBoard(sendMessage);
                    sendMsg(message, "Кого Вы желаете искать?");
                    System.out.println(message.getText());
                    break;
                case "Анкета":
                    KeyBoard.getAnketaKeyBoard(sendMessage);
                    sendMsg(message, "Отлично! Что вы хотите изменить?");
                    System.out.println(message.getText());
                    break;
                case "Судари":
                    sendMsg(message, "Отлично! Вот анкеты наших Сударей:");
                    listString = anketas.stream()
                            .filter(anketas1->(anketas1.getSex()).equals("Сударь"))
                            .map(Object::toString)
                            .collect(Collectors.joining("\n"));
                    sendMsg(message, listString);
                    System.out.println(message.getText());
                    break;
                case  "Сударыни":
                    sendMsg(message, "Отлично! Вот анкеты наших Сударынь:");
                    listString = anketas.stream()
                            .filter(anketas1->(anketas1.getSex()).equals("Сударыня"))
                            .map(Object::toString)
                            .collect(Collectors.joining("\n"));
                    sendMsg(message, listString);
                    System.out.println(message.getText());
                    break;
                case  "Все":
                    sendMsg(message, "Отлично! Вот все наши анкеты:");
                    listString = anketas.stream().map(Object::toString)
                        .collect(Collectors.joining("\n"));
                    sendMsg(message, listString);
                    anketas.forEach(System.out::println);
                    System.out.println(message.getText());
                    break;
                case  "Имя":
                    sendMsg(message, "Ведите новое имя:");

                    anketas.forEach(System.out::println);
                    System.out.println(message.getText());
                    break;
                default:
                    if(anketaImpl.countAnkets(message.getChatId().toString())==1){
                        if(anketaImpl.countAnketsWithSex(message.getChatId().toString())==0){
                            anketaImpl.updateAnket(null,message.getText(),null,message.getChatId().toString());
                            sendMsg(message, "Отлично! Вы Сударь или Сударыня?");
                            KeyBoard.getSexKeyBoard(sendMessage);
                        }else {
                            anketaImpl.updateAnket(null,null,message.getText(),message.getChatId().toString());
                            sendMsg(message, "Вы заполнили анкету! Кого вы ищите?");
                            KeyBoard.getQueryKeyBoard(sendMessage);
                        }
                    }else{
                        sendMsg(message, "Отлично! Приятно познакомиться "+ message.getText() +"! Расскажите немного о себе");
                        anketaImpl.saveAnket(message.getText(),message.getChatId().toString());
                    }
                    System.out.println(message.getText());
                    break;
            }
        }

    }
    public void sendMsg (Message message, String text) {
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "dz3_oldtinder_bot";
}

    @Override
    public String getBotToken() {
        return "5563837825:AAFe4EqDl-fdniK5YoBHjRmMkyOH4C1gtPM";
    }
}