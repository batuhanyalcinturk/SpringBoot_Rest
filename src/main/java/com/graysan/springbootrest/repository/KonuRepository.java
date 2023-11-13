package com.graysan.springbootrest.repository;

import com.graysan.springbootrest.model.Konu;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class KonuRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public KonuRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Konu> getAll()
    {
        return namedParameterJdbcTemplate.query("select * from \"public\".\"KONU\" order by \"ID\" asc", BeanPropertyRowMapper.newInstance(Konu.class));
    }

    public Konu getByID(long id)
    {
        Konu konu = null;
        String sql = "select * from \"public\".\"KONU\" where \"ID\" = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID",id);
        konu = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Konu.class));
        return konu;
    }

    public Konu getAllLike(String name){
        String sql = "select * from \"public\".\"KONU\" where \"NAME\" LIKE :NAME";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME","%" + name + "%");
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Konu.class));
    }

    public boolean deleteByID(long id)
    {
        String sql = "delete from \"public\".\"KONU\" where \"ID\" = :ID";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public boolean save(Konu konu)
    {
        String sql = "INSERT INTO \"public\".\"KONU\"(\"NAME\") VALUES (:NAME)";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("NAME", konu.getNAME());
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }
}
