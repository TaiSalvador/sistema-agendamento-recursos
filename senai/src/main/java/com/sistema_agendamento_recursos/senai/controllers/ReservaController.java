package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.ReservaDto;
import com.sistema_agendamento_recursos.senai.servicies.RecursoService;
import com.sistema_agendamento_recursos.senai.servicies.ReservaService;
import com.sistema_agendamento_recursos.senai.servicies.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReservaController {

    private final UsuarioService usuarioService;
    private final RecursoService recursoService;
    private final ReservaService service;

    public ReservaController(UsuarioService usuarioservice, UsuarioService usuarioService, RecursoService recursoService, ReservaService service) {
        this.usuarioService = usuarioService;
        this.recursoService = recursoService;
        this.service = service;
    }

    @PostMapping("/reservainserir")
    public String inserir(
            @Valid @ModelAttribute("reserva") ReservaDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {

            model.addAttribute("usuarios", usuarioService.obterListaUsuarios());

            model.addAttribute("recursos", recursoService.obterListaRecurso());

            return "reservainserir";
        }


        service.inserir(dto);


        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Reserva cadastrada com sucesso!"
        );


        return "redirect:/reservalista";
    }

    @PostMapping("/reservacancelar/{id}")
    public String cancelar(@PathVariable Long id,
                           @RequestParam String observacao,
                           RedirectAttributes redirectAttributes) {

        service.cancelar(id, observacao);

        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Reserva cancelada com sucesso!"
        );

        return "redirect:/reservalista";
    }

}