package net.Verinario.VeterinarioServer.service;


import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import net.Verinario.VeterinarioServer.exception.ResourceNotFoundException;
import net.Verinario.VeterinarioServer.exception.ResourceNotModifiedException;
import net.Verinario.VeterinarioServer.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.Verinario.VeterinarioServer.repository.UsertypeRepository;
import net.Verinario.VeterinarioServer.entity.UserEntity;
import net.Verinario.VeterinarioServer.exception.CannotPerformOperationException;
import net.Verinario.VeterinarioServer.helper.RandomHelper;
import net.Verinario.VeterinarioServer.helper.UsertypeHelper;
import net.Verinario.VeterinarioServer.helper.ValidationHelper;
import net.Verinario.VeterinarioServer.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import net.Verinario.VeterinarioServer.repository.UsertypeRepository;

@Service
public class UserService {

    @Autowired
    UsertypeService oUsertypeService;
    @Autowired
    UsertypeRepository oUsertypeRepository;

    @Autowired
    UserRepository oUserRepository;

    @Autowired
    AuthService oAuthService;

    private final String DNI_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";
    private final String VETERINARIO_DEFAULT_PASSWORD = "4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64"; //VETERINARIO
    private final String[] NAMES = {"Jose", "Mark", "Elen", "Toni", "Hector", "Jose", "Laura", "Vika", "Sergio",
        "Javi", "Marcos", "Pere", "Daniel", "Jose", "Javi", "Sergio", "Aaron", "Rafa", "Lionel", "Borja"};

    private final String[] SURNAMES = {"Penya", "Tatay", "Coronado", "Cabanes", "Mikayelyan", "Gil", "Martinez",
        "Bargues", "Raga", "Santos", "Sierra", "Arias", "Santos", "Kuvshinnikova", "Cosin", "Frejo", "Marti",
        "Valcarcel", "Sesa", "Lence", "Villanueva", "Peyro", "Navarro", "Navarro", "Primo", "Gil", "Mocholi",
        "Ortega", "Dung", "Vi", "Sanchis", "Merida", "Aznar", "Aparici", "Tarazón", "Alcocer", "Salom", "Santamaría"};

    public void validate(Long id) {
        if (!oUserRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(UserEntity oUserEntity) {
        ValidationHelper.validateStringLength(oUserEntity.getName(), 2, 50, "campo Name de User (el campo debe tener longitud de 2 a 50 caracteres)");
        ValidationHelper.validateStringLength(oUserEntity.getSurname1(), 2, 50, "campo primer Surname de User (el campo debe tener longitud de 2 a 50 caracteres)");
        ValidationHelper.validateStringLength(oUserEntity.getSurname2(), 2, 50, "campo segundo Surname de User (el campo debe tener longitud de 2 a 50 caracteres)");
        ValidationHelper.validateEmail(oUserEntity.getEmail(), " campo email de User");
        ValidationHelper.validateLogin(oUserEntity.getUsername(), " campo login de User");
        oUsertypeService.validate(oUserEntity.getUsertype().getId());
    }
 
    public UserEntity get(Long id) {
       oAuthService.OnlyAdminsOrOwnUsersData(id);
        try {
            return oUserRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oUserRepository.count();
    }


    /* 
    public Page<UserEntity> getPage(Long id_usertype, int page, int size , String strFilter) {
        //oAuthService.OnlyAdmins();
        Pageable oPageable = PageRequest.of(page, size);
        if (id_usertype == null && strFilter == null) {
            return oUserRepository.findAll(oPageable);
        }else// else if (strFilter == null) {
            return oUserRepository.findByUsertypeId(id_usertype,  oPageable);
        } //else con solo filtro
    //}
    */

    public Page<UserEntity> getPage(Pageable oPageable, String strFilter, Long id_usertype) {
        oAuthService.OnlyAdmins();
        Page<UserEntity> oPage = null;
        if (id_usertype != null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oUserRepository.findByUsertypeId(id_usertype, oPageable);
            } else {
                oPage = oUserRepository
                        .findByUsertypeIdAndNameIgnoreCaseContainingOrSurname1IgnoreCaseContainingOrSurname2IgnoreCaseContaining( id_usertype, strFilter, strFilter, strFilter, oPageable);
            }
        } else {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oUserRepository.findAll(oPageable);
            } else {
                oPage = oUserRepository
                        .findByNameIgnoreCaseContainingOrSurname1IgnoreCaseContainingOrSurname2IgnoreCaseContaining(strFilter, strFilter, strFilter, oPageable);
            }
        }
        return oPage;
    }
    
    public Long create(UserEntity oNewUserEntity) {
       oAuthService.OnlyAdmins();
       validate(oNewUserEntity);
        oNewUserEntity.setId(0L);
        oNewUserEntity.setPassword(VETERINARIO_DEFAULT_PASSWORD);
         //oNewUserEntity.setToken(RandomHelper.getToken(100));
        return oUserRepository.save(oNewUserEntity).getId();

    }
    @Transactional //pte
    public Long update(UserEntity oUserEntity) {
        validate(oUserEntity.getId());
        oAuthService.OnlyAdminsOrOwnUsersData(oUserEntity.getId());
        validate(oUserEntity);
        oUsertypeService.validate(oUserEntity.getUsertype().getId());
        if (oAuthService.isAdmin()) {
            return update4Admins(oUserEntity).getId();
        } else {
            return update4Users(oUserEntity).getId();
        }
    }
    
    @Transactional
    private UserEntity update4Admins(UserEntity oUpdatedUserEntity) {
        UserEntity oUserEntity = oUserRepository.findById(oUpdatedUserEntity.getId()).get();
        //keeping login password token & validado 
        oUserEntity.setDni(oUpdatedUserEntity.getDni());
        oUserEntity.setName(oUpdatedUserEntity.getName());
        oUserEntity.setSurname1(oUpdatedUserEntity.getSurname1());
        oUserEntity.setSurname2(oUpdatedUserEntity.getSurname2());
        oUserEntity.setEmail(oUpdatedUserEntity.getEmail());
        oUserEntity.setUsertype(oUsertypeService.get(oUpdatedUserEntity.getUsertype().getId()));
        oUserEntity.setUsername(oUpdatedUserEntity.getUsername());
        return oUserRepository.save(oUserEntity);
    }

    @Transactional
    private UserEntity update4Users(UserEntity oUpdatedUserEntity) {
        UserEntity oUserEntity = oUserRepository.findById(oUpdatedUserEntity.getId()).get();
        //keeping login password token & validado descuento activo Usertype
        oUserEntity.setDni(oUpdatedUserEntity.getDni());
        oUserEntity.setName(oUpdatedUserEntity.getName());
        oUserEntity.setSurname1(oUpdatedUserEntity.getSurname1());
        oUserEntity.setSurname2(oUpdatedUserEntity.getSurname2());
        oUserEntity.setEmail(oUpdatedUserEntity.getEmail());
        oUserEntity.setUsername(oUpdatedUserEntity.getUsername());
        oUserEntity.setUsertype(oUsertypeService.get(2L));
        return oUserRepository.save(oUserEntity);
    }

    public Long delete(Long id) {
      oAuthService.OnlyAdmins();
        if (oUserRepository.existsById(id)) {
            oUserRepository.deleteById(id);
            if (oUserRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }
  
    public UserEntity generate() {
      oAuthService.OnlyAdmins();
        return oUserRepository.save(generateRandomUser());
    }

    public Long generateSome(Integer amount) {
      oAuthService.OnlyAdmins();
        List<UserEntity> userList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            UserEntity oUserEntity = generateRandomUser();
            oUserRepository.save(oUserEntity);
            userList.add(oUserEntity);
        }
        return oUserRepository.count();
    }
    

    public UserEntity getOneRandom() {
        if (count() > 0) {
            UserEntity oUserEntity = null;
            int iPosicion = RandomHelper.getRandomInt(0, (int) oUserRepository.count() - 1);
            Pageable oPageable = PageRequest.of(iPosicion, 1);
            Page<UserEntity> UserPage = oUserRepository.findAll(oPageable);
            List<UserEntity> UserList = UserPage.getContent();
            oUserEntity = oUserRepository.getById(UserList.get(0).getId());
            return oUserEntity;
        } else {
            throw new CannotPerformOperationException("ho hay Users en la base de datos");
        }
    }
    
    private UserEntity generateRandomUser() {
        UserEntity oUserEntity = new UserEntity();
        oUserEntity.setDni(generateDNI());
        oUserEntity.setName(generateName());
        oUserEntity.setSurname1(generateSurname());
        oUserEntity.setSurname2(generateSurname());
        oUserEntity.setUsername(oUserEntity.getName() + "_" + oUserEntity.getSurname1());
        oUserEntity.setPassword(VETERINARIO_DEFAULT_PASSWORD); // VETERINARIO
        oUserEntity.setEmail(generateEmail(oUserEntity.getName(), oUserEntity.getSurname1()));
        int totalUsertypes = (int) oUsertypeRepository.count();
        int randomUserTypeId = RandomHelper.getRandomInt(1, totalUsertypes);
        oUsertypeRepository.findById((long) randomUserTypeId)
                .ifPresent(oUserEntity::setUsertype);


        return oUserEntity;
    }
    

    private String generateDNI() {
        String dni = "";
        int dniNumber = RandomHelper.getRandomInt(11111111, 99999999 + 1);
        dni += dniNumber + "" + DNI_LETTERS.charAt(dniNumber % 23);
        return dni;
    }

    private String generateName() {
        return NAMES[RandomHelper.getRandomInt(0, NAMES.length - 1)].toLowerCase();
    }

    private String generateSurname() {
        return SURNAMES[RandomHelper.getRandomInt(0, SURNAMES.length - 1)].toLowerCase();
    }

    private String generateEmail(String name, String surname) {
        List<String> list = new ArrayList<>();
        list.add(name);
        list.add(surname);
        return getFromList(list) + "_" + getFromList(list) + "@daw.tk";
    }

    private String getFromList(List<String> list) {
        int randomNumber = RandomHelper.getRandomInt(0, list.size() - 1);
        String value = list.get(randomNumber);
        list.remove(randomNumber);
        return value;
    }
   
}
