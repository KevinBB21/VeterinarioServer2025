package net.Verinario.VeterinarioServer.repository;

import net.Verinario.VeterinarioServer.entity.VacunaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;




public interface VacunaRepository extends JpaRepository<VacunaEntity, Long> {

    //Page<VacunaEntity> findByTipoVacunaIdAndDniIgnoreCaseContainingOrNameIgnoreCaseContainingOrSurname1IgnoreCaseContainingOrSurname2IgnoreCaseContaining(Long id_Vacunatype, String dni, String Name, String Surname1, String Surname2, Pageable oPageable);

   // Page<VacunaEntity> findByDniIgnoreCaseContainingOrNameIgnoreCaseContainingOrSurname1IgnoreCaseContainingOrSurname2IgnoreCaseContaining(String dni, String name, String surname1, String surname2 ,Pageable oPageable);

   Page<VacunaEntity> findByTipoanimalIdAndNombreIgnoreCaseContaining(Long id_TipoVacuna , String strFilterNombre, Pageable oPageable);

   Page<VacunaEntity> findByNombreIgnoreCaseContaining( String strFilterNombre,  Pageable oPageable);

    Page<VacunaEntity> findByTipoanimalId(Long id_TipoAnimal, Pageable oPageable);

    VacunaEntity getById(Long id);

    
}
 
