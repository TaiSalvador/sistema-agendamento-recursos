package com.sistema_agendamento_recursos.senai.sessao;

import jakarta.servlet.http.HttpSession;

public class SessaoUtil {

        private static final String USUARIO_LOGADO = "usuarioLogado";

        public static void criarSessao(HttpSession session, SessaoDto usuario) {
            session.setAttribute(USUARIO_LOGADO, usuario);
        }

        public static SessaoDto obterSessao(HttpSession session) {
            return (SessaoDto) session.getAttribute(USUARIO_LOGADO);
        }

        public static void removerSessao(HttpSession session) {
            session.removeAttribute(USUARIO_LOGADO);
        }

        public static boolean estaLogado(HttpSession session) {
            return obterSessao(session) != null;
        }
    }
