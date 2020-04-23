package main.java.br.com.baronheid.entity;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "TBL_JAX_USER")
@SequenceGenerator(name = "sqUser", sequenceName = "SEQ_USER", allocationSize = 1)
//Lombok to avoid boilerplate
@Data
//This resource will be exposed as a XML and that's why the follow annotation is used
@XmlRootElement(name = "User")
public class User implements Serializable {

    private static final long serialVersionUID = 213231L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqUser")
    @Column(name = "id_user")
    private int userId;

    @Column(name = "nm_user", nullable = false, length = 100)
    private String name;

    @Column(name = "ds_email", nullable = false, unique = true, length = 100)
    private String email;

    @XmlElement(name = "userId", required = true)
    public int getUserId(){
        return userId;
    }

    @XmlElement(name = "name", required = true)
    public String getName() {
        return name;
    }

    @XmlElement(name = "email", required = true)
    public String getEmail(){
        return email;
    }


}
