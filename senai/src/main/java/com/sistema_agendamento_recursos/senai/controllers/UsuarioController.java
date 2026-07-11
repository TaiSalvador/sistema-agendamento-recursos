package com.sistema_agendamento_recursos.senai.controllers;

import com.sistema_agendamento_recursos.senai.dtos.UsuarioDto;
import com.sistema_agendamento_recursos.senai.servicies.UsuarioService;
import com.sistema_agendamento_recursos.senai.sessao.SessaoDto;
import com.sistema_agendamento_recursos.senai.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String realizarLogin(@RequestParam String email,
                                @RequestParam String senha,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {

        System.out.println("email =" + email + " senha = " + senha);

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail(email);
        usuarioDto.setSenha(senha);

        UsuarioDto usuarioDtoRetorno = service.realizarLogin(usuarioDto);

        if (usuarioDtoRetorno.getNome() != null) {

            SessaoDto sessaoDto = new SessaoDto();

            sessaoDto.setId(usuarioDtoRetorno.getId());
            sessaoDto.setNome(usuarioDtoRetorno.getNome());
            sessaoDto.setEmail(usuarioDtoRetorno.getEmail());

            session.setAttribute("usuarioLogado", sessaoDto);

            redirectAttributes.addFlashAttribute(
                    "mensagem",
                    "Bem-Vindo, " + usuarioDtoRetorno.getNome()
            );

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
            return "usuarioinserir";
        }

        try {
            service.usuarioInserir(usuarioDto);

        } catch (IllegalArgumentException e) {

            if (e.getMessage().contains("e-mail")) {

                bindingResult.rejectValue(
                        "email",
                        "email.duplicado",
                        e.getMessage()
                );

            } else if (e.getMessage().contains("matrícula")) {

                bindingResult.rejectValue(
                        "matricula",
                        "matricula.duplicada",
                        e.getMessage()
                );

            } else if (e.getMessage().contains("data de nascimento")) {

                bindingResult.rejectValue(
                        "dataNascimento",
                        "data.invalida",
                        e.getMessage()
                );

            }

            return "usuarioinserir";
        }

        redirectAttributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso.");

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

    @PostMapping("/cadastro")
    public String cadastrar(@Valid @ModelAttribute("usuario") UsuarioDto dto,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "cadastro";
        }

        try {

            service.usuarioInserir(dto);

            redirectAttributes.addFlashAttribute(
                    "mensagem",
                    "Conta criada com sucesso! Faça o login."
            );

            return "redirect:/login";

        } catch (IllegalArgumentException e) {

            model.addAttribute("erro", e.getMessage());

            return "cadastro";
        }
    }


    @DeleteMapping("/usuarioexcluir/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {

        try {

            service.excluir(id);

            return ResponseEntity.ok("Usuário excluído com sucesso.");

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
