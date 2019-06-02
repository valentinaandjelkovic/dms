package dms.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.*;

@Entity
@Table(name = "DOCUMENT_TYPE")
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "type")
//    @Fetch(FetchMode.SUBSELECT)
    private Set<Document> documentList;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "DOCUMENT_TYPE_ID")
//    @Fetch(FetchMode.SUBSELECT)
    private Set<DescriptorType> descriptorTypes;

    public DocumentType() {
        this.descriptorTypes = new HashSet<>();
        this.documentList = new HashSet<>();
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

    public Set<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(Set<Document> documentList) {
        this.documentList = documentList;
    }

    public Set<DescriptorType> getDescriptorTypes() {
        return descriptorTypes;
    }

    public void setDescriptorTypes(Set<DescriptorType> descriptorTypes) {
        this.descriptorTypes = descriptorTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentType)) return false;
        DocumentType that = (DocumentType) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
