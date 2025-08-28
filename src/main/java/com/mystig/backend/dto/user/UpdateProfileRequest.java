package com.mystig.backend.dto.user;

import com.mystig.backend.model.embedded.Address;
import com.mystig.backend.model.embedded.BusinessInfo;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String name;
    private String phone;
    private String avatar;
    private Address address;
    private BusinessInfo businessInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BusinessInfo getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
    }
}
