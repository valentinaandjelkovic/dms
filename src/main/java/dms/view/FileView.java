package dms.view;


import dms.model.Document;
import dms.service.DocumentService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

@Component("fileView")
public class FileView extends AbstractView {

    @Autowired
    private DocumentService documentService;

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Document document = documentService.getById((Long) map.get("id"));
        File file = new File(document.getFileName());
        FileUtils.writeByteArrayToFile(file, document.getFile());
        httpServletResponse.setHeader("Content-Type", document.getFileType());
        httpServletResponse.setContentLength((int) file.length());
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        FileCopyUtils.copy(new FileInputStream(file), httpServletResponse.getOutputStream());
    }
}
