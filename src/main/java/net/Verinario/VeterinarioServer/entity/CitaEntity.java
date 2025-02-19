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
@Table(name = "cita")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class CitaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private int pagado;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_servicio")
    private ServicioEntity servicio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_animal")
    private AnimalEntity animal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getPagado() {
        return pagado;
    }

    public void setPagado(int pagado) {
        this.pagado = pagado;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    

    public CitaEntity() {
        
    }

    public CitaEntity(Long id, LocalDate fecha, int pagado, UserEntity user, ServicioEntity servicio,
            AnimalEntity animal) {
        this.id = id;
        this.fecha = fecha;
        this.pagado = pagado;
        this.user = user;
        this.servicio = servicio;
        this.animal = animal;
    }

    public ServicioEntity getServicio() {
        return servicio;
    }

    public void setServicio(ServicioEntity servicio) {
        this.servicio = servicio;
    }

    public AnimalEntity getAnimal() {
        return animal;
    }

    public void setAnimal(AnimalEntity animal) {
        this.animal = animal;
    }

    





/*   
    @PreRemove
    public void nullify() {
        this.citas.forEach(c -> c.setUser(null));
    }

    */
}
