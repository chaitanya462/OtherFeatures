package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LocationPrefrence.
 */
@Entity
@Table(name = "location_prefrence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class LocationPrefrence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prefrence_order")
    private Integer prefrenceOrder;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "locationPrefrences", "fieldValues", "subCategory", "worker" }, allowSetters = true)
    private JobPreference worker;

    @ManyToOne
    @JsonIgnoreProperties(value = { "locationPrefrences", "employment" }, allowSetters = true)
    private Location location;

    public LocationPrefrence id(Long id) {
        this.id = id;
        return this;
    }

    public LocationPrefrence prefrenceOrder(Integer prefrenceOrder) {
        this.prefrenceOrder = prefrenceOrder;
        return this;
    }

    public LocationPrefrence worker(JobPreference jobPreference) {
        this.setWorker(jobPreference);
        return this;
    }

    public LocationPrefrence location(Location location) {
        this.setLocation(location);
        return this;
    }

    public LocationPrefrence createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LocationPrefrence createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocationPrefrence updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public LocationPrefrence updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
