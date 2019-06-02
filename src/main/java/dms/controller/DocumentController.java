package dms.controller;

import dms.dto.DocumentDto;
import dms.dto.formatter.DocumentDtoFormatter;
import dms.dto.formatter.DocumentTypeDtoFormatter;
import dms.service.DocumentService;
import dms.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RequestMapping("document")
@Controller
public class DocumentController {


    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentDtoFormatter documentDtoFormatter;

    @Autowired
    private DocumentTypeDtoFormatter documentTypeDtoFormatter;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView list(HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("document.all");
        modelAndView.addObject("documentList", documentDtoFormatter.formatToDto(documentService.getAll()));
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("document.add");
        modelAndView.addObject("mode", WebConstants.MODE_ADD);
        modelAndView.addObject("documentTypeList", documentTypeDtoFormatter.formatToDto(documentTypeService.getAll()));
        modelAndView.addObject(WebConstants.FORM_DTO, new DocumentDto());
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    @ResponseBody
    public ResponseEntity save(@RequestPart("documentDto") @Valid DocumentDto documentDto,
                               @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file) throws Exception {
        documentDto.setFile(file.getBytes());
        documentDto.setFileType(file.getContentType());
        documentDto.setFileName(file.getOriginalFilename());
        return new ResponseEntity(documentDtoFormatter.formatToDto(documentService.save(documentDto)), HttpStatus.OK);
    }

    @RequestMapping(path = "/download/{id}", method = RequestMethod.GET)
    public String getFile(Model model, @PathVariable("id") Long id) throws IOException {
        model.addAttribute("id", id);
        return "fileView";
    }


}
