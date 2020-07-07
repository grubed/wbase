package com.mrwind.windbase.vo;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-16
 */

public class UserBasicInfoVO {

    private String userId;
    private String name;
    private String avatar;
    private String tel;
    private String currentCountry;
    private String rootTeamId;
    private String rootTeamName;
    private String teamId;
    private String teamName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCurrentCountry() {
        return currentCountry;
    }

    public void setCurrentCountry(String currentCountry) {
        this.currentCountry = currentCountry;
    }

    public String getRootTeamId() {
        return rootTeamId;
    }

    public void setRootTeamId(String rootTeamId) {
        this.rootTeamId = rootTeamId;
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

    public String getRootTeamName() {
        return rootTeamName;
    }

    public void setRootTeamName(String rootTeamName) {
        this.rootTeamName = rootTeamName;
    }

}
