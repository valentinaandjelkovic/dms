package dms.model;

import javax.persistence.*;
import java.util.*;

@javax.persistence.Entity
@Table(name = "COMPANY")
public class Company implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "COMPANY_NUMBER", nullable = false)
    private String companyNumber;

    @OneToMany(mappedBy = "company")
    private Set<User> users;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "company")
    private Set<Process> processList;


    public Company() {
        this.users = new HashSet<>();
        this.processList = new HashSet<>();
    }

    public Company(Long id) {
        this.id = id;
        this.users = new HashSet<>();
        this.processList = new HashSet<>();
    }

    public Company(Long id, String name, String companyNumber) {
        this.id = id;
        this.name = name;
        this.companyNumber = companyNumber;
        this.users = new HashSet<>();
        this.processList = new HashSet<>();
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

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public Set<Process> getProcessList() {
        return processList;
    }

    public void setProcessList(Set<Process> processList) {
        this.processList = processList;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
