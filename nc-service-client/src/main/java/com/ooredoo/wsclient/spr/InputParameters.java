
package com.ooredoo.wsclient.spr;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ACTIONTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CONTRACT_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MSISDN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CUSTOMER_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SIM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ID_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IMSI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ID_VALUE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RATE_PLAN_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RATE_PLAN_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CUST_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CUSTOMER_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CONTRACT_STATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PMT_RESP_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PROMO_SEGMENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CUSTOMER_SEGMENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BILL_CYCLE_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDD_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CREDIT_LIMIT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LNGUAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BARRING_STATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LAST_UPDT_DT" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LAST_UPDT_BY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UPDATE_REASON" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="BILLCYCLE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VIP_STATUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ACTIVATION_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SUSPENSION_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PRGCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CONTRACT_FREEZE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COMMITMENT_STARTDATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="COMMITMENT_ENDDATE" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CORE_OFFER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DIGITAL_SN_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CORE_OFFER_PRICE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="DIGITAL_SUBSCRIBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SN_CODES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="USED_FREE_COUNTER" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="TOTAL_FREE_COUNTER" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "actiontype",
    "contractid",
    "msisdn",
    "customerid",
    "sim",
    "idtype",
    "imsi",
    "idvalue",
    "rateplancode",
    "rateplantype",
    "custcode",
    "customertype",
    "contractstatus",
    "pmtrespflag",
    "promosegment",
    "customersegment",
    "billcyclecd",
    "iddflag",
    "creditlimit",
    "lnguage",
    "barringstatus",
    "lastupdtdt",
    "lastupdtby",
    "updatereason",
    "id",
    "billcycle",
    "vipstatus",
    "activationdate",
    "suspensiondate",
    "prgcode",
    "contractfreeze",
    "commitmentstartdate",
    "commitmentenddate",
    "coreoffer",
    "digitalsncode",
    "coreofferprice",
    "digitalsubscriber",
    "sncodes",
    "usedfreecounter",
    "totalfreecounter"
})
@XmlRootElement(name = "InputParameters")
public class InputParameters {

    @XmlElementRef(name = "ACTIONTYPE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> actiontype;
    @XmlElementRef(name = "CONTRACT_ID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> contractid;
    @XmlElementRef(name = "MSISDN", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> msisdn;
    @XmlElementRef(name = "CUSTOMER_ID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> customerid;
    @XmlElementRef(name = "SIM", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> sim;
    @XmlElementRef(name = "ID_TYPE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idtype;
    @XmlElementRef(name = "IMSI", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> imsi;
    @XmlElementRef(name = "ID_VALUE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idvalue;
    @XmlElementRef(name = "RATE_PLAN_CODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> rateplancode;
    @XmlElementRef(name = "RATE_PLAN_TYPE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> rateplantype;
    @XmlElementRef(name = "CUST_CODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> custcode;
    @XmlElementRef(name = "CUSTOMER_TYPE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> customertype;
    @XmlElementRef(name = "CONTRACT_STATUS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> contractstatus;
    @XmlElementRef(name = "PMT_RESP_FLAG", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> pmtrespflag;
    @XmlElementRef(name = "PROMO_SEGMENT", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> promosegment;
    @XmlElementRef(name = "CUSTOMER_SEGMENT", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> customersegment;
    @XmlElementRef(name = "BILL_CYCLE_CD", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> billcyclecd;
    @XmlElementRef(name = "IDD_FLAG", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> iddflag;
    @XmlElementRef(name = "CREDIT_LIMIT", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> creditlimit;
    @XmlElementRef(name = "LNGUAGE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lnguage;
    @XmlElementRef(name = "BARRING_STATUS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> barringstatus;
    @XmlElementRef(name = "LAST_UPDT_DT", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> lastupdtdt;
    @XmlElementRef(name = "LAST_UPDT_BY", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lastupdtby;
    @XmlElementRef(name = "UPDATE_REASON", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> updatereason;
    @XmlElementRef(name = "ID", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<BigDecimal> id;
    @XmlElementRef(name = "BILLCYCLE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> billcycle;
    @XmlElementRef(name = "VIP_STATUS", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> vipstatus;
    @XmlElementRef(name = "ACTIVATION_DATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> activationdate;
    @XmlElementRef(name = "SUSPENSION_DATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> suspensiondate;
    @XmlElementRef(name = "PRGCODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> prgcode;
    @XmlElementRef(name = "CONTRACT_FREEZE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> contractfreeze;
    @XmlElementRef(name = "COMMITMENT_STARTDATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> commitmentstartdate;
    @XmlElementRef(name = "COMMITMENT_ENDDATE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> commitmentenddate;
    @XmlElementRef(name = "CORE_OFFER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> coreoffer;
    @XmlElementRef(name = "DIGITAL_SN_CODE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> digitalsncode;
    @XmlElementRef(name = "CORE_OFFER_PRICE", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<BigDecimal> coreofferprice;
    @XmlElementRef(name = "DIGITAL_SUBSCRIBER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> digitalsubscriber;
    @XmlElementRef(name = "SN_CODES", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<String> sncodes;
    @XmlElementRef(name = "USED_FREE_COUNTER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<BigDecimal> usedfreecounter;
    @XmlElementRef(name = "TOTAL_FREE_COUNTER", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/SDM_PROCESS_DATA", type = JAXBElement.class, required = false)
    protected JAXBElement<BigDecimal> totalfreecounter;

    /**
     * Gets the value of the actiontype property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getACTIONTYPE() {
        return actiontype;
    }

    /**
     * Sets the value of the actiontype property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setACTIONTYPE(JAXBElement<String> value) {
        this.actiontype = value;
    }

    /**
     * Gets the value of the contractid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCONTRACTID() {
        return contractid;
    }

    /**
     * Sets the value of the contractid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCONTRACTID(JAXBElement<String> value) {
        this.contractid = value;
    }

    /**
     * Gets the value of the msisdn property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMSISDN() {
        return msisdn;
    }

    /**
     * Sets the value of the msisdn property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMSISDN(JAXBElement<String> value) {
        this.msisdn = value;
    }

    /**
     * Gets the value of the customerid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCUSTOMERID() {
        return customerid;
    }

    /**
     * Sets the value of the customerid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCUSTOMERID(JAXBElement<String> value) {
        this.customerid = value;
    }

    /**
     * Gets the value of the sim property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSIM() {
        return sim;
    }

    /**
     * Sets the value of the sim property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSIM(JAXBElement<String> value) {
        this.sim = value;
    }

    /**
     * Gets the value of the idtype property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIDTYPE() {
        return idtype;
    }

    /**
     * Sets the value of the idtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIDTYPE(JAXBElement<String> value) {
        this.idtype = value;
    }

    /**
     * Gets the value of the imsi property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIMSI() {
        return imsi;
    }

    /**
     * Sets the value of the imsi property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIMSI(JAXBElement<String> value) {
        this.imsi = value;
    }

    /**
     * Gets the value of the idvalue property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIDVALUE() {
        return idvalue;
    }

    /**
     * Sets the value of the idvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIDVALUE(JAXBElement<String> value) {
        this.idvalue = value;
    }

    /**
     * Gets the value of the rateplancode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRATEPLANCODE() {
        return rateplancode;
    }

    /**
     * Sets the value of the rateplancode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRATEPLANCODE(JAXBElement<String> value) {
        this.rateplancode = value;
    }

    /**
     * Gets the value of the rateplantype property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRATEPLANTYPE() {
        return rateplantype;
    }

    /**
     * Sets the value of the rateplantype property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRATEPLANTYPE(JAXBElement<String> value) {
        this.rateplantype = value;
    }

    /**
     * Gets the value of the custcode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCUSTCODE() {
        return custcode;
    }

    /**
     * Sets the value of the custcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCUSTCODE(JAXBElement<String> value) {
        this.custcode = value;
    }

    /**
     * Gets the value of the customertype property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCUSTOMERTYPE() {
        return customertype;
    }

    /**
     * Sets the value of the customertype property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCUSTOMERTYPE(JAXBElement<String> value) {
        this.customertype = value;
    }

    /**
     * Gets the value of the contractstatus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCONTRACTSTATUS() {
        return contractstatus;
    }

    /**
     * Sets the value of the contractstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCONTRACTSTATUS(JAXBElement<String> value) {
        this.contractstatus = value;
    }

    /**
     * Gets the value of the pmtrespflag property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPMTRESPFLAG() {
        return pmtrespflag;
    }

    /**
     * Sets the value of the pmtrespflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPMTRESPFLAG(JAXBElement<String> value) {
        this.pmtrespflag = value;
    }

    /**
     * Gets the value of the promosegment property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPROMOSEGMENT() {
        return promosegment;
    }

    /**
     * Sets the value of the promosegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPROMOSEGMENT(JAXBElement<String> value) {
        this.promosegment = value;
    }

    /**
     * Gets the value of the customersegment property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCUSTOMERSEGMENT() {
        return customersegment;
    }

    /**
     * Sets the value of the customersegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCUSTOMERSEGMENT(JAXBElement<String> value) {
        this.customersegment = value;
    }

    /**
     * Gets the value of the billcyclecd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBILLCYCLECD() {
        return billcyclecd;
    }

    /**
     * Sets the value of the billcyclecd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBILLCYCLECD(JAXBElement<String> value) {
        this.billcyclecd = value;
    }

    /**
     * Gets the value of the iddflag property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIDDFLAG() {
        return iddflag;
    }

    /**
     * Sets the value of the iddflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIDDFLAG(JAXBElement<String> value) {
        this.iddflag = value;
    }

    /**
     * Gets the value of the creditlimit property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCREDITLIMIT() {
        return creditlimit;
    }

    /**
     * Sets the value of the creditlimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCREDITLIMIT(JAXBElement<String> value) {
        this.creditlimit = value;
    }

    /**
     * Gets the value of the lnguage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLNGUAGE() {
        return lnguage;
    }

    /**
     * Sets the value of the lnguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLNGUAGE(JAXBElement<String> value) {
        this.lnguage = value;
    }

    /**
     * Gets the value of the barringstatus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBARRINGSTATUS() {
        return barringstatus;
    }

    /**
     * Sets the value of the barringstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBARRINGSTATUS(JAXBElement<String> value) {
        this.barringstatus = value;
    }

    /**
     * Gets the value of the lastupdtdt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getLASTUPDTDT() {
        return lastupdtdt;
    }

    /**
     * Sets the value of the lastupdtdt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setLASTUPDTDT(JAXBElement<XMLGregorianCalendar> value) {
        this.lastupdtdt = value;
    }

    /**
     * Gets the value of the lastupdtby property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLASTUPDTBY() {
        return lastupdtby;
    }

    /**
     * Sets the value of the lastupdtby property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLASTUPDTBY(JAXBElement<String> value) {
        this.lastupdtby = value;
    }

    /**
     * Gets the value of the updatereason property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUPDATEREASON() {
        return updatereason;
    }

    /**
     * Sets the value of the updatereason property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUPDATEREASON(JAXBElement<String> value) {
        this.updatereason = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setID(JAXBElement<BigDecimal> value) {
        this.id = value;
    }

    /**
     * Gets the value of the billcycle property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBILLCYCLE() {
        return billcycle;
    }

    /**
     * Sets the value of the billcycle property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBILLCYCLE(JAXBElement<String> value) {
        this.billcycle = value;
    }

    /**
     * Gets the value of the vipstatus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVIPSTATUS() {
        return vipstatus;
    }

    /**
     * Sets the value of the vipstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVIPSTATUS(JAXBElement<String> value) {
        this.vipstatus = value;
    }

    /**
     * Gets the value of the activationdate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getACTIVATIONDATE() {
        return activationdate;
    }

    /**
     * Sets the value of the activationdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setACTIVATIONDATE(JAXBElement<XMLGregorianCalendar> value) {
        this.activationdate = value;
    }

    /**
     * Gets the value of the suspensiondate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSUSPENSIONDATE() {
        return suspensiondate;
    }

    /**
     * Sets the value of the suspensiondate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSUSPENSIONDATE(JAXBElement<XMLGregorianCalendar> value) {
        this.suspensiondate = value;
    }

    /**
     * Gets the value of the prgcode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPRGCODE() {
        return prgcode;
    }

    /**
     * Sets the value of the prgcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPRGCODE(JAXBElement<String> value) {
        this.prgcode = value;
    }

    /**
     * Gets the value of the contractfreeze property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCONTRACTFREEZE() {
        return contractfreeze;
    }

    /**
     * Sets the value of the contractfreeze property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCONTRACTFREEZE(JAXBElement<String> value) {
        this.contractfreeze = value;
    }

    /**
     * Gets the value of the commitmentstartdate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getCOMMITMENTSTARTDATE() {
        return commitmentstartdate;
    }

    /**
     * Sets the value of the commitmentstartdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setCOMMITMENTSTARTDATE(JAXBElement<XMLGregorianCalendar> value) {
        this.commitmentstartdate = value;
    }

    /**
     * Gets the value of the commitmentenddate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getCOMMITMENTENDDATE() {
        return commitmentenddate;
    }

    /**
     * Sets the value of the commitmentenddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setCOMMITMENTENDDATE(JAXBElement<XMLGregorianCalendar> value) {
        this.commitmentenddate = value;
    }

    /**
     * Gets the value of the coreoffer property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCOREOFFER() {
        return coreoffer;
    }

    /**
     * Sets the value of the coreoffer property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCOREOFFER(JAXBElement<String> value) {
        this.coreoffer = value;
    }

    /**
     * Gets the value of the digitalsncode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDIGITALSNCODE() {
        return digitalsncode;
    }

    /**
     * Sets the value of the digitalsncode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDIGITALSNCODE(JAXBElement<String> value) {
        this.digitalsncode = value;
    }

    /**
     * Gets the value of the coreofferprice property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getCOREOFFERPRICE() {
        return coreofferprice;
    }

    /**
     * Sets the value of the coreofferprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setCOREOFFERPRICE(JAXBElement<BigDecimal> value) {
        this.coreofferprice = value;
    }

    /**
     * Gets the value of the digitalsubscriber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDIGITALSUBSCRIBER() {
        return digitalsubscriber;
    }

    /**
     * Sets the value of the digitalsubscriber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDIGITALSUBSCRIBER(JAXBElement<String> value) {
        this.digitalsubscriber = value;
    }

    /**
     * Gets the value of the sncodes property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSNCODES() {
        return sncodes;
    }

    /**
     * Sets the value of the sncodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSNCODES(JAXBElement<String> value) {
        this.sncodes = value;
    }

    /**
     * Gets the value of the usedfreecounter property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getUSEDFREECOUNTER() {
        return usedfreecounter;
    }

    /**
     * Sets the value of the usedfreecounter property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setUSEDFREECOUNTER(JAXBElement<BigDecimal> value) {
        this.usedfreecounter = value;
    }

    /**
     * Gets the value of the totalfreecounter property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getTOTALFREECOUNTER() {
        return totalfreecounter;
    }

    /**
     * Sets the value of the totalfreecounter property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setTOTALFREECOUNTER(JAXBElement<BigDecimal> value) {
        this.totalfreecounter = value;
    }

}
