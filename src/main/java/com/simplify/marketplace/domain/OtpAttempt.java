package com.simplify.marketplace.domain;

import com.simplify.marketplace.domain.enumeration.OtpStatus;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OtpAttempt.
 */
@Entity
@Table(name = "otp_attempt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OtpAttempt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "context_id")
    private String contextId;

    @Column(name = "otp")
    private Integer otp;

    @Column(name = "is_active")
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OtpStatus status;

    @Column(name = "ip")
    private String ip;

    @Column(name = "coookie")
    private String coookie;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OtpAttempt id(Long id) {
        this.id = id;
        return this;
    }

    public String getContextId() {
        return this.contextId;
    }

    public OtpAttempt contextId(String contextId) {
        this.contextId = contextId;
        return this;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public Integer getOtp() {
        return this.otp;
    }

    public OtpAttempt otp(Integer otp) {
        this.otp = otp;
        return this;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public OtpAttempt isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public OtpStatus getStatus() {
        return this.status;
    }

    public OtpAttempt status(OtpStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OtpStatus status) {
        this.status = status;
    }

    public String getIp() {
        return this.ip;
    }

    public OtpAttempt ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCoookie() {
        return this.coookie;
    }

    public OtpAttempt coookie(String coookie) {
        this.coookie = coookie;
        return this;
    }

    public void setCoookie(String coookie) {
        this.coookie = coookie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OtpAttempt)) {
            return false;
        }
        return id != null && id.equals(((OtpAttempt) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OtpAttempt{" +
            "id=" + getId() +
            ", contextId='" + getContextId() + "'" +
            ", otp=" + getOtp() +
            ", isActive='" + getIsActive() + "'" +
            ", status='" + getStatus() + "'" +
            ", ip='" + getIp() + "'" +
            ", coookie='" + getCoookie() + "'" +
            "}";
    }
}
