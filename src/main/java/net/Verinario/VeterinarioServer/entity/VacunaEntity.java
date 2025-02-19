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
@Table(name = "vacuna")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class VacunaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;


    @OneToMany(mappedBy = "vacuna", fetch = FetchType.LAZY)
    private final List<FechaVacEntity> fechasvac;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipoanimal")
    private TipoAnimalEntity tipoanimal;

    

  
    public VacunaEntity() {
        this.fechasvac = new ArrayList<>();
    }

    public VacunaEntity(Long id, String name) {
        this.id = id;
        this.fechasvac = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechasvac() {
        return fechasvac.size();
    }

    public TipoAnimalEntity getTipoanimal() {
        return tipoanimal;
    }

    public void setTipoanimal(TipoAnimalEntity tipoanimal) {
        this.tipoanimal = tipoanimal;
    }


  /* 
    @PreRemove
    public void nullify() {
        this.citas.forEach(c -> c.setUser(null));
    }
    */
    
}
