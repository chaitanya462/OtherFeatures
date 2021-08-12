package com.simplify.marketplace.service;

import com.simplify.marketplace.service.dto.PortfolioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.simplify.marketplace.domain.Portfolio}.
 */
public interface PortfolioService {
    /**
     * Save a portfolio.
     *
     * @param portfolioDTO the entity to save.
     * @return the persisted entity.
     */
    PortfolioDTO save(PortfolioDTO portfolioDTO);

    /**
     * Partially updates a portfolio.
     *
     * @param portfolioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PortfolioDTO> partialUpdate(PortfolioDTO portfolioDTO);

    /**
     * Get all the portfolios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PortfolioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" portfolio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PortfolioDTO> findOne(Long id);

    /**
     * Delete the "id" portfolio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
