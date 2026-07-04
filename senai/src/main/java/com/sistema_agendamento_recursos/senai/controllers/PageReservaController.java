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

    @GetMapping("/reservas")
    public String get() {
        return "reservalista";
    }

    @GetMapping("/reservaslista")
    public String lista(Model model) {

        model.addAttribute("reservas", service.obterListaReserva());

        return "reservalista";
    }

    @GetMapping("/reservainserir")
    public String inserir(Model model) {

        model.addAttribute("reserva", new ReservaDto());

        return "reservainserir";
    }

    @GetMapping("/reservaatualizar/{id}")
    public String atualizar(@PathVariable Long id, Model model) {

        model.addAttribute("recurso", service.obterReservaPorId(id));

        return "reservaatualizar";
    }

}
