package com.mrwind.windbase.vo;

import com.mrwind.windbase.entity.mysql.User;

import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/3
 */

public class UserInfoVO {

    private String userId;
    private String userName;
    private String name;
    private Object avatar;
    private String tel;
    private Object currentCountry;
    private Object currentTeamId;
    private int countryCode;
    private boolean member;
    private String idCardNo;
    private Integer age;
    private String homeAddress;
    private Double homeLat;
    private Double homeLng;
    private boolean isCourier;
    private String rootTeamId;
    private String rootTeamName;
    private String teamId;
    private String teamName;
    private boolean manager;
    private List<AccountVO> accounts;
    private String windId;
    private long windIdUpdateCount;
    private Integer receiveOrder;
    private Boolean hasPayPwd;

    public UserInfoVO() {
    }

    public UserInfoVO(User user, CourierSettingsVO courierSettings) {
        setUserId(user.getUserId());
        setUserName(user.getUserName());
        setName(user.getName());
        setWindId(user.getWindId());
        setAvatar(user.getAvatar());
        setTel(user.getTel());
        setCurrentCountry(user.getCurrentCountry());
        setCurrentTeamId(user.getCurrentTeamId());
        setCountryCode(user.getCountryCode());
        if (courierSettings != null) {
            setIdCardNo(courierSettings.getIdCardNo());
            setAge(courierSettings.getAge());
            setHomeAddress(courierSettings.getHomeAddress());
            setHomeLat(courierSettings.getHomeLat());
            setHomeLng(courierSettings.getHomeLng());
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Object getCurrentCountry() {
        return currentCountry;
    }

    public void setCurrentCountry(Object currentCountry) {
        this.currentCountry = currentCountry;
    }

    public Object getCurrentTeamId() {
        return currentTeamId;
    }

    public void setCurrentTeamId(Object currentTeamId) {
        this.currentTeamId = currentTeamId;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Double getHomeLat() {
        return homeLat;
    }

    public void setHomeLat(Double homeLat) {
        this.homeLat = homeLat;
    }

    public Double getHomeLng() {
        return homeLng;
    }

    public void setHomeLng(Double homeLng) {
        this.homeLng = homeLng;
    }

    public boolean getIsCourier() {
        return isCourier;
    }

    public void setIsCourier(boolean courier) {
        isCourier = courier;
    }

    public String getRootTeamId() {
        return rootTeamId;
    }

    public void setRootTeamId(String rootTeamId) {
        this.rootTeamId = rootTeamId;
    }

    public String getRootTeamName() {
        return rootTeamName;
    }

    public void setRootTeamName(String rootTeamName) {
        this.rootTeamName = rootTeamName;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<AccountVO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountVO> accounts) {
        this.accounts = accounts;
    }

    public String getWindId() {
        return windId;
    }

    public void setWindId(String windId) {
        this.windId = windId;
    }

    public long getWindIdUpdateCount() {
        return windIdUpdateCount;
    }

    public void setWindIdUpdateCount(long windIdUpdateCount) {
        this.windIdUpdateCount = windIdUpdateCount;
    }

    public Integer getReceiveOrder() {
        return receiveOrder;
    }

    public void setReceiveOrder(Integer receiveOrder) {
        this.receiveOrder = receiveOrder;
    }

    public Boolean getHasPayPwd() {
        return hasPayPwd;
    }

    public void setHasPayPwd(Boolean hasPayPwd) {
        this.hasPayPwd = hasPayPwd;
    }

}

