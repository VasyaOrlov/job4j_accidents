package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.List;
import java.util.Optional;

@Controller
public class AccidentController {

    private final AccidentService accidentService;
    private final AccidentTypeService typeService;

    public AccidentController(AccidentService accidentService, AccidentTypeService typeService) {
        this.accidentService = accidentService;
        this.typeService = typeService;
    }

    @GetMapping("/accidents")
    public String accidents(Model model) {
        model.addAttribute("accidents", accidentService.getAll());
        return "accidents";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = typeService.getTypes();
        model.addAttribute("types", types);
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        int id = accident.getType().getId();
        Optional<AccidentType> at = typeService.findById(id);
        if (at.isPresent()) {
            accident.setType(at.get());
            accidentService.add(accident);
        }
        return "redirect:/accidents";
    }

    @GetMapping("/updateAccident")
    public String updateView(@RequestParam("id") int id, Model model) {
        Optional<Accident> rsl = accidentService.findById(id);
        if (rsl.isEmpty()) {
            return "redirect:/accidents";
        }
        List<AccidentType> types = typeService.getTypes();
        model.addAttribute("types", types);
        model.addAttribute("accident", rsl.get());
        return "updateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        int id = accident.getType().getId();
        Optional<AccidentType> at = typeService.findById(id);
        if (at.isPresent()) {
            accident.setType(at.get());
            accidentService.replace(accident);
        }
        return "redirect:/accidents";
    }
}
