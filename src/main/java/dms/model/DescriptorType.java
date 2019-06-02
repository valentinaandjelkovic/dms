package dms.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "DESCRIPTOR_TYPE")
public class DescriptorType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private DescriptorClass type;

    @Column(name = "MANDATORY", columnDefinition = "tinyint(1) default 0")
    private boolean mandatory;

    public DescriptorType() {
    }

    public DescriptorType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DescriptorType(Long id, String name, DescriptorClass type, boolean mandatory) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.mandatory = mandatory;
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

    public DescriptorClass getType() {
        return type;
    }

    public void setType(DescriptorClass type) {
        this.type = type;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DescriptorType)) return false;
        DescriptorType that = (DescriptorType) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
