
package com.sistema_agendamento_recursos.senai.servicies;

import com.sistema_agendamento_recursos.senai.dtos.UsuarioDto;
import com.sistema_agendamento_recursos.senai.entities.UsuarioEntity;
import com.sistema_agendamento_recursos.senai.repository.ReservaRepository;
import com.sistema_agendamento_recursos.senai.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final ReservaRepository reservaRepository;

    public UsuarioService(UsuarioRepository repository, ReservaRepository reservaRepository) {
        this.repository = repository;
        this.reservaRepository = reservaRepository;
    }

    public UsuarioDto realizarLogin(UsuarioDto usuarioDto) {
        Optional<UsuarioEntity> usuarioOP = this.repository.findByEmailAndSenha(usuarioDto.getEmail(), usuarioDto.getSenha());
        UsuarioDto usuarioDtoRetorno = new UsuarioDto();
        if (usuarioOP.isPresent()) {
            usuarioDtoRetorno = this.converterEntityParaDto((UsuarioEntity)usuarioOP.get());
            return usuarioDtoRetorno;
        } else {
            return usuarioDtoRetorno;
        }
    }

    public List<UsuarioDto> obterListaUsuarios() {
        List<UsuarioDto> listaDto = new ArrayList();

        for(UsuarioEntity usuarioEntity : this.repository.findAll()) {
            listaDto.add(this.converterEntityParaDto(usuarioEntity));
        }

        return listaDto;
    }

    public void usuarioInserir(UsuarioDto usuarioDto) {

        LocalDate hoje = LocalDate.now();

        if (repository.findByEmail(usuarioDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este e-mail.");
        }

        if (repository.existsByMatricula(usuarioDto.getMatricula())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esta matrícula.");
        }

        if (usuarioDto.getDataNascimento() == null) {
            throw new IllegalArgumentException("A data de nascimento é obrigatória.");
        }

        if (usuarioDto.getDataNascimento().isAfter(hoje)) {
            throw new IllegalArgumentException("A data de nascimento não pode ser futura.");
        }

        if (usuarioDto.getDataNascimento().isBefore(hoje.minusYears(500))) {
            throw new IllegalArgumentException("A data de nascimento é inválida.");
        }

        repository.save(converterDtoParaEntity(usuarioDto));
    }

    public UsuarioDto obterUsuarioPorId(Long id) {
        UsuarioDto usuarioDto = new UsuarioDto();
        Optional<UsuarioEntity> usuarioOp = this.repository.findById(id);
        if (usuarioOp.isPresent()) {
            usuarioDto = this.converterEntityParaDto((UsuarioEntity)usuarioOp.get());
        }

        return usuarioDto;
    }

    public void usuarioAtualizar(UsuarioDto usuarioDto) {
        Optional<UsuarioEntity> usuarioOP = this.repository.findById(usuarioDto.getId());
        if (usuarioOP.isPresent()) {
            UsuarioEntity usuario = (UsuarioEntity)usuarioOP.get();
            usuario.setNome(usuarioDto.getNome());
            usuario.setEmail(usuarioDto.getEmail());
            usuario.setMatricula(usuarioDto.getMatricula());
            usuario.setDataNascimento(usuarioDto.getDataNascimento());
            if (!usuarioDto.getSenha().isEmpty()) {
                usuario.setSenha(usuarioDto.getSenha());
            }

            this.repository.save(usuario);
        }

    }

    @Transactional
    public void excluir(Long id) {

        if (reservaRepository.existsByUsuarioIdAndDataCancelamentoIsNull(id)) {
            throw new RuntimeException(
                    "Não é possível excluir este usuário porque possui reservas ativas."
            );
        }

        reservaRepository.deleteByUsuarioIdAndDataCancelamentoIsNotNull(id);

        repository.deleteById(id);
    }

    private UsuarioDto converterEntityParaDto(UsuarioEntity usuario) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(usuario.getId());
        usuarioDto.setNome(usuario.getNome());
        usuarioDto.setEmail(usuario.getEmail());
        usuarioDto.setMatricula(usuario.getMatricula());
        usuarioDto.setDataNascimento(usuario.getDataNascimento());
        return usuarioDto;
    }

    private UsuarioEntity converterDtoParaEntity(UsuarioDto usuarioDto) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(usuarioDto.getId());
        usuario.setNome(usuarioDto.getNome());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setSenha(usuarioDto.getSenha());
        usuario.setMatricula(usuarioDto.getMatricula());
        usuario.setDataNascimento(usuarioDto.getDataNascimento());
        return usuario;
    }
}
