package net.Verinario.VeterinarioServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.Verinario.VeterinarioServer.entity.TipoServicioEntity;
import net.Verinario.VeterinarioServer.exception.ResourceNotFoundException;
import net.Verinario.VeterinarioServer.helper.ValidationHelper;
import net.Verinario.VeterinarioServer.repository.TipoServicioRepository;


@Service
public class TipoServicioService {

    private final TipoServicioRepository oTipoServicioRepository;
    private final AuthService oAuthService;

    @Autowired
    public TipoServicioService(TipoServicioRepository oTipoServicioRepository, AuthService oAuthService) {
        this.oTipoServicioRepository = oTipoServicioRepository;
        this.oAuthService = oAuthService;
    }

    public TipoServicioEntity get(Long id) {
       oAuthService.OnlyAdmins();
        return oTipoServicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoServicio with id: " + id + " not found"));
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oTipoServicioRepository.count();
    }

    public Page<TipoServicioEntity> getPage(Pageable oPageable, String strFilter) {
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<TipoServicioEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = oTipoServicioRepository.findAll(oPageable);
        } else {
            oPage = oTipoServicioRepository.findByNameIgnoreCaseContaining(strFilter, oPageable);
        }
        return oPage;
    }

    public Long update(TipoServicioEntity oTipoServicioEntity) {
        oAuthService.OnlyAdmins();
        validate(oTipoServicioEntity.getId());
        oTipoServicioRepository.save(oTipoServicioEntity);
        return oTipoServicioEntity.getId();
    }
    
    public void validate(Long id) {
        if (!oTipoServicioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }
}
