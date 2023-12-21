package com.graysan.springbootrest.repository;

import com.graysan.springbootrest.model.Ders;
import com.graysan.springbootrest.model.DersDTO;
import com.graysan.springbootrest.model.Konu;
import com.graysan.springbootrest.model.Ogretmen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DersRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
    {
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

    @Transactional
    // içeride try catch olmayacak, bu sayede rollback yapıyor
    public boolean save(Ogretmen ogretmen, Konu konu)
    {
        String sql = "insert into \"public\".\"OGRETMEN\" (\"NAME\", \"IS_GICIK\") values (:NAME, :GICIK)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME", ogretmen.getNAME());
        paramMap.put("GICIK", ogretmen.isIS_GICIK());
        namedParameterJdbcTemplate.update(sql, paramMap);
        sql = "insert into \"public\".\"KONU\" (\"NAME\") values (:NAME)";
        paramMap = new HashMap<>();
        paramMap.put("NAME", konu.getNAME());
        namedParameterJdbcTemplate.update(sql, paramMap);
        sql = "insert into \"public\".\"DERS\" (\"OGRETMEN_ID\", \"KONU_ID\") values (:OGRETMENID, :KONUID)";
        paramMap = new HashMap<>();
        paramMap.put("OGRETMENID", 112233);
        paramMap.put("KONUID", 112233);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public boolean update(Ders ders)
    {
        String sql = "UPDATE public.\"DERS\" SET \"OGRETMEN_ID\" = :OGRETMEN_ID, \"KONU_ID\" = :KONU_ID WHERE \"ID\" = :ID;";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("OGRETMEN_ID", ders.getOGRETMEN_ID());
        paramMap.put("KONU_ID", ders.getKONU_ID());
        paramMap.put("ID", ders.getID());
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public List<DersDTO> getAllDTO()
    {
        RowMapper<DersDTO> mapper = new RowMapper<DersDTO>()
        {
            @Override
            public DersDTO mapRow(ResultSet rs, int rowNum) throws SQLException
            {
                DersDTO dersDTO = new DersDTO();
                Ogretmen ogretmen = new Ogretmen();
                Konu konu = new Konu();
                ogretmen.setID(rs.getLong("OGRETMEN_ID"));
                ogretmen.setNAME(rs.getString("OGR_NAME"));
                ogretmen.setIS_GICIK(rs.getBoolean("IS_GICIK"));
                konu.setID(rs.getLong("KONU_ID"));
                konu.setNAME(rs.getString("KONU_NAME"));
                dersDTO.setId(rs.getLong("ID"));
                dersDTO.setOgretmen(ogretmen);
                dersDTO.setKonu(konu);
                return dersDTO;
            }
        };
        return jdbcTemplate.query("select \"DERS\".\"ID\", \"OGRETMEN\".\"ID\" AS \"OGRETMEN_ID\", \"OGRETMEN\".\"NAME\" AS \"OGR_NAME\", \"OGRETMEN\".\"IS_GICIK\", \"KONU\".\"ID\" AS \"KONU_ID\", \"KONU\".\"NAME\" AS \"KONU_NAME\" from \"public\".\"DERS\" inner join \"public\".\"OGRETMEN\" ON \"OGRETMEN\".\"ID\" = \"DERS\".\"OGRETMEN_ID\" inner join \"public\".\"KONU\" ON \"KONU\".\"ID\" = \"DERS\".\"KONU_ID\"", mapper);
    }

    public DersDTO getByIdDTO(long id)
    {
        RowMapper<DersDTO> mapper = new RowMapper<DersDTO>()
        {
            @Override
            public DersDTO mapRow(ResultSet rs, int rowNum) throws SQLException
            {
                DersDTO dersDTO = new DersDTO();
                Ogretmen ogretmen = new Ogretmen();
                Konu konu = new Konu();
                ogretmen.setID(rs.getLong("OGRETMEN_ID"));
                ogretmen.setNAME(rs.getString("OGR_NAME"));
                ogretmen.setIS_GICIK(rs.getBoolean("IS_GICIK"));
                konu.setID(rs.getLong("KONU_ID"));
                konu.setNAME(rs.getString("KONU_NAME"));
                dersDTO.setId(rs.getLong("ID"));
                dersDTO.setOgretmen(ogretmen);
                dersDTO.setKonu(konu);
                return dersDTO;
            }
        };
        Map<String, Object> params = new HashMap<>();
        params.put("ID", id);
        return namedParameterJdbcTemplate.queryForObject("select \"DERS\".\"ID\", \"OGRETMEN\".\"ID\" AS \"OGRETMEN_ID\", \"OGRETMEN\".\"NAME\" AS \"OGR_NAME\", \"OGRETMEN\".\"IS_GICIK\", \"KONU\".\"ID\" AS \"KONU_ID\", \"KONU\".\"NAME\" AS \"KONU_NAME\" from \"public\".\"DERS\" inner join \"public\".\"OGRETMEN\" ON \"OGRETMEN\".\"ID\" = \"DERS\".\"OGRETMEN_ID\" inner join \"public\".\"KONU\" ON \"KONU\".\"ID\" = \"DERS\".\"KONU_ID\" where \"DERS\".\"ID\" = :ID", params, mapper);
    }
}
