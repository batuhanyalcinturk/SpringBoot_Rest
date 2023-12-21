package com.graysan.springbootrest.repository;

import com.graysan.springbootrest.model.Ogrenci;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OgrenciRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OgrenciRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Ogrenci> getAll()
    {
        return namedParameterJdbcTemplate.query("select * from \"public\".\"OGRENCI\" order by \"ID\" asc", BeanPropertyRowMapper.newInstance(Ogrenci.class));
    }

    public Ogrenci getByID(long id)
    {
        Ogrenci ogrenci = null;
        String sql = "select * from \"public\".\"OGRENCI\" where \"ID\" = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID",id);
        ogrenci = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Ogrenci.class));
        return ogrenci;
    }

    public Ogrenci getAllLike(String name){
        String sql = "select * from \"public\".\"OGRENCI\" where \"NAME\" LIKE :NAME";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME","%" + name + "%");
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Ogrenci.class));
    }
    public boolean deleteByID(long id)
    {
        String sql = "delete from \"public\".\"OGRENCI\" where \"ID\" = :ID";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public boolean save(Ogrenci ogr)
    {
        String sql = "INSERT INTO \"public\".\"OGRENCI\"(\"NAME\", \"OGR_NUMBER\", \"YEAR\") VALUES (:NAME, :NUMBER, :YEAR)";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("NAME", ogr.getName());
        paramMap.put("NUMBER", ogr.getOgr_number());
        paramMap.put("YEAR", ogr.getYear());
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;

    }

}
