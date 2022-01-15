package com.farmexercise.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq")
    private Long id;
    private String kayttaja;
    private String salasana;

    @ManyToMany
    private List<Farm> farmit = new ArrayList<>();

    // Getterit
    public Long getIdd() {
        return id;
    }

    public String getUsername() {
        return kayttaja;
    }

    public String getPassword() {
        return salasana;
    }

    public List<Farm> getFarm() {
        return farmit;
    }

    // Setterit
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.kayttaja = username;
    }

    public void setPassword(String password) {
        this.salasana = password;
    }

    public void setFarm(List<Farm> farm) {
        this.farmit = farm;
    }
}