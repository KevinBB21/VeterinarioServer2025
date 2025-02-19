package net.Verinario.VeterinarioServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.Verinario.VeterinarioServer.entity.UsertypeEntity;
import net.Verinario.VeterinarioServer.exception.ResourceNotFoundException;
import net.Verinario.VeterinarioServer.helper.ValidationHelper;
import net.Verinario.VeterinarioServer.repository.UsertypeRepository;


@Service
public class UsertypeService {

    private final UsertypeRepository oUsertypeRepository;
    private final AuthService oAuthService;

    @Autowired
    public UsertypeService(UsertypeRepository oUsertypeRepository, AuthService oAuthService) {
        this.oUsertypeRepository = oUsertypeRepository;
        this.oAuthService = oAuthService;
    }

    public UsertypeEntity get(Long id) {
       oAuthService.OnlyAdmins();
        return oUsertypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserType with id: " + id + " not found"));
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oUsertypeRepository.count();
    }

    public Page<UsertypeEntity> getPage(Pageable oPageable, String strFilter) {
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<UsertypeEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = oUsertypeRepository.findAll(oPageable);
        } else {
            oPage = oUsertypeRepository.findByNameIgnoreCaseContaining(strFilter, oPageable);
        }
        return oPage;
    }

    public Long update(UsertypeEntity oUsertypeEntity) {
        oAuthService.OnlyAdmins();
        validate(oUsertypeEntity.getId());
        oUsertypeRepository.save(oUsertypeEntity);
        return oUsertypeEntity.getId();
    }
    
    public void validate(Long id) {
        if (!oUsertypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }
}
