package com.ooredoo.wsclient.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.serializer.DateSerializer;
import org.apache.xmlrpc.serializer.TypeSerializer;
import org.xml.sax.SAXException;

public class CSTypefactory extends TypeFactoryImpl {

	private DateFormat newFormat() {
		//20180527T13:20:39+0300
        return new SimpleDateFormat("yyyyMMdd'T'HH:mm:ssZ");
    }
	
	public CSTypefactory(XmlRpcController pController) {
		super(pController);
	}
	
	public TypeSerializer getSerializer(XmlRpcStreamConfig pConfig, Object pObject) throws SAXException  {  
        if (pObject instanceof String){
            return new CSStringSerializer();
        } else if(pObject instanceof Date) {
        	return new DateSerializer(newFormat());
        }else{
            return super.getSerializer(pConfig, pObject);
        }
    }
}
