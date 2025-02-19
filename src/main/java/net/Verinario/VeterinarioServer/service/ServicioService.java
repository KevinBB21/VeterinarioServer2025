package net.Verinario.VeterinarioServer.service;


import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import net.Verinario.VeterinarioServer.exception.ResourceNotFoundException;
import net.Verinario.VeterinarioServer.exception.ResourceNotModifiedException;
import net.Verinario.VeterinarioServer.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.Verinario.VeterinarioServer.repository.TipoServicioRepository;
import net.Verinario.VeterinarioServer.entity.ServicioEntity;
import net.Verinario.VeterinarioServer.exception.CannotPerformOperationException;
import net.Verinario.VeterinarioServer.helper.RandomHelper;
import net.Verinario.VeterinarioServer.helper.ValidationHelper;
import net.Verinario.VeterinarioServer.repository.ServicioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import net.Verinario.VeterinarioServer.repository.TipoServicioRepository;

@Service
public class ServicioService {

    @Autowired
    TipoServicioService oTipoServicioService;
    @Autowired
    TipoServicioRepository oTipoServicioRepository;

    @Autowired
    ServicioRepository oServicioRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oServicioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public ServicioEntity get(Long id) {
       oAuthService.OnlyAdminsOrOwnUsersData(id);
        try {
            return oServicioRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oServicioRepository.count();
    }


    /* 
    public Page<ServicioEntity> getPage(Long id_TipoServicio, int page, int size , String strFilter) {
        //oAuthService.OnlyAdmins();
        Pageable oPageable = PageRequest.of(page, size);
        if (id_TipoServicio == null && strFilter == null) {
            return oServicioRepository.findAll(oPageable);
        }else// else if (strFilter == null) {
            return oServicioRepository.findByTipoServicioId(id_TipoServicio,  oPageable);
        } //else con solo filtro
    //}
    */

    public Page<ServicioEntity> getPage(Pageable oPageable, String strFilter, Long id_TipoServicio) {
        oAuthService.OnlyAdmins();
        Page<ServicioEntity> oPage = null;
        if (id_TipoServicio != null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oServicioRepository.findByTiposervicioId(id_TipoServicio, oPageable);
            } else {
                oPage = oServicioRepository
                        .findByTiposervicioIdAndNombreIgnoreCaseContainingOrDescripcionIgnoreCaseContaining( id_TipoServicio, strFilter, strFilter, oPageable);
            }
        } else {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oServicioRepository.findAll(oPageable);
            } else {
                oPage = oServicioRepository
                        .findByNombreIgnoreCaseContainingOrDescripcionIgnoreCaseContaining( strFilter, strFilter, oPageable);
            }
        }
        return oPage;
    }
    
    public Long create(ServicioEntity oNewServicioEntity) {
       oAuthService.OnlyAdmins();
         //oNewServicioEntity.setToken(RandomHelper.getToken(100));
        return oServicioRepository.save(oNewServicioEntity).getId();

    }

    public Long update(ServicioEntity oServicioEntity) {
        oAuthService.OnlyAdmins();
        validate(oServicioEntity.getId());
        oServicioRepository.save(oServicioEntity);
        return oServicioEntity.getId();
    }


    public Long delete(Long id) {
      oAuthService.OnlyAdmins();
        if (oServicioRepository.existsById(id)) {
            oServicioRepository.deleteById(id);
            if (oServicioRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }

    public ServicioEntity getOneRandom() {
        if (count() > 0) {
            ServicioEntity oServicioEntity = null;
            int iPosicion = RandomHelper.getRandomInt(0, (int) oServicioRepository.count() - 1);
            Pageable oPageable = PageRequest.of(iPosicion, 1);
            Page<ServicioEntity> ServicioPage = oServicioRepository.findAll(oPageable);
            List<ServicioEntity> ServicioList = ServicioPage.getContent();
            oServicioEntity = oServicioRepository.getById(ServicioList.get(0).getId());
            return oServicioEntity;
        } else {
            throw new CannotPerformOperationException("ho hay Servicios en la base de datos");
        }
    }
    
   
    


    private String getFromList(List<String> list) {
        int randomNumber = RandomHelper.getRandomInt(0, list.size() - 1);
        String value = list.get(randomNumber);
        list.remove(randomNumber);
        return value;
    }
   
}
