package net.Verinario.VeterinarioServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.Verinario.VeterinarioServer.entity.TipoAnimalEntity;


public interface TipoAnimalRepository extends JpaRepository<TipoAnimalEntity, Long> {

    public Page<TipoAnimalEntity> findByTipoIgnoreCaseContaining(String strFilter, Pageable oPageable);

}
 
