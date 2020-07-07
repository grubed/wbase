package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by zjw on 2018/8/22  下午5:36
 */
public class AccountSearchDTO {
    @NotNull
    private String startTime;

    @NotNull
    private String endTime;

    @NotNull
    private int page;

    @NotNull
    private int size;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
