package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;

@Document(indexName = "elasticsearchworkerindex")
//@Mapping(mappingPath = "/ElasticSearch/mappings/mapping.json")
public class ElasticWorker implements Serializable {

    @Id
    private String id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "primary_phone")
    private String primaryPhone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Date, format = DateFormat.date)
    private LocalDate dateOfBirth;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

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

    @JsonIgnoreProperties(value = { "userEmails", "userPhones", "addresses" }, allowSetters = true)
    @OneToOne
    //    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(unique = true)
    private CustomUser customUser;

    @ManyToMany
    //    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_worker__skill",
        joinColumns = @JoinColumn(name = "worker_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @JsonIgnoreProperties(value = { "workers" }, allowSetters = true)
    private Set<SkillsMaster> skills = new HashSet<>();

    @OneToMany(mappedBy = "worker", fetch = FetchType.EAGER)
    //    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "worker" }, allowSetters = true)
    private Set<File> files = new HashSet<>();

    @OneToMany(mappedBy = "worker")
    //    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "worker" }, allowSetters = true)
    private Set<Education> educations = new HashSet<>();

    @OneToMany(mappedBy = "worker")
    //    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "worker" }, allowSetters = true)
    private Set<Certificate> certificates = new HashSet<>();

    @OneToMany(mappedBy = "worker")
    //    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "locations", "worker" }, allowSetters = true)
    private Set<Employment> employments = new HashSet<>();

    @OneToMany(mappedBy = "worker")
    //    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "worker" }, allowSetters = true)
    private Set<Portfolio> portfolios = new HashSet<>();

    @OneToMany(mappedBy = "worker")
    //    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "worker" }, allowSetters = true)
    private Set<Refereces> refereces = new HashSet<>();

    @OneToMany(mappedBy = "worker")
    //    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "worker" }, allowSetters = true)
    private Set<JobPreference> jobPreferences = new HashSet<>();

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public ElasticWorker addFile(File file) {
        this.files.add(file);
        return this;
    }

    public ElasticWorker removeFile(File file) {
        this.files.remove(file);
        file.setWorker(null);
        return this;
    }

    public Set<Education> getEducations() {
        return educations;
    }

    public void setEducations(Set<Education> educations) {
        this.educations = educations;
    }

    public ElasticWorker addEducation(Education education) {
        this.educations.add(education);
        return this;
    }

    public ElasticWorker removeEducation(Education education) {
        this.educations.remove(education);
        education.setWorker(null);
        return this;
    }

    public Set<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(Set<Certificate> certificates) {
        this.certificates = certificates;
    }

    public ElasticWorker addCertificate(Certificate certificate) {
        this.certificates.add(certificate);
        return this;
    }

    public ElasticWorker removeCertificate(Certificate certificate) {
        this.certificates.remove(certificate);
        certificate.setWorker(null);
        return this;
    }

    public Set<Employment> getEmployments() {
        return employments;
    }

    public void setEmployments(Set<Employment> employments) {
        this.employments = employments;
    }

    public ElasticWorker addEmployment(Employment employment) {
        this.employments.add(employment);
        return this;
    }

    public ElasticWorker removeEmployment(Employment employment) {
        this.employments.remove(employment);
        employment.setWorker(null);
        return this;
    }

    public Set<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(Set<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }

    public ElasticWorker addPortfolio(Portfolio portfolio) {
        this.portfolios.add(portfolio);
        return this;
    }

    public ElasticWorker removePortfolio(Portfolio portfolio) {
        this.portfolios.remove(portfolio);
        portfolio.setWorker(null);
        return this;
    }

    public Set<Refereces> getRefereces() {
        return refereces;
    }

    public void setRefereces(Set<Refereces> refereces) {
        this.refereces = refereces;
    }

    public ElasticWorker addRefereces(Refereces refereces) {
        this.refereces.add(refereces);
        return this;
    }

    public ElasticWorker removeRefereces(Refereces refereces) {
        this.refereces.remove(refereces);
        refereces.setWorker(null);
        return this;
    }

    public Set<JobPreference> getJobPreferences() {
        return jobPreferences;
    }

    public void setJobPreferences(Set<JobPreference> jobPreferences) {
        this.jobPreferences = jobPreferences;
    }

    public ElasticWorker addJobPreference(JobPreference jobPreference) {
        this.jobPreferences.add(jobPreference);
        return this;
    }

    public ElasticWorker removeJobPreference(JobPreference jobPreference) {
        this.jobPreferences.remove(jobPreference);
        jobPreference.setWorker(null);
        return this;
    }

    public Set<SkillsMaster> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillsMaster> skills) {
        this.skills = skills;
    }

    public ElasticWorker(
        String id,
        @NotNull String firstName,
        String middleName,
        @NotNull String lastName,
        String primaryPhone,
        String description,
        LocalDate dateOfBirth,
        Boolean isActive,
        CustomUser customUser,
        Set<File> files,
        Set<Education> educations,
        Set<Certificate> certificates,
        Set<Employment> employments,
        Set<Portfolio> portfolios,
        Set<Refereces> refereces,
        Set<JobPreference> jobPreferences,
        Set<SkillsMaster> skills
    ) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.primaryPhone = primaryPhone;
        this.description = description;
        this.dateOfBirth = dateOfBirth;
        this.isActive = isActive;
        this.customUser = customUser;
        this.files = files;
        this.educations = educations;
        this.certificates = certificates;
        this.employments = employments;
        this.portfolios = portfolios;
        this.refereces = refereces;
        this.jobPreferences = jobPreferences;
        this.skills = skills;
    }

    public ElasticWorker() {
        super();
    }
}
