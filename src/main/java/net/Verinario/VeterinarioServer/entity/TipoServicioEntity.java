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
@Table(name = "tiposervicio")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipoServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "tiposervicio", fetch = FetchType.LAZY)
    private final List<ServicioEntity> servicios;


    public TipoServicioEntity() {
        this.servicios = new ArrayList<>();
    }

    public TipoServicioEntity(Long id, String name, List<ServicioEntity> servicios) {
        this.id = id;
        this.name = name;
        this.servicios = servicios;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getServicios() {
        return servicios.size();
    }


    @PreRemove
    public void nullify() {
        this.servicios.forEach(c -> c.setTiposervicio(null));
    }
}
