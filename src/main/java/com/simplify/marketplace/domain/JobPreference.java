package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplify.marketplace.domain.enumeration.EmploymentType;
import com.simplify.marketplace.domain.enumeration.EngagementType;
import com.simplify.marketplace.domain.enumeration.LocationType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobPreference.
 */
@Entity
@Table(name = "job_preference")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class JobPreference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hourly_rate")
    private Integer hourlyRate;

    @Column(name = "daily_rate")
    private Integer dailyRate;

    @Column(name = "monthly_rate")
    private Integer monthlyRate;

    @Column(name = "hour_per_day")
    private Integer hourPerDay;

    @Column(name = "hour_per_week")
    private Integer hourPerWeek;

    @Enumerated(EnumType.STRING)
    @Column(name = "engagement_type")
    private EngagementType engagementType;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_type")
    private LocationType locationType;

    @Column(name = "available_from")
    private LocalDate availableFrom;

    @Column(name = "available_to")
    private LocalDate availableTo;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "worker")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "worker", "location" }, allowSetters = true)
    private Set<LocationPrefrence> locationPrefrences = new HashSet<>();

    @OneToMany(mappedBy = "jobpreference")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jobpreference", "field" }, allowSetters = true)
    private Set<FieldValue> fieldValues = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "categories", "fields", "parent" }, allowSetters = true)
    private Category subCategory;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "user", "files", "educations", "certificates", "employments", "portfolios", "refereces", "jobPreferences", "skills" },
        allowSetters = true
    )
    private Worker worker;

    public JobPreference id(Long id) {
        this.id = id;
        return this;
    }

    public JobPreference hourlyRate(Integer hourlyRate) {
        this.hourlyRate = hourlyRate;
        return this;
    }

    public JobPreference dailyRate(Integer dailyRate) {
        this.dailyRate = dailyRate;
        return this;
    }

    public JobPreference monthlyRate(Integer monthlyRate) {
        this.monthlyRate = monthlyRate;
        return this;
    }

    public JobPreference hourPerDay(Integer hourPerDay) {
        this.hourPerDay = hourPerDay;
        return this;
    }

    public JobPreference hourPerWeek(Integer hourPerWeek) {
        this.hourPerWeek = hourPerWeek;
        return this;
    }

    public JobPreference engagementType(EngagementType engagementType) {
        this.engagementType = engagementType;
        return this;
    }

    public JobPreference employmentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
        return this;
    }

    public JobPreference locationType(LocationType locationType) {
        this.locationType = locationType;
        return this;
    }

    public JobPreference availableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
        return this;
    }

    public JobPreference availableTo(LocalDate availableTo) {
        this.availableTo = availableTo;
        return this;
    }

    public JobPreference isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public JobPreference locationPrefrences(Set<LocationPrefrence> locationPrefrences) {
        this.setLocationPrefrences(locationPrefrences);
        return this;
    }

    public JobPreference addLocationPrefrence(LocationPrefrence locationPrefrence) {
        this.locationPrefrences.add(locationPrefrence);
        locationPrefrence.setWorker(this);
        return this;
    }

    public JobPreference removeLocationPrefrence(LocationPrefrence locationPrefrence) {
        this.locationPrefrences.remove(locationPrefrence);
        locationPrefrence.setWorker(null);
        return this;
    }

    public JobPreference fieldValues(Set<FieldValue> fieldValues) {
        this.setFieldValues(fieldValues);
        return this;
    }

    public JobPreference addFieldValue(FieldValue fieldValue) {
        this.fieldValues.add(fieldValue);
        fieldValue.setJobpreference(this);
        return this;
    }

    public JobPreference removeFieldValue(FieldValue fieldValue) {
        this.fieldValues.remove(fieldValue);
        fieldValue.setJobpreference(null);
        return this;
    }

    public JobPreference subCategory(Category category) {
        this.setSubCategory(category);
        return this;
    }

    public JobPreference worker(Worker worker) {
        this.setWorker(worker);
        return this;
    }

    public JobPreference createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public JobPreference createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public JobPreference updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public JobPreference updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
