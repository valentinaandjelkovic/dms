package dms.dto.validator;

import dms.dto.CompanyDto;
import dms.exception.DmsException;
import dms.model.Company;
import dms.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyDtoSaveValidator implements DtoValidator<CompanyDto> {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void validate(CompanyDto companyDto) throws Exception {
        List<String> errors = new ArrayList<>();

        if(companyDto.getName()==null || companyDto.getName().trim().isEmpty()){
            errors.add("Company name is required field");
        }
        if(companyDto.getCompanyNumber()==null || companyDto.getCompanyNumber().trim().isEmpty()){
            errors.add("Company number is required field");
        }
        if (!isUniqueName(companyDto)) {
            errors.add("Company name must be unique");
        }
        if (!isUniqueCompanyNumber(companyDto)) {
            errors.add("Company number must be unique");
        }
        if (errors.size() > 0) {
            throw new DmsException("Error while saving company", errors);
        }
    }

    private boolean isUniqueName(CompanyDto companyDto) {
        List<Company> companies = companyRepository.findByName(companyDto.getName());
        if (companies.size() == 0 || (companies.size() == 1 && companyDto.getId() != null && companyDto.getId().equals(companies.get(0).getId()))) {
            return true;
        }
        return false;
    }

    private boolean isUniqueCompanyNumber(CompanyDto companyDto) {
        List<Company> companies = companyRepository.findByCompanyNumber(companyDto.getCompanyNumber());
        if (companies.size() == 0 || (companies.size() == 1 && companyDto.getId() != null && companyDto.getId().equals(companies.get(0).getId()))) {
            return true;
        }
        return false;
    }
}
