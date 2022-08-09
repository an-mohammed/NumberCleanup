/**
 * 
 */
package com.ooredoo.nc.utility;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author S.Chatterjee
 *
 */
public interface ApplicationConstants {
	public static final Integer ACCOUNT_LOCKED = 1;
	public static final Integer ACCOUNT_DISABLED = 0;
	public static final String S_CREDENTIAL = "S_CREDENTIAL";
	public static final String S_TRANSACTION_ID = "S_TRANSACTION_ID";
	public static final String B2B_EPR = "EPR";
	public static final String S_ROLE_PRIV = "S_ROLE_PRIV";
	public static final String DEFAULT_PASSWORD = "User@1234";
	public static final String DEFAULT_TIMEZONE = "Asia/Kolkata";
	public static final String HTTP_URL_PREFIX = "http://";
	public static final String MSISDN_PREFIX = "965";
	public static final String ERROR_LABEL = "Error";
	public static final String SUCCESS_LEBEL = "Success";
	public static final String SKIPPED_LEBEL = "Skipped";
	public static final String SIMNO_PREFIX = "89965";
	public static final String INVALID_FILE = "Invalid Request File";
	public static final String MODE_SINGLE = "Single";
	public static final String MODE_BULK = "Bulk";
	public static final String FATAL_ERROR_LABEL = "Fatal Error";
	public static final String OK_LABEL = "OK";
	
	public static final String DF_YYMMDDHHMMSS = "yyMMddhhmmss";
	public static final String DF_YYYYMMDD = "yyyy-MM-dd";
	public static final String DF_YYYYMMDD_HHMMSS = "yyyy-MM-dd hh:mm:ss";
	
	public static final SimpleDateFormat SDF_YYYYMMDD_HHMMSS = new SimpleDateFormat(DF_YYYYMMDD_HHMMSS);
	
	public static final String SLASH = "/";
	public static final String STAR = "*";
	public static final String USCORE = "_";
	public static final String COMMA = ",";
	public static final String COLON = ":";
	public static final String BLANK = "";
	public static final String HYPHEN = "-";
	public static final String HASH = "#";
	public static final String OP_FILE_EXT_TXT = ".txt";
	public static final String OP_FILE_EXT_CSV = ".csv";
	public static final String OP_FILE_EXT_XML = ".xml";
	
	public static final String HEADER_KEY_AUDIT_EVENT="AUDIT_EVENT";
	public static final String HEADER_KEY_ACTION_USER="ACTION_USER";
	public static final String HEADER_KEY_CHANNEL="CHANNEL";
	public static final String SFTP_CHANNEL="sftp";
	public static final String BU_ID_KEY="BU_ID";
	public static final String BU_ID_VALUE="1";
	public static final String CURRENCY_CODE="KWD";
	
	public static final Locale ENGLISH = Locale.ENGLISH;
	public static final Locale ARABIC = new Locale("ar");
	
	public static final String HOME_PAGE="/portal/home.xhtml";
	public static final String CLEANUP_PAGE="/portal/cleanupProfile.xhtml";
	public static final String MANAGE_TASK_PAGE="/portal/manageAssignment.xhtml";
	public static final String RESET_PASSWORD_PAGE="/resetPassword.xhtml";
	
	public static final String ERROR_MESSAGE_JSON_TAG="message";
	public static final String GENERIC_ERROR_MESSAGE = "Fatal Error. Please contact administrator or check logs for more details";
	
	public static final String DIRECT_DEBIT_CHANNEL_GUI = "gui";
	
	public static final String SUBS_DATA_EXT_SERVICE_SUCCESS_ECODE = "GEN000";
	public static final String SUBS_DATA_EXT_SERVICE_SUCCESS_EMSG = "Success";
	public static final String SUBS_DATA_EXT_SERVICE_CONTEXT = "com.ooredoo.wsclient.getcustomerprofile.proxy";
	
	public static final String SEND_EMAIL_SUCCESS_MESSAGE = "Success";
	public static final String SEND_EMAIL_SERVICE_CONTEXT = "com.ooredoo.wsclient.emailwithattachmentproxy";
	
	public static final String CREATE_SUBSCRIBER_SERVICE_CONTEXT = "com.ooredoo.createsubs";
	public static final String GET_CUSTOMERCID_SERVICE_CONTEXT = "com.ooredoo.service.wsclient.b2bcustomercid";
	public static final String CREATE_ORDER_SERVICE_CONTEXT = "com.ooredoo.createorder";
	public static final String CREATE_B2BCPR_SUBSCRIBER_SERVICE_CONTEXT = "com.ooredoo.service.wsclient.createb2bsubscriberproxy";
	public static final String NUMBER_RESERVATION_SERVICE_CONTEXT = "com.ooredoo.wsclient.numberreservation";
	public static final String SIM_SERIAL_RESERVATION_SERVICE_CONTEXT = "com.ooredoo.service.wsclient.oraappservicenmtc";
	public static final String NUMBER_UN_RESERVATION_SERVICE_CONTEXT = "com.ooredoo.service.wsclient.numberunreservepostpaidservice";
	public static final String ANA_ONBOARDING_SERVICE_CONTEXT = "com.ooredoo.wsclient.anaonboarding";
	
	public static final String HLR_DATA_EXT_SERVICE_CONTEXT = "com.ooredoo.wsclient.dashboardhlr";
	
	
	public static final String NUMBER_CLEANUP_GUI = "NUMBER_CLEANUP_GUI";
	public static final String NC_ADMIN_SERVICE = "NC_ADMIN_SERVICE";
	public static final String NC_BSCS_SERVICE = "NC_BSCS_SERVICE";
	public static final String NC_ERP_SERVICE = "NC_ERP_SERVICE";
	public static final String NC_NMS_SERVICE = "NC_NMS_SERVICE";
	public static final String NC_RAS_SERVICE = "NC_RAS_SERVICE";
	
	//HLR
	public static  final String SUCCESS_RESPONSE_CODE = "0";
	public static  final String SUBSCRIBER_NOT_FOUND_ERROR = "3001";
	public static  final String IMSI_NOT_FOUND_ERROR = "900003113";
	public static  final String SUBS_NOT_FOUND_ERROR = "900003051";
	public static  final String VENSTB_NOT_FOUND_ERROR = "300003308";
	public static  final String USER_AGENT =  "Jakarta Commons-HttpClient/3.1";
	public static  final String SOAP_ACTION = "HSSV900R009C00SPC208#RMV_SUB";
	public static  final String CONTENT_TYPE = "text/xml;charset=UTF-8";
	public static  final String ACCEPTED_ENCODING = "gzip,deflate";
	public static final String PLUS = "+";
	public static final String SPLITTER = "@";
	
	//System Names
	public static final String BSCS = "BSCS";
	public static final String CS = "CS";
	public static final String ESM = "ESM";
	public static final String HLR = "HLR";
	public static final String PCRF = "PCRF";
	public static final String RAS = "RAS";
	public static final String ERP = "ERP";
	public static final String NMS = "NMS";
	public static final String SPR = "SPR";
}
