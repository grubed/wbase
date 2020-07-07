package com.mrwind.windbase.vo;

import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/1
 */

public class UserDetailsInfoVO {

    private String userId;

    private String userName;

    private String name;

    private String avatar;

    private String tel;

    private Integer countryCode;

    private String imId;

    /**
     * 被查看的人是否为自己的下属
     */
    private boolean member;

    /**
     * 是否为当前所在分组管理员（不是那个根团队，现在一个人只能在一个分组）
     */
    private boolean manager;

    /**
     * 前所在团队名字
     */
    private String teamName;

    /**
     * 前所在团队 id
     */
    private String teamId;

    private String idCardNo;
    private Integer age;
    private String homeAddress;
    private Double homeLat;
    private Double homeLng;

    private UserAttendanceVO attendance;

    private List<RoleVO> roles;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    public UserAttendanceVO getAttendance() {
        return attendance;
    }

    public void setAttendance(UserAttendanceVO attendance) {
        this.attendance = attendance;
    }

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
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

}
