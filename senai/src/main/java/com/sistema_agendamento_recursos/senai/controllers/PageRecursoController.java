package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.RecursoDto;
import com.sistema_agendamento_recursos.senai.servicies.RecursoService;
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
    public String lista(Model model) {

        model.addAttribute("recursos", service.obterListaRecurso());

        return "recursolista";
    }

    @GetMapping("/recursoinserir")
    public String inserir(Model model) {

        RecursoDto dto = new RecursoDto();

        model.addAttribute("recurso", new RecursoDto());

        return "recursoinserir";
    }

    @GetMapping("/recursoatualizar/{id}")
    public String atualizar(@PathVariable Long id, Model model) {

        model.addAttribute("recurso", service.obterRecursoPorId(id));

        return "recursoatualizar";
    }
}