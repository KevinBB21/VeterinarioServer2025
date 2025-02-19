package net.Verinario.VeterinarioServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.Verinario.VeterinarioServer.entity.ServicioEntity;


public interface ServicioRepository extends JpaRepository<ServicioEntity, Long> {

   public Page <ServicioEntity> findByNombreIgnoreCaseContainingOrDescripcionIgnoreCaseContaining(String strFilterNombre,String  strFilterDescripc, Pageable oPageable);

   public Page <ServicioEntity> findByTiposervicioIdAndNombreIgnoreCaseContainingOrDescripcionIgnoreCaseContaining(Long id_TipoServicio,String strFilterNombre,String strFilterDescrip,Pageable oPageable);

   public Page <ServicioEntity> findByTiposervicioId(Long id_TipoServicio, Pageable oPageable);
}
 
