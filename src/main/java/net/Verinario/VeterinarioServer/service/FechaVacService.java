package net.Verinario.VeterinarioServer.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.coyote.http11.InputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.Verinario.VeterinarioServer.entity.FechaVacEntity;
import net.Verinario.VeterinarioServer.exception.CannotPerformOperationException;
import net.Verinario.VeterinarioServer.exception.ResourceNotFoundException;
import net.Verinario.VeterinarioServer.exception.ResourceNotModifiedException;
import net.Verinario.VeterinarioServer.helper.RandomHelper;
import net.Verinario.VeterinarioServer.helper.ValidationHelper;
import net.Verinario.VeterinarioServer.repository.FechaVacRepository;
@Service
public class FechaVacService {

    @Autowired
    AnimalService oAnimalService;
    
    @Autowired
    VacunaService oVacunaService;

    private final FechaVacRepository oFechaVacRepository;
    private final AuthService oAuthService;
            
    private final String[] DATES = {"1995-07-22 22:11:12", "1970-07-22 22:11:12", "1980-07-22 22:11:12", "2003-07-22 22:11:12", "2015-07-22 22:11:12", "2000-07-22 22:11:12", "2008-07-22 22:11:12"};

    @Autowired
    public FechaVacService(FechaVacRepository oFechaVacRepository, AuthService oAuthService) {
        this.oFechaVacRepository = oFechaVacRepository;
        this.oAuthService = oAuthService;
    }

    public void validate(Long id) {
        if (!oFechaVacRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public FechaVacEntity get(Long id) {
        return oFechaVacRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FechaVac with id: " + id + " not found"));
    }

    public Long update(FechaVacEntity oFechaVacEntity) {
        validate(oFechaVacEntity.getId());
        oAuthService.OnlyAdmins();
        return oFechaVacRepository.save(oFechaVacEntity).getId();
    }

    public Page<FechaVacEntity> getPage(Long id_Vacuna, Long id_Animal, int page, int size) {
        oAuthService.OnlyAdmins();
        Pageable oPageable = PageRequest.of(page, size);
        if (id_Animal == null && id_Vacuna == null) {
            return oFechaVacRepository.findAll(oPageable);
        } else if (id_Animal == null) {
            return oFechaVacRepository.findByVacunaId(id_Vacuna, oPageable);
        } else if (id_Vacuna == null) {
            return oFechaVacRepository.findByAnimalId(id_Animal, oPageable);
        } else {
            return oFechaVacRepository.findByAnimalIdAndVacunaId(id_Animal, id_Vacuna, oPageable);
        }
    }
        

    public Long delete(Long id) {
        //oAuthService.OnlyAdmins();
        validate(id);
        oFechaVacRepository.deleteById(id);
        return id;
    }

   


    public Long create(FechaVacEntity oNewFechaVacEntity) {
        //oAuthService.OnlyAdmins();
     
        oNewFechaVacEntity.setId(0L);
        return oFechaVacRepository.save(oNewFechaVacEntity).getId();
    }
    
    public Long count(){
        //oAuthService.OnlyAdmins();
        return oFechaVacRepository.count();

    }

    public FechaVacEntity getOneRandom() {
        if (count() > 0) {
            FechaVacEntity oFechaVacEntity = null;
            int iPosicion = RandomHelper.getRandomInt(0, (int) oFechaVacRepository.count() - 1);
            Pageable oPageable = PageRequest.of(iPosicion, 1);
            Page<FechaVacEntity> FechaVacPage = oFechaVacRepository.findAll(oPageable);
            List<FechaVacEntity> FechaVacList = FechaVacPage.getContent();
            oFechaVacEntity = oFechaVacRepository.getById(FechaVacList.get(0).getId());
            return oFechaVacEntity;
        } else {
            throw new CannotPerformOperationException("ho hay usuarios en la base de datos");
        }
    }

    private FechaVacEntity generateFechaVac() {
        //oAuthService.OnlyAdmins();
        return oFechaVacRepository.save(generateRandomFechaVac());
    }
    
    private LocalDateTime generateDates() {
        String fechaRandom = DATES[RandomHelper.getRandomInt(0, DATES.length-1)];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
        return LocalDateTime.parse(fechaRandom, formatter);
    }    

    private FechaVacEntity generateRandomFechaVac() {
        FechaVacEntity oFechaVacEntity = new FechaVacEntity();
        oFechaVacEntity.setAnimal(oAnimalService.getOneRandom()); 
        oFechaVacEntity.setVacuna(oVacunaService.getOneRandom());
        return oFechaVacEntity;
    }




    public Long generateSome(Integer amount) {
        //oAuthService.OnlyAdmins();
        List<FechaVacEntity> userList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            FechaVacEntity oFechaVacEntity = generateFechaVac();
            oFechaVacRepository.save(oFechaVacEntity);
            userList.add(oFechaVacEntity);
        }
        return oFechaVacRepository.count();
    }

}
