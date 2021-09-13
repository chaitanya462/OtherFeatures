package com.simplify.marketplace.web.rest;

import com.simplify.marketplace.domain.*;
import com.simplify.marketplace.domain.Certificate;
import com.simplify.marketplace.domain.Employment;
import com.simplify.marketplace.domain.LocationPrefrence;
import com.simplify.marketplace.domain.Portfolio;
import com.simplify.marketplace.repository.*;
import com.simplify.marketplace.repository.LocationPrefrenceRepository;
import com.simplify.marketplace.repository.PortfolioRepository;
import com.simplify.marketplace.service.EducationService;
import com.simplify.marketplace.service.EmploymentService;
import com.simplify.marketplace.service.JobPreferenceService;
import com.simplify.marketplace.service.UserService;
import com.simplify.marketplace.service.WorkerService;
import com.simplify.marketplace.service.dto.WorkerDTO;
import com.simplify.marketplace.service.mapper.UserMapper;
import com.simplify.marketplace.service.mapper.WorkerMapper;
import com.simplify.marketplace.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.simple.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.simplify.marketplace.domain.Worker}.
 */
@RestController
@RequestMapping("/api")
public class WorkerResource {

    private CategoryRepository categoryRepository;
    private JobPreferenceRepository jobPreferenceRepository;
    private EducationRepository educationRepository;
    private CertificateRepository certificateRepository;
    private EmploymentRepository employmentRepository;
    private LocationPrefrenceRepository locationPrefrenceRepository;
    private PortfolioRepository portfolioRepository;
    private final WorkerMapper workerMapper;
    private final UserMapper userMapper;

    private UserService userService;
    private JobPreferenceService jobPreferenceService;
    private EmploymentService employmentService;
    private EducationService educationService;

    @Autowired
    RabbitTemplate rabbit_msg;

    @Autowired
    WorkerRepository workerRepo;

    @Autowired
    RestHighLevelClient client;

    private final Logger log = LoggerFactory.getLogger(WorkerResource.class);

    private static final String ENTITY_NAME = "worker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkerService workerService;

    private final WorkerRepository workerRepository;

    private final FileRepository fileRepository;

    public WorkerResource(
        WorkerService workerService,
        WorkerRepository workerRepository,
        UserService userService,
        FileRepository fileRepository,
        CategoryRepository categoryRepository,
        JobPreferenceRepository jobPreferenceRepositor,
        EducationRepository educationRepository,
        CertificateRepository certificateRepository,
        EmploymentRepository employmentRepository,
        WorkerMapper workerMapper,
        JobPreferenceService jobPreferenceService,
        EducationService educationService,
        EmploymentService employmentService,
        UserMapper userMapper,
        LocationPrefrenceRepository locationPrefrenceRepository,
        PortfolioRepository portfolioRepository
    ) {
        this.workerService = workerService;
        this.workerRepository = workerRepository;
        this.userService = userService;
        this.fileRepository = fileRepository;
        this.categoryRepository = categoryRepository;

        this.educationRepository = educationRepository;
        this.certificateRepository = certificateRepository;
        this.employmentRepository = employmentRepository;
        this.workerMapper = workerMapper;
        this.jobPreferenceService = jobPreferenceService;
        this.educationService = educationService;
        this.employmentService = employmentService;
        this.userMapper = userMapper;
        this.locationPrefrenceRepository = locationPrefrenceRepository;
        this.portfolioRepository = portfolioRepository;
    }

    /**
     * {@code POST  /workers} : Create a new worker.
     *
     * @param workerDTO the workerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workerDTO, or with status {@code 400 (Bad Request)} if the worker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workers")
    public ResponseEntity<WorkerDTO> createWorker(@Valid @RequestBody WorkerDTO workerDTO) throws URISyntaxException {
        log.debug("REST request to save Worker : {}", workerDTO);
        if (workerDTO.getId() != null) {
            throw new BadRequestAlertException("A new worker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        workerDTO.setUser(userMapper.userToUserDTO(userService.getUserWithAuthorities().get()));
        workerDTO.setCreatedBy(userService.getUserWithAuthorities().get().getId() + "");
        workerDTO.setUpdatedBy(userService.getUserWithAuthorities().get().getId() + "");
        workerDTO.setUpdatedAt(LocalDate.now());
        workerDTO.setCreatedAt(LocalDate.now());
        WorkerDTO result = workerService.save(workerDTO);

        ElasticWorker ew = new ElasticWorker();
        Worker arr = workerRepository.findOneWithEagerRelationships(result.getId()).get();
        ew.setId(result.getId().toString());
        ew.setFirstName(arr.getFirstName());
        ew.setMiddleName(arr.getMiddleName());
        ew.setLastName(arr.getLastName());
        ew.setPrimaryPhone(arr.getPrimaryPhone());
        ew.setDescription(arr.getDescription());
        ew.setDateOfBirth(arr.getDateOfBirth());
        ew.setIsActive(arr.getIsActive());
        ew.setSkills(arr.getSkills());
        rabbit_msg.convertAndSend("topicExchange1", "routingKey", ew);

        //        IndexRequest request = new IndexRequest("elasticsearchworkerindex");
        //		request.routing(obj.getCity());
        //		Map<String,Object> source = new HashMap<>();
        //		source.put("name", obj.getName());
        //		source.put("city",obj.getCity());
        //		request.source(source);
        //		IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        //
        //
        //		System.out.println("\n\n\n\n\n\n\n\n\n\n"+indexResponse+"\n\n\n\n\n\n\n\n\n");

        return ResponseEntity
            .created(new URI("/api/workers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workers/:id} : Updates an existing worker.
     *
     * @param id the id of the workerDTO to save.
     * @param workerDTO the workerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workerDTO,
     * or with status {@code 400 (Bad Request)} if the workerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workers/{id}")
    public ResponseEntity<WorkerDTO> updateWorker(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WorkerDTO workerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Worker : {}, {}", id, workerDTO);
        if (workerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        workerDTO.setUpdatedAt(LocalDate.now());
        workerDTO.setUpdatedBy(userService.getUserWithAuthorities().get().getId() + "");
        WorkerDTO result = workerService.save(workerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /workers/:id} : Partial updates given fields of an existing worker, field will ignore if it is null
     *
     * @param id the id of the workerDTO to save.
     * @param workerDTO the workerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workerDTO,
     * or with status {@code 400 (Bad Request)} if the workerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the workerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the workerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/workers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<WorkerDTO> partialUpdateWorker(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WorkerDTO workerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Worker partially : {}, {}", id, workerDTO);
        if (workerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        workerDTO.setUpdatedAt(LocalDate.now());
        workerDTO.setUpdatedBy(userService.getUserWithAuthorities().get().getId() + "");
        Optional<WorkerDTO> result = workerService.partialUpdate(workerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /workers} : get all the workers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workers in body.
     */
    @GetMapping("/workers")
    public ResponseEntity<List<WorkerDTO>> getAllWorkers(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Workers");
        Page<WorkerDTO> page;
        if (eagerload) {
            page = workerService.findAllWithEagerRelationships(pageable);
        } else {
            page = workerService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /workers/:id} : get the "id" worker.
     *
     * @param id the id of the workerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workers/{id}")
    public ResponseEntity<WorkerDTO> getWorker(@PathVariable Long id) {
        log.debug("REST request to get Worker : {}", id);
        Optional<WorkerDTO> workerDTO = workerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workerDTO);
    }

    @GetMapping("/workers/get/{id}")
    public ResponseEntity<Worker> getWorkerByUserId(@PathVariable Long id) {
        System.out.println("\n\n\n\n======>");
        Worker worker = workerRepository.findByUserId(id).get();
        if (worker != null) {
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, worker.getId().toString()))
                .body(worker);
        }
        return ResponseEntity.status(404).body(null);
    }

    @Transactional
    @GetMapping("/workers/profile/{id}")
    public JSONObject getProfile(@PathVariable Long id) {
        log.debug("REST request to get Worker : {}", id);
        JSONObject obj = new JSONObject();
        Worker worker = workerMapper.toEntity(workerService.findOne(id).get());
        obj.put("worker", workerService.findOne(id).get());
        JSONArray job = new JSONArray();
        JSONArray categArray = new JSONArray();
        JSONArray locationprefs = new JSONArray();
        if (jobPreferenceService.findOneWorker(id) != null) {
            for (JobPreference temp : jobPreferenceService.findOneWorker(id)) {
                if (locationPrefrenceRepository.findByJobPreferenceId(temp.getId()) != null) {
                    for (LocationPrefrence x : locationPrefrenceRepository.findByJobPreferenceId(temp.getId())) {
                        locationprefs.add(x);
                    }
                    locationprefs.add(locationPrefrenceRepository.findByJobPreferenceId(temp.getId()));
                }
                job.add(temp);
                categArray.add(temp.getSubCategory());
            }
        }
        obj.put("jobPreference", job);
        obj.put("category", categArray);
        obj.put("locationpreference", locationprefs);
        JSONArray portArray = new JSONArray();
        if (portfolioRepository.findByWorkerId(id) != null) {
            for (Portfolio temp : portfolioRepository.findByWorkerId(id)) {
                portArray.add(temp);
            }
        }
        obj.put("portfolio", portArray);
        JSONArray educaArray = new JSONArray();
        if (educationService.findOneWorker(id) != null) {
            for (Education temp : educationService.findOneWorker(id)) educaArray.add(temp);
        }
        obj.put("Education", educaArray);
        JSONArray EmpArray = new JSONArray();
        if (employmentService.findOneWorker(id) != null) {
            for (Employment temp : employmentService.findOneWorker(id)) EmpArray.add(temp);
        }
        obj.put("Employment", EmpArray);
        return obj;
    }

    /**
     * {@code DELETE  /workers/:id} : delete the "id" worker.
     *
     * @param id the id of the workerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workers/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
        log.debug("REST request to delete Worker : {}", id);
        workerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
