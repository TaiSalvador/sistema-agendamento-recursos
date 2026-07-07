package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.RecursoDto;
import com.sistema_agendamento_recursos.senai.servicies.RecursoService;
import com.sistema_agendamento_recursos.senai.sessao.SessaoDto;
import com.sistema_agendamento_recursos.senai.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageRecursoController {

    private final RecursoService service;

    public PageRecursoController(RecursoService service) {
        this.service = service;
    }

    @GetMapping("/recursolista")
    public String lista(HttpSession session,
                        Model model) {

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null){
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);

        model.addAttribute("recursos", service.obterListaRecurso());

        return "recursolista";
    }

    @GetMapping("/recursoinserir")
    public String inserir(HttpSession session,
                          Model model) {

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null){
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);

        RecursoDto dto = new RecursoDto();

        model.addAttribute("recurso", new RecursoDto());

        return "recursoinserir";
    }

    @GetMapping("/recursoatualizar/{id}")
    public String atualizar(HttpSession session,
                            @PathVariable Long id,
                            Model model) {

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null){
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);

        model.addAttribute("recurso", service.obterRecursoPorId(id));

        return "recursoatualizar";
    }
}