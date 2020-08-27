package tech.aistar.day01.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 本类功能:实体类
 *
 * @author cxylk
 * @date 2020/8/27 11:12
 */
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private Date birthday;

    private Gender gender;

    public User() {
    }

    public User(String username, String password, Date birthday, Gender gender) {
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", birthday=").append(birthday);
        sb.append(", gender=").append(gender);
        sb.append('}');
        return sb.toString();
    }
}
