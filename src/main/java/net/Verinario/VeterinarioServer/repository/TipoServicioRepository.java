package net.Verinario.VeterinarioServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.Verinario.VeterinarioServer.entity.TipoServicioEntity;


public interface TipoServicioRepository extends JpaRepository<TipoServicioEntity, Long> {

    public Page<TipoServicioEntity> findByNameIgnoreCaseContaining(String strFilter, Pageable oPageable);

}
 
