package dms.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "DESCRIPTOR")
public class Descriptor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "INTEGER_VALUE")
    private Integer integerValue;

    @Column(name = "DOUBLE_VALUE")
    private Double doubleValue;

    @Column(name = "BOOLEAN_VALUE")
    private boolean booleanValue;

    @Column(name = "STRING_VALUE")
    private String stringValue;

    @Column(name = "DATE_VALUE")
    private Date dateValue;

    @ManyToOne
    @JoinColumn(name = "DESCRIPTOR_TYPE_ID")
    private DescriptorType type;

    public Descriptor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public DescriptorType getType() {
        return type;
    }

    public void setType(DescriptorType type) {
        this.type = type;
    }

    public Object getValue() {
        DescriptorClass descriptorClass = type.getType();

        switch (descriptorClass) {
            case Date:
                return getDateValue();
            case Text:
                return getStringValue();
            case Numeric:
                return getIntegerValue();
            case Decimal:
                return getDoubleValue();
            case Boolean:
                return isBooleanValue();
        }
        return null;
    }

    public void setValue(Object value) {
        System.out.println("\n\n\n\n******** VALUE ********" + value);

        DescriptorClass descriptorClass = type.getType();

        if (value == null || value.equals("")) {
            integerValue = null;
            doubleValue = null;
            stringValue = null;
            dateValue = null;
            booleanValue = false;
        } else {

            switch (descriptorClass) {
                case Numeric:
                    integerValue = (Integer) Integer.parseInt(value.toString());
                case Decimal:
                    doubleValue = Double.parseDouble(value.toString());
                case Text:
                    stringValue = value.toString();
                case Boolean:
                    booleanValue = Boolean.parseBoolean(value.toString());
                case Date:
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        dateValue = sdf.parse(value.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
            }

        }
    }

}
