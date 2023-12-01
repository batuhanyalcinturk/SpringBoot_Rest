package com.graysan.springbootrest.controller;

import com.graysan.springbootrest.model.Ogrenci;
import com.graysan.springbootrest.repository.OgrenciRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/ogrenci")
public class OgrenciController {

    private final OgrenciRepository ogrenciRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OgrenciController(OgrenciRepository ogrenciRepository) {
        this.ogrenciRepository = ogrenciRepository;
    }

    @GetMapping(path = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ogrenci>> getAll() {
        // localhost:8080/ogrenci/getall
        try
        {
            List<Ogrenci> temp = ogrenciRepository.getAll();
            return ResponseEntity.ok(temp);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/getbyid/{id}")
    public ResponseEntity<Ogrenci> getById(@PathVariable(name = "id") long id)
    {
        // Log seviyeleri önemliden önemsize
        // FATAL - ERROR - WARN - INFO - DEBUG - TRACE

        // localhost:8080/ogrenci/getbyid/1
        try
        {
            Ogrenci ogr = ogrenciRepository.getByID(id);
            logger.info("ÖĞRENCİ get by id yapıldı");
            return ResponseEntity.ok(ogr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") long id)
    {
        // localhost:8080/ogrenci/delete/1
        try
        {
            boolean result = ogrenciRepository.deleteByID(id);
            if(result){
                return ResponseEntity.ok(id + "nolu kayıt başarı ile silindi.");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id + " nolu kayıt bulunamadı");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(id + " nolu kayıt silinemedi");
        }
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@RequestBody Ogrenci ogr){
        // localhost:8080/ogrenci/save
        try
        {
            boolean result = ogrenciRepository.save(ogr);
            if(result){
                return ResponseEntity.ok("Kayıt başarı ile kaydedildi.");
            }else {
                return ResponseEntity.internalServerError().body("Kayıt başarı ile kaydedilemedi server error");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Kayıt başarı ile kaydedilemedi");
        }
    }


}
