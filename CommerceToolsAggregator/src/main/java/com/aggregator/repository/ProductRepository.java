package com.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aggregator.model.Product;
/**
 * Repo to interact with h2 db
 * @author nakaushik
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
