package com.mrwind.windbase.vo;

import com.mrwind.windbase.entity.mysql.ExpressBillingMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-12-13
 */

public class UserExpressBillingModeVO {

    private List<UserModeInfo> userModeInfos = new ArrayList<>();
    private List<ExpressBillingMode> allModes = new ArrayList<>();

    public List<UserModeInfo> getUserModeInfos() {
        return userModeInfos;
    }

    public void setUserModeInfos(List<UserModeInfo> userModeInfos) {
        this.userModeInfos = userModeInfos;
    }

    public List<ExpressBillingMode> getAllModes() {
        return allModes;
    }

    public void setAllModes(List<ExpressBillingMode> allModes) {
        this.allModes = allModes;
    }

    public static class UserModeInfo {
        private String userId;
        private String userName;
        private String tel;
        private String modeId;

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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getModeId() {
            return modeId;
        }

        public void setModeId(String modeId) {
            this.modeId = modeId;
        }
    }


}
