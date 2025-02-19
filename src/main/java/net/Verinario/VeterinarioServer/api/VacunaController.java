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
import net.Verinario.VeterinarioServer.entity.VacunaEntity;
import net.Verinario.VeterinarioServer.service.VacunaService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/vacuna")
public class VacunaController {

    @Autowired
    VacunaService oVacunaService;

    @GetMapping("/{id}")
    public ResponseEntity<VacunaEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<VacunaEntity>(oVacunaService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oVacunaService.count(), HttpStatus.OK);
    }
    
    @GetMapping()
    public ResponseEntity<Page<VacunaEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "tipoanimal", required = false) Long tipoanimal) {
        return new ResponseEntity<Page<VacunaEntity>>(oVacunaService.getPage(oPageable, strFilter, tipoanimal), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody VacunaEntity oNewVacunaEntity) {
        return new ResponseEntity<Long>(oVacunaService.create(oNewVacunaEntity), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody VacunaEntity oVacunaEntity) {
        return new ResponseEntity<Long>(oVacunaService.update(oVacunaEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oVacunaService.delete(id), HttpStatus.OK);
    }

    /*
    @PostMapping("/generate")
    public ResponseEntity<VacunaEntity> generate() {
        return new ResponseEntity<VacunaEntity>(oVacunaService.generate(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        return new ResponseEntity<>(oVacunaService.generateSome(amount), HttpStatus.OK);
    }
     */
     
}
