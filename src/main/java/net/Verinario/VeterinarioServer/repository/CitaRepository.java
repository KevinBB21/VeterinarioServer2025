package net.Verinario.VeterinarioServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.Verinario.VeterinarioServer.entity.CitaEntity;


public interface CitaRepository extends JpaRepository<CitaEntity, Long> {

    public Page<CitaEntity> findByIdIgnoreCaseContainingAndFechaIgnoreCaseContaining(Long id, String fecha, Pageable oPageable);

    public Page<CitaEntity> findByAnimalId(Long id_animal, Pageable oPageable);

    public Page<CitaEntity> findByServicioId(Long id_servicio,Pageable oPageable);

    public Page<CitaEntity> findByServicioIdAndAnimalId( Long id_servicio, Long id_animal, Pageable oPageable);

    public Page<CitaEntity> findByUserId(Long id_usuario , Pageable oPageable);

    public Page<CitaEntity> findByUserIdAndAnimalId( Long id_animal,Long id_usuario,Pageable oPageable);

    public Page<CitaEntity> findByServicioIdAndAnimalIdAndUserId(Long id_servicio,Long id_animal,Long id_usuario,Pageable oPageable);

    public Page<CitaEntity> findByFechaIgnoreCaseContaining(String strFilterFecha ,Pageable oPageable);

    public Page<CitaEntity> findByFechaIgnoreCaseContainingAndAnimalId( String strFilterFecha,Long id_animal,Pageable oPageable);

    public Page<CitaEntity> findByFechaIgnoreCaseContainingAndServicioId(String strFilterFecha,Long id_servicio,Pageable oPageable);

    public Page<CitaEntity> findByFechaIgnoreCaseContainingAndServicioIdAndAnimalId(String strFilterFecha,Long id_servicio,Long id_animal,Pageable oPageable);

    public Page<CitaEntity> findByFechaIgnoreCaseContainingAndUserId(String strFilterFecha,Long id_usuario,Pageable oPageable);
}
 
