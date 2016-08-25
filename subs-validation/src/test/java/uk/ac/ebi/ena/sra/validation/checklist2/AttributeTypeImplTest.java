package uk.ac.ebi.ena.sra.validation.checklist2;

import org.apache.xmlbeans.XmlOptions;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.ena.sra.xml.AttributeType;
import uk.ac.ebi.ena.sra.xml.SAMPLESETDocument;
import uk.ac.ebi.ena.sra.xml.SampleSetType;
import uk.ac.ebi.ena.sra.xml.SampleType;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AttributeTypeImplTest {
    AttributeType attributeType = null;
    public static final String CHECKLIST_VALID_SAMPLE_XML = "/uk/ac/ebi/ena/sra/validation/checklist/ERCX01_valid_sample.xml";
    SampleType.SAMPLEATTRIBUTES sampleattributes = null;
    List<Attribute> attributeTypeList = new ArrayList<Attribute>();
    public static final String SAMPLE_XML_STRING = "<?xml version = '1.0' encoding = 'UTF-8'?>\n" +
            "<SAMPLE_SET>\n" +
            "    <SAMPLE>\n" +
            "        <IDENTIFIERS>\n" +
            "            <SUBMITTER_ID namespace=\"EBI\">ERCX01_Valid_Sample</SUBMITTER_ID>\n" +
            "        </IDENTIFIERS>\n" +
            "        <TITLE>ERCX01_Valid_Sample</TITLE>\n" +
            "        <SAMPLE_NAME>\n" +
            "            <TAXON_ID>9606</TAXON_ID>\n" +
            "            <SCIENTIFIC_NAME>Homo Sapiens</SCIENTIFIC_NAME>\n" +
            "        </SAMPLE_NAME>\n" +
            "        <SAMPLE_ATTRIBUTES>\n" +
            "            <SAMPLE_ATTRIBUTE>\n" +
            "                <TAG>Text field</TAG>\n" +
            "                <VALUE>blah</VALUE>\n" +
            "            </SAMPLE_ATTRIBUTE>\n" +
            "            <SAMPLE_ATTRIBUTE>\n" +
            "                <TAG>Date field</TAG>\n" +
            "                <VALUE>2015-11-04T09:20:22+00:00</VALUE>\n" +
            "            </SAMPLE_ATTRIBUTE>\n" +
            "            <SAMPLE_ATTRIBUTE>\n" +
            "                <TAG>1.83</TAG>\n" +
            "                <VALUE>Units field</VALUE>\n" +
            "                <UNITS>m</UNITS>\n" +
            "            </SAMPLE_ATTRIBUTE>\n" +
            "        </SAMPLE_ATTRIBUTES>\n" +
            "    </SAMPLE>\n" +
            "</SAMPLE_SET>";

    @Before
    public void setup() throws Exception {
        attributeType = AttributeType.Factory.newInstance();
        XmlOptions xmlOptions = new XmlOptions();
        xmlOptions.setLoadLineNumbers(XmlOptions.LOAD_LINE_NUMBERS);
        StringReader stringReader = new StringReader(SAMPLE_XML_STRING);
        final SAMPLESETDocument samplesetDocument = SAMPLESETDocument.Factory.parse(stringReader, xmlOptions);
        final SampleSetType sampleset = samplesetDocument.getSAMPLESET();
        final SampleType sampleType = sampleset.getSAMPLEArray()[0];
        final SampleType.SAMPLEATTRIBUTES sampleattributes = sampleType.getSAMPLEATTRIBUTES();
        attributeTypeList = new ArrayList<Attribute>();
        for (AttributeType attributeType : sampleattributes.getSAMPLEATTRIBUTEArray()) {
            Attribute attribute = new AttributeTypeImpl(attributeType);
            attributeTypeList.add(attribute);
        }
    }

    @Test
    public void testLineNumbers() {
        assertTrue(attributeTypeList.size() > 0);
        for (Attribute attribute : attributeTypeList) {
            AttributeTypeImpl attributeType1 = (AttributeTypeImpl) attribute;
            assertNotNull(attributeType1.getTagElementLineNumber());
            assertNotNull(attributeType1.getValueElementLineNumber());
        }

    }
}