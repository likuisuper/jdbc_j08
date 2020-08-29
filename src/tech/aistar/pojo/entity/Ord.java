package tech.aistar.pojo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Ord implements Serializable {

  private Integer id;
  private String ordno;
  private Double total;
  private Date createDate;

  public Ord() {
  }

  public Ord(Double total, Date createDate) {
    this.ordno = UUID.randomUUID().toString();
    this.total = total;
    this.createDate = createDate;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getOrdno() {
    return ordno;
  }

  public void setOrdno(String ordno) {
    this.ordno = ordno;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Ord{");
    sb.append("id=").append(id);
    sb.append(", ordno='").append(ordno).append('\'');
    sb.append(", total=").append(total);
    sb.append(", createDate=").append(createDate);
    sb.append('}');
    return sb.toString();
  }
}
