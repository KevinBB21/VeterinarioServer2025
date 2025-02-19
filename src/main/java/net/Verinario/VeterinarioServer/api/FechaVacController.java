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
import net.Verinario.VeterinarioServer.entity.FechaVacEntity;
import net.Verinario.VeterinarioServer.service.FechaVacService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/fechavac")
public class FechaVacController {

    @Autowired
    FechaVacService oFechaVacService;

    @GetMapping("/{id}")
    public ResponseEntity<FechaVacEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<FechaVacEntity>(oFechaVacService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oFechaVacService.count(), HttpStatus.OK);
    }
    
    @GetMapping("")
	public ResponseEntity<Page<FechaVacEntity>> getPage(
        	@RequestParam(value = "tipovacuna", required = false) Long id_vacuna,
            @RequestParam(value = "animal", required = false) Long id_animal,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
            ) {
    	return new ResponseEntity<Page<FechaVacEntity>>(oFechaVacService.getPage(id_vacuna,id_animal,page, size), HttpStatus.OK);
	}
    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody FechaVacEntity oNewFechaVacEntity) {
        return new ResponseEntity<Long>(oFechaVacService.create(oNewFechaVacEntity), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody FechaVacEntity oFechaVacEntity) {
        return new ResponseEntity<Long>(oFechaVacService.update(oFechaVacEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oFechaVacService.delete(id), HttpStatus.OK);
    }

  /*   @PostMapping("/generate")
    public ResponseEntity<FechaVacEntity> generate() {
        return new ResponseEntity<FechaVacEntity>(oFechaVacService.generate(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        return new ResponseEntity<>(oFechaVacService.generateSome(amount), HttpStatus.OK);
    }
    */
     
}