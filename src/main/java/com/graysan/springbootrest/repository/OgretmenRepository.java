package com.graysan.springbootrest.repository;

import com.graysan.springbootrest.model.Ogretmen;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OgretmenRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OgretmenRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Ogretmen> getAll()
    {
        return namedParameterJdbcTemplate.query("select * from \"public\".\"OGRETMEN\" order by \"ID\" asc", BeanPropertyRowMapper.newInstance(Ogretmen.class));
    }

    public Ogretmen getByID(long id)
    {
        Ogretmen ogretmen = null;
        String sql = "select * from \"public\".\"OGRETMEN\" where \"ID\" = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID",id);
        ogretmen = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Ogretmen.class));
        return ogretmen;
    }

    public Ogretmen getAllLike(String name){
        String sql = "select * from \"public\".\"OGRETMEN\" where \"NAME\" LIKE :NAME";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME","%" + name + "%");
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Ogretmen.class));
    }

    public boolean deleteByID(long id)
    {
        String sql = "delete from \"public\".\"OGRETMEN\" where \"ID\" = :ID";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
     }

    public boolean save(Ogretmen ogr)
    {
        String sql = "INSERT INTO \"public\".\"OGRETMEN\"(\"NAME\", \"IS_GICIK\") VALUES (:NAME, :GICIK)";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("NAME", ogr.getNAME());
        paramMap.put("GICIK", ogr.isIS_GICIK());
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;

    }





}
