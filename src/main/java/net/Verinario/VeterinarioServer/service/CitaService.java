package net.Verinario.VeterinarioServer.service;

import org.apache.coyote.http11.InputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.Verinario.VeterinarioServer.entity.CitaEntity;
import net.Verinario.VeterinarioServer.exception.ResourceNotFoundException;
import net.Verinario.VeterinarioServer.exception.ResourceNotModifiedException;
import net.Verinario.VeterinarioServer.helper.ValidationHelper;
import net.Verinario.VeterinarioServer.repository.CitaRepository;

@Service
public class CitaService {

    private final CitaRepository oCitaRepository;
    private final AuthService oAuthService;

    @Autowired
    public CitaService(CitaRepository oCitaRepository, AuthService oAuthService) {
        this.oCitaRepository = oCitaRepository;
        this.oAuthService = oAuthService;
    }

    public CitaEntity get(Long id) {
        oAuthService.OnlyAdmins();
        return oCitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita with id: " + id + " not found"));
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oCitaRepository.count();
    }

    //Mirar con karim , que no esta muy hayá
    public Page<CitaEntity> getPage(Pageable oPageable, String strFilter, Long id_animal ,Long id_servicio ,Long id_usuario) {
        oAuthService.OnlyAdmins();
        Page<CitaEntity> oPage = null;
        if (id_usuario != null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oCitaRepository.findByUserId(id_usuario, oPageable);
            } else {
                oPage = oCitaRepository
                        .findByFechaIgnoreCaseContainingAndUserId(strFilter, id_usuario, oPageable);
            }
        } else if (id_animal != null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oCitaRepository.findByAnimalId(id_animal ,oPageable);
            } else {
                oPage = oCitaRepository
                        .findByFechaIgnoreCaseContainingAndAnimalId(strFilter, id_animal, oPageable);
            }
        } else if (id_servicio != null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oCitaRepository.findByServicioId(id_servicio ,oPageable);
            } else {
                oPage = oCitaRepository
                        .findByFechaIgnoreCaseContainingAndServicioId(strFilter, id_servicio, oPageable);
            }
        }else if (id_servicio == null && id_animal == null && id_usuario == null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oCitaRepository.findAll(oPageable);
            } else {
                oPage = oCitaRepository
                        .findByServicioIdAndAnimalIdAndUserId(id_servicio,id_animal,id_usuario,oPageable);
            }
        }
        return oPage;
    }

    public Long create(CitaEntity oNewCitaEntity) {
        oAuthService.OnlyAdmins();
          //oNewCitaEntity.setToken(RandomHelper.getToken(100));
         return oCitaRepository.save(oNewCitaEntity).getId();
 
     }

    public Long update(CitaEntity oCitaEntity) {
        oAuthService.OnlyAdmins();
        validate(oCitaEntity.getId());
        oCitaRepository.save(oCitaEntity);
        return oCitaEntity.getId();
    }

    public void validate(Long id) {
        if (!oCitaRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        if (oCitaRepository.existsById(id)) {
            oCitaRepository.deleteById(id);
            if (oCitaRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }
}
