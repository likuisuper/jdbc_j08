package tech.aistar.pojo.entity;

import java.io.Serializable;
import java.util.Set;

public class Customer implements Serializable {

  private Integer id;

  private String cname;

  private String phone;

  private Set<Ord> orders;

  public Customer() {
  }

  public Customer(String cname, String phone) {
    this.cname = cname;
    this.phone = phone;
  }

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


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Set<Ord> getOrders() {
    return orders;
  }

  public void setOrders(Set<Ord> orders) {
    this.orders = orders;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Customer{");
    sb.append("id=").append(id);
    sb.append(", cname='").append(cname).append('\'');
    sb.append(", phone='").append(phone).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
