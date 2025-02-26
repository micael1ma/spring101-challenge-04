package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SummaryDTO> findSummary(String minDate, String maxDate) {

		LocalDate pastDate;
		if (minDate.isEmpty()) {
			pastDate = LocalDate.now().minusYears(1);
		}else{
			pastDate = LocalDate.parse(minDate);
		}

		LocalDate presentDate;
		if (maxDate.isEmpty()) {
			presentDate = LocalDate.now();
		}else{
			presentDate = LocalDate.parse(maxDate);
		}

		List<SummaryProjection> projection = repository.searchSummary(pastDate, presentDate);
		List<SummaryDTO> dto = projection.stream().map(x -> new SummaryDTO(x)).collect(Collectors.toList());
		return dto;
	}
}
