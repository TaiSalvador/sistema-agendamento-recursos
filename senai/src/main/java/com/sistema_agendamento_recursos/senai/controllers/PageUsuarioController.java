package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.UsuarioDto;
import com.sistema_agendamento_recursos.senai.servicies.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageUsuarioController {

    final UsuarioService service;

    public PageUsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/")
    public String getIndex() {
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String getHome() {
        return "home";
    }

    @GetMapping("/usuariolista")
    public String getUsuarios(Model model){

        model.addAttribute("usuarios", service.obterListaUsuarios());

        return "usuariolista";
    }

    @GetMapping("/usuarioinserir")
    public String getInserirUsuario (Model model) {

        UsuarioDto dto = new UsuarioDto();

        model.addAttribute("usuario", dto);

        return "usuarioinserir";
    }

    @GetMapping("/usuarioatualizar/{id}")
    public String getUsuarioAtualizar(Model model , @PathVariable Long id){

        UsuarioDto dto = service.obterUsuarioPorId(id);

        model.addAttribute("usuario", dto);

        return "usuarioatualizar";
    }

}
