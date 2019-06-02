package dms.controller;

import dms.config.WebConfigiuration;
import dms.dto.CompanyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfigiuration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CompanyControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/companies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("company.all"));
    }

    @Test
    public void testAdd() throws Exception {
        mockMvc.perform(get("/companies/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("company.add"))
                .andExpect(model().attributeExists("formDto"));
    }

    @Test
    public void testPreviewWhenCompanyExists() throws Exception {
        mockMvc.perform(post("/companies/save")
                .content(asJsonString(new CompanyDto(null, "Company name test", "123456789")))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/companies/preview/{id}", 1l))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("company.preview"));
    }

    @Test
    public void testPreviewWhenCompanyNotExists() throws Exception {
        mockMvc.perform(get("/companies/preview/{id}", 1l))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("error.404"))
                .andExpect(model().attribute("message", "Company not found"));
    }


    @Test
    public void testEditWhenCompanyExists() throws Exception {
        mockMvc.perform(post("/companies/save")
                .content(asJsonString(new CompanyDto(null, "Company name test", "123456789")))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/companies/edit/{id}", 1l))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("company.edit"))
                .andExpect(model().attributeExists("formDto"));
    }

    @Test
    public void testEditWhenCompanyNotExists() throws Exception {
        mockMvc.perform(get("/companies/edit/{id}", 1l))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("error.404"))
                .andExpect(model().attribute("message", "Company not found"));
    }

    @Test
    public void testSave() throws Exception {

        mockMvc.perform(post("/companies/save")
                .content(asJsonString(new CompanyDto(null, "Company name test", "123456789")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Company name test"));
    }

    @Test
    public void testSaveWhenCompanyExists() throws Exception {
        CompanyDto companyDto = new CompanyDto(null, "Company test", "123456787");
        mockMvc.perform(post("/companies/save")
                .content(asJsonString(companyDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        companyDto.setId(1l);
        companyDto.setName("Company test 2");

        mockMvc.perform(post("/companies/save").
                content(asJsonString(companyDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Company test 2"));
    }

    @Test
    public void testSaveWhenNameIsNotUnique() throws Exception {
        CompanyDto companyDto = new CompanyDto(null, "Company test", "123456789");
        mockMvc.perform(post("/companies/save")
                .content(asJsonString(companyDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        companyDto = new CompanyDto(null, "Company test", "123456788");

        mockMvc.perform(post("/companies/save").
                content(asJsonString(companyDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error while saving company"))
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors", hasSize(1)));
    }

    @Test
    public void testSaveWhenCompanyNumberIsNotUnique() throws Exception {
        CompanyDto companyDto = new CompanyDto(null, "Company test", "123456789");
        mockMvc.perform(post("/companies/save")
                .content(asJsonString(companyDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        companyDto = new CompanyDto(null, "Company test 2", "123456789");

        mockMvc.perform(post("/companies/save").
                content(asJsonString(companyDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error while saving company"))
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors[0]").value("Company number must be unique"));
    }

    @Test
    public void testSaveWhenNameAndCompanyNumberIsEmpty() throws Exception {
        CompanyDto companyDto = new CompanyDto(null, null, null);
        mockMvc.perform(post("/companies/save")
                .content(asJsonString(companyDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error while saving company"))
                .andExpect(jsonPath("$.errors", hasSize(2)));

    }

    @Test
    public void testDeleteWhenCompanyExists() throws Exception {
        mockMvc.perform(post("/companies/save")
                .content(asJsonString(new CompanyDto(null, "Company name test", "123456789")))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/companies/delete/{id}", 1l))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void testDeleteWhenCompanyDoesNotExist() throws Exception {
        mockMvc.perform(get("/companies/delete/{id}", 1l))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}