package dms.controller;

import dms.datatables.UserDataTablesRepository;
import dms.dto.UserDto;
import dms.dto.formatter.CompanyDtoFormatter;
import dms.dto.formatter.RoleDtoFormatter;
import dms.dto.formatter.UserDtoFormatter;
import dms.exception.ResourceNotFoundException;
import dms.model.User;
import dms.service.CompanyService;
import dms.service.RoleService;
import dms.service.UserService;
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

@RequestMapping("users")
@Controller
public class UserController {


    private UserService userService;
    @Autowired
    private UserDataTablesRepository userDataTablesRepository;

    @Autowired
    private UserDtoFormatter userDtoFormatter;
    @Autowired
    private RoleDtoFormatter roleDtoFormatter;

    @Autowired
    private CompanyDtoFormatter companyDtoFormatter;

    @Autowired
    private RoleService roleService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("user.all");
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() throws ResourceNotFoundException {
        return setData("user.add", WebConstants.MODE_ADD, null);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity save(@Valid @RequestBody UserDto userDto) throws Exception {
        User user = userService.save(userDto);
        if (user != null) {
            return new ResponseEntity(userDtoFormatter.formatToDto(user), HttpStatus.OK);
        }
        return new ResponseEntity("Error while saving user, try again later!", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Long id) throws ResourceNotFoundException {
        return setData("user.edit", WebConstants.MODE_EDIT, id);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity delete(@PathVariable Long id) throws ResourceNotFoundException {
        boolean result = userService.deleteById(id);
        return new ResponseEntity(result ? "User was successfully delete!" : "You can not delete user, try again!", result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    @ResponseBody
    public DataTablesOutput<UserDto> search(@Valid @RequestBody DataTablesInput input) {
        DataTablesOutput dataTablesOutput = userDataTablesRepository.findAll(input);
        dataTablesOutput.setData(userDtoFormatter.formatToDto(dataTablesOutput.getData()));
        return dataTablesOutput;
    }

    @RequestMapping(value = "/preview/{id}", method = RequestMethod.GET)
    public ModelAndView add(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        return setData("user.preview", WebConstants.MODE_PREVIEW, id);
    }

    private ModelAndView setData(String view, int mode, Long id) throws ResourceNotFoundException {
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("mode", mode);
        modelAndView.addObject("roleList", roleDtoFormatter.formatToDto(roleService.getAll()));
        modelAndView.addObject("companyList", companyDtoFormatter.formatToDto(companyService.getAll()));
        modelAndView.addObject(WebConstants.FORM_DTO, id != null ? userDtoFormatter.formatToDto(userService.getById(id)) : new UserDto());
        return modelAndView;
    }

}
