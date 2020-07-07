package com.mrwind.windbase.dto;

import java.util.List;

/**
 * Created by CL-J on 2018/12/11.
 */
public class CreateReciverDTO {

    private String tel;

    private String name;

    private List<AddressDTO> addresses;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
}
