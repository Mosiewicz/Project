package com.example.modulv.controllers;


import com.example.modulv.commands.AutoCommand;
import com.example.modulv.converters.AutoCommandToAuto;
import com.example.modulv.model.Auto;
import com.example.modulv.repositories.AutoRepository;
import com.example.modulv.repositories.ProducentRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class AutoController {

    private AutoRepository autoRepository;
    private ProducentRepository producentRepository;
    private AutoCommandToAuto autoCommandToAuto;

    public AutoController(AutoRepository autoRepository, ProducentRepository producentRepository) {
        this.autoRepository = autoRepository;
        this.producentRepository = producentRepository;
    }

    @RequestMapping(value = {"/auta", "/auto/list"})
    public String getAuta(Model model) {
        model.addAttribute("auta", autoRepository.findAll());
        return "auto/list";
    }

    @RequestMapping("/auto/{id}/producenci")
    public String getAutoProducenci(Model model, @PathVariable("id") Long id) {
        Optional<Auto> auto = autoRepository.findById(id);

        if (auto.isPresent()) {
            model.addAttribute("auta", producentRepository.getAllByAutaIsContaining(auto.get()));
            model.addAttribute("filtr", "auto: " + auto.get().getModel() +" " + auto.get().getRprod());
        }else{
            model.addAttribute("auta", new ArrayList<>());
            model.addAttribute("filtr", "Auto pod tym id nie istnieje");
        }
        return "auto/list";
    }

    @RequestMapping("/auto/{id}/pokaz")
    public String getAutoDetails(Model model, @PathVariable("id") Long id) {
        model.addAttribute("auto", autoRepository.findById(id).get());
        return "auto/pokaz";
    }

    @RequestMapping("/auto/{id}/usun")
    public String deleteAuto(@PathVariable("id") Long id) {
        autoRepository.deleteById(id);
        return "redirect:/auta";
    }

    @GetMapping
    @RequestMapping("/auto/new")
    public String newProducent(Model model) {
        model.addAttribute("auto", new AutoCommand());
        return "auto/addedit";
    }

    @PostMapping("auto")
    public String saveOrUpdate(@ModelAttribute AutoCommand command) {

        Optional<Auto> autoOptional = autoRepository.getFirstByModelAndRprod(command.getModel(), command.getRprod());

        if (!autoOptional.isPresent()) {
            Auto detachedAuto = autoCommandToAuto.convert(command);
            Auto savedAuto = autoRepository.save(detachedAuto);
            return "redirect:/auto/" + savedAuto.getId() + "/pokaz";
        }
        else {
            System.out.println("Juz istnieje taki producent");
            return "redirect:/auto/" + autoOptional.get().getId() + "/pokaz";
        }
    }

}
