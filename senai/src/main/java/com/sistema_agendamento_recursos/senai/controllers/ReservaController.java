package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.ReservaDto;
import com.sistema_agendamento_recursos.senai.servicies.ReservaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    // Cadastro de uma nova reserva
    @PostMapping("/reservainserir")
    public String inserir(@Valid @ModelAttribute("reserva") ReservaDto dto,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "reservainserir";
        }

        service.inserir(dto);

        redirectAttributes.addFlashAttribute("mensagem",
                "Reserva cadastrada com sucesso.");

        return "redirect:/reservalista";
    }

    // Cancelamento da reserva
    @PostMapping("/reservacancelar/{id}")
    public String cancelar(@PathVariable Long id,
                           @RequestParam String observacao,
                           RedirectAttributes redirectAttributes) {

        service.cancelar(id, observacao);

        redirectAttributes.addFlashAttribute("mensagem",
                "Reserva cancelada com sucesso.");

        return "redirect:/reservas";
    }

}