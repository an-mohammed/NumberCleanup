package com.ooredoo.nc.gui.managedbean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ooredoo.nc.dto.AnaOnboardingRequest;
import com.ooredoo.nc.dto.B2BSubscriberOnboardingRequest;
import com.ooredoo.nc.dto.B2BSubscriberOnboardingResponse;
import com.ooredoo.nc.dto.B2CSubscriberOnboardingRequest;
import com.ooredoo.nc.dto.B2CSubscriberOnboardingResponse;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.exception.ApplicationRestException;
import com.ooredoo.nc.gui.configuration.SessionHandler;
import com.ooredoo.nc.model.BulkActivationDetail;
import com.ooredoo.nc.model.DigitalOffer;
import com.ooredoo.nc.model.NcDigitalOnboarding;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="cn")
@ViewScoped
public class CreateNumber extends ManagedBean implements Serializable, ApplicationConstants {

	private static final long serialVersionUID = 1L;
	private String msisdn = null;
	private String civilId = null;
	private String custCode = null;
	private List<SelectItem> prepaidPromoList;
	private List<SelectItem> postpaidPromoList;
	private List<SelectItem> b2bEprPromoList;
	private List<SelectItem> b2bCprPromoList;
	private String selectedPromo;
	private String sibelRowId;
	private String siebelOrderId;
	private B2CSubscriberOnboardingResponse onboardingResp;
	private B2BSubscriberOnboardingResponse onboardingB2BResp;
	private List<BulkActivationDetail> bulkActivationDetailsList;
	private List<NcDigitalOnboarding> digitalActivationList;
	private StreamedContent templateFile;
	private List<BulkActivationDetail> bulkActivationDetailsRequestList;
	private Map<String, Long> digitalPromo;
	private Long selectedDigitalPromo;
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateNumber.class);
	
	@PostConstruct
	public void init() {
		prepaidPromoList = new ArrayList<SelectItem>();
		SelectItemGroup prepaidVoicePromo = new SelectItemGroup("Voice");
		prepaidVoicePromo.setSelectItems(new SelectItem[]{
	            new SelectItem("1-17PPMJWR", "OOREDOO PREPAID")
	        });
		
		SelectItemGroup prepaidWbbPromo = new SelectItemGroup("WBB");
		prepaidWbbPromo.setSelectItems(new SelectItem[]{
	            new SelectItem("1-17VQC4VN", "MY NET PREPAID")
	        });
		
		prepaidPromoList.add(prepaidVoicePromo);
		prepaidPromoList.add(prepaidWbbPromo);
		
		postpaidPromoList = new ArrayList<SelectItem>();
		SelectItemGroup postpaidVoicePromo = new SelectItemGroup("Voice");
		postpaidVoicePromo.setSelectItems(new SelectItem[]{
	            new SelectItem("1-16R8QQZ1", "B2C_N_2019_Shamel10_24M"),
	            new SelectItem("1-16QRV0NT", "B2C_N_2019_Shamel_20_24M"),
	            new SelectItem("1-16Z9PCLV", "B2C_N_Eshop_2019_Shamel40_24M"),
	            new SelectItem("1-1973E83G", "B2C_N_E_shop_2020_Shamel10_24M"),
	            new SelectItem("1-19QOT4QW", "B2C_N_P40_EShop_2020_Shamel30_24M")
	        });
		
		SelectItemGroup postpaidDataPromo = new SelectItemGroup("Data");
		postpaidDataPromo.setSelectItems(new SelectItem[]{
	            new SelectItem("1-1B8IJEZU", "B2C_LS_N_Unlimited_15KD_24M"),
	    });
		
		postpaidPromoList.add(postpaidVoicePromo);
		postpaidPromoList.add(postpaidDataPromo);
		
		
		b2bCprPromoList = new ArrayList<SelectItem>();
		SelectItemGroup b2bCprVoicePromo = new SelectItemGroup("Voice");
		/* new SelectItem("1-1B0D6TK1", "B2B_CPR_EOY_SHAMEL_10KD_24M"), */
		b2bCprVoicePromo.setSelectItems(new SelectItem[]{
				new SelectItem("1-1B0D6TKH", "B2B_CPR_EOY_SHAMEL_40KD_24M"),
	            new SelectItem("1-169VO6P9", "B2B_CPR_MAR_2019_LB_10KD_24M"),
	            new SelectItem("1-171YCKAG", "TMO_ADEL_HAIDARI_2019_PRO_70KD_24M"),
	            new SelectItem("1-175XM9LB", "TMO_ALBAREJA_2019_PRO_35KD_24M")
	        });
		
		SelectItemGroup b2bCprDataPromo = new SelectItemGroup("Data");
		b2bCprDataPromo.setSelectItems(new SelectItem[]{
	            new SelectItem("1-182NJYSW", "B2B_CPR_5G_1TB_2019_24M"),
	            new SelectItem("1-182NJYT4", "B2B_CPR_5G_2TB_2019_24M"),
	            new SelectItem("1-176Q27CD", "B2B_IN_1TB_10KD_24M"),
	            new SelectItem("1-X7DGG36", "B2B_IN_WBB_3TB_15KD_24M"),
	            new SelectItem("1-B6B8157", "B2B_IN_4G_PLUS_GAMING_1TB_10KD_24M"),
	    });
		
		b2bCprPromoList.add(b2bCprVoicePromo);
		b2bCprPromoList.add(b2bCprDataPromo);
		
		
		b2bEprPromoList = new ArrayList<SelectItem>();
		SelectItemGroup b2bEprVoicePromo = new SelectItemGroup("Voice");
		b2bEprVoicePromo.setSelectItems(new SelectItem[]{
	            new SelectItem("1-1B0G6E4J", "B2B_EPR_EOY_SHAMEL_10KD_24M"),
	            new SelectItem("1-1B0G6E4R", "B2B_EPR_EOY_SHAMEL_15KD_24M"),
	            new SelectItem("1-1B0G6E4Z", "B2B_EPR_EOY_SHAMEL_40KD_24M"),
	            new SelectItem("1-1B0G6E4B", "B2B_EPR_EOY_SHAMEL_5KD_24M"),
	            new SelectItem("1-1B0G6E57", "B2B_EPR_EOY_SHAMEL_70KD_24M"),
	            
	            new SelectItem("1-1A3UH6ZG", "B2B_EPR_BACK_BUSINESS_2020_PRO_40KD_24M"),
	            new SelectItem("1-15JTM9NA", "B2B_EPR_HALA_FEB_2019_BUNDLE3_PRO_21KD_24M"),
	            new SelectItem("1-18M6YDH4", "B2B_EPR_HALA_FEB_2020_PRO_40KD_24M"),
	            new SelectItem("1-184GRGLR", "B2B_EPR_NOTE10_LA_2019_24M"),
	            new SelectItem("1-11CUHR1S", "B2B_EPR_S9_SHAMEL_PRO_50KD_24M")
	        });
		
			
		b2bEprPromoList.add(b2bEprVoicePromo);
		
		digitalPromo = new HashMap<String, Long>();
		try {
			List<DigitalOffer> offerList = getConfigService().getDigitalPromoList();
			
			for(DigitalOffer o : offerList) {
				digitalPromo.put(o.getOfferDesc(), o.getOfferId());
			}
		} catch (ApplicationRestException e) {
			LOGGER.error("Unable to extract digital offer list. Cause-", e);
		}
	}
	
	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public void onTabChange() {
		this.msisdn = null;
		this.civilId = null;
		this.custCode = null;
		this.selectedPromo = null;
		this.sibelRowId = null;
		this.siebelOrderId = null;
		this.onboardingResp = null;
		this.selectedDigitalPromo = null;
	}
	
	public void getFile() {
		templateFile = new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/BulkActivationTemplate.csv"), "file/csv", "Bulk_Activation_Template.csv");
	}
	
	public String getSelectedPromo() {
		return selectedPromo;
	}

	public void setSelectedPromo(String selectedPromo) {
		this.selectedPromo = selectedPromo;
	}

	public void createPrepaidProfile() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(null == msisdn || msisdn.isEmpty()) {
				throw new ApplicationException("Missing MSISDN", true);
			}
			
			if(null == selectedPromo || selectedPromo.isEmpty()) {
				throw new ApplicationException("No promotion type selected", true);
			}
			
			B2CSubscriberOnboardingRequest req = new B2CSubscriberOnboardingRequest();
			req.setMsisdn(this.msisdn);
			req.setCivilId(this.civilId);
			req.setPromoId(this.selectedPromo);
			req.setSubscriberType("PREPAID");
			req.setUsername(getActionUser());
			this.onboardingResp = getConfigService().createSubscriberProfile(req);
			
			if(null != onboardingResp) {
				LOGGER.info("Message : {}", onboardingResp.getStatus());
				
				if(null != onboardingResp.getStatus() && onboardingResp.getStatus().equals("OK")) {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Success",
									"Subscriber onboarded successfully with SPC-ID#" + onboardingResp.getSiebelRowId() + "  and SPC-TYPE#" + onboardingResp.getSiebelOrderId()));
				} else {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Fail",
									onboardingResp.getStatus()));
				}
			} else {
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Fail",
								"Unable to determine subscriber onboarding status"));
			}
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while creating prepaid number. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		} finally {
			this.msisdn = null;
			this.civilId = null;
			this.selectedPromo = null;
		}
	}
	
	public void createPostpaidProfile() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(null == msisdn || msisdn.isEmpty()) {
				throw new ApplicationException("Missing MSISDN", true);
			}
			
			if(null == selectedPromo || selectedPromo.isEmpty()) {
				throw new ApplicationException("No promotion type selected", true);
			}
			
			B2CSubscriberOnboardingRequest req = new B2CSubscriberOnboardingRequest();
			req.setMsisdn(this.msisdn);
			req.setCivilId(this.civilId);
			req.setPromoId(this.selectedPromo);
			req.setSubscriberType("POSTPAID");
			req.setUsername(getActionUser());
			this.onboardingResp = getConfigService().createSubscriberProfile(req);
			
			if(null != onboardingResp) {
				LOGGER.info("Message : {}", onboardingResp.getStatus());
				
				if(null != onboardingResp.getStatus() && onboardingResp.getStatus().equals("OK")) {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Success",
									"Subscriber onboarded successfully with SPC-ID#" + onboardingResp.getSiebelRowId() + "  and SPC-NUMBER#" + onboardingResp.getSiebelOrderId()));
				} else {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Fail",
									onboardingResp.getStatus()));
				}
				
			} else {
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Fail",
								"Unable to determine subscriber onboarding status"));
			}
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while creating prepaid number. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		} finally {
			this.msisdn = null;
			this.civilId = null;
			this.selectedPromo = null;
		}
	}
	
	public void getBulkAssignmentDetails() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			String username = getActionUser();
			bulkActivationDetailsList = getConfigService().getBulkActivationDetails(username);
			
		}catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting bulk activation details. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Error Occurred while extracting bulk activation details. Cause", e);
			
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		LOGGER.info("Invoking file Upload Method");
		
        if(event.getFile() != null && event.getFile().getSize() > 0) {
        	BufferedReader br = null;
        	String line = null;
        	
        	try {
        		if(null != this.bulkActivationDetailsRequestList) {
        			this.bulkActivationDetailsRequestList.clear();
        		} else {
        			bulkActivationDetailsRequestList = new ArrayList<BulkActivationDetail>();
        		}
        		
        		br = new BufferedReader(new InputStreamReader(event.getFile().getInputstream(), "utf-8"));
        		
        		while((line = br.readLine()) != null) {
        			parseRecord(line, this.bulkActivationDetailsRequestList);
        		}
        		
        		String resp = getConfigService().uploadFileContentForBulkActivation(this.bulkActivationDetailsRequestList);
        		String username = getActionUser();
    			bulkActivationDetailsList = getConfigService().getBulkActivationDetails(username);
        		
        		if(null != resp && resp.startsWith("Request successfully posted with batch Id")) {
        			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, SUCCESS_LEBEL, resp));
        		} else if(resp == null){
        			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR_LABEL, "Fatal Error. Please check logs for more details"));
        		} else {
        			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR_LABEL, resp));
        		}
        	
        	} catch(IOException e) {
        		LOGGER.error("Error Occurred while uploading and parsing file", e);
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR_LABEL, getFatalErrorMessage()));
        		
        	} catch(ApplicationException ae) {
        		LOGGER.error("Error Occurred while uploading and parsing file", ae);
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR_LABEL, ae.getErrorMessage()));
        		
        	} catch(Exception e) {
        		LOGGER.error("Error Occurred while uploading and parsing file", e);
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR_LABEL, getFatalErrorMessage()));
        		
        	}finally {
        		try {
	        		event.getFile().getInputstream().close();
	        		if(null != br) {
	        			try {br.close();} catch(Exception e) {
	        				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR_LABEL, getFatalErrorMessage()));
	        			}
	        		}
	        		
        		} catch(Exception e) {
        			LOGGER.error("Error Occurred while uploading and parsing file", e);
            		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,ERROR_LABEL, getFatalErrorMessage()));
        		}
        	}
            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_LABEL, INVALID_FILE));
        }
    }
	
	private void parseRecord(String record, List<BulkActivationDetail> profiles) throws ApplicationException {
		
		BulkActivationDetail profile = null;
		
		String[] columns = record.split(COMMA);
		
		if(columns.length == 4 && validateColumn(columns[0]) && validateColumn(columns[2]) && validateColumn(columns[3])) {
			if(columns[0].trim().startsWith(MSISDN_PREFIX) && columns[0].trim().length() == 11) {
				if(columns[2].trim().equals("PP") || columns[2].trim().equals("PO")) {
					if(columns[3].trim().equals("V") || columns[3].trim().equals("D")) {
						profile = new BulkActivationDetail();
						profile.setMsisdn(columns[0]);
						profile.setCivilId(columns[1]);
						profile.setSubsType(columns[2]);
						profile.setCoType(columns[3]);
						profile.setUsername(getActionUser());
						profiles.add(profile);
					} else {
						throw new ApplicationException("Line type should be V/D. Error value is : " + columns[3], true);
					}
				} else {
					throw new ApplicationException("Subscriber type should be PP/PO. Error value is : " + columns[2], true);
				}
			} else {
				throw new ApplicationException("MSISDN Must Start with " + MSISDN_PREFIX +" and length must be 11. MSISDN-" + columns[0], true);
			}
		} else {
			throw new ApplicationException("Records must be in format without blank/space- <MSISDN>,<Civil ID>,<Subscriber Type>,<Line Type>", true);
		}
	}
	
	public void createAnaProfile() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(null == msisdn || msisdn.isEmpty()) {
				throw new ApplicationException("Missing MSISDN", true);
			}
			
			if(null == selectedDigitalPromo) {
				throw new ApplicationException("No promotion type selected", true);
			}
			
			AnaOnboardingRequest req = new AnaOnboardingRequest();
			req.setMsisdn(this.msisdn);
			req.setCivilId(this.civilId);
			req.setOfferId(String.valueOf(this.selectedDigitalPromo));
			req.setUsername(getActionUser());
			String resp = getConfigService().createAnaSubscriberProfile(req);
			
			String trnasactionId="";
			
			if(resp!=null && resp!="") {
				 String[] trnsaction = resp.split("-");
				 trnasactionId = trnsaction[1].trim();
				 SessionHandler.putTransactionToSession(trnasactionId);
			}
			this.digitalActivationList = getConfigService()
					.getDigitalActivationDetails(trnasactionId/* getActionUser() */);
			
			if(null != resp && !resp.isEmpty()) {
				LOGGER.info("Message : {}", resp);
				
				if(resp.contains("successfully")) {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Success",
									resp));
				} else {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Fail",
									resp));
				}
				
			} else {
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Fail",
								"Unable to determine subscriber onboarding status"));
			}
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while creating prepaid number. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		} finally {
			this.msisdn = null;
			this.civilId = null;
			this.selectedPromo = null;
		}
	}
	
	public void getDigitalActivationDetails() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			//String username = getActionUser();
			
			String trnsactionId = SessionHandler.getTransactionId();
			 if(trnsactionId!="") {
				 digitalActivationList = getConfigService().getDigitalActivationDetails(trnsactionId);
			 }
		}catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting digital activation details. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Error Occurred while extracting digital activation details. Cause", e);
			
		}
	}
	
	public void createB2BEprProfile() {
FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			LOGGER.info(" ===============Invoking B2B ERP Profile================== ");
			if(null == msisdn || msisdn.isEmpty()) {
				throw new ApplicationException("Missing MSISDN", true);
			}
			
			if(null == selectedPromo || selectedPromo.isEmpty()) {
				throw new ApplicationException("No promotion type selected", true);
			}
			
			LOGGER.info(" custCode: {}", custCode);
			
			if(null == custCode || custCode.isEmpty()) {
				throw new ApplicationException("Missing Cust Code", true);
			}
			
			B2BSubscriberOnboardingRequest req = new B2BSubscriberOnboardingRequest();
			
			req.setMsisdn(this.msisdn);
			req.setCivilId("");
			req.setPromoId(this.selectedPromo);
			req.setSubscriberType("POSTPAID");
			req.setUsername(getActionUser());
			req.setCustCode(this.custCode);
			req.setSuscriber("EPR");//New Element added for to find out EPR or CPR.
			
			LOGGER.info(" req.getCustCode(): {}", req.getCustCode());
			 
			this.onboardingB2BResp = getConfigService().createB2BSubscriberProfile(req);
			
			LOGGER.info(" this.onboardingB2BResp: {}", this.onboardingB2BResp);
			
			if(null != this.onboardingB2BResp) {
				LOGGER.info("Message : {}", this.onboardingB2BResp.getStatus());
				
				if(null != this.onboardingB2BResp.getStatus() && this.onboardingB2BResp.getStatus().equals("OK")) {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Success",
									"Subscriber onboarded successfully with OrderId#"+this.onboardingB2BResp.getSiebelOrderId()+", OrderNumber#"+this.onboardingB2BResp.getSiebelRowId()
									));
				}else if(null != this.onboardingB2BResp.getStatus() && !(this.onboardingB2BResp.getStatus()).equals("OK")) {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Fail",
									"Subscriber onboarded fail: "+this.onboardingB2BResp.getStatus()
									));
				}else {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Fail",
									onboardingResp.getStatus()));
				}
				
			} else {
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Fail",
								"Unable to determine subscriber onboarding status"));
			}
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while creating B2B Profile. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		} finally {
			this.msisdn = null;
			this.civilId = null;
			this.selectedPromo = null;
			this.custCode = null;
		}
	}
	
	public void createB2BProfile() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			LOGGER.info(" ===============Invoking B2B CPR Profile================== ");
			if(null == msisdn || msisdn.isEmpty()) {
				throw new ApplicationException("Missing MSISDN", true);
			}
			
			if(null == selectedPromo || selectedPromo.isEmpty()) {
				throw new ApplicationException("No promotion type selected", true);
			}
			
			LOGGER.info(" custCode: {}", custCode);
			
			if(null == custCode || custCode.isEmpty()) {
				throw new ApplicationException("Missing Cust Code", true);
			}
			
			B2BSubscriberOnboardingRequest req = new B2BSubscriberOnboardingRequest();
			
			req.setMsisdn(this.msisdn);
			req.setCivilId("");
			req.setPromoId(this.selectedPromo);
			req.setSubscriberType("POSTPAID");
			req.setUsername(getActionUser());
			req.setCustCode(this.custCode);
			req.setSuscriber("CPR");//New Element added for to find out EPR or CPR.
			
			LOGGER.info(" req.getCustCode(): {}", req.getCustCode());
			 
			this.onboardingB2BResp = getConfigService().createB2BSubscriberProfile(req);
			
			LOGGER.info(" this.onboardingB2BResp: {}", this.onboardingB2BResp);
			
			if(null != this.onboardingB2BResp) {
				LOGGER.info("Message : {}", this.onboardingB2BResp.getStatus());
				
				if(null != this.onboardingB2BResp.getStatus() && this.onboardingB2BResp.getStatus().equals("OK")) {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Success",
									"Subscriber onboarded successfully with OrderId#"+this.onboardingB2BResp.getSiebelOrderId()+", OrderNumber#"+this.onboardingB2BResp.getSiebelRowId()
									));
				}else if(null != this.onboardingB2BResp.getStatus() && !(this.onboardingB2BResp.getStatus()).equals("OK")) {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Fail",
									"Subscriber onboarded fail: "+this.onboardingB2BResp.getStatus()
									));
				}else {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Fail",
									onboardingResp.getStatus()));
				}
				
			} else {
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Fail",
								"Unable to determine subscriber onboarding status"));
			}
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while creating B2B Profile. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		} finally {
			this.msisdn = null;
			this.civilId = null;
			this.selectedPromo = null;
			this.custCode = null;
		}
	}
	private boolean validateColumn(String column) {
		if(null == column || column.trim().isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	public String getMsisdn() {
		return msisdn;
	}


	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getCivilId() {
		return civilId;
	}

	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}

	public String getSiebelOrderId() {
		return siebelOrderId;
	}

	public void setSiebelOrderId(String siebelOrderId) {
		this.siebelOrderId = siebelOrderId;
	}

	public String getSibelRowId() {
		return sibelRowId;
	}

	public void setSibelRowId(String sibelRowId) {
		this.sibelRowId = sibelRowId;
	}

	public List<SelectItem> getPrepaidPromoList() {
		return prepaidPromoList;
	}

	public void setPrepaidPromoList(List<SelectItem> prepaidPromoList) {
		this.prepaidPromoList = prepaidPromoList;
	}

	public List<SelectItem> getPostpaidPromoList() {
		return postpaidPromoList;
	}

	public void setPostpaidPromoList(List<SelectItem> postpaidPromoList) {
		this.postpaidPromoList = postpaidPromoList;
	}

	public B2CSubscriberOnboardingResponse getOnboardingResp() {
		return onboardingResp;
	}

	public void setOnboardingResp(B2CSubscriberOnboardingResponse onboardingResp) {
		this.onboardingResp = onboardingResp;
	}

	public List<SelectItem> getB2bEprPromoList() {
		return b2bEprPromoList;
	}

	public void setB2bEprPromoList(List<SelectItem> b2bEprPromoList) {
		this.b2bEprPromoList = b2bEprPromoList;
	}

	public List<SelectItem> getB2bCprPromoList() {
		return b2bCprPromoList;
	}

	public void setB2bCprPromoList(List<SelectItem> b2bCprPromoList) {
		this.b2bCprPromoList = b2bCprPromoList;
	}

	public List<BulkActivationDetail> getBulkActivationDetailsList() {
		return bulkActivationDetailsList;
	}

	public void setBulkActivationDetailsList(List<BulkActivationDetail> bulkActivationDetailsList) {
		this.bulkActivationDetailsList = bulkActivationDetailsList;
	}

	public StreamedContent getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(StreamedContent templateFile) {
		this.templateFile = templateFile;
	}

	public List<BulkActivationDetail> getBulkActivationDetailsRequestList() {
		return bulkActivationDetailsRequestList;
	}

	public void setBulkActivationDetailsRequestList(List<BulkActivationDetail> bulkActivationDetailsRequestList) {
		this.bulkActivationDetailsRequestList = bulkActivationDetailsRequestList;
	}

	public Map<String, Long> getDigitalPromo() {
		return digitalPromo;
	}

	public void setDigitalPromo(Map<String, Long> digitalPromo) {
		this.digitalPromo = digitalPromo;
	}

	public Long getSelectedDigitalPromo() {
		return selectedDigitalPromo;
	}

	public void setSelectedDigitalPromo(Long selectedDigitalPromo) {
		this.selectedDigitalPromo = selectedDigitalPromo;
	}

	public List<NcDigitalOnboarding> getDigitalActivationList() {
		return digitalActivationList;
	}

	public void setDigitalActivationList(List<NcDigitalOnboarding> digitalActivationList) {
		this.digitalActivationList = digitalActivationList;
	}

	public B2BSubscriberOnboardingResponse getOnboardingB2BResp() {
		return onboardingB2BResp;
	}

	public void setOnboardingB2BResp(B2BSubscriberOnboardingResponse onboardingB2BResp) {
		this.onboardingB2BResp = onboardingB2BResp;
	}
}
