package ru.liga.repository;

import ru.liga.model.Anketa;

import java.util.List;

public interface AnketaRepository {
    //List<Anketa> findPeople(String sex);

    int countAnkets(String chat_id);
    List<Anketa> allAnkets();
    void saveAnket(String name, String chat_id);
    void updateAnket(String name, String description, String sex, String chat_id);
    int countAnketsWithSex(String chat_id);
}
