package com.i4rt.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@Table(name="registers")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String input;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "data_id", referencedColumnName = "id")
    private Data data;


    @ManyToMany(mappedBy = "registers")
    private Set<Mode> modes = new HashSet<>();

    public Register(String name, String input){
        this.name = name;
        this.input = input;
        this.data = new Data(input);
        System.out.println(data);
    }

}
