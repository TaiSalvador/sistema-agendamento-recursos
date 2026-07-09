package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.ReservaDto;
import com.sistema_agendamento_recursos.senai.servicies.RecursoService;
import com.sistema_agendamento_recursos.senai.servicies.ReservaService;
import com.sistema_agendamento_recursos.senai.servicies.UsuarioService;
import com.sistema_agendamento_recursos.senai.sessao.SessaoDto;
import com.sistema_agendamento_recursos.senai.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageReservaController {

    private final ReservaService reservaService;
    private final UsuarioService usuarioService;
    private final RecursoService recursoService;

    public PageReservaController(ReservaService reservaService,
                                 UsuarioService usuarioService,
                                 RecursoService recursoService) {
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.recursoService = recursoService;
    }

    @GetMapping("/reservalista")
    public String listar(HttpSession session, Model model) {

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);
        model.addAttribute("reservas", reservaService.obterListaReserva());

        return "reservalista";
    }

    @GetMapping("/reservainserir")
    public String inserir(HttpSession session, Model model) {

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);

        model.addAttribute("reserva", new ReservaDto());

        model.addAttribute("usuarios",
                usuarioService.obterListaUsuarios());

        model.addAttribute("recursos",
                recursoService.obterListaRecurso());


        return "reservainserir";
    }

    @GetMapping("/reservavisualizar/{id}")
    public String visualizar(HttpSession session,
                             @PathVariable Long id,
                             Model model) {

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);
        model.addAttribute("reserva", reservaService.obterReservaPorId(id));

        return "reservavisualizar";
    }

    @GetMapping("/reservacancelar/{id}")
    public String cancelar(HttpSession session,
                           @PathVariable Long id,
                           Model model) {

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);
        model.addAttribute("reserva", reservaService.obterReservaPorId(id));

        return "reservacancelar";
    }

}