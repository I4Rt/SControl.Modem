package com.i4rt.demo.controllers;

import com.i4rt.demo.interfaces.DataRepo;
import com.i4rt.demo.interfaces.ModeRepo;
import com.i4rt.demo.interfaces.RegisterRepo;
import com.i4rt.demo.interfaces.ScriptRepo;
import com.i4rt.demo.model.Data;
import com.i4rt.demo.model.Mode;
import com.i4rt.demo.model.Register;
import com.i4rt.demo.model.Script;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    private final DataRepo dataRepo;
    @Autowired
    private final RegisterRepo registerRepo;
    @Autowired
    private final ModeRepo modeRepo;
    @Autowired
    private final ScriptRepo scriptRepo;


    public MainController(DataRepo dataRepo, RegisterRepo registerRepo, ModeRepo modeRepo, ScriptRepo scriptRepo) {
        this.dataRepo = dataRepo;
        this.registerRepo = registerRepo;
        this.modeRepo = modeRepo;
        this.scriptRepo = scriptRepo;
    }


    @GetMapping("/")
    public String greetingForm(Model model) {
        model.addAttribute("data", new Data());
        return "main";
    }
    @GetMapping("/main")
    public String getMain(Model model) {
        model.addAttribute("scripts", scriptRepo.findAll());
        return "main";
    }

    @PostMapping("/send")
    public String sendData(@ModelAttribute(name = "data") @Valid Data data, Model model) throws IOException {


        data.generateData();
        System.out.println(data);
        dataRepo.save(data);
        String answer = data.sendData();

        model.addAttribute("data", new Data());
        model.addAttribute("answer", "Получено: " + answer);

        return "demo";
    }


    @GetMapping("/addRegister")
    public String getCreateRegisterForm(Model model) {
        model.addAttribute("register", new Register());
        return "addRegister";
    }

    @PostMapping("/addRegister")
    public String createRegister(@ModelAttribute(name = "register") @Valid Register register, Model model) throws IOException {
        System.out.println(register);
        register.setData(new Data(register.getInput()));

        registerRepo.save(register);
        String answer = "Регистр с имененм " + register.getName() + " успешно создан!";

        model.addAttribute("register", new Register());
        model.addAttribute("answer", "Получено: " + answer);

        return "addRegister";
    }

    @GetMapping("/addMode")
    public String getCreateModeForm(Model model) {

        Mode newMode = new Mode();
        model.addAttribute("mode", newMode);
        List<Object> allRegistersInUse = Arrays.asList(newMode.getRegisters().toArray());
        System.out.println("\n\n\n");
        System.out.println(allRegistersInUse);
        System.out.println("\n\n\n");

        JSONObject modeJson = newMode.getJson();



        List<Register> allRegisters = registerRepo.findAll();
        model.addAttribute("modeJson",  modeJson);
        model.addAttribute("allRegisters",  allRegisters);
        model.addAttribute("allRegistersInUse",  allRegistersInUse);
        return "addMode";
    }

    @GetMapping("/addScript")
    public String getCreateScriptForm(Model model) {

        Script newScript = new Script();
        model.addAttribute("script", newScript);
        List<Object> allModesInUse = Arrays.asList(newScript.getModes().toArray());
        System.out.println("\n\n\n");
        System.out.println(allModesInUse);
        System.out.println("\n\n\n");

        JSONObject scriptJson = newScript.getJson();



        List<Mode> allModes = modeRepo.findAll();
        model.addAttribute("scriptJson",  scriptJson);
        model.addAttribute("allModes",  allModes);
        model.addAttribute("allModesInUse",  allModesInUse);
        return "addScript";
    }

    @GetMapping("/getScript/{id}")
    public String getUser(@PathVariable Long id, Model model) {

        Script newScript = scriptRepo.getById(id);
        model.addAttribute("script", newScript);
        Set<Mode> allModesInUse = newScript.getModes() ;
        System.out.println("\n\n\n");
        System.out.println(allModesInUse);
        System.out.println("\n\n\n");

        JSONObject scriptJson = newScript.getJson();

        List<Mode> allModes = modeRepo.findAll();
        model.addAttribute("scriptJson",  scriptJson);
        model.addAttribute("allModes",  allModes);
        model.addAttribute("allModesInUse",  allModesInUse);

        return "scriptInfo";
    }

}
