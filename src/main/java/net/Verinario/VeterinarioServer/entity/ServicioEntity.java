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
@Table(name = "servicio")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class ServicioEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double precio;
    private Long imagen;
    private String descripcion;

    @OneToMany(mappedBy = "servicio", fetch = FetchType.LAZY)
    private final List<CitaEntity> citas;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tiposervicio")
    private TipoServicioEntity tiposervicio;

    public ServicioEntity() {
        this.citas = new ArrayList<>();
    }
  


    public ServicioEntity(Long id, String nombre, double precio, Long imagen, String descripcion,
            List<CitaEntity> citas, TipoServicioEntity tiposervicio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.citas = citas;
        this.tiposervicio = tiposervicio;
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



    public double getPrecio() {
        return precio;
    }



    public void setPrecio(double precio) {
        this.precio = precio;
    }



    public Long getImagen() {
        return imagen;
    }



    public void setImagen(Long imagen) {
        this.imagen = imagen;
    }



    public String getDescripcion() {
        return descripcion;
    }



    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



    public int getCitas() {
        return citas.size();
    }



    public TipoServicioEntity getTiposervicio() {
        return tiposervicio;
    }



    public void setTiposervicio(TipoServicioEntity tiposervicio) {
        this.tiposervicio = tiposervicio;
    }



    @PreRemove
    public void nullify() {
        this.citas.forEach(c -> c.setUser(null));
    }
    
}
