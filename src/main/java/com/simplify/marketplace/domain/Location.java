package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 10000)
    @Max(value = 99999)
    @Column(name = "pincode")
    private Integer pincode;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "created_by")
    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Date, format = DateFormat.date)
    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Date, format = DateFormat.date)
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "location")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "worker", "location" }, allowSetters = true)
    private Set<LocationPrefrence> locationPrefrences = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "locations", "company", "worker" }, allowSetters = true)
    private Employment employment;

    public Location id(Long id) {
        this.id = id;
        return this;
    }

    public Location pincode(Integer pincode) {
        this.pincode = pincode;
        return this;
    }

    public Location country(String country) {
        this.country = country;
        return this;
    }

    public Location state(String state) {
        this.state = state;
        return this;
    }

    public Location city(String city) {
        this.city = city;
        return this;
    }

    public Location locationPrefrences(Set<LocationPrefrence> locationPrefrences) {
        this.setLocationPrefrences(locationPrefrences);
        return this;
    }

    public Location addLocationPrefrence(LocationPrefrence locationPrefrence) {
        this.locationPrefrences.add(locationPrefrence);
        locationPrefrence.setLocation(this);
        return this;
    }

    public Location removeLocationPrefrence(LocationPrefrence locationPrefrence) {
        this.locationPrefrences.remove(locationPrefrence);
        locationPrefrence.setLocation(null);
        return this;
    }

    public Location employment(Employment employment) {
        this.setEmployment(employment);
        return this;
    }

    public Location createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Location createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Location updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Location updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
