package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;

public class IdDTO {
    @NotNull
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
