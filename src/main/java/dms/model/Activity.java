package dms.model;

import javax.persistence.*;
import java.util.*;

@javax.persistence.Entity
@Table(name = "ACTIVITY")
public class Activity implements Entity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "PROCESS_ID", nullable = false)
    private Process process;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "activity_document_input", joinColumns = @JoinColumn(name = "activity"), inverseJoinColumns = @JoinColumn(name = "document"))
//    @Fetch(FetchMode.SUBSELECT)
    private Set<Document> inputDocuments;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "activity_document_output", joinColumns = @JoinColumn(name = "activity"), inverseJoinColumns = @JoinColumn(name = "document"))
//    @Fetch(FetchMode.SUBSELECT)
    private Set<Document> outputDocuments;

    public Activity() {
        this.inputDocuments = new HashSet<>();
        this.outputDocuments = new HashSet<>();
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

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Set<Document> getInputDocuments() {
        return inputDocuments;
    }

    public void setInputDocuments(Set<Document> inputDocuments) {
        this.inputDocuments = inputDocuments;
    }

    public Set<Document> getOutputDocuments() {
        return outputDocuments;
    }

    public void setOutputDocuments(Set<Document> outputDocuments) {
        this.outputDocuments = outputDocuments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity)) return false;
        Activity activity = (Activity) o;
        return id.equals(activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
