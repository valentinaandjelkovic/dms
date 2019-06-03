package dms.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DocumentDto implements EntityDto {
    private Long id;
    private String name;
    private byte[] file;
    private Date date;
    private DocumentTypeDto typeDto;
    private List<DescriptorDto> descriptorDtoList;
    private Long documentTypeId;
    private String fileType;
    private String fileName;
    private Long inputActivityId;
    private Long outputActivityId;

//    private MultipartFile file;

    public DocumentDto(Long id, String name, byte[] file, Date date, DocumentTypeDto typeDto, List<DescriptorDto> descriptorDtoList) {
        this.id = id;
        this.name = name;
        this.file = file;
        this.date = date;
        this.typeDto = typeDto;
        this.descriptorDtoList = descriptorDtoList;
    }

    public DocumentDto() {
        descriptorDtoList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DocumentTypeDto getTypeDto() {
        return typeDto;
    }

    public void setTypeDto(DocumentTypeDto typeDto) {
        this.typeDto = typeDto;
    }

    public List<DescriptorDto> getDescriptorDtoList() {
        return descriptorDtoList;
    }

    public void setDescriptorDtoList(List<DescriptorDto> descriptorDtoList) {
        this.descriptorDtoList = descriptorDtoList;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

//    public MultipartFile getFile() {
//        return file;
//    }
//
//    public void setFile(MultipartFile file) {
//        this.file = file;
//    }


    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentDto)) return false;
        DocumentDto that = (DocumentDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getInputActivityId() {
        return inputActivityId;
    }

    public void setInputActivityId(Long inputActivityId) {
        this.inputActivityId = inputActivityId;
    }

    public Long getOutputActivityId() {
        return outputActivityId;
    }

    public void setOutputActivityId(Long outputActivityId) {
        this.outputActivityId = outputActivityId;
    }
}
