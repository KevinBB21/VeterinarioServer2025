package net.Verinario.VeterinarioServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.Verinario.VeterinarioServer.entity.TipoAnimalEntity;
import net.Verinario.VeterinarioServer.exception.ResourceNotFoundException;
import net.Verinario.VeterinarioServer.helper.ValidationHelper;
import net.Verinario.VeterinarioServer.repository.TipoAnimalRepository;


@Service
public class TipoAnimalService {

    private final TipoAnimalRepository oTipoAnimalRepository;
    private final AuthService oAuthService;

    @Autowired
    public TipoAnimalService(TipoAnimalRepository oTipoAnimalRepository, AuthService oAuthService) {
        this.oTipoAnimalRepository = oTipoAnimalRepository;
        this.oAuthService = oAuthService;
    }

    public TipoAnimalEntity get(Long id) {
       oAuthService.OnlyAdmins();
        return oTipoAnimalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoAnimal with id: " + id + " not found"));
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oTipoAnimalRepository.count();
    }

    public Page<TipoAnimalEntity> getPage(Pageable oPageable, String strFilter) {
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<TipoAnimalEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = oTipoAnimalRepository.findAll(oPageable);
        } else {
            oPage = oTipoAnimalRepository.findByTipoIgnoreCaseContaining(strFilter, oPageable);
        }
        return oPage;
    }

    public Long update(TipoAnimalEntity oTipoAnimalEntity) {
        oAuthService.OnlyAdmins();
        validate(oTipoAnimalEntity.getId());
        oTipoAnimalRepository.save(oTipoAnimalEntity);
        return oTipoAnimalEntity.getId();
    }
    
    public void validate(Long id) {
        if (!oTipoAnimalRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }
}
