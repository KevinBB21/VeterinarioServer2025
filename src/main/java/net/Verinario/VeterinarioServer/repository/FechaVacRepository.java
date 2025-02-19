package net.Verinario.VeterinarioServer.repository;

import net.Verinario.VeterinarioServer.entity.FechaVacEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;




public interface FechaVacRepository extends JpaRepository<FechaVacEntity, Long> {

    //Page<FechaVacEntity> findByFechaVactypeIdAndDniIgnoreCaseContainingOrNameIgnoreCaseContainingOrSurname1IgnoreCaseContainingOrSurname2IgnoreCaseContaining(Long id_FechaVactype, String dni, String Name, String Surname1, String Surname2, Pageable oPageable);

   // Page<FechaVacEntity> findByDniIgnoreCaseContainingOrNameIgnoreCaseContainingOrSurname1IgnoreCaseContainingOrSurname2IgnoreCaseContaining(String dni, String name, String surname1, String surname2 ,Pageable oPageable);

   Page<FechaVacEntity> findByVacunaId(Long id_Vacuna ,  Pageable oPageable);

   Page<FechaVacEntity> findByAnimalId( Long id_Animal,  Pageable oPageable);

    Page<FechaVacEntity> findByAnimalIdAndVacunaId(Long id_Vacuna,Long id_Animal, Pageable oPageable);

    FechaVacEntity getById(Long id);
 
    
}
 
