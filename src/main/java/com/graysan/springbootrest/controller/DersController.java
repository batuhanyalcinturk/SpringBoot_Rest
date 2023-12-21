package com.graysan.springbootrest.controller;

import com.graysan.springbootrest.model.Ders;
import com.graysan.springbootrest.model.DersDTO;
import com.graysan.springbootrest.repository.DersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/ders")
public class DersController {

    private final DersRepository dersRepository;

    public DersController(DersRepository dersRepository) {
        this.dersRepository = dersRepository;
    }

    @GetMapping(path = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ders>> getAll() {
        // localhost:8080/ders/getall
        try
        {
            List<Ders> temp = dersRepository.getAll();
            return ResponseEntity.ok(temp);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "getalldto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DersDTO>> getalldto()
    {
        // localhost:8080/ders/getalldto
        try
        {
            return ResponseEntity.ok(dersRepository.getAllDTO());
        }
        catch (Exception e)
        {
            // daha sonra değişecek exception handling olacak
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/getbyid/{id}")
    public ResponseEntity<Ders> getById(@PathVariable(name = "id") long id)
    {
        // localhost:8080/ders/getbyid/1
        try
        {
            Ders ders = dersRepository.getByID(id);
            return ResponseEntity.ok(ders);
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
            boolean result = dersRepository.deleteByID(id);
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
    public ResponseEntity<String> save(@RequestBody Ders ders){
        // localhost:8080/konu/save
        try
        {
            boolean result = dersRepository.save(ders);
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

    @PostMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Ders ders)
    {
        // localhost:8080/ders/update
        try
        {
            boolean result = dersRepository.update(ders);
            if (result)
            {
                return ResponseEntity.ok("Kayıt başarı ile güncellendi");
            }
            else
            {
                return ResponseEntity.internalServerError().body("Kayıt başarı ile güncellendi");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Kayıt başarı ile güncellendi");
        }
    }
}
