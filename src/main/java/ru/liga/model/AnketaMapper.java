package ru.liga.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnketaMapper implements RowMapper<Anketa> {
    public Anketa mapRow(ResultSet rs, int rowNum) throws SQLException {
        Anketa anketa = new Anketa();
        anketa.setId(rs.getLong("id"));
        anketa.setPerson_name(rs.getString("person_name"));
        anketa.setSex(rs.getString("sex"));
        anketa.setDescription(rs.getString("description"));
        anketa.setChat_id(rs.getString("chat_id"));
        return anketa;
    }
}
