package com.farmexercise.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Farm extends AbstractPersistable<Long> {
    
    @Id
    @GeneratedValue(generator = "farm_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "farm_id_seq", sequenceName = "farm_id_seq")
    private Long id;
    private String nimi;
    
    @ManyToMany(mappedBy = "farmit")
    private List<User> kayttajat = new ArrayList<>();
}
