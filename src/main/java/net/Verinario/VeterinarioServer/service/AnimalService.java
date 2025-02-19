package net.Verinario.VeterinarioServer.service;


import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import net.Verinario.VeterinarioServer.exception.ResourceNotFoundException;
import net.Verinario.VeterinarioServer.exception.ResourceNotModifiedException;
import net.Verinario.VeterinarioServer.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.Verinario.VeterinarioServer.repository.TipoAnimalRepository;
import net.Verinario.VeterinarioServer.entity.AnimalEntity;
import net.Verinario.VeterinarioServer.exception.CannotPerformOperationException;
import net.Verinario.VeterinarioServer.helper.RandomHelper;
import net.Verinario.VeterinarioServer.helper.ValidationHelper;
import net.Verinario.VeterinarioServer.repository.AnimalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class AnimalService {

    @Autowired
    TipoAnimalService oTipoAnimalService;
    @Autowired
    TipoAnimalRepository oTipoAnimalRepository;

    @Autowired
    AnimalRepository oAnimalRepository;

    @Autowired
    AuthService oAuthService;


    private final String[] NAMES = {"", "Mico", "Chispa", "Rayo", "Pluton", "Chico", "Luna", "Lola", "Coco",
        "Linda", "Noa", "Nina", "Toby", "Rocky", "Thor", "Simba", "Bruno", "Nico", "Bimba", "Max"};

    private final String[] COLORS = {"Negro", "Marrón", "Verde", "Gris", "Rosa", "Purpura", "Blanco",
        "Rojo", "Amarillo"};

    private final String[] RAZAS = {"RogWailer", "Pitbull", "Pug", "Pug", "BUlldog Frances", "Egipcio", "Orejon",
    "Peludi", "Enano"};

    

    public void validate(Long id) {
        if (!oAnimalRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

  
 
    public AnimalEntity get(Long id) {
        try {
            return oAnimalRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        
        return oAnimalRepository.count();
    }


    /* 
    public Page<AnimalEntity> getPage(Long id_TipoAnimal, int page, int size , String strFilter) {
        //
        Pageable oPageable = PageRequest.of(page, size);
        if (id_TipoAnimal == null && strFilter == null) {
            return oAnimalRepository.findAll(oPageable);
        }else// else if (strFilter == null) {
            return oAnimalRepository.findByTipoAnimalId(id_TipoAnimal,  oPageable);
        } //else con solo filtro
    //}
    */

    public Page<AnimalEntity> getPage(Pageable oPageable, String strFilter, Long id_TipoAnimal) {
        
        Page<AnimalEntity> oPage = null;
        if (id_TipoAnimal != null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oAnimalRepository.findByTipoanimalId(id_TipoAnimal, oPageable);
            } else {
                oPage = oAnimalRepository
                        .findByTipoanimalIdAndNombreIgnoreCaseContainingOrColorIgnoreCaseContainingOrRazaIgnoreCaseContaining( id_TipoAnimal, strFilter, strFilter, strFilter, oPageable);
            }
        } else {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oAnimalRepository.findAll(oPageable);
            } else {
                oPage = oAnimalRepository
                        .findByNombreIgnoreCaseContainingOrColorIgnoreCaseContainingOrRazaIgnoreCaseContaining(strFilter, strFilter, strFilter, oPageable);
            }
        }
        return oPage;
    }
    
    public Long create(AnimalEntity oNewAnimalEntity) {
       
       
        oNewAnimalEntity.setId(0L);
         //oNewAnimalEntity.setToken(RandomHelper.getToken(100));
        return oAnimalRepository.save(oNewAnimalEntity).getId();

    }

    public Long update(AnimalEntity oAnimalEntity) {
        
        validate(oAnimalEntity.getId());
        oAnimalRepository.save(oAnimalEntity);
        return oAnimalEntity.getId();
    }
    
  

    public Long delete(Long id) {
      
        if (oAnimalRepository.existsById(id)) {
            oAnimalRepository.deleteById(id);
            if (oAnimalRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }
  
    public AnimalEntity generate() {
      
        return oAnimalRepository.save(generateRandomAnimal());
    }

    public Long generateSome(Integer amount) {
      
        List<AnimalEntity> AnimalList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            AnimalEntity oAnimalEntity = generateRandomAnimal();
            oAnimalRepository.save(oAnimalEntity);
            AnimalList.add(oAnimalEntity);
        }
        return oAnimalRepository.count();
    }
    

    public AnimalEntity getOneRandom() {
        if (count() > 0) {
            AnimalEntity oAnimalEntity = null;
            int iPosicion = RandomHelper.getRandomInt(0, (int) oAnimalRepository.count() - 1);
            Pageable oPageable = PageRequest.of(iPosicion, 1);
            Page<AnimalEntity> AnimalPage = oAnimalRepository.findAll(oPageable);
            List<AnimalEntity> AnimalList = AnimalPage.getContent();
            oAnimalEntity = oAnimalRepository.getById(AnimalList.get(0).getId());
            return oAnimalEntity;
        } else {
            throw new CannotPerformOperationException("no hay Animals en la base de datos");
        }
    }
    
    private AnimalEntity generateRandomAnimal() {
        AnimalEntity oAnimalEntity = new AnimalEntity();
        oAnimalEntity.setNombre(generateNombre());
        oAnimalEntity.setColor(generateColor());
        oAnimalEntity.setRaza(generateRaza());
        
        oAnimalEntity.setVacunado(RandomHelper.getRandomInt2(0, 1));
        oAnimalEntity.setPeso(RandomHelper.getRadomDouble(5, 25));
        int totalTipoAnimals = (int) oTipoAnimalRepository.count();
        int randomTipoAnimalId = RandomHelper.getRandomInt(1, totalTipoAnimals);
        oTipoAnimalRepository.findById((long) randomTipoAnimalId)
        .ifPresent(oAnimalEntity::setTipoanimal);


        return oAnimalEntity;
    }
    

    private String generateNombre() {
        return NAMES[RandomHelper.getRandomInt(0, NAMES.length - 1)].toLowerCase();
    }

    private String generateColor() {
        return COLORS[RandomHelper.getRandomInt(0, COLORS.length - 1)].toLowerCase();
    }

    private String generateRaza() {
        return RAZAS[RandomHelper.getRandomInt(0, RAZAS.length - 1)].toLowerCase();
    }

    private String getFromList(List<String> list) {
        int randomNumber = RandomHelper.getRandomInt(0, list.size() - 1);
        String value = list.get(randomNumber);
        list.remove(randomNumber);
        return value;
    }
   
}
