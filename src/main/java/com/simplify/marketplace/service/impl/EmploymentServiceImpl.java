package com.simplify.marketplace.service.impl;

import com.simplify.marketplace.domain.ElasticWorker;
import com.simplify.marketplace.domain.Employment;
import com.simplify.marketplace.repository.ClientRepository;
import com.simplify.marketplace.repository.ESearchWorkerRepository;
import com.simplify.marketplace.repository.EmploymentRepository;
import com.simplify.marketplace.repository.LocationRepository;
import com.simplify.marketplace.repository.WorkerRepository;
import com.simplify.marketplace.service.EmploymentService;
import com.simplify.marketplace.service.dto.EmploymentDTO;
import com.simplify.marketplace.service.mapper.EmploymentMapper;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Employment}.
 */
@Service
@Transactional
public class EmploymentServiceImpl implements EmploymentService {

    private final Logger log = LoggerFactory.getLogger(EmploymentServiceImpl.class);

    private final EmploymentRepository employmentRepository;

    private final EmploymentMapper employmentMapper;

    @Autowired
    WorkerRepository workerrepo;

    @Autowired
    ESearchWorkerRepository elasticworkerrepo;

    @Autowired
    LocationRepository locRepo;

    @Autowired
    ClientRepository clientRepo;

    public EmploymentServiceImpl(EmploymentRepository employmentRepository, EmploymentMapper employmentMapper) {
        this.employmentRepository = employmentRepository;
        this.employmentMapper = employmentMapper;
    }

    @Override
    public EmploymentDTO save(EmploymentDTO employmentDTO) {
        log.debug("Request to save Employment : {}", employmentDTO);
        Employment employment = employmentMapper.toEntity(employmentDTO);
        employment = employmentRepository.save(employment);
        return employmentMapper.toDto(employment);
    }

    @Override
    public Optional<EmploymentDTO> partialUpdate(EmploymentDTO employmentDTO) {
        log.debug("Request to partially update Employment : {}", employmentDTO);

        return employmentRepository
            .findById(employmentDTO.getId())
            .map(
                existingEmployment -> {
                    employmentMapper.partialUpdate(existingEmployment, employmentDTO);

                    return existingEmployment;
                }
            )
            .map(employmentRepository::save)
            .map(employmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmploymentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employments");
        return employmentRepository.findAll(pageable).map(employmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmploymentDTO> findOne(Long id) {
        log.debug("Request to get Employment : {}", id);
        return employmentRepository.findById(id).map(employmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employment> findOneWorker(Long id) {
        log.debug("Request to get Employment : {}", id);
        return employmentRepository.findByWorkerId(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employment : {}", id);
        employmentRepository.deleteById(id);
    }

    public Set<Employment> getSetOfEmployment(EmploymentDTO employmentDTO) {
        String Workerid = employmentDTO.getWorker().getId().toString();

        ElasticWorker elasticWorker = elasticworkerrepo.findById(Workerid).get();

        Employment employment = new Employment();

        employment.setCompanyName(employmentDTO.getCompanyName());
        employment.setDescription(employmentDTO.getDescription());
        employment.setEndDate(employmentDTO.getEndDate());
        employment.setId(employmentDTO.getId());
        employment.setIsCurrent(employmentDTO.getIsCurrent());
        employment.setJobTitle(employmentDTO.getJobTitle());
        employment.setLastSalary(employmentDTO.getLastSalary());
        employment.setStartDate(employmentDTO.getStartDate());

        employment.setCompany(clientRepo.findById(employmentDTO.getCompany().getId()).get());

        //employment.setLocations(locRepo.findById(employmentDTO.get));

        employment.setWorker(workerrepo.findById(employmentDTO.getWorker().getId()).get());

        Set<Employment> set = elasticWorker.getEmployments();
        set.add(employment);

        return set;
    }
}
