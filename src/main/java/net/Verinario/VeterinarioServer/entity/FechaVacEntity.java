package net.Verinario.VeterinarioServer.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "fechavac")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class FechaVacEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha_inic;
    

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_vacuna")
    private VacunaEntity vacuna;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_animal")
    private AnimalEntity animal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha_inic() {
        return fecha_inic;
    }

    public void setFecha_inic(LocalDate fecha_inic) {
        this.fecha_inic = fecha_inic;
    }

    public VacunaEntity getVacuna() {
        return vacuna;
    }

    public void setVacuna(VacunaEntity vacuna) {
        this.vacuna = vacuna;
    }

    public AnimalEntity getAnimal() {
        return animal;
    }

    public void setAnimal(AnimalEntity animal) {
        this.animal = animal;
    }

    public FechaVacEntity(Long id, LocalDate fecha_inic, VacunaEntity vacuna, AnimalEntity animal) {
        this.id = id;
        this.fecha_inic = fecha_inic;
        this.vacuna = vacuna;
        this.animal = animal;
    }

    public FechaVacEntity() {
    }

    

  /* 
    @PreRemove
    public void nullify() {
        this.citas.forEach(c -> c.setUser(null));
    }
    */
    
}
