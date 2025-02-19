package net.Verinario.VeterinarioServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;



@Entity
@Table(name = "usertype")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsertypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "usertype", fetch = FetchType.LAZY)
    private List<UserEntity> users;

    public UsertypeEntity() {
        this.users = new ArrayList<>();
    }

    public UsertypeEntity(Long id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUsers() {
        return users.size();
    }
    

    public UsertypeEntity(Long id, String name, List<UserEntity> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    @PreRemove
    public void nullify() {
        this.users.forEach(c -> c.setUsertype(null));
    }
}
