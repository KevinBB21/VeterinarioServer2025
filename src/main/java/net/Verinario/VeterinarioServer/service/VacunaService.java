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
import net.Verinario.VeterinarioServer.entity.VacunaEntity;
import net.Verinario.VeterinarioServer.exception.CannotPerformOperationException;
import net.Verinario.VeterinarioServer.helper.RandomHelper;
import net.Verinario.VeterinarioServer.helper.ValidationHelper;
import net.Verinario.VeterinarioServer.repository.VacunaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class VacunaService {

    @Autowired
    TipoAnimalService oTipoVacunaService;
    @Autowired
    TipoAnimalRepository oTipoVacunaRepository;

    @Autowired
    VacunaRepository oVacunaRepository;

    @Autowired
    AuthService oAuthService;


    private final String[] NAMES = {"", "Mico", "Chispa", "Rayo", "Pluton", "Chico", "Luna", "Lola", "Coco",
        "Linda", "Noa", "Nina", "Toby", "Rocky", "Thor", "Simba", "Bruno", "Nico", "Bimba", "Max"};

    private final String[] COLORS = {"Negro", "Marrón", "Verde", "Gris", "Rosa", "Purpura", "Blanco",
        "Rojo", "Amarillo"};

    private final String[] RAZAS = {"RogWailer", "Pitbull", "Pug", "Pug", "BUlldog Frances", "Egipcio", "Orejon",
    "Peludi", "Enano"};

    

    public void validate(Long id) {
        if (!oVacunaRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public VacunaEntity get(Long id) {
       //oAuthService.OnlyAdminsOrOwnUsersData(id);
        try {
            return oVacunaRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oVacunaRepository.count();
    }


    /* 
    public Page<VacunaEntity> getPage(Long id_TipoVacuna, int page, int size , String strFilter) {
        //oAuthService.OnlyAdmins();
        Pageable oPageable = PageRequest.of(page, size);
        if (id_TipoVacuna == null && strFilter == null) {
            return oVacunaRepository.findAll(oPageable);
        }else// else if (strFilter == null) {
            return oVacunaRepository.findByTipoVacunaId(id_TipoVacuna,  oPageable);
        } //else con solo filtro
    //}
    */

    public Page<VacunaEntity> getPage(Pageable oPageable, String strFilter, Long id_TipoAnimal) {
        oAuthService.OnlyAdmins();
        Page<VacunaEntity> oPage = null;
        if (id_TipoAnimal != null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oVacunaRepository.findByTipoanimalId(id_TipoAnimal, oPageable);
            } else {
                oPage = oVacunaRepository
                        .findByTipoanimalIdAndNombreIgnoreCaseContaining( id_TipoAnimal, strFilter, oPageable);
            }
        } else {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oVacunaRepository.findAll(oPageable);
            } else {
                oPage = oVacunaRepository
                        .findByNombreIgnoreCaseContaining(strFilter, oPageable);
            }
        }
        return oPage;
    }
    
    public Long create(VacunaEntity oNewVacunaEntity) {
       oAuthService.OnlyAdmins();
        oNewVacunaEntity.setId(0L);
         //oNewVacunaEntity.setToken(RandomHelper.getToken(100));
        return oVacunaRepository.save(oNewVacunaEntity).getId();

    }

    public Long update(VacunaEntity oVacunaEntity) {
        oAuthService.OnlyAdmins();
        validate(oVacunaEntity.getId());
        oVacunaRepository.save(oVacunaEntity);
        return oVacunaEntity.getId();
    }
    
  

    public Long delete(Long id) {
      oAuthService.OnlyAdmins();
        if (oVacunaRepository.existsById(id)) {
            oVacunaRepository.deleteById(id);
            if (oVacunaRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }
  /* 
    public VacunaEntity generate() {
      oAuthService.OnlyAdmins();
        return oVacunaRepository.save(generateRandomVacuna());
    }

    public Long generateSome(Integer amount) {
      oAuthService.OnlyAdmins();
        List<VacunaEntity> VacunaList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            VacunaEntity oVacunaEntity = generateRandomVacuna();
            oVacunaRepository.save(oVacunaEntity);
            VacunaList.add(oVacunaEntity);
        }
        return oVacunaRepository.count();
    }
    */

    public VacunaEntity getOneRandom() {
        if (count() > 0) {
            VacunaEntity oVacunaEntity = null;
            int iPosicion = RandomHelper.getRandomInt(0, (int) oVacunaRepository.count() - 1);
            Pageable oPageable = PageRequest.of(iPosicion, 1);
            Page<VacunaEntity> VacunaPage = oVacunaRepository.findAll(oPageable);
            List<VacunaEntity> VacunaList = VacunaPage.getContent();
            oVacunaEntity = oVacunaRepository.getById(VacunaList.get(0).getId());
            return oVacunaEntity;
        } else {
            throw new CannotPerformOperationException("no hay Vacunas en la base de datos");
        }
    }
   
    /*
    private VacunaEntity generateRandomVacuna() {
        VacunaEntity oVacunaEntity = new VacunaEntity();
        oVacunaEntity.setNombre(generateNombre());
        oVacunaEntity.setColor(generateColor());
        oVacunaEntity.setRaza(generateRaza());
        oVacunaEntity.setFecha_nac(RandomHelper.getRadomDateTime());
        oVacunaEntity.setVacunado(RandomHelper.getRandomInt2(0, 1));
        oVacunaEntity.setPeso(RandomHelper.getRadomDouble(5, 25));
        int totalTipoVacunas = (int) oTipoVacunaRepository.count();
        int randomTipoVacunaId = RandomHelper.getRandomInt(1, totalTipoVacunas);
        oTipoVacunaRepository.findById((long) randomTipoVacunaId)
        .ifPresent(oVacunaEntity::setTipoVacuna);


        return oVacunaEntity;
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
     */
   
}
