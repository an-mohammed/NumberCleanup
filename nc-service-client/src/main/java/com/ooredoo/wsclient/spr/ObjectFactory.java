
package com.ooredoo.wsclient.spr;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ooredoo.wsclient.spr package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InputParametersBILLCYCLECD_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "BILL_CYCLE_CD");
    private final static QName _InputParametersTOTALFREECOUNTER_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "TOTAL_FREE_COUNTER");
    private final static QName _InputParametersDIGITALSUBSCRIBER_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "DIGITAL_SUBSCRIBER");
    private final static QName _InputParametersIMSI_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "IMSI");
    private final static QName _InputParametersRATEPLANTYPE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "RATE_PLAN_TYPE");
    private final static QName _InputParametersCUSTCODE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "CUST_CODE");
    private final static QName _InputParametersPRGCODE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "PRGCODE");
    private final static QName _InputParametersID_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "ID");
    private final static QName _InputParametersLASTUPDTDT_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "LAST_UPDT_DT");
    private final static QName _InputParametersCUSTOMERTYPE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "CUSTOMER_TYPE");
    private final static QName _InputParametersCOREOFFER_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "CORE_OFFER");
    private final static QName _InputParametersRATEPLANCODE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "RATE_PLAN_CODE");
    private final static QName _InputParametersMSISDN_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "MSISDN");
    private final static QName _InputParametersIDTYPE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "ID_TYPE");
    private final static QName _InputParametersCOMMITMENTENDDATE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "COMMITMENT_ENDDATE");
    private final static QName _InputParametersSIM_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "SIM");
    private final static QName _InputParametersCUSTOMERSEGMENT_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "CUSTOMER_SEGMENT");
    private final static QName _InputParametersCREDITLIMIT_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "CREDIT_LIMIT");
    private final static QName _InputParametersPMTRESPFLAG_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "PMT_RESP_FLAG");
    private final static QName _InputParametersLASTUPDTBY_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "LAST_UPDT_BY");
    private final static QName _InputParametersIDVALUE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "ID_VALUE");
    private final static QName _InputParametersDIGITALSNCODE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "DIGITAL_SN_CODE");
    private final static QName _InputParametersACTIVATIONDATE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "ACTIVATION_DATE");
    private final static QName _InputParametersSNCODES_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "SN_CODES");
    private final static QName _InputParametersBARRINGSTATUS_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "BARRING_STATUS");
    private final static QName _InputParametersCOREOFFERPRICE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "CORE_OFFER_PRICE");
    private final static QName _InputParametersIDDFLAG_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "IDD_FLAG");
    private final static QName _InputParametersCOMMITMENTSTARTDATE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "COMMITMENT_STARTDATE");
    private final static QName _InputParametersSUSPENSIONDATE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "SUSPENSION_DATE");
    private final static QName _InputParametersPROMOSEGMENT_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "PROMO_SEGMENT");
    private final static QName _InputParametersUPDATEREASON_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "UPDATE_REASON");
    private final static QName _InputParametersBILLCYCLE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "BILLCYCLE");
    private final static QName _InputParametersLNGUAGE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "LNGUAGE");
    private final static QName _InputParametersCUSTOMERID_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "CUSTOMER_ID");
    private final static QName _InputParametersCONTRACTSTATUS_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "CONTRACT_STATUS");
    private final static QName _InputParametersACTIONTYPE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "ACTIONTYPE");
    private final static QName _InputParametersUSEDFREECOUNTER_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "USED_FREE_COUNTER");
    private final static QName _InputParametersCONTRACTID_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "CONTRACT_ID");
    private final static QName _InputParametersVIPSTATUS_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "VIP_STATUS");
    private final static QName _InputParametersCONTRACTFREEZE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "CONTRACT_FREEZE");
    private final static QName _OutputParametersRESULTDESC_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "RESULTDESC");
    private final static QName _OutputParametersRESULTCODE_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", "RESULTCODE");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ooredoo.wsclient.spr
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OutputParameters }
     * 
     */
    public OutputParameters createOutputParameters() {
        return new OutputParameters();
    }

    /**
     * Create an instance of {@link InputParameters }
     * 
     */
    public InputParameters createInputParameters() {
        return new InputParameters();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "BILL_CYCLE_CD", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersBILLCYCLECD(String value) {
        return new JAXBElement<String>(_InputParametersBILLCYCLECD_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "TOTAL_FREE_COUNTER", scope = InputParameters.class)
    public JAXBElement<BigDecimal> createInputParametersTOTALFREECOUNTER(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_InputParametersTOTALFREECOUNTER_QNAME, BigDecimal.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "DIGITAL_SUBSCRIBER", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersDIGITALSUBSCRIBER(String value) {
        return new JAXBElement<String>(_InputParametersDIGITALSUBSCRIBER_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "IMSI", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersIMSI(String value) {
        return new JAXBElement<String>(_InputParametersIMSI_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "RATE_PLAN_TYPE", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersRATEPLANTYPE(String value) {
        return new JAXBElement<String>(_InputParametersRATEPLANTYPE_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "CUST_CODE", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersCUSTCODE(String value) {
        return new JAXBElement<String>(_InputParametersCUSTCODE_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "PRGCODE", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersPRGCODE(String value) {
        return new JAXBElement<String>(_InputParametersPRGCODE_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "ID", scope = InputParameters.class)
    public JAXBElement<BigDecimal> createInputParametersID(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_InputParametersID_QNAME, BigDecimal.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "LAST_UPDT_DT", scope = InputParameters.class)
    public JAXBElement<XMLGregorianCalendar> createInputParametersLASTUPDTDT(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_InputParametersLASTUPDTDT_QNAME, XMLGregorianCalendar.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "CUSTOMER_TYPE", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersCUSTOMERTYPE(String value) {
        return new JAXBElement<String>(_InputParametersCUSTOMERTYPE_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "CORE_OFFER", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersCOREOFFER(String value) {
        return new JAXBElement<String>(_InputParametersCOREOFFER_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "RATE_PLAN_CODE", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersRATEPLANCODE(String value) {
        return new JAXBElement<String>(_InputParametersRATEPLANCODE_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "MSISDN", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersMSISDN(String value) {
        return new JAXBElement<String>(_InputParametersMSISDN_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "ID_TYPE", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersIDTYPE(String value) {
        return new JAXBElement<String>(_InputParametersIDTYPE_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "COMMITMENT_ENDDATE", scope = InputParameters.class)
    public JAXBElement<XMLGregorianCalendar> createInputParametersCOMMITMENTENDDATE(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_InputParametersCOMMITMENTENDDATE_QNAME, XMLGregorianCalendar.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "SIM", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersSIM(String value) {
        return new JAXBElement<String>(_InputParametersSIM_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "CUSTOMER_SEGMENT", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersCUSTOMERSEGMENT(String value) {
        return new JAXBElement<String>(_InputParametersCUSTOMERSEGMENT_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "CREDIT_LIMIT", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersCREDITLIMIT(String value) {
        return new JAXBElement<String>(_InputParametersCREDITLIMIT_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "PMT_RESP_FLAG", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersPMTRESPFLAG(String value) {
        return new JAXBElement<String>(_InputParametersPMTRESPFLAG_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "LAST_UPDT_BY", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersLASTUPDTBY(String value) {
        return new JAXBElement<String>(_InputParametersLASTUPDTBY_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "ID_VALUE", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersIDVALUE(String value) {
        return new JAXBElement<String>(_InputParametersIDVALUE_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "DIGITAL_SN_CODE", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersDIGITALSNCODE(String value) {
        return new JAXBElement<String>(_InputParametersDIGITALSNCODE_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "ACTIVATION_DATE", scope = InputParameters.class)
    public JAXBElement<XMLGregorianCalendar> createInputParametersACTIVATIONDATE(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_InputParametersACTIVATIONDATE_QNAME, XMLGregorianCalendar.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "SN_CODES", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersSNCODES(String value) {
        return new JAXBElement<String>(_InputParametersSNCODES_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "BARRING_STATUS", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersBARRINGSTATUS(String value) {
        return new JAXBElement<String>(_InputParametersBARRINGSTATUS_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "CORE_OFFER_PRICE", scope = InputParameters.class)
    public JAXBElement<BigDecimal> createInputParametersCOREOFFERPRICE(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_InputParametersCOREOFFERPRICE_QNAME, BigDecimal.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "IDD_FLAG", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersIDDFLAG(String value) {
        return new JAXBElement<String>(_InputParametersIDDFLAG_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "COMMITMENT_STARTDATE", scope = InputParameters.class)
    public JAXBElement<XMLGregorianCalendar> createInputParametersCOMMITMENTSTARTDATE(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_InputParametersCOMMITMENTSTARTDATE_QNAME, XMLGregorianCalendar.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "SUSPENSION_DATE", scope = InputParameters.class)
    public JAXBElement<XMLGregorianCalendar> createInputParametersSUSPENSIONDATE(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_InputParametersSUSPENSIONDATE_QNAME, XMLGregorianCalendar.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "PROMO_SEGMENT", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersPROMOSEGMENT(String value) {
        return new JAXBElement<String>(_InputParametersPROMOSEGMENT_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "UPDATE_REASON", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersUPDATEREASON(String value) {
        return new JAXBElement<String>(_InputParametersUPDATEREASON_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "BILLCYCLE", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersBILLCYCLE(String value) {
        return new JAXBElement<String>(_InputParametersBILLCYCLE_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "LNGUAGE", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersLNGUAGE(String value) {
        return new JAXBElement<String>(_InputParametersLNGUAGE_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "CUSTOMER_ID", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersCUSTOMERID(String value) {
        return new JAXBElement<String>(_InputParametersCUSTOMERID_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "CONTRACT_STATUS", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersCONTRACTSTATUS(String value) {
        return new JAXBElement<String>(_InputParametersCONTRACTSTATUS_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "ACTIONTYPE", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersACTIONTYPE(String value) {
        return new JAXBElement<String>(_InputParametersACTIONTYPE_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "USED_FREE_COUNTER", scope = InputParameters.class)
    public JAXBElement<BigDecimal> createInputParametersUSEDFREECOUNTER(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_InputParametersUSEDFREECOUNTER_QNAME, BigDecimal.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "CONTRACT_ID", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersCONTRACTID(String value) {
        return new JAXBElement<String>(_InputParametersCONTRACTID_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "VIP_STATUS", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersVIPSTATUS(String value) {
        return new JAXBElement<String>(_InputParametersVIPSTATUS_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "CONTRACT_FREEZE", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersCONTRACTFREEZE(String value) {
        return new JAXBElement<String>(_InputParametersCONTRACTFREEZE_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "RESULTDESC", scope = OutputParameters.class)
    public JAXBElement<String> createOutputParametersRESULTDESC(String value) {
        return new JAXBElement<String>(_OutputParametersRESULTDESC_QNAME, String.class, OutputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", name = "RESULTCODE", scope = OutputParameters.class)
    public JAXBElement<String> createOutputParametersRESULTCODE(String value) {
        return new JAXBElement<String>(_OutputParametersRESULTCODE_QNAME, String.class, OutputParameters.class, value);
    }

}
