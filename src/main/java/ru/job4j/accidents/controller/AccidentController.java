package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final HbmAccidentService accidentService;
    private final HbmAccidentTypeService typeService;
    private final HbmRuleService ruleService;

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
        boolean rsl = accidentService.add(accident, req.getParameterValues("rIds"));
        if (!rsl) {
            return "redirect:/showError";
        }
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
                boolean rsl = accidentService.replace(accident, req.getParameterValues("rIds"));
        if (!rsl) {
            return "redirect:/showError";
        }
        return "redirect:/accidents";
    }

    @GetMapping("/showError")
    public String error() {
        return "showError";
    }
}
