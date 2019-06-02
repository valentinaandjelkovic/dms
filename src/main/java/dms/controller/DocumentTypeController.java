package dms.controller;

import dms.datatables.DocumentTypeDataTablesRepository;
import dms.dto.DocumentTypeDto;
import dms.dto.UserDto;
import dms.dto.formatter.DescriptorTypeDtoFormatter;
import dms.dto.formatter.DocumentTypeDtoFormatter;
import dms.model.DescriptorClass;
import dms.model.DocumentType;
import dms.service.DocumentTypeService;
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
import java.util.ArrayList;

@RequestMapping("document/type")
@Controller
public class DocumentTypeController {

    @Autowired
    private DocumentTypeDtoFormatter dtoFormatter;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private DescriptorTypeDtoFormatter descriptorTypeDtoFormatter;


    @Autowired
    private DocumentTypeDataTablesRepository documentTypeDataTablesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletResponse response) throws IOException {
        return new ModelAndView("document.type.all");
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("document.type.add");
        modelAndView.addObject("mode", WebConstants.MODE_ADD);
        modelAndView.addObject("descriptorClassList", DescriptorClass.values());
        modelAndView.addObject(WebConstants.FORM_DTO, new DocumentTypeDto());
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView("document.type.edit");
        modelAndView.addObject("mode", WebConstants.MODE_EDIT);
        modelAndView.addObject("descriptorClassList", DescriptorClass.values());
        modelAndView.addObject(WebConstants.FORM_DTO, dtoFormatter.formatToDto(documentTypeService.getById(id)));
        return modelAndView;
    }

    @RequestMapping(value = "/descriptors/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getDescriptorsTypes(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        return new ResponseEntity(descriptorTypeDtoFormatter.formatToDto(new ArrayList<>(documentTypeService.getById(id).getDescriptorTypes())), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity save(@Valid @RequestBody DocumentTypeDto documentTypeDto) {
        DocumentType documentType = documentTypeService.save(dtoFormatter.formatToEntity(documentTypeDto));
        if (documentType != null) {
            return new ResponseEntity(dtoFormatter.formatToDto(documentType), HttpStatus.OK);
        }
        return new ResponseEntity("DmsException, document type was not saved", HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteDescriptorType(@RequestParam("id") Long id, @RequestParam("descriptorTypeId") Long descriptorTypeId) {
        System.out.println("Document id: " + id + ", descriptor type id: " + descriptorTypeId);
        return new ResponseEntity(documentTypeService.deleteDescriptorType(id, descriptorTypeId), HttpStatus.OK);
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    @ResponseBody
    public DataTablesOutput<UserDto> search(@Valid @RequestBody DataTablesInput input) {
        DataTablesOutput dataTablesOutput = documentTypeDataTablesRepository.findAll(input);
        dataTablesOutput.setData(dtoFormatter.formatToDto(dataTablesOutput.getData()));
        return dataTablesOutput;
    }
}
