package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by CL-J on 2018/7/26.
 */
public class SearchUserDTO {
    @NotNull(message = "error.parameter")
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
