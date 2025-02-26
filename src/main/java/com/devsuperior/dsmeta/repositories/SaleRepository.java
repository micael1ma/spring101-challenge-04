package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.projections.ReportProjection;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = "SELECT " +
            "tb_sales.id, " +
            "tb_sales.date, " +
            "tb_sales.amount, " +
            "tb_seller.name " +
            "FROM tb_seller " +
            "INNER JOIN tb_sales ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN '2022-05-01' AND '2022-05-31' " +
            "AND UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', 'Odinson', '%'))")
    Page<ReportProjection> serachReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);


    @Query(nativeQuery = true, value = "SELECT tb_seller.name, SUM(tb_sales.amount) AS sum " +
            "FROM tb_seller " +
            "INNER JOIN tb_sales ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY tb_seller.name;")
    List<SummaryProjection> searchSummary(LocalDate minDate, LocalDate maxDate);

}
