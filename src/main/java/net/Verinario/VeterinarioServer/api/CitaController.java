package net.Verinario.VeterinarioServer.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.Verinario.VeterinarioServer.entity.CitaEntity;
import net.Verinario.VeterinarioServer.service.CitaService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/cita")
public class CitaController {

    @Autowired
    CitaService oCitaService;

    @GetMapping("/{id}")
    public ResponseEntity<CitaEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<CitaEntity>(oCitaService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oCitaService.count(), HttpStatus.OK);
    }
    
    @GetMapping()
    public ResponseEntity<Page<CitaEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "servicio", required = false) Long Servicio,
            @RequestParam(name = "user", required = false) Long User,
            @RequestParam(name = "animal", required = false) Long Animal)
             {
        return new ResponseEntity<Page<CitaEntity>>(oCitaService.getPage(oPageable, strFilter, Animal ,Servicio ,User), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody CitaEntity oNewCitaEntity) {
        return new ResponseEntity<Long>(oCitaService.create(oNewCitaEntity), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody CitaEntity oCitaEntity) {
        return new ResponseEntity<Long>(oCitaService.update(oCitaEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oCitaService.delete(id), HttpStatus.OK);
    }

  /*   @PostMapping("/generate")
    public ResponseEntity<CitaEntity> generate() {
        return new ResponseEntity<CitaEntity>(oCitaService.generate(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        return new ResponseEntity<>(oCitaService.generateSome(amount), HttpStatus.OK);
    }
    */
     
}