package com.example.modulv.controllers;


import com.example.modulv.commands.ProducentCommand;
import com.example.modulv.converters.ProducentCommandToProducent;
import com.example.modulv.model.Producent;
import com.example.modulv.repositories.AutoRepository;
import com.example.modulv.repositories.DystrybutorRepository;
import com.example.modulv.repositories.KlientRepository;
import com.example.modulv.repositories.ProducentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProducentController {

    private ProducentRepository producentRepository;
    private ProducentCommandToProducent producentCommandToProducent;
    private DystrybutorRepository dystrybutorRepository;
    private KlientRepository klientRepository;
    private AutoRepository autoRepository;

    public ProducentController(ProducentRepository producentRepository, ProducentCommandToProducent producentCommandToProducent, DystrybutorRepository dystrybutorRepository,KlientRepository klientRepository, AutoRepository autorepository) {
        this.producentRepository = producentRepository;
        this.producentCommandToProducent = producentCommandToProducent;
        this.dystrybutorRepository = dystrybutorRepository;
        this.klientRepository = klientRepository;
        this.autoRepository = autorepository;
    }

    @GetMapping
    @RequestMapping(value = {"/producenci" , "producent/list"})
    public String getproducenci(Model model) {
        model.addAttribute("songs", producentRepository.findAll());
        return "producent/list";
    }

    @GetMapping
    @RequestMapping("/producent/{id}/pokaz")
    public String getProducentDetails(Model model, @PathVariable("id") Long id) {
        model.addAttribute("producent", producentRepository.findById(id).get());
        return "producent/pokaz";
    }

    @GetMapping
    @RequestMapping("/producent/{id}/usun")
    public String deleteProducent(@PathVariable("id") Long id) {
        producentRepository.deleteById(id);
        return "redirect:/producenci";
    }

    @GetMapping
    @RequestMapping("/producent/new")
    public String newSong(Model model){
        model.addAttribute("song", new ProducentCommand());
        model.addAttribute("publishers", dystrybutorRepository.findAll());
        model.addAttribute("artists", autoRepository.findAll());
        return "producent/addedit";
    }

    @PostMapping("producent")
    public String saveOrUpdate(@ModelAttribute ProducentCommand command){
        Producent detachedSong = producentCommandToProducent.convert(command);
        Producent savedProducent = producentRepository.save(detachedSong);

        return "redirect:/producent/" + savedProducent.getId() + "/pokaz";
    }
}

