package com.simplify.marketplace.service.dto;

import com.simplify.marketplace.domain.enumeration.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.simplify.marketplace.domain.Field} entity.
 */
public class FieldDTO implements Serializable {

    private Long id;

    private String fieldName;

    private String fieldLabel;

    private FieldType fieldType;

    private Boolean isActive;

    private CategoryDTO category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldDTO)) {
            return false;
        }

        FieldDTO fieldDTO = (FieldDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fieldDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldDTO{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldLabel='" + getFieldLabel() + "'" +
            ", fieldType='" + getFieldType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", category=" + getCategory() +
            "}";
    }
}
