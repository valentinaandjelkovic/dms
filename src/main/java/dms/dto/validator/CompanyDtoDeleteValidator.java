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
public class CompanyDtoDeleteValidator implements DtoValidator<CompanyDto> {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void validate(CompanyDto companyDto) throws Exception {
        List<String> errors = new ArrayList<>();
        Company company = companyRepository.findById(companyDto.getId()).get();

        if (company.getProcessList().size() > 0) {
            errors.add("You can not delete company because there are process assignee to it");
        }

        if (errors.size() > 0) {
            throw new DmsException("Error while deleting company", errors);
        }
    }
}
