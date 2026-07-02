package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.UsuarioDto;
import com.sistema_agendamento_recursos.senai.servicies.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String realizarLogin(String email, String senha,
                                Model model, RedirectAttributes redirectAttributes) {

        System.out.println("email =" + email + "senha = " + senha);

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail(email);
        usuarioDto.setSenha(senha);

        UsuarioDto usuarioDtoRetorno = service.realizarLogin(usuarioDto);

        if (usuarioDtoRetorno.getNome() != null) {

            redirectAttributes.addFlashAttribute("mensagem", "Bem-Vindo, " + usuarioDtoRetorno.getNome());
            return "redirect:/home";
        }

        model.addAttribute("erro", "E-mail ou senha inválido.");
        return "login";

    }

    @PostMapping("/usuarioinserir")
    public String inserirUsuario(
            @Valid @ModelAttribute("usuario") UsuarioDto usuarioDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "usuarionserir";
        }

        service.usuarioInserir(usuarioDto);
        redirectAttributes.addFlashAttribute("mensagem", "Usuario cadastrado com sucesso.");

        return "redirect:/usuariolista";
    }

    @PostMapping("/usuarioatualizar")
    public String atualizarUsuario(Model model, @Valid @ModelAttribute("usuario") UsuarioDto usuarioDto, BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "usuarioatualizar";
        }

        redirectAttributes.addFlashAttribute("mensagem", "Usuario atualizado com sucesso.");
        service.usuarioAtualizar(usuarioDto);


        service.usuarioAtualizar(usuarioDto);

        return "redirect:/usuariolista";

    }

    @DeleteMapping("/usuarioexcluir/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.excluir(id);
        return ResponseEntity.ok().body("Excluido");
    }
}
