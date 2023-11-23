package com.graysan.springbootrest.controller;

import com.graysan.springbootrest.model.Ogretmen;
import com.graysan.springbootrest.repository.OgretmenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/ogretmen")
@RestControllerAdvice(basePackageClasses = OgretmenRepository.class)
public class OgretmenController {
    private final OgretmenRepository ogretmenRepository;

    public OgretmenController(OgretmenRepository ogretmenRepository) {
        this.ogretmenRepository = ogretmenRepository;
    }

    //	@ExceptionHandler(value = BadSqlGrammarException.class)
//	public String badSqlGrammerExceptionHandler(BadSqlGrammarException e)
//	{
//		System.err.println("Bad sql yakalandı -> " + e.getMessage());
//		return "bad sql hatası";
//	}

    @ExceptionHandler(value = ArithmeticException.class)
    // bu mantıksız yöntem
//	@ResponseStatus(code = HttpStatus.IM_USED, reason = "invalid jdbc usage")
//	public String aritmetichandler(ArithmeticException e)
    public ResponseEntity<String> aritmetichandler(ArithmeticException e)
    {
        // server.error.include-message = always
        System.err.println(e.getMessage());
        // isterseniz responseentity döndürebilirsiniz
        return ResponseEntity.status(HttpStatus.IM_USED).body("yazılımcı kodu yanlış yazmış");
        // veya aşağıdaki gibi döndürülebilir
//		return "yazılımcı kodu yanlış yazmış";
    }

    @GetMapping(path = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ogretmen>> getAll() {
        // localhost:8080/ogretmen/getall
        try
        {
            List<Ogretmen> temp = ogretmenRepository.getAll();
            return ResponseEntity.ok(temp);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/getbyid/{id}")
    public ResponseEntity<Ogretmen> getById(@PathVariable(name = "id") long id)
    {
        // localhost:8080/ogretmen/getbyid/1
        try
        {
            Ogretmen ogr = ogretmenRepository.getByID(id);
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
        // localhost:8080/ogretmen/delete/1
        try
        {
            boolean result = ogretmenRepository.deleteByID(id);
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
    public ResponseEntity<String> save(@RequestBody Ogretmen ogr){
        // localhost:8080/ogretmen/save
        try
        {
            boolean result = ogretmenRepository.save(ogr);
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
