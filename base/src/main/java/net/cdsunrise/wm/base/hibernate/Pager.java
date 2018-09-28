package net.cdsunrise.wm.base.hibernate;

import java.util.List;

/**
 * @author lijun
 * @date 2018-04-02.
 */
public class Pager<T> {
    /**
     * 当前页 从0开始
     */
    private Integer number;
    /**
     * 每页数量
     */
    private Integer pageSize;
    private Integer totalPages;
    private Integer totalElements;
    private List<T> content;

    public Pager(int number, int pageSize, int totalElements, List<T> content) {
        this.number = number;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.content = content;
        if (totalElements <= 0) {
            this.totalPages = 1;
        } else {
            this.totalPages = (int) Math.ceil((double) totalElements / (double) pageSize);
        }
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
