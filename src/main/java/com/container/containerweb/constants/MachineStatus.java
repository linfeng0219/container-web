package com.container.containerweb.constants;

public enum MachineStatus {

    ONLINE(1000),

    OFFLINE(9999);

    private int code;

    MachineStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
