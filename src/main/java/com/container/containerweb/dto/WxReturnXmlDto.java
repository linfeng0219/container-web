package com.container.containerweb.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class WxReturnXmlDto {

    @XmlElement(name = "return_code")
    private String returnCode;

    @XmlElement(name = "return_msg")
    private String returnMsg;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public static WxReturnXmlDto success(){
        WxReturnXmlDto dto = new WxReturnXmlDto();
        dto.setReturnCode("SUCCESS");
        dto.setReturnMsg("OK");
        return dto;
    }
}
