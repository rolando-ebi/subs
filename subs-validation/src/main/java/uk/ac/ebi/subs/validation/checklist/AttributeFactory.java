package uk.ac.ebi.subs.validation.checklist;

import java.util.List;

public interface AttributeFactory {
    public List<Attribute> getAttributeList();

    public int getAttributeSize();
}
