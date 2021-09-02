package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserPhone.
 */
@Entity
@Table(name = "user_phone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class UserPhone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_primary")
    private Boolean isPrimary;

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
    private User user;

        public UserPhone id(Long id) {
        this.id = id;
        return this;
    }

    public UserPhone phone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserPhone isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public UserPhone isPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
        return this;
    }

    public UserPhone tag(String tag) {
        this.tag = tag;
        return this;
    }

    public UserPhone user(User user) {
        this.setUser(user);
        return this;
    }

    public UserPhone createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public UserPhone createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserPhone updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public UserPhone updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
  
}
