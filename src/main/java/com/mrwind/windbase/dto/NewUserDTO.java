package com.mrwind.windbase.dto;

/**
 * Created by CL-J on 2019/3/25.
 */
public class NewUserDTO {
    /**
     {
     "id":"",
     "name": "",
     "tel":"",
     "avatar": "",
     "address": {
        "userAddress":"王道公园111",
        "userAddressDetail":"建业路",
        "shopId":"",
        "userCountry":"",
        "userProvince":"",
        "userCity":"",
        "userDistrict":"",
        "userLandMark":"",
        "isDefault":0,
        "userLat":120.233,
        "userLng":30.233
        }
     }
     */
    private String id;

    private  String name;

    private  String tel;

    private String  avatar;

    private  AddressDTO  address;

    public NewUserDTO() {
    }

    public NewUserDTO(String name, String tel, String avatar, AddressDTO address) {
        this.name = name;
        this.tel = tel;
        this.avatar = avatar;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddressDto(AddressDTO address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "NewUserDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address=" + address +
                '}';
    }
}
