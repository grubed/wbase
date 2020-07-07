package com.mrwind.windbase.vo;

import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/10/19
 */

public class PageListVO<T> {

    private long total;

    private List<T> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
