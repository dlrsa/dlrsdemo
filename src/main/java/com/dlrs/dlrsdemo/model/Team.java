package com.dlrs.dlrsdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long teamId;
    private String teamName;

    @ManyToOne
    @JoinColumn(name = "supervisor")
    private AppUser supervisor;

    @OneToMany
    @JoinColumn(name = "surveyors")
    List<AppUser> surveyors;
}
