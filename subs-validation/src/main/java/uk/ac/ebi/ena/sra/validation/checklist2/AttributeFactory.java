package uk.ac.ebi.ena.sra.validation.checklist2;

import java.util.List;

public interface AttributeFactory {
    public List<Attribute> getAttributeList();

    public int getAttributeSize();
}
