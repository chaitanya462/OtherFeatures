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
 * A SkillsMaster.
 */
@Entity
@Table(name = "skills_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class SkillsMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill_name")
    private String skillName;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToMany(mappedBy = "skills")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "user", "files", "educations", "certificates", "employments", "portfolios", "refereces", "jobPreferences", "skills" },
        allowSetters = true
    )
    private Set<Worker> workers = new HashSet<>();

    public SkillsMaster id(Long id) {
        this.id = id;
        return this;
    }

    public SkillsMaster skillName(String skillName) {
        this.skillName = skillName;
        return this;
    }

    public SkillsMaster workers(Set<Worker> workers) {
        this.setWorkers(workers);
        return this;
    }

    public SkillsMaster addWorker(Worker worker) {
        this.workers.add(worker);
        worker.getSkills().add(this);
        return this;
    }

    public SkillsMaster removeWorker(Worker worker) {
        this.workers.remove(worker);
        worker.getSkills().remove(this);
        return this;
    }

    public SkillsMaster createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public SkillsMaster createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SkillsMaster updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public SkillsMaster updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
