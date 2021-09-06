package com.simplify.marketplace.web.rest;

import com.simplify.marketplace.domain.ElasticWorker;
import com.simplify.marketplace.domain.File;
import com.simplify.marketplace.repository.ESearchWorkerRepository;
import com.simplify.marketplace.repository.FileRepository;
import com.simplify.marketplace.service.FileService;
import com.simplify.marketplace.service.UserService;
import com.simplify.marketplace.service.dto.FileDTO;
import com.simplify.marketplace.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.simplify.marketplace.domain.File}.
 */
@RestController
@RequestMapping("/api")
public class FileResource {

    private UserService userService;

    @Autowired
    ESearchWorkerRepository rep1;

    @Autowired
    RabbitTemplate rabbit_msg;

    private final Logger log = LoggerFactory.getLogger(FileResource.class);

    private static final String ENTITY_NAME = "file";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileService fileService;

    private final FileRepository fileRepository;

    public FileResource(FileService fileService, FileRepository fileRepository, UserService userService) {
        this.fileService = fileService;
        this.fileRepository = fileRepository;
        this.userService = userService;
    }

    /**
     * {@code POST  /files} : Create a new file.
     *
     * @param fileDTO the fileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileDTO, or with status {@code 400 (Bad Request)} if the file has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/files")
    public ResponseEntity<FileDTO> createFile(@RequestBody FileDTO fileDTO) throws URISyntaxException {
        log.debug("REST request to save File : {}", fileDTO);
        if (fileDTO.getId() != null) {
            throw new BadRequestAlertException("A new file cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // fileDTO.setCreatedBy(userService.getUserWithAuthorities().get().getId() + "");
        // fileDTO.setUpdatedBy(userService.getUserWithAuthorities().get().getId() + "");
        fileDTO.setUpdatedAt(LocalDate.now());
        fileDTO.setCreatedAt(LocalDate.now());

        FileDTO result = fileService.save(fileDTO);
        String Workerid = fileDTO.getWorker().getId().toString();
        ElasticWorker e = rep1.findById(Workerid).get();
        Set<File> files = fileService.insertElasticSearch(result);
        e.setFiles(files);

        rabbit_msg.convertAndSend("topicExchange1", "routingKey", e);
        return ResponseEntity
            .created(new URI("/api/files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /files/:id} : Updates an existing file.
     *
     * @param id the id of the fileDTO to save.
     * @param fileDTO the fileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileDTO,
     * or with status {@code 400 (Bad Request)} if the fileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/files/{id}")
    public ResponseEntity<FileDTO> updateFile(@PathVariable(value = "id", required = false) final Long id, @RequestBody FileDTO fileDTO)
        throws URISyntaxException {
        log.debug("REST request to update File : {}, {}", id, fileDTO);
        if (fileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        // fileDTO.setUpdatedBy(userService.getUserWithAuthorities().get().getId() + "");
        fileDTO.setUpdatedAt(LocalDate.now());

        FileDTO result = fileService.save(fileDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /files/:id} : Partial updates given fields of an existing file, field will ignore if it is null
     *
     * @param id the id of the fileDTO to save.
     * @param fileDTO the fileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileDTO,
     * or with status {@code 400 (Bad Request)} if the fileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/files/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FileDTO> partialUpdateFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FileDTO fileDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update File partially : {}, {}", id, fileDTO);
        if (fileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        // fileDTO.setUpdatedBy(userService.getUserWithAuthorities().get().getId() + "");
        fileDTO.setUpdatedAt(LocalDate.now());
        Optional<FileDTO> result = fileService.partialUpdate(fileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /files} : get all the files.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of files in body.
     */
    @GetMapping("/files")
    public ResponseEntity<List<FileDTO>> getAllFiles(Pageable pageable) {
        log.debug("REST request to get a page of Files");
        Page<FileDTO> page = fileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/files/worker/{workerid}")
    public List<File> getworkerFile(@PathVariable Long workerid) {
        log.debug("REST request to get Certificate : {}", workerid);
        List<File> files = fileService.findOneWorker(workerid);
        return files;
    }

    /**
     * {@code GET  /files/:id} : get the "id" file.
     *
     * @param id the id of the fileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/files/{id}")
    public ResponseEntity<FileDTO> getFile(@PathVariable Long id) {
        log.debug("REST request to get File : {}", id);
        Optional<FileDTO> fileDTO = fileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileDTO);
    }

    /**
     * {@code DELETE  /files/:id} : delete the "id" file.
     *
     * @param id the id of the fileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/files/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        log.debug("REST request to delete File : {}", id);
        fileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
