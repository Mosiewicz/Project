package com.example.modulv.controllers;


import com.example.modulv.commands.DystrybutorCommand;
import com.example.modulv.converters.DystrybutorCommandToDystrybutor;
import com.example.modulv.model.Dystrybutor;
import com.example.modulv.repositories.DystrybutorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class DystrybutorController {

    private DystrybutorRepository dystrybutorRepository;
    private DystrybutorCommandToDystrybutor dystrybutorCommandToDystrybutor;

    public DystrybutorController(DystrybutorRepository dystrybutorRepository, DystrybutorCommandToDystrybutor dystrybutorCommandToDystrybutor) {
        this.dystrybutorRepository = dystrybutorRepository;
        this.dystrybutorCommandToDystrybutor = dystrybutorCommandToDystrybutor;
    }

    @RequestMapping(value = {"/dystrbutorzy", "/dystrybutorzy/list"})
    public String getPublishers(Model model) {
        model.addAttribute("dystrybutorzy", dystrybutorRepository.findAll());
        return "pdystrybutor/list";
    }

    @RequestMapping("/dystrybutor/{id}/pokaz")
    public String getPublisherDetails(Model model, @PathVariable("id") Long id) {
        model.addAttribute("publisher", dystrybutorRepository.findById(id).get());
        return "dystrybutor/pokaz";
    }

    @RequestMapping("/pdystrybutor/{id}/delete")
    public String deletePublisher(@PathVariable("id") Long id) {
        dystrybutorRepository.deleteById(id);
        return "redirect:/publishers";
    }

    @GetMapping
    @RequestMapping("/dystrybutor/new")
    public String newSong(Model model){
        model.addAttribute("dystrybutor", new DystrybutorCommand());
        return "dystrybutor/addedit";
    }

    @PostMapping("publisher")
    public String saveOrUpdate(@ModelAttribute DystrybutorCommand command){

        Optional<Dystrybutor> publisherOptional = dystrybutorRepository.getDystrybutorByNazwasalonu(command.getNazwasalonu());

        if (!publisherOptional.isPresent()) {
            Dystrybutor detachedPublisher = dystrybutorCommandToDystrybutor.convert(command);
            Dystrybutor savedPublisher = dystrybutorRepository.save(detachedPublisher);
            return "redirect:/pdystrybutor/" + savedPublisher.getId() + "/pokaz";
        } else {
            System.out.println("Juz isstnieje taki dystrybutor w bazie danych");
            return "redirect:/dystrybutor/" + publisherOptional.get().getId() + "/pokaz";
        }
    }
}
