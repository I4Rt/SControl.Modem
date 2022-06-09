package com.i4rt.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import javax.persistence.*;
import java.io.IOException;
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
    private String modeValue;

    private boolean isSet = false;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "mode_register",
            joinColumns = { @JoinColumn(name = "mode_id") },
            inverseJoinColumns = { @JoinColumn(name = "register_id") }
    )
    private Set<Register> registers = new HashSet<>();

    @ManyToMany(mappedBy = "modes")
    private Set<Script> scripts = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mode_data_id", referencedColumnName = "id")
    private ModeData modeData;

    public JSONObject getJson(){
        JSONObject json= new JSONObject();
        JSONArray registersArrayId = new JSONArray();
        for (int i = 0; i < registers.size(); i++){
            registersArrayId.add(((Register)(registers.toArray()[i])).getId());
        }

        json.put("id", this.id);
        json.put("name", this.name);
        json.put("modeValue", this.modeValue);
        json.put("registers", registersArrayId);

        return json;
    }

    public String calibrateData(){
        modeData.sendModeData();

        System.out.println("\n\n");
        List<String> allAnswers =  new ArrayList<>();
        String answer = "";
        try {
            for(Register register :registers){
                    answer = register.sendData();
                    allAnswers.add(answer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < allAnswers.size(); i++){
            System.out.println(allAnswers.get(i));
        }

        return allAnswers.toString();
    }

}
