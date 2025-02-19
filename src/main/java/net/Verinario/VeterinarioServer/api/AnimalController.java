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
import net.Verinario.VeterinarioServer.entity.AnimalEntity;
import net.Verinario.VeterinarioServer.service.AnimalService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    AnimalService oAnimalService;

    @GetMapping("/{id}")
    public ResponseEntity<AnimalEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<AnimalEntity>(oAnimalService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oAnimalService.count(), HttpStatus.OK);
    }
    
    @GetMapping()
    public ResponseEntity<Page<AnimalEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "tipoanimal", required = false) Long Tipoanimal) {
        return new ResponseEntity<Page<AnimalEntity>>(oAnimalService.getPage(oPageable, strFilter, Tipoanimal), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody AnimalEntity oNewAnimalEntity) {
        return new ResponseEntity<Long>(oAnimalService.create(oNewAnimalEntity), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody AnimalEntity oAnimalEntity) {
        return new ResponseEntity<Long>(oAnimalService.update(oAnimalEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oAnimalService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<AnimalEntity> generate() {
        return new ResponseEntity<AnimalEntity>(oAnimalService.generate(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        return new ResponseEntity<>(oAnimalService.generateSome(amount), HttpStatus.OK);
    }
     
}
