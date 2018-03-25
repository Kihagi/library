package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mathenge on 11/21/2017.
 */
@Entity
@Table(name = "tbl_users")
public class User extends Common {

    public static final String USERNAME = "username";

    @Column(name = "username")
    private String username = "";

    @Column(name = "password")
    private String password = "";

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
}
