package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employment.
 */
@Entity
@Table(name = "employment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
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

    @Column(name = "employee_locations")
    private String employeeLocations;

    @Column(name = "last_salary")
    private Integer lastSalary;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "employment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "locationPrefrences", "employment" }, allowSetters = true)
    private Set<Location> locations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "customUser" }, allowSetters = true)
    private Client company;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "user", "files", "educations", "certificates", "employments", "portfolios", "refereces", "jobPreferences", "skills" },
        allowSetters = true
    )
    private Worker worker;

    public Employment id(Long id) {
        this.id = id;
        return this;
    }

    public Employment jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public Employment companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public Employment startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public Employment endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Employment isCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
        return this;
    }

    public Employment lastSalary(Integer lastSalary) {
        this.lastSalary = lastSalary;
        return this;
    }

    public Employment description(String description) {
        this.description = description;
        return this;
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

    public Employment company(Client client) {
        this.setCompany(client);
        return this;
    }

    public Employment worker(Worker worker) {
        this.setWorker(worker);
        return this;
    }

    public Employment createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Employment createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Employment updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Employment updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
