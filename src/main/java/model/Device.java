package model;

import java.io.Serializable;

public class Device implements Serializable {
    private int deviceId;
    private String deviceName;
    private String deviceType;
    private double unitPrice;
    private int quantity;

    public Device() {
    }

    public Device(String deviceName, String deviceType, double unitPrice, int quantity) {
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
