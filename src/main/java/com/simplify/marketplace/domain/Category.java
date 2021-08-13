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
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_parent")
    private Boolean isParent;

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

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categories", "fields", "parent" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fieldValues", "category" }, allowSetters = true)
    private Set<Field> fields = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "categories", "fields", "parent" }, allowSetters = true)
    private Category parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Category id(Long id) {
        this.id = id;
        return this;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public Category isParent(Boolean isParent) {
        this.isParent = isParent;
        return this;
    }

    public Category isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public Category categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    public Category addCategory(Category category) {
        this.categories.add(category);
        category.setParent(this);
        return this;
    }

    public Category removeCategory(Category category) {
        this.categories.remove(category);
        category.setParent(null);
        return this;
    }

    public Category fields(Set<Field> fields) {
        this.setFields(fields);
        return this;
    }

    public Category addField(Field field) {
        this.fields.add(field);
        field.setCategory(this);
        return this;
    }

    public Category removeField(Field field) {
        this.fields.remove(field);
        field.setCategory(null);
        return this;
    }

    public Category parent(Category category) {
        this.setParent(category);
        return this;
    }

    public Category createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Category createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Category updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Category updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
