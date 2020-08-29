package tech.aistar.pojo.dto;

/**
 * 本类功能:DTO
 *
 * @author cxylk
 * @date 2020/8/29 10:43
 */
public class CustomerDTO {
    private Integer id;
    private String cname;
    private long count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomerDTO{");
        sb.append("id=").append(id);
        sb.append(", cname='").append(cname).append('\'');
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
