package tech.aistar.pojo.dto;

import java.util.List;

/**
 * 本类功能:分页bean
 * 采用泛型提高通用性
 *
 * @author cxylk
 * @date 2020/8/29 14:49
 */
public class PageBean<T> {
    //真实的分页的数据
    private List<T> datas;

    //当前页
    private Integer pageNow;

    //每页显示的条数
    private Integer pageSize;

    //总的条数
    private long rows;

    //总的页数
    private Integer pageCounts;

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Integer getPageNow() {
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public long getRows() {
        return rows;
    }

    public void setRows(long rows) {
        this.rows = rows;
    }

    public Integer getPageCounts() {
        return pageCounts;
    }

    public void setPageCounts(Integer pageCounts) {
        this.pageCounts = pageCounts;
    }
}
