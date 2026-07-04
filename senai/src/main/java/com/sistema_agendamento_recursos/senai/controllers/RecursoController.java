package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.RecursoDto;
import com.sistema_agendamento_recursos.senai.servicies.RecursoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecursoController {

    private final RecursoService service;

    public RecursoController(RecursoService service) {
        this.service = service;
    }

    @PostMapping("/recursoinserir")
    public String inserir(
            @Valid @ModelAttribute("recurso") RecursoDto dto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "recursoinserir";
        }

        service.inserir(dto);

        redirectAttributes.addFlashAttribute("mensagem", "Recurso cadastrado com sucesso.");

        return "redirect:/recursos";
    }

    @PostMapping("/recursoatualizar")
    public String atualizar(@Valid @ModelAttribute("recurso") RecursoDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "recursoatualizar";
        }

        service.atualizar(dto);

        redirectAttributes.addFlashAttribute("mensagem", "Recurso atualizado com sucesso.");

        return "redirect:/recursos";
    }

    @DeleteMapping("/recursoexcluir/{id}")
    public String excluir(@PathVariable Long id,
                          RedirectAttributes redirectAttributes) {

        service.excluir(id);

        redirectAttributes.addFlashAttribute("mensagem", "Recurso excluído com sucesso.");

        return "redirect:/recursos";
    }
}
