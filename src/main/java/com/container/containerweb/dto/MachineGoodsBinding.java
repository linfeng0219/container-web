package com.container.containerweb.dto;

import java.util.List;
import java.util.stream.Collectors;

public class MachineGoodsBinding {
    private String serial;

    private List<GoodsIdxCode> IdxCode;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public List<GoodsIdxCode> getIdxCode() {
        return IdxCode;
    }

    public void setIdxCode(List<GoodsIdxCode> idxCode) {
        IdxCode = idxCode;
    }

    public List<String> getCodes() {
       return this.IdxCode.stream().map(GoodsIdxCode::getCode).collect(Collectors.toList());
    }
}
