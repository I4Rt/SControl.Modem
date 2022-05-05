package com.i4rt.demo.model;


import lombok.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Script {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "script_mode",
            joinColumns = { @JoinColumn(name = "script_id") },
            inverseJoinColumns = { @JoinColumn(name = "mode_id") }
    )
    private Set<Mode> modes = new HashSet<>();

    public JSONObject getJson(){
        JSONObject json= new JSONObject();
        JSONArray modesArrayId = new JSONArray();
        for (int i = 0; i < modes.size(); i++){
            modesArrayId.add(((Mode)(modes.toArray()[i])).getId());
        }

        json.put("id", this.id);
        json.put("name", this.name);
        json.put("modes", modesArrayId);

        return json;
    }

}
