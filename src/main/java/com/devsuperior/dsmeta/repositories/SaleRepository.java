package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.projections.SummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = "SELECT tb_seller.name, SUM(tb_sales.amount) AS sum " +
            "FROM tb_seller " +
            "INNER JOIN tb_sales ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY tb_seller.name;")
    List<SummaryProjection> searchSummary(LocalDate minDate, LocalDate maxDate);

}
