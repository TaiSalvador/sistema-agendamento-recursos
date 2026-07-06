package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.ReservaDto;
import com.sistema_agendamento_recursos.senai.servicies.ReservaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageReservaController {

    private final ReservaService service;

    public PageReservaController(ReservaService service) {
        this.service = service;
    }

    @GetMapping("/reservalista")
    public String listar(Model model) {

        model.addAttribute("reservas", service.obterListaReserva());

        return "reservalista";
    }

    @GetMapping("/reservainserir")
    public String inserir(Model model) {

        model.addAttribute("reserva", new ReservaDto());

        return "reservainserir";
    }

    @GetMapping("/reservavisualizar/{id}")
    public String visualizar(@PathVariable Long id, Model model) {

        model.addAttribute("reserva", service.obterReservaPorId(id));

        return "reservavisualizar";
    }

    @GetMapping("/reservacancelar/{id}")
    public String cancelar(@PathVariable Long id, Model model) {

        model.addAttribute("reserva", service.obterReservaPorId(id));

        return "reservacancelar";
    }

}
