package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

@Controller
public class AccidentController {

    private final AccidentService accidentService;
    private final AccidentTypeService typeService;
    private final RuleService ruleService;

    public AccidentController(AccidentService accidentService, AccidentTypeService typeService, RuleService ruleService) {
        this.accidentService = accidentService;
        this.typeService = typeService;
        this.ruleService = ruleService;
    }

    @GetMapping("/accidents")
    public String accidents(Model model) {
        model.addAttribute("accidents", accidentService.getAll());
        return "accidents";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("rules", ruleService.getRules());
        model.addAttribute("types", typeService.getTypes());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> ruleList = ruleService.findByIds(ids);
        Optional<AccidentType> at = typeService.findById(accident.getType().getId());
        if (ruleList.size() != ids.length || at.isEmpty()) {
            return "redirect:/showError";
        }
        accident.setRules(ruleList);
        accident.setType(at.get());
        accidentService.add(accident);
        return "redirect:/accidents";
    }

    @GetMapping("/updateAccident")
    public String updateView(@RequestParam("id") int id, Model model) {
        Optional<Accident> rsl = accidentService.findById(id);
        if (rsl.isEmpty()) {
            return "redirect:/accidents";
        }
        model.addAttribute("rules", ruleService.getRules());
        model.addAttribute("types", typeService.getTypes());
        model.addAttribute("accident", rsl.get());
        return "updateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> ruleList = ruleService.findByIds(ids);
        Optional<AccidentType> at = typeService.findById(accident.getType().getId());
        if (ruleList.size() != ids.length || at.isEmpty()) {
            return "redirect:/showError";
        }
        accident.setRules(ruleList);
        accident.setType(at.get());
        accidentService.replace(accident);
        return "redirect:/accidents";
    }

    @GetMapping("/showError")
    public String error() {
        return "showError";
    }
}
