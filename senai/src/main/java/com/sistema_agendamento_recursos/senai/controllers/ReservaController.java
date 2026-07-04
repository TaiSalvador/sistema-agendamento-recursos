package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.ReservaDto;
import com.sistema_agendamento_recursos.senai.servicies.ReservaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @PostMapping("/reservainserir")
    public String inserir(@Valid @ModelAttribute("reserva") ReservaDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "reservainserir";
        }

        service.inserir(dto);

        redirectAttributes.addFlashAttribute("mensagem", "Reserva cadastrada com sucesso.");

        return "redirect:/reservas";
    }

    @DeleteMapping("reservaexcluir")
    public String excuir(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        service.excluir(id);

        redirectAttributes.addFlashAttribute("mensagem", "Recurso excluído com sucesso.");

        return "redirect:/recursos";
    }
}
