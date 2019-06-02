package dms.service;

import dms.dto.ProcessDto;
import dms.model.Process;

import java.util.List;

public interface ProcessService {
    List<Process> getAll();

    Process save(ProcessDto processDto) throws Exception;

    boolean deleteById(Long id) throws Exception;

    Process getById(Long id);

    List<Process> findPrimitive(boolean primitive);
}
