package net.Verinario.VeterinarioServer.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.Verinario.VeterinarioServer.entity.TipoAnimalEntity;
import net.Verinario.VeterinarioServer.service.TipoAnimalService;

import org.springdoc.api.annotations.ParameterObject;

@RestController
@RequestMapping("/tipoanimal")
public class TipoAnimalController {

    @Autowired
    TipoAnimalService oTipoAnimalService;

    @GetMapping("/{id}")
    public ResponseEntity<TipoAnimalEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<TipoAnimalEntity>(oTipoAnimalService.get(id), HttpStatus.OK);
    }
    /* 
    @GetMapping("/all")
    public ResponseEntity<List<TipoAnimalEntity>> all() {
        return new ResponseEntity<List<TipoAnimalEntity>>(oTipoAnimalService.all(), HttpStatus.OK);
    }
    */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oTipoAnimalService.count(), HttpStatus.OK);
    }
    
    @GetMapping("")
    public ResponseEntity<Page<TipoAnimalEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<Page<TipoAnimalEntity>>(oTipoAnimalService.getPage(oPageable, strFilter), HttpStatus.OK);
    }
    

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody TipoAnimalEntity oTipoAnimalEntity) {
        return new ResponseEntity<Long>(oTipoAnimalService.update(oTipoAnimalEntity), HttpStatus.OK);
    }
    /*
    @PostMapping("/generate")
    public ResponseEntity<Long> generate() {
        return new ResponseEntity<Long>(oTipoAnimalService.generate(), HttpStatus.OK);
    }
 */
}
