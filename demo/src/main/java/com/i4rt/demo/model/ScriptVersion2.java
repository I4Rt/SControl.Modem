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
public class ScriptVersion2 {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition="TEXT")
    private String rowCommandsJsonRow;
}
