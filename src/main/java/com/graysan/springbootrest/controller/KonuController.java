package com.graysan.springbootrest.controller;

import com.graysan.springbootrest.model.Konu;
import com.graysan.springbootrest.repository.KonuRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/konu")
public class KonuController {

    private final KonuRepository konuRepository;


    public KonuController(KonuRepository konuRepository) {
        this.konuRepository = konuRepository;
    }

    @GetMapping(path = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Konu>> getAll() {
        // localhost:8080/konu/getall
        try
        {
            List<Konu> temp = konuRepository.getAll();
            return ResponseEntity.ok(temp);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/getbyid/{id}")
    public ResponseEntity<Konu> getById(@PathVariable(name = "id") long id)
    {
        // localhost:8080/konu/getbyid/1
        try
        {
            Konu konu = konuRepository.getByID(id);
            return ResponseEntity.ok(konu);
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
        // localhost:8080/konu/delete/1
        try
        {
            boolean result = konuRepository.deleteByID(id);
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
    public ResponseEntity<String> save(@RequestBody Konu konu){
        // localhost:8080/konu/save
        try
        {
            boolean result = konuRepository.save(konu);
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
