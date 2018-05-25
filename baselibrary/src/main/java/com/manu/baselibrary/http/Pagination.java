package com.manu.baselibrary.http;

import java.util.ArrayList;

/**
 * Pagination.java
 * @author jzman
 * create at 2018/5/21 0021 10:39
 */
public class Pagination<T> {
    public int page = 1;
    public int pageSize = 10;
    public int total = 0;

    public ArrayList<T> rows = new ArrayList<>();

    public boolean hasNextPage() {
        return total > 0 && (double) total / pageSize > page;
    }

    public boolean isFirstPage() {
        return page == 1;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Pagination setFirstPage() {
        page = 1;
        return this;
    }

    public Pagination nextPage() {
        if (hasNextPage()) {
            page++;
        }
        return this;
    }

    protected String appendToUrl(String url) {
        if (url.contains("?")) {
            return url + "&page=" + page + "&pageSize=" + pageSize;
        } else {
            return url + "?page=" + page + "&pageSize=" + pageSize;
        }
    }

}
