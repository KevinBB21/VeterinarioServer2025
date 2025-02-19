package net.Verinario.VeterinarioServer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "animal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class AnimalEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String color;
    private String raza;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha_nac;
    private int vacunado;
    private double peso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipoanimal")
    private TipoAnimalEntity tipoanimal;

    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY)
    private final List<FechaVacEntity> fechasvac;

    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY)
    private final List<CitaEntity> citas;


    public AnimalEntity() {
        this.citas = new ArrayList<>();
        this.fechasvac = new ArrayList<>();
    }   
    
    
    
    public AnimalEntity(Long id, String nombre, String color, String raza, LocalDate fecha_nac, int vacunado,
            double peso, TipoAnimalEntity tipoanimal, List<FechaVacEntity> fechasvac, List<CitaEntity> citas) {
        this.id = id;
        this.nombre = nombre;
        this.color = color;
        this.raza = raza;
        this.fecha_nac = fecha_nac;
        this.vacunado = vacunado;
        this.peso = peso;
        this.tipoanimal = tipoanimal;
        this.fechasvac = fechasvac;
        this.citas = citas;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public LocalDate getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(LocalDate fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public int getVacunado() {
        return vacunado;
    }

    public void setVacunado(int vacunado) {
        this.vacunado = vacunado;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public TipoAnimalEntity getTipoanimal() {
        return tipoanimal;
    }

    public void setTipoanimal(TipoAnimalEntity tipoanimal) {
        this.tipoanimal = tipoanimal;
    }

    public int getFechasvac() {
        return fechasvac.size();
    }

    public int getCitas() {
        return citas.size();
    }



  


/*   
    @PreRemove
    public void nullify() {
        this.citas.forEach(c -> c.setUser(null));
    }

    */
}
