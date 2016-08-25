package uk.ac.ebi.subs.validation.checklist.field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.ebi.ena.sra.xml.ChecklistType;
import uk.ac.ebi.ena.taxonomy.client.TaxonomyClient;
import uk.ac.ebi.ena.taxonomy.client.TaxonomyClientCurrentImpl;
import uk.ac.ebi.ena.taxonomy.client.TaxonomyException;
import uk.ac.ebi.ena.taxonomy.client.model.Taxon;
import uk.ac.ebi.subs.validation.ValidationException;
import uk.ac.ebi.subs.validation.ValidationMessage;
import uk.ac.ebi.subs.validation.ValidationResult;
import uk.ac.ebi.subs.validation.checklist.AbstractAttributeValidator;
import uk.ac.ebi.subs.validation.checklist.Attribute;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TaxonFieldValidator extends AbstractAttributeValidator {
    public static final String TAXONOMY_SERVICE_PROPERTIES_FILE_NAME = "/sra-services.properties";
    public static final String TAXONOMY_SERVICE_URL_PROPERTY_NAME = "taxonServiceUrl";
    //    public static final String DEFAULT_TAXONOMY_SERVICE_URL = "http://ves-hx-d0:8080/ena/data/taxonomy";
    public static final String DEFAULT_TAXONOMY_SERVICE_URL = "http://www.ebi.ac.uk/ena/data/taxonomy";
    static Logger logger = LoggerFactory.getLogger(TaxonFieldValidator.class);
    private static TaxonomyClient taxonomyClient = null;

    static {
        String taxonServiceURL = DEFAULT_TAXONOMY_SERVICE_URL;
        InputStream inputStream = TaxonFieldValidator.class.getResourceAsStream(TAXONOMY_SERVICE_PROPERTIES_FILE_NAME);
        if (inputStream != null) {
            Properties properties = new Properties();
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                logger.error("Failed to parse " + TAXONOMY_SERVICE_PROPERTIES_FILE_NAME, e);
            }
            if (properties.getProperty(TAXONOMY_SERVICE_URL_PROPERTY_NAME) != null) {
                taxonServiceURL = properties.getProperty(TAXONOMY_SERVICE_URL_PROPERTY_NAME);
            }
        }
        taxonomyClient = new TaxonomyClientCurrentImpl(taxonServiceURL);
    }

    // concurent hap map for taxon look ups
//    Map<Integer,>
    private Map<Long, Taxon> taxonMap = new HashMap<Long, Taxon>();
    private Map<String, Taxon> taxonScientificNameMap = new HashMap<String, Taxon>();
    private ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.FIELDTYPE.TAXONFIELD.RestrictionType.Enum restrictionType = null;
    private String[] taxons = new String[0];
    private Map<Long, Taxon> permittedTaxonMap = new HashMap<Long, Taxon>();
    private Map<Long, Taxon> notPermittedTaxonMap = new HashMap<Long, Taxon>();

    public TaxonFieldValidator(String id, ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.FIELDTYPE.TAXONFIELD.RestrictionType.Enum restrictionType, String[] taxons) {
        super(id);
        this.restrictionType = restrictionType;
        for (String taxIdString : taxons) {
            for (Taxon taxon : getTaxon(taxIdString)) {
                if (restrictionType.equals(ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.FIELDTYPE.TAXONFIELD.RestrictionType.PERMITTED_TAXA)) {
                    permittedTaxonMap.put(taxon.getTaxId(), taxon);
                } else if (restrictionType.equals(ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.FIELDTYPE.TAXONFIELD.RestrictionType.NOT_PERMITTED_TAXA)) {
                    notPermittedTaxonMap.put(taxon.getTaxId(), taxon);
                }
            }
        }
    }

    public TaxonFieldValidator(String id) {
        super(id);
    }

    private List<Taxon> getTaxon(String taxonString) throws TaxonomyException {
        Long taxonId = null;
        Taxon taxon = null;
        List<Taxon> taxonList = new ArrayList<Taxon>();

        try {

            taxonId = Long.parseLong(taxonString);
        } catch (NumberFormatException nfe) {
        }

        if (taxonId != null) {
            taxon = taxonomyClient.getTaxonById(taxonId);
            taxonList = new ArrayList<Taxon>();
            taxonList.add(taxon);
        } else {
            return taxonomyClient.suggestTaxa(taxonString);
        }

        return taxonList;
    }

    @Override
    public boolean validate(Attribute attribute, ValidationResult validationResult) throws ValidationException {
        boolean validationStatus = true;
        if (attribute.getTagValue() != null) {
            List<Taxon> taxonList = new ArrayList<Taxon>();
            try {
                taxonList = getTaxon(attribute.getTagValue());
                if (taxonList.isEmpty()) {
                    validationStatus = false;
                    validationResult.append(ValidationMessage.error("ERAM.2.1.13", attribute.getTagValuePosition(), attribute.getTagValue()));
                }
            } catch (TaxonomyException te) {
                validationStatus = false;
                validationResult.append(ValidationMessage.error("ERAM.2.1.13", attribute.getTagValuePosition(), attribute.getTagValue()));
            }
        }

        if (ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.FIELDTYPE.TAXONFIELD.RestrictionType.NOT_PERMITTED_TAXA.equals(restrictionType)) {
            if (taxonMap.get(attribute.getTagValue()) != null) {
                // new taxon validation error taxon is not permitted
            }

        } else if (ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.FIELDTYPE.TAXONFIELD.RestrictionType.PERMITTED_TAXA.equals(restrictionType)) {

        }


        return validationStatus;
    }
}
