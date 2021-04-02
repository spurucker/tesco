package com.tesco.repository;

import com.tesco.domain.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UrlDao {
    private final JdbcTemplate jdbcTemplate;
    private final ShortenedUrlMapper shortenedUrlMapper;

    private SimpleJdbcInsert simpleJdbcInsert;

    @Resource(name = "urlByValueQuery")
    private final String urlByValueQuery;
    @Resource(name = "urlByIdQuery")
    private final String urlByIdQuery;
    @Resource(name = "urlUpdate")
    private final String urlUpdate;

    @PostConstruct
    public void init() {
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("url")
            .usingGeneratedKeyColumns("id");
    }

    public Long save(final Url url) throws DataAccessException {
        try {
            SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("value", url.getValue())
                .addValue("accessed", url.getStatistics().getAccessed())
                .addValue("shortened", url.getStatistics().getShortened());

            return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        } catch (DataAccessException dae) {
            throw dae;
        }
    }

    public void update(Url url){
        jdbcTemplate.update(urlUpdate, url.getStatistics().getShortened(), url.getStatistics().getAccessed(), url.getId());
    }

    public Optional<Url> getUrlByValue(String value){
        return jdbcTemplate.query(urlByValueQuery, shortenedUrlMapper, value)
            .stream()
            .findFirst();
    }

    public Optional<Url> getUrlById(Long id){
        return jdbcTemplate.query(urlByIdQuery, shortenedUrlMapper, id)
            .stream()
            .findFirst();
    }
}
