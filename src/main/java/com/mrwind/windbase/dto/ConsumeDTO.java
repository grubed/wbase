package com.mrwind.windbase.dto;


import java.util.List;

public class ConsumeDTO {
    private String amountTotal;

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    private String rootId;
    private List<InventoryDTO> list;

    public String getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(String amountTotal) {
        this.amountTotal = amountTotal;
    }


    public List<InventoryDTO> getList() {
        return list;
    }

    public void setList(List<InventoryDTO> list) {
        this.list = list;
    }
}

