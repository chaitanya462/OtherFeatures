package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employment.
 */
@Entity
@Table(name = "employment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_current")
    private Boolean isCurrent;

    @Column(name = "last_salary")
    private Integer lastSalary;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "employment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "locationPrefrences", "employment" }, allowSetters = true)
    private Set<Location> locations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "customUser" }, allowSetters = true)
    private Client company;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "customUser", "files", "educations", "certificates", "employments", "portfolios", "refereces", "jobPreferences", "skills",
        },
        allowSetters = true
    )
    private Worker worker;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employment id(Long id) {
        this.id = id;
        return this;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public Employment jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Employment companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Employment startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Employment endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsCurrent() {
        return this.isCurrent;
    }

    public Employment isCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
        return this;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Integer getLastSalary() {
        return this.lastSalary;
    }

    public Employment lastSalary(Integer lastSalary) {
        this.lastSalary = lastSalary;
        return this;
    }

    public void setLastSalary(Integer lastSalary) {
        this.lastSalary = lastSalary;
    }

    public String getDescription() {
        return this.description;
    }

    public Employment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Location> getLocations() {
        return this.locations;
    }

    public Employment locations(Set<Location> locations) {
        this.setLocations(locations);
        return this;
    }

    public Employment addLocation(Location location) {
        this.locations.add(location);
        location.setEmployment(this);
        return this;
    }

    public Employment removeLocation(Location location) {
        this.locations.remove(location);
        location.setEmployment(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        if (this.locations != null) {
            this.locations.forEach(i -> i.setEmployment(null));
        }
        if (locations != null) {
            locations.forEach(i -> i.setEmployment(this));
        }
        this.locations = locations;
    }

    public Client getCompany() {
        return this.company;
    }

    public Employment company(Client client) {
        this.setCompany(client);
        return this;
    }

    public void setCompany(Client client) {
        this.company = client;
    }

    public Worker getWorker() {
        return this.worker;
    }

    public Employment worker(Worker worker) {
        this.setWorker(worker);
        return this;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employment)) {
            return false;
        }
        return id != null && id.equals(((Employment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employment{" +
            "id=" + getId() +
            ", jobTitle='" + getJobTitle() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", isCurrent='" + getIsCurrent() + "'" +
            ", lastSalary=" + getLastSalary() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
