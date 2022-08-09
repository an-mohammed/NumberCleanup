package com.ooredoo.nc.gui.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ooredoo.nc.dto.ProfileStatus;
import com.ooredoo.nc.dto.StatusRequest;
import com.ooredoo.nc.dto.StatusResponse;
import com.ooredoo.nc.exception.ApplicationException;
import com.ooredoo.nc.utility.ApplicationConstants;

@Named(value="ps")
@ViewScoped
public class ProfileStatusBean extends ManagedBean  implements Serializable, ApplicationConstants {

	private static final long serialVersionUID = 1L;
	private String content;
	private List<ProfileStatus> profileStatus;
	private boolean execStatus = false;
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileStatusBean.class);
	
	public void submit() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			execStatus = true;
			StatusRequest req = new StatusRequest();
			List<ProfileStatus> profiles = new ArrayList<ProfileStatus>();
			req.setProfileStatus(profiles);
			
			if(null != content && !content.isEmpty()) {
				
				String[] rec = content.split(COMMA);
				ProfileStatus ps = null;
				
				for(int i = 0; i < rec.length; i++) {
					String[] fields = rec[i].split("#");
					ps = new ProfileStatus();
					ps.setMsisdn(fields[0]);
					ps.setSimNo(fields[1]);
					req.getProfileStatus().add(ps);
				}
				
				StatusResponse profileStatusResp = getConfigService().getProfileStatus(req);
				
				if(null != profileStatusResp) {
					if(null != profileStatusResp.getErrorCode() && profileStatusResp.getErrorCode().equals("0")) {
						profileStatus = profileStatusResp.getProfileStatus();
					} else {
						throw new ApplicationException(profileStatusResp.getErrorMessage(), true);
					}
					
				} else {
					throw new ApplicationException("No response received", true);
				}
			} else {
				throw new ApplicationException("No profile given for status check", true);
			}
			
		} catch(ApplicationException e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							e.getErrorMessage()));
			LOGGER.error("Error Occurred while extracting profile status. Cause", e);
			
		} catch(Exception e) {
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error",
							getFatalErrorMessage()));
			LOGGER.error("Fatal exception occurred. Cause", e);
			
		} finally {
			if(null != this.content) {
				this.content = null;
			}
		}
	}



	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getExecStatus() {
		return execStatus;
	}

	public void setExecStatus(boolean execStatus) {
		this.execStatus = execStatus;
	}

	public List<ProfileStatus> getProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(List<ProfileStatus> profileStatus) {
		this.profileStatus = profileStatus;
	}
}
