package com.ecw.admin.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Where;


import java.io.Serializable;

@Entity
@Table(name = "prizemaster")
@Where(clause = " delflag=0")
public class PrizeEntity implements Serializable {

    @Id
    @Column(name="prizeid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="prizename")
    String prizename;

    @Column(name="imagename")
    String imagename;

    @Column(name = "rules")
    String rules;

    public String getPrizename() {
        return prizename;
    }

    public void setPrizename(String prizename) {
        this.prizename = prizename;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
