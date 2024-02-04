package org.example.basic.global.utils.page;

import lombok.Getter;

@Getter
public class PageUtil {

    private final int PAGE_NUM = 9;
    private int pageSize;
    private int startPage;
    private int endPage;
    private int currentPage;
    private int totalPage;
    private boolean prev, next;

    private long total;

    public PageUtil() {
    }

    public PageUtil(long total, int page, int size, int totalPage) {
        this.total = total;
        this.currentPage = page;
        this.pageSize = size;
        this.totalPage = totalPage;

        this.startPage = ((currentPage - 1) / PAGE_NUM) * PAGE_NUM + 1;
        this.endPage = startPage + PAGE_NUM - 1;

        int realEnd = (int) (Math.ceil((total * 1.0) / pageSize));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = (currentPage) > 1;
        this.next = (currentPage) < realEnd;
    }
}
