package dms.controller;

import dms.dto.ActivityDto;
import dms.dto.DocumentDto;
import dms.dto.formatter.ActivityDtoFormatter;
import dms.dto.formatter.DocumentDtoFormatter;
import dms.dto.formatter.DocumentTypeDtoFormatter;
import dms.service.ActivityService;
import dms.service.DocumentService;
import dms.service.DocumentTypeService;
import dms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.List;

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
    private ActivityService activityService;

    @Autowired
    private ActivityDtoFormatter activityDtoFormatter;

    @Autowired
    private DocumentTypeDtoFormatter documentTypeDtoFormatter;

    @Autowired
    private UserService userService;

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
        List<ActivityDto> activityDtoList = activityDtoFormatter.formatToDto(activityService.getByCompany(getCompanyId()));
        modelAndView.addObject("inputActivityList", activityDtoList);
        modelAndView.addObject("outputActivityList", activityDtoList);
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

    private Long getCompanyId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            return userService.getByUsername(username).getCompany().getId();
        }
        return null;
    }


}
