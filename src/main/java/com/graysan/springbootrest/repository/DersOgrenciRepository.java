package com.graysan.springbootrest.repository;

import com.graysan.springbootrest.model.DersOgrenci;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DersOgrenciRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DersOgrenciRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<DersOgrenci> getAll()
    {
        return namedParameterJdbcTemplate.query("select * from \"public\".\"DERS_OGRENCI\" order by \"ID\" asc", BeanPropertyRowMapper.newInstance(DersOgrenci.class));
    }

    public DersOgrenci getByID(long id)
    {
        DersOgrenci dersOgrenci = null;
        String sql = "select * from \"public\".\"DERS_OGRENCI\" where \"ID\" = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID",id);
        dersOgrenci = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(DersOgrenci.class));
        return dersOgrenci;
    }

    public boolean deleteByID(long id)
    {
        String sql = "delete from \"public\".\"DERS_OGRENCI\" where \"ID\" = :ID";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public boolean save(DersOgrenci dersOgrenci)
    {
        String sql = "INSERT INTO \"public\".\"DERS_OGRENCI\"(\"DERS_ID\", \"OGRENCI_ID\", \"DEVAMSIZLIK\", \"NOTE\") VALUES (:DERSID, :OGRID, :DEVAMSIZLIK , :NOTE)";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("DERSID", dersOgrenci.getDERS_ID());
        paramMap.put("OGRID", dersOgrenci.getOGR_ID());
        paramMap.put("DEVAMSIZLIK", dersOgrenci.getDevamsizlik());
        paramMap.put("NOTE", dersOgrenci.getNote());
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

}
