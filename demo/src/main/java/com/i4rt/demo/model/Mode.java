package com.i4rt.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Mode {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "mode_register",
            joinColumns = { @JoinColumn(name = "mode_id") },
            inverseJoinColumns = { @JoinColumn(name = "register_id") }
    )
    private Set<Register> registers = new HashSet<>();

    @ManyToMany(mappedBy = "modes")
    private Set<Script> scripts = new HashSet<>();

    public JSONObject getJson(){
        JSONObject json= new JSONObject();
        JSONArray registersArrayId = new JSONArray();
        for (int i = 0; i < registers.size(); i++){
            registersArrayId.add(((Register)(registers.toArray()[i])).getId());
        }

        json.put("id", this.id);
        json.put("name", this.name);
        json.put("registers", registersArrayId);

        return json;
    }



}
