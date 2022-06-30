package com.i4rt.demo.controllers;

import com.i4rt.demo.RequestObjects.ScriptCommandsRequest;
import com.i4rt.demo.interfaces.ModeRepo;
import com.i4rt.demo.interfaces.RegisterRepo;
import com.i4rt.demo.interfaces.ScriptRepo;
import com.i4rt.demo.interfaces.ScriptVersion2Repo;
import com.i4rt.demo.model.*;
import com.i4rt.demo.model.CCDLogic.CCDCommands.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @Autowired
    private final ScriptVersion2Repo scriptVersion2Repo;

    public RestController(ModeRepo modeRepo, RegisterRepo registerRepo, ScriptRepo scriptRepo, ScriptVersion2Repo scriptVersion2Repo) {
        this.modeRepo = modeRepo;
        this.registerRepo = registerRepo;
        this.scriptRepo = scriptRepo;
        this.scriptVersion2Repo = scriptVersion2Repo;
    }


    @RequestMapping(value = "updateMode", method = RequestMethod.POST)
    public String updateMode(@RequestBody String rowJsonData) throws JSONException {
        try{
            System.out.println(rowJsonData);
            org.json.JSONObject data = new org.json.JSONObject(rowJsonData);
            System.out.println(data.getString("name"));
            Mode modeCheckList = modeRepo.getModeByName(data.getString("name"));
            System.out.println(modeCheckList);

            Mode newMode = modeRepo.getById(data.getLong("id"));
            newMode.setName(data.getString("name"));

            newMode.setModeValue(data.getString("modeValue"));
            ModeData modeData= newMode.getModeData();
            modeData.setModeRegisterValueBin(newMode.getModeValue());
            newMode.setModeData(modeData);

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

        return "Режим успешно обновлен";
    }

    @RequestMapping(value = "deleteMode", method = RequestMethod.POST)
    public String deleteMode(@RequestBody Long id){
        modeRepo.deleteById(id);
        return "Режим удален";
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
            newMode.setModeValue(data.getString("modeValue"));
            ModeData modeData= new ModeData(newMode.getModeValue());
            newMode.setModeData(modeData);
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
                Mode mode = modeRepo.getById(modesIdLong.get(i));
                modes.add(mode);
            }
            newScript.setModes(modes);
            scriptRepo.save(newScript);
        }catch (Exception e){
            System.out.println(e);
            return "Что-то пошло не так";
        }

        return "Сценарий успешно сохранен";
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

    @RequestMapping(value = "/runMode", method = RequestMethod.POST)
    public String getScript(@RequestBody Long id) {

        List<Mode> modes = modeRepo.getAllActiveModes();

        for (Mode mode : modes) {
            mode.setSet(false);
            modeRepo.save(mode);
        }

        Mode mode = modeRepo.getById(id);

        return mode.execute();
    }


    @RequestMapping(value = "/saveScript2", method = RequestMethod.POST)
    public String saveScript2(@Valid @RequestBody ScriptVersion2 script){

        System.out.println("saving script2");
        System.out.println(script);

        ScriptVersion2 result = scriptVersion2Repo.getScriptByName(script.getName());

        if(result != null){
            scriptVersion2Repo.updateScript(script.getName(), script.getRowCommandsJsonRow());
            return "Сценарий " + script.getName() + " обновлен";
        }
        else{
            scriptVersion2Repo.save(script);
        }


        return "Сценарий создан";
    }

    @RequestMapping(value = "/executeCommand", method = RequestMethod.POST)
    public String executeCommand(@Valid @RequestBody ScriptCommandsRequest scriptCommandsRequest){

        if(scriptCommandsRequest.getTo().equals("modem")){
            Mode mode = modeRepo.getById(Long.parseLong(scriptCommandsRequest.getType()));
            return mode.execute();
        }
        else if(scriptCommandsRequest.getTo().equals("CCD")){
            if(scriptCommandsRequest.getType().equals("N5746APowerSupplySetRequest")){
                return N5746APowerSupplySet.sendData(scriptCommandsRequest.getParams());
            }
            else if(scriptCommandsRequest.getType().equals("PXIePowerSupplySetRequest")){
                return PXIePowerSupplySet.sendData(scriptCommandsRequest.getParams());
            }
            else if(scriptCommandsRequest.getType().equals("PXIStatusRequest")){
                return PXIStatusRequest.sendData();
            }
            else if(scriptCommandsRequest.getType().equals("PeakFrequencyPowerGetRequest")){
                return PeakFrequencyPowerGet.sendData(scriptCommandsRequest.getParams());
            }
            else if(scriptCommandsRequest.getType().equals("CCDTurnOffRequest")){
                return CCDTurnOffRequest.sendData();
            }

        }
        return "<div class = \"row\" style = \"width: fit-content; align-items: baseline\"><b style=\"color: #b8a500; font-weight: bold\">WARNING</b>: Неопознанная команда. Пропуск.<br></div>";
    }
}