package com.graysan.springbootrest.repository;

import com.graysan.springbootrest.model.Ders;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DersRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DersRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Ders> getAll()
    {
        return namedParameterJdbcTemplate.query("select * from \"public\".\"DERS\" order by \"ID\" asc", BeanPropertyRowMapper.newInstance(Ders.class));
    }

    public Ders getByID(long id)
    {
        Ders ders = null;
        String sql = "select * from \"public\".\"DERS\" where \"ID\" = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID",id);
        ders = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Ders.class));
        return ders;
    }

    public Ders getAllLike(String name){
        String sql = "select * from \"public\".\"DERS\" where \"NAME\" LIKE :NAME";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME","%" + name + "%");
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Ders.class));
    }

    public boolean deleteByID(long id)
    {
        String sql = "delete from \"public\".\"DERS\" where \"ID\" = :ID";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public boolean save(Ders ders)
    {
        String sql = "INSERT INTO public.\"DERS\"(\"OGRETMEN_ID\", \"KONU_ID\")VALUES (:OGRTID, KONUID)";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("OGRTID", ders.getOGRETMEN_ID());
        paramMap.put("KONUID", ders.getKONU_ID());
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }
}
