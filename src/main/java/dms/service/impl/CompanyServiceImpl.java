package dms.service.impl;

import dms.dto.CompanyDto;
import dms.dto.formatter.CompanyDtoFormatter;
import dms.dto.validator.CompanyDtoSaveValidator;
import dms.exception.ResourceNotFoundException;
import dms.model.Company;
import dms.repository.CompanyRepository;
import dms.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyDtoSaveValidator saveValidator;

    @Autowired
    private CompanyDtoFormatter formatter;

    @Override
    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company getById(Long id) throws ResourceNotFoundException {
        Optional<Company> company = companyRepository.findById(id);
        if (!company.isPresent()) throw new ResourceNotFoundException("Company not found");
        return company.get();
    }

    @Override
    public Company save(CompanyDto companyDto) throws Exception {
        saveValidator.validate(companyDto);
        return companyRepository.save(formatter.formatToEntity(companyDto));
    }

    @Override
    public boolean deleteById(Long id) throws ResourceNotFoundException {
        Optional<Company> company = companyRepository.findById(id);
        if (!company.isPresent()) throw new ResourceNotFoundException("Company not found");
        try {
            companyRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
