package dms.controller;

import dms.datatables.CompanyDataTablesRepository;
import dms.dto.CompanyDto;
import dms.dto.formatter.CompanyDtoFormatter;
import dms.model.Company;
import dms.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequestMapping("companies")
@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyDtoFormatter companyDtoFormatter;

    @Autowired
    private CompanyDataTablesRepository companyDataTablesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletResponse response) throws IOException {
        return new ModelAndView("company.all");
    }

    @RequestMapping(value = "/preview/{id}", method = RequestMethod.GET)
    public ModelAndView preview(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView("company.preview");
        modelAndView.addObject("mode", WebConstants.MODE_PREVIEW);
        modelAndView.addObject(WebConstants.FORM_DTO, companyDtoFormatter.formatToDto(companyService.getById(id)));
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView("company.edit");
        modelAndView.addObject("mode", WebConstants.MODE_EDIT);
        modelAndView.addObject(WebConstants.FORM_DTO, companyDtoFormatter.formatToDto(companyService.getById(id)));
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("company.add");
        modelAndView.addObject("mode", WebConstants.MODE_ADD);
        modelAndView.addObject(WebConstants.FORM_DTO, new CompanyDto());
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity save(@RequestBody @Valid CompanyDto companyDto) throws Exception {
        Company company = companyService.save(companyDto);
        return new ResponseEntity(companyDtoFormatter.formatToDto(company), HttpStatus.OK);
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    @ResponseBody
    public DataTablesOutput<CompanyDto> search(@Valid @RequestBody DataTablesInput input) {
        DataTablesOutput dataTablesOutput = companyDataTablesRepository.findAll(input);
        dataTablesOutput.setData(companyDtoFormatter.formatToDto(dataTablesOutput.getData()));
        return dataTablesOutput;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity delete(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        boolean result = companyService.deleteById(id);
        return new ResponseEntity(result ? "Company was successfully delete!" : "You can not delete company, try again!", result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
