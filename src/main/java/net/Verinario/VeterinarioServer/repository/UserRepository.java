package net.Verinario.VeterinarioServer.repository;

import net.Verinario.VeterinarioServer.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;




public interface UserRepository extends JpaRepository<UserEntity, Long> {

   UserEntity findByUsernameAndPassword(String login, String password); //oAuth

    boolean existsByUsername(String login);

    //Page<UserEntity> findByUsertypeIdAndDniIgnoreCaseContainingOrNameIgnoreCaseContainingOrSurname1IgnoreCaseContainingOrSurname2IgnoreCaseContaining(Long id_usertype, String dni, String Name, String Surname1, String Surname2, Pageable oPageable);

   // Page<UserEntity> findByDniIgnoreCaseContainingOrNameIgnoreCaseContainingOrSurname1IgnoreCaseContainingOrSurname2IgnoreCaseContaining(String dni, String name, String surname1, String surname2 ,Pageable oPageable);

   Page<UserEntity> findByUsertypeIdAndNameIgnoreCaseContainingOrSurname1IgnoreCaseContainingOrSurname2IgnoreCaseContaining(Long id_usertype , String strFilterName, String strFilterSurname1, String strFilterSurname2, Pageable oPageable);

   Page<UserEntity> findByNameIgnoreCaseContainingOrSurname1IgnoreCaseContainingOrSurname2IgnoreCaseContaining( String strFilterName, String strFilterSurname1, String strFilterSurname2, Pageable oPageable);

    Page<UserEntity> findByUsertypeId(Long id_usertype, Pageable oPageable);

    UserEntity getById(Long id);
 
    
}
 
