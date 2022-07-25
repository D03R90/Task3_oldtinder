package ru.liga.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import ru.liga.model.Anketa;
import ru.liga.model.AnketaMapper;
import ru.liga.repository.AnketaRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnketaImpl implements AnketaRepository {

    @Override
    public int countAnkets(String chat_id) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new org.postgresql.Driver());
        dataSource.setUrl("jdbc:postgresql://localhost:5432/league_db");
        dataSource.setUsername("admin");
        dataSource.setPassword("qwerty$4");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int i = jdbcTemplate.queryForObject("SELECT count(*) FROM old_tinder WHERE chat_id=?",new Object[]{chat_id}, Integer.class);
        return i;
    }
    @Override
    public int countAnketsWithSex(String chat_id) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new org.postgresql.Driver());
        dataSource.setUrl("jdbc:postgresql://localhost:5432/league_db");
        dataSource.setUsername("admin");
        dataSource.setPassword("qwerty$4");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int i = jdbcTemplate.queryForObject("SELECT count(*) FROM old_tinder WHERE chat_id=? AND sex is not null",new Object[]{chat_id}, Integer.class);
        return i;
    }
    @Override
    public List<Anketa> allAnkets() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new org.postgresql.Driver());
        dataSource.setUrl("jdbc:postgresql://localhost:5432/league_db");
        dataSource.setUsername("admin");
        dataSource.setPassword("qwerty$4");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Anketa> allAnkets = jdbcTemplate.query("SELECT * FROM old_tinder", new AnketaMapper());
        return allAnkets;
    }
    public void saveAnket(String name, String chat_id) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new org.postgresql.Driver());
        dataSource.setUrl("jdbc:postgresql://localhost:5432/league_db");
        dataSource.setUsername("admin");
        dataSource.setPassword("qwerty$4");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("INSERT INTO old_tinder (person_name, chat_id) VALUES (?, ?)",name, chat_id);
    }
    public void updateAnket(String name, String description, String sex, String chat_id) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new org.postgresql.Driver());
        dataSource.setUrl("jdbc:postgresql://localhost:5432/league_db");
        dataSource.setUsername("admin");
        dataSource.setPassword("qwerty$4");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        if(name.length()>0){
            jdbcTemplate.update("UPDATE old_tinder SET person_name = ? WHERE chat_id = ?",name,chat_id);
        }else if(description.length()>0){
            jdbcTemplate.update("UPDATE old_tinder SET description=? WHERE chat_id = ?",description,chat_id);
        }else {
            jdbcTemplate.update("UPDATE old_tinder SET sex=? WHERE chat_id = ?",sex,chat_id);
        }
    }
}
