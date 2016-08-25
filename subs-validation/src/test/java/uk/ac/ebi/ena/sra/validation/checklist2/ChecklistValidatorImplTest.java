package uk.ac.ebi.ena.sra.validation.checklist2;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.ebi.embl.api.validation.Origin;
import uk.ac.ebi.embl.api.validation.Severity;
import uk.ac.ebi.embl.api.validation.ValidationMessage;
import uk.ac.ebi.embl.api.validation.ValidationMessageManager;
import uk.ac.ebi.embl.api.validation.ValidationResult;
import uk.ac.ebi.ena.sra.xml.AttributeType;
import uk.ac.ebi.ena.sra.xml.CHECKLISTSETDocument;
import uk.ac.ebi.ena.sra.xml.ChecklistType;
import uk.ac.ebi.ena.sra.xml.SAMPLESETDocument;
import uk.ac.ebi.ena.sra.xml.SampleSetType;
import uk.ac.ebi.ena.sra.xml.SampleType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

public class ChecklistValidatorImplTest {
    public static final String CHECKLIST_XML = "/uk/ac/ebi/ena/sra/validation/checklist/ERCX01.xml";
    public static final String CHECKLIST_VALID_SAMPLE_XML = "/uk/ac/ebi/ena/sra/validation/checklist/ERCX01_valid_sample.xml";
    ChecklistValidator checklistValidator = null;
    ChecklistType checklistType = null;
    static final Logger logger = LoggerFactory.getLogger(ChecklistValidatorFactoryImplTest.class);
    private ValidationResult validationResult;

    @Before
    public void setUp() throws IOException, XmlException {
        ValidationMessageManager.addBundle("uk.ac.ebi.ena.sra.validation.validationMessages");
        validationResult = new ValidationResult();
        final InputStream resourceAsStream = getClass().getResourceAsStream(CHECKLIST_XML);
        final CHECKLISTSETDocument checklistsetDocument = CHECKLISTSETDocument.Factory.parse(resourceAsStream);
        checklistType = checklistsetDocument.getCHECKLISTSET().getCHECKLISTArray(0);
        checklistValidator = new ChecklistValidatorImpl(checklistType);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testValidate() throws Exception {
        XmlOptions xmlOptions = new XmlOptions();
        xmlOptions.setLoadLineNumbers(XmlOptions.LOAD_LINE_NUMBERS);
        final URL resource = getClass().getResource(CHECKLIST_VALID_SAMPLE_XML);
        File sampleFile = new File(resource.toURI());
        final SAMPLESETDocument samplesetDocument = SAMPLESETDocument.Factory.parse(sampleFile, xmlOptions);
//        final InputStream resourceAsStream = getClass().getResourceAsStream(CHECKLIST_VALID_SAMPLE_XML);
        final SampleSetType sampleset = samplesetDocument.getSAMPLESET();
        final SampleType sampleType = sampleset.getSAMPLEArray()[0];
        final SampleType.SAMPLEATTRIBUTES sampleattributes = sampleType.getSAMPLEATTRIBUTES();
        List<Attribute> attributeTypeList = new ArrayList<Attribute>();
        for (AttributeType attributeType : sampleattributes.getSAMPLEATTRIBUTEArray()) {
            Attribute attribute = new AttributeTypeImpl(attributeType);
            attributeTypeList.add(attribute);
        }
        checklistValidator.validate(attributeTypeList, validationResult);
        if (validationResult.count(Severity.ERROR) > 0) {
            logger.info("Errors found validating sample against checklist " + checklistValidator.getId());
            for (ValidationMessage<Origin> validationMessage : validationResult.getMessages()) {
                logger.info(validationMessage.getMessage());
            }
        } else {
            logger.info("No errors found validating sample against checklist " + checklistValidator.getId());
        }
        assertTrue(validationResult.count(Severity.ERROR) == 0);
    }
}
