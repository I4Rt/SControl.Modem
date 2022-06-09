package com.i4rt.demo.controllers;

import com.i4rt.demo.interfaces.*;
import com.i4rt.demo.model.*;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private final MDKRPKTRepo mdkrpktRepo;




    public MainController(DataRepo dataRepo, RegisterRepo registerRepo, ModeRepo modeRepo, ScriptRepo scriptRepo, MDKRPKTRepo mdkrpktRepo) {
        this.dataRepo = dataRepo;
        this.registerRepo = registerRepo;
        this.modeRepo = modeRepo;
        this.scriptRepo = scriptRepo;
        this.mdkrpktRepo = mdkrpktRepo;
    }


    @GetMapping("/")
    public String greetingForm(Model model) {
        model.addAttribute("data", new Data());
        return "main";
    }
    @GetMapping("/main")
    public String getMain(Model model) {
        model.addAttribute("scripts", scriptRepo.findAll());
        model.addAttribute("modes", modeRepo.findAll());
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
        DataBin dataBin = new DataBin(register.getPosition(), register.getInput(), register.getMode());

        register.setData(new Data(dataBin.getResultData()));
        System.out.println(register.toString());

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

        List<Register> allModemRegisters = registerRepo.getRegistersByMode("modem");
        List<Register> allChannelRegisters = registerRepo.getRegistersByMode("gadget");


        //List<Register> allRegisters = registerRepo.findAll();
        model.addAttribute("modeJson",  modeJson);
        model.addAttribute("allModemRegisters",  allModemRegisters);
        model.addAttribute("allChannelRegisters",  allChannelRegisters);
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
    public String getScript(@PathVariable Long id, Model model) {

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

    @GetMapping("/getMode/{id}")
    public String getMode(@PathVariable Long id, Model model) {


        Mode mode = modeRepo.getById(id);
        model.addAttribute("mode", mode);
        List<Object> allRegistersInUse = Arrays.asList(mode.getRegisters().toArray());
        /*
        System.out.println("\n\n\n");
        System.out.println(allRegistersInUse);
        System.out.println("\n\n\n");
        */
        JSONObject modeJson = mode.getJson();



        List<Register> allModemRegisters = registerRepo.getRegistersByMode("modem");
        List<Register> allChannelRegisters = registerRepo.getRegistersByMode("gadget");
        model.addAttribute("modeJson",  modeJson);
        model.addAttribute("allModemRegisters",  allModemRegisters);
        model.addAttribute("allChannelRegisters",  allChannelRegisters);
        model.addAttribute("allRegistersInUse",  allRegistersInUse);

        return "modeInfo";
    }

    @GetMapping("/demo2")
    private String createNewMode(Model model){
        MDKRPKT mode1 = new MDKRPKT();
        mode1.setCelebrating(false);

        List<MDKRPKT> allModes1 = mdkrpktRepo.findAll();

        model.addAttribute("mode1", mode1);
        model.addAttribute("allModes1", allModes1);

        return "allModes";
    }

    @PostMapping("/createMDKRPKT")
    private String saveMDKRPKT(Model model, @ModelAttribute("mode1") @Valid MDKRPKT mode1){
        if(mdkrpktRepo.getByName(mode1.getName()) != null){
            model.addAttribute("answer", "Режим с таким именем уже существует");
        }
        else{
            mode1.setRegister13RowData(mode1.getRegister6RowData());
            mode1.setRegister14RowData(mode1.getRegister7RowData());

            mode1.setRegister15RowData(mode1.getRegister8RowData());
            mode1.setRegister16RowData(mode1.getRegister9RowData());
            mdkrpktRepo.save(mode1);
        }
        return "redirect:/demo2";
    }

    @GetMapping("/getMDKRPKT")
    private String getMDKRPKT(@RequestParam(value = "id", required = true) Long id, Model model){
        MDKRPKT mode1 = mdkrpktRepo.getById(id);
        System.out.println(mode1);

        List<MDKRPKT> allModes1 = mdkrpktRepo.findAll();

        DataBin dataBin = new DataBin(1, mode1.getRegister1RowData(), "modem");

        System.out.println(dataBin);

        model.addAttribute("mode1", mode1);
        model.addAttribute("allModes1", allModes1);

        return "allModes";
    }

}
