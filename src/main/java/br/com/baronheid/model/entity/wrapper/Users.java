package main.java.br.com.baronheid.model.entity.wrapper;

import main.java.br.com.baronheid.model.entity.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Users {

    private List<User> users = new ArrayList<>();

    public Users() {}

    public Users(List<User> users) {
        this.users = users;
    }

    @XmlElement(name = "user")
    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
