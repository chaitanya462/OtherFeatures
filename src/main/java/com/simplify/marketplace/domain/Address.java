package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_1")
    private String line1;

    @Column(name = "line_2")
    private String line2;

    @Column(name = "tag")
    private String tag;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "locationPrefrences", "employment" }, allowSetters = true)
    private Location location;

    @ManyToOne
    private User user;

    public Address id(Long id) {
        this.id = id;
        return this;
    }

    public Address line1(String line1) {
        this.line1 = line1;
        return this;
    }

    public Address line2(String line2) {
        this.line2 = line2;
        return this;
    }

    public Address tag(String tag) {
        this.tag = tag;
        return this;
    }

    public Address location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Address user(User user) {
        this.setUser(user);
        return this;
    }

    public Address createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Address createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Address updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Address updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
