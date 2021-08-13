package com.simplify.marketplace.service.dto;

import java.io.Serializable;
import java.util.Objects;
import lombok.Data;
import java.time.LocalDate;
/**
 * A DTO for the {@link com.simplify.marketplace.domain.SkillsMaster} entity.
 */
@Data
public class SkillsMasterDTO implements Serializable {

    private Long id;

    private String skillName;

    private String createdBy;

    private LocalDate createdAt;
    
    private String updatedBy;
    
    private LocalDate updatedAt;
    



}
