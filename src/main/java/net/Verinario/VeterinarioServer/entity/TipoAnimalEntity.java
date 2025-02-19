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
@Table(name = "tipoanimal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipoAnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;

    @OneToMany(mappedBy = "tipoanimal", fetch = FetchType.LAZY)
    private final List<AnimalEntity> animales;

    @OneToMany(mappedBy = "tipoanimal", fetch = FetchType.LAZY)
    private final List<VacunaEntity> vacunas;

    public TipoAnimalEntity() {
        this.vacunas = new ArrayList<>();
        this.animales = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAnimales() {
        return animales.size();
    }

    public int getVacunas() {
        return vacunas.size();
    }

    public TipoAnimalEntity(Long id, String tipo, List<AnimalEntity> animales, List<VacunaEntity> vacunas) {
        this.id = id;
        this.tipo = tipo;
        this.animales = animales;
        this.vacunas = vacunas;
    }

    
/* 
    @PreRemove
    public void nullify() {
        this.users.forEach(c -> c.setUsertype(null));
    }
    */
}
