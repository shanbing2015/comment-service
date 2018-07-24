package top.shanbing.domain.model.result;

import java.util.List;

public class PageResult<T> {

    private List <T> list;

    private int pageSize = 10;
    private int pageNum = 1;

    public PageResult() {
    }

    public PageResult(int pageSize,int pageNum, List <T> list) {
        this.list = list;
        this.pageNum = pageNum;
    }

    public List <T> getList() {
        return list;
    }

    public void setList(List <T> list) {
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
