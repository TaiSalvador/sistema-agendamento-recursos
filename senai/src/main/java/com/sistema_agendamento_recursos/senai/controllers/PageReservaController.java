package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.ReservaDto;
import com.sistema_agendamento_recursos.senai.servicies.ReservaService;
import com.sistema_agendamento_recursos.senai.sessao.SessaoDto;
import com.sistema_agendamento_recursos.senai.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;

@Controller
public class PageReservaController {

    private final ReservaService service;

    public PageReservaController(ReservaService service) {
        this.service = service;
    }

    @GetMapping("/reservalista")
    public String listar(HttpSession session,
                         Model model) {

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null){
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);

        model.addAttribute("reservas", service.obterListaReserva());

        return "reservalista";
    }

    @GetMapping("/reservainserir")
    public String inserir(HttpSession session,
                          Model model) {


        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null){
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);

        model.addAttribute("reserva", new ReservaDto());

        return "reservainserir";
    }

    @GetMapping("/reservavisualizar/{id}")
    public String visualizar(HttpSession session,
                             @PathVariable Long id,
                             Model model) {


        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null){
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);

        model.addAttribute("reserva", service.obterReservaPorId(id));

        return "reservavisualizar";
    }

    @GetMapping("/reservacancelar/{id}")
    public String cancelar(HttpSession session,
                           @PathVariable Long id,
                           Model model) {


        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null){
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);

        model.addAttribute("reserva", service.obterReservaPorId(id));

        return "reservacancelar";
    }

}
