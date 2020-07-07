package com.mrwind.windbase.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-12-29
 */

public class WindChatLevelVO {

    private boolean valid;

    private boolean memberIsManager;

    private List<BasicUserVO> up = new ArrayList<>();

    private List<BasicUserVO> down = new ArrayList<>();

    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean getMemberIsManager() {
        return memberIsManager;
    }

    public void setMemberIsManager(boolean memberIsManager) {
        this.memberIsManager = memberIsManager;
    }

    public List<BasicUserVO> getUp() {
        return up;
    }

    public void setUp(List<BasicUserVO> up) {
        this.up = up;
    }

    public List<BasicUserVO> getDown() {
        return down;
    }

    public void setDown(List<BasicUserVO> down) {
        this.down = down;
    }

}
