package net.Verinario.VeterinarioServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.Verinario.VeterinarioServer.entity.UsertypeEntity;


public interface UsertypeRepository extends JpaRepository<UsertypeEntity, Long> {

    public Page<UsertypeEntity> findByNameIgnoreCaseContaining(String strFilter, Pageable oPageable);

}
 
