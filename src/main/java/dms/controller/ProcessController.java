package dms.controller;

import dms.dto.ProcessDto;
import dms.dto.formatter.ProcessDtoFormatter;
import dms.model.User;
import dms.service.ProcessService;
import dms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("process")
@Controller
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private ProcessDtoFormatter dtoFormatter;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView list(HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("process.all");
        modelAndView.addObject("primitiveProcessList", dtoFormatter.formatToDto(processService.findPrimitive(true)));
        modelAndView.addObject("processList", dtoFormatter.formatToDto(processService.getAll()));
        return modelAndView;
    }

    @RequestMapping(value = "/fetch", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity fetch(HttpServletResponse response) throws Exception {
        return new ResponseEntity(dtoFormatter.formatToDto(processService.getAll()), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity save(@RequestBody @Valid ProcessDto processDto) throws Exception {

        if (processDto.getId() != null) {
            ProcessDto dto = dtoFormatter.formatToDto(processService.getById(processDto.getId()));
            dto.setName(processDto.getName());
            System.out.println("Process by id:" + dto.getId());
            processDto = dtoFormatter.formatToDto(processService.save(dto));
        } else {
            User user = getUser();
            processDto.setCompanyId(user.getCompany().getId());
            processDto = dtoFormatter.formatToDto(processService.save(processDto));
        }
        return new ResponseEntity(processDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity delete(@PathVariable("id") Long id) throws Exception {
        boolean result = processService.deleteById(id);
        return new ResponseEntity(result ? "Process was successfully delete!" : "You can not delete process, try again!", result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity fetch(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity(dtoFormatter.formatToDto(processService.getById(id)), HttpStatus.OK);
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            System.out.println("Username " + username);
            return userService.getByUsername(username);
        }
        return null;
    }

}
