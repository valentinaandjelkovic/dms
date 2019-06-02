package dms.service;

import dms.dto.CompanyDto;
import dms.exception.ResourceNotFoundException;
import dms.model.Company;

import java.util.List;

public interface CompanyService {

    public List<Company> getAll();

    public Company getById(Long id) throws ResourceNotFoundException;

    public Company save(CompanyDto companyDto) throws Exception;

    public boolean deleteById(Long id) throws ResourceNotFoundException;
}
