package dms.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PROCESS")
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @JoinColumn(name = "COMPANY_ID", nullable = false)
    @ManyToOne
    private Company company;

    @JoinColumn(name = "PARENT_ID")
    @ManyToOne
    private Process parent;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "process")
//    @Fetch(FetchMode.SUBSELECT)
    private List<Activity> activityList;

    @Column(name = "PRIMITIVE", columnDefinition = "tinyint(1) default 0")
    private boolean primitive;


    public Process(Long id, String name) {
        this.id = id;
        this.name = name;
        this.activityList = new ArrayList<>();
    }

    public Process() {
        this.activityList = new ArrayList<>();
    }

    public Process(Long id)
    {
        this.id=id;
        this.activityList = new ArrayList<>();
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Process getParent() {
        return parent;
    }

    public void setParent(Process parent) {
        this.parent = parent;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public void setPrimitive(boolean primitive) {
        this.primitive = primitive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Process)) return false;
        Process process = (Process) o;
        return id.equals(process.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
