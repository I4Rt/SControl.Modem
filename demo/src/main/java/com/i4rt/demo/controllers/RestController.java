package com.i4rt.demo.controllers;

import com.i4rt.demo.interfaces.ModeRepo;
import com.i4rt.demo.interfaces.RegisterRepo;
import com.i4rt.demo.interfaces.ScriptRepo;
import com.i4rt.demo.model.Mode;
import com.i4rt.demo.model.Register;
import com.i4rt.demo.model.Script;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private final ModeRepo modeRepo;
    @Autowired
    private final RegisterRepo registerRepo;

    @Autowired
    private final ScriptRepo scriptRepo;

    public RestController(ModeRepo modeRepo, RegisterRepo registerRepo, ScriptRepo scriptRepo) {
        this.modeRepo = modeRepo;
        this.registerRepo = registerRepo;
        this.scriptRepo = scriptRepo;
    }


    @RequestMapping(value = "saveMode", method = RequestMethod.POST)
    public String saveMode(@RequestBody String rowJsonData) throws JSONException {
        try{
            System.out.println(rowJsonData);
            org.json.JSONObject data = new org.json.JSONObject(rowJsonData);
            System.out.println(data.getString("name"));
            Mode modeCheckList = modeRepo.getModeByName(data.getString("name"));
            System.out.println(modeCheckList);



            if(modeCheckList != null){
                return "Режим с таим именем уже существует";
            }



            Mode newMode = new Mode();
            newMode.setName(data.getString("name"));
            JSONArray registersId = data.getJSONArray("registers");
            Set<Register> registers = new HashSet<>();
            System.out.print(registersId);
            System.out.print(registersId.length());

            List<Long> registersIdLong = new ArrayList<>();
            for(Integer i = 0; i < registersId.length(); i++){
                registersIdLong.add(registersId.getLong(i));
            }

            for(int i = 0; i < registersIdLong.size(); i++){
                Register register = registerRepo.getById(registersIdLong.get(i));
                registers.add(register);
            }
            newMode.setRegisters(registers);
            modeRepo.save(newMode);
        }catch (Exception e){
            System.out.println(e);
            return "Что-то пошло не так";
        }

        return "Режим успешно сохранен";
    }

    @RequestMapping(value = "saveScript", method = RequestMethod.POST)
    public String saveScript(@RequestBody String rowJsonData) throws JSONException {
        try{
            System.out.println(rowJsonData);
            org.json.JSONObject data = new org.json.JSONObject(rowJsonData);
            System.out.println(data.getString("name"));
            Script scriptCheckList = scriptRepo.getScriptByName(data.getString("name"));
            System.out.println(scriptCheckList);

            if(scriptCheckList != null){
                return "Скрипт с таим именем уже существует";
            }


            Script newScript = new Script();
            newScript.setName(data.getString("name"));
            JSONArray modesId = data.getJSONArray("modes");
            Set<Mode> modes = new HashSet<>();
            System.out.print(modesId);
            System.out.print(modesId.length());

            List<Long> modesIdLong = new ArrayList<>();
            for(Integer i = 0; i < modesId.length(); i++){
                modesIdLong.add(modesId.getLong(i));
            }

            for(int i = 0; i < modesIdLong.size(); i++){
                Mode register = modeRepo.getById(modesIdLong.get(i));
                modes.add(register);
            }
            newScript.setModes(modes);
            scriptRepo.save(newScript);
        }catch (Exception e){
            System.out.println(e);
            return "Что-то пошло не так";
        }

        return "Режим успешно сохранен";
    }

    @RequestMapping(value = "updateScript", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateScript(@RequestBody String rowJsonData) throws JSONException {
        try{
            System.out.println("updating");
            System.out.println(rowJsonData);
            org.json.JSONObject data = new org.json.JSONObject(rowJsonData);
            System.out.println(data.getString("name"));


            Script newScript = scriptRepo.getById(data.getLong("id"));
            newScript.setName(data.getString("name"));
            JSONArray modesId = data.getJSONArray("modes");
            Set<Mode> modes = new HashSet<>();
            System.out.print(modesId);
            System.out.print(modesId.length());

            List<Long> modesIdLong = new ArrayList<>();
            for(Integer i = 0; i < modesId.length(); i++){
                modesIdLong.add(modesId.getLong(i));
            }

            for(int i = 0; i < modesIdLong.size(); i++){
                Mode register = modeRepo.getById(modesIdLong.get(i));
                modes.add(register);
            }
            newScript.setModes(modes);
            scriptRepo.save(newScript);
        }catch (Exception e){
            System.out.println(e);
            return "Что-то пошло не так";
        }

        return "Сценарий успешно сохранен";
    }
}