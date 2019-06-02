package dms.service.impl;

import dms.dto.ProcessDto;
import dms.dto.formatter.ProcessDtoFormatter;
import dms.dto.validator.ProcessDtoDeleteValidator;
import dms.dto.validator.ProcessDtoSaveValidator;
import dms.model.Process;
import dms.repository.ProcessRepository;
import dms.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProcessServiceImpl implements ProcessService {


    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ProcessDtoSaveValidator processDtoSaveValidator;

    @Autowired
    private ProcessDtoDeleteValidator processDtoDeleteValidator;

    @Autowired
    private ProcessDtoFormatter processDtoFormatter;

    @Override
    public List<Process> getAll() {
        return processRepository.findAll();
    }

    @Override
    public Process save(ProcessDto processDto) throws Exception {
        processDtoSaveValidator.validate(processDto);
        return processRepository.save(processDtoFormatter.formatToEntity(processDto));
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        System.out.println("Usaoo u poziv com.service " + id);
        ProcessDto processDto = new ProcessDto();
        processDto.setId(id);
        System.out.println("Pre validacije");
        processDtoDeleteValidator.validate(processDto);
        System.out.println("Posle validacije");

        try {
            System.out.println("Brisanjeee ");
            processRepository.delete(new Process(id));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Process getById(Long id) {
        return processRepository.findById(id).get();
    }

    @Override
    public List<Process> findPrimitive(boolean primitive) {
        return processRepository.findByPrimitive(true);
    }
}
