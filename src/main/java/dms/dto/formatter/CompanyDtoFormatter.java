package dms.dto.formatter;

import dms.dto.CompanyDto;
import dms.model.Company;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyDtoFormatter implements DtoFormatter<Company, CompanyDto> {

    @Override
    public CompanyDto formatToDto(Company company) {
        if (company == null)
            return null;
        return new CompanyDto(company.getId(), company.getName(), company.getCompanyNumber());
    }

    @Override
    public List<CompanyDto> formatToDto(List<Company> companies) {
        List<CompanyDto> companyDtoList = new ArrayList<>();
        for (Company company : companies) {
            companyDtoList.add(formatToDto(company));
        }
        return companyDtoList;
    }

    @Override
    public Company formatToEntity(CompanyDto companyDto) {
        if (companyDto == null) {
            return null;
        }
        return new Company(companyDto.getId(), companyDto.getName(), companyDto.getCompanyNumber());
    }

    @Override
    public List<Company> formatToEntity(List<CompanyDto> companyDtos) {
        return null;
    }
}
