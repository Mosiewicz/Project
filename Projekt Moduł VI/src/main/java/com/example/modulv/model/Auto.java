package com.example.modulv.model;

import lombok.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String model;
    private String rprod;
    private String paliwo;

    @ManyToMany(mappedBy = "auta")
    private Set<Producent> producenci = new HashSet<>();

    public Auto(){
    }
    public Auto(String model, String rprod, String paliwo){
        this.model = model;
        this.rprod = rprod;
        this.paliwo = paliwo;
    }

}
