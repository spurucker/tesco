package com.tesco.repository;

import com.tesco.domain.Url;
import com.tesco.domain.Statistics;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ShortenedUrlMapper implements RowMapper<Url> {
    @Override
    public Url mapRow(ResultSet row, int i) throws SQLException {
        return Url.builder()
            .id(row.getLong("id"))
            .value(row.getString("value"))
            .statistics(new Statistics(row.getLong("shortened"), row.getLong("accessed")))
            .build();
    }
}
