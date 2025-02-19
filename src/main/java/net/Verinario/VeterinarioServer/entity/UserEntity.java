package net.Verinario.VeterinarioServer.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dni;
    private String name;
    private String surname1;
    private String surname2;
    private String email;
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<CitaEntity> citas;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usertype")
    private UsertypeEntity usertype;

 

    public UserEntity() {
        this.citas = new ArrayList<>();
    }

    
   

    public UserEntity(Long id, String dni, String name, String surname1, String surname2, String email, String username,
            String password, List<CitaEntity> citas, UsertypeEntity usertype) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.email = email;
        this.username = username;
        this.password = password;
        this.citas = new ArrayList<>();
        this.usertype = usertype;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsertypeEntity getUsertype() {
        return usertype;
    }

    public void setUsertype(UsertypeEntity usertype) {
        this.usertype = usertype;
    }
    public int getCitas() {
        return this.citas.size();
    }
    
  
    @PreRemove
    public void nullify() {
        this.citas.forEach(c -> c.setUser(null));
    }


    
}
