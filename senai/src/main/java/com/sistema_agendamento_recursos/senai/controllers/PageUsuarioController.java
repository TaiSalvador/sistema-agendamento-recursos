package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.UsuarioDto;
import com.sistema_agendamento_recursos.senai.servicies.UsuarioService;
import com.sistema_agendamento_recursos.senai.sessao.SessaoDto;
import com.sistema_agendamento_recursos.senai.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
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
    public String getHome(HttpSession session,
                          Model model) {
        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null){
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);
        return "home";
    }

    @GetMapping("/usuariolista")
    public String getUsuarios(HttpSession session,
                              Model model){

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null){
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);

        model.addAttribute("usuarios", service.obterListaUsuarios());

        return "usuariolista";
    }

    @GetMapping("/usuarioinserir")
    public String getInserirUsuario (HttpSession session,
                                     Model model) {

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null) {
            return "redirect:/login";

        }

        model.addAttribute("usuarioLogado", sessaoDto);

        UsuarioDto dto = new UsuarioDto();

        model.addAttribute("usuario", dto);

        return "usuarioinserir";
    }

    @GetMapping("/usuarioatualizar/{id}")
    public String getUsuarioAtualizar(HttpSession session,
                                      Model model ,
                                      @PathVariable Long id){

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null){
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", sessaoDto);

        UsuarioDto dto = service.obterUsuarioPorId(id);

        model.addAttribute("usuario", dto);

        return "usuarioatualizar";
    }

}
