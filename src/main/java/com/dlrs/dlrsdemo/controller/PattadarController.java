package com.dlrs.dlrsdemo.controller;

import com.dlrs.dlrsdemo.dto.LocationResponseDto;
import com.dlrs.dlrsdemo.dto.ReqDTO;
import com.dlrs.dlrsdemo.service.PattadarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/secure")
@CrossOrigin(origins =  {"http://localhost:8081" , "http://172.20.10.2:8081", "http://172.20.10.2:8080"})  // Allow a specific origin
public class PattadarController {

    @Autowired

    private PattadarService pattadarService;
    @ResponseBody
    @GetMapping("/getSubDivision")
    List<Map<String, Object>> getSubdivision(){
        System.out.println("here");
        return pattadarService.getAllSubdivisions();
    }


    @ResponseBody
    @GetMapping("/getCircle")
    List<Map<String, Object>> getCircle(@RequestParam String subdivCode){
        return pattadarService.getAllCircles(subdivCode);
    }


    @ResponseBody
    @GetMapping("/getMouza")
    List<Map<String, Object>> getMouzaData(@RequestParam String subdivCode, @RequestParam String circleCode){
        return pattadarService.getAllMouzas(subdivCode, circleCode);
    }

    @ResponseBody
    @GetMapping("/getLot")
    List<Map<String, Object>> getLotData(@RequestParam String subdivCode, @RequestParam String circleCode, @RequestParam String mouzaCode){
        return pattadarService.getAllLots(subdivCode, circleCode, mouzaCode);
    }

    @ResponseBody
    @GetMapping("/getVillage")
    List<Map<String, Object>> getVillageData(@RequestParam String subdivCode, @RequestParam String circleCode, @RequestParam String mouzaCode, @RequestParam String lotNo){
        return pattadarService.getAllVillages(subdivCode, circleCode, mouzaCode, lotNo);
    }

    @ResponseBody
    @GetMapping("/getPattaNo")
    List<Map<String, Object>> getPattaNo(@RequestParam String subdivCode, @RequestParam String circleCode, @RequestParam String mouzaCode, @RequestParam String lotNo, @RequestParam String villageNo){
        return pattadarService.getAllPattaNo(subdivCode, circleCode, mouzaCode, lotNo, villageNo);
    }

    @ResponseBody
    @GetMapping("/getPattaType")
    List<Map<String, Object>> getPattaType(@RequestParam String subdivCode, @RequestParam String circleCode, @RequestParam String mouzaCode, @RequestParam String lotNo, @RequestParam String villageNo, @RequestParam String pattaNo){
        return pattadarService.getAllPattaType(subdivCode, circleCode, mouzaCode, lotNo, villageNo, pattaNo);
    }

    @ResponseBody
    @GetMapping("/getPattadars")
    List<Map<String, Object>> getPattadars(@RequestParam String subdivCode, @RequestParam String circleCode, @RequestParam String mouzaCode, @RequestParam String lotNo, @RequestParam String villageNo, @RequestParam String pattaNo, @RequestParam String pattaType){
        return pattadarService.getAllPattadars(subdivCode, circleCode, mouzaCode, lotNo, villageNo, pattaNo, pattaType);
    }

    @PostMapping("/processForm")
    public String processForm(@ModelAttribute ReqDTO reqDTO) {

        Integer count = pattadarService.getAllPattadarsCount(reqDTO);


        pattadarService.addChithaPattadar(reqDTO, count+1);
        pattadarService.addChithaDagPattadar(reqDTO, count+1);

        return "redirect:/secure/home";
    }

    @ResponseBody
    @GetMapping("/getDagNo")
    List<Map<String, Object>> getDagNo(@RequestParam String subdivCode, @RequestParam String circleCode, @RequestParam String mouzaCode, @RequestParam String lotNo, @RequestParam String villageNo, @RequestParam String pattaNo, @RequestParam String pattaType){
        return pattadarService.getAllDagNo(subdivCode, circleCode, mouzaCode, lotNo, villageNo, pattaNo,pattaType);
    }

}
