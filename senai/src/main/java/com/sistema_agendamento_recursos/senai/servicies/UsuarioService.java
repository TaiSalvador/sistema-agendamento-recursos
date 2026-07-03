package com.sistema_agendamento_recursos.senai.servicies;

import com.sistema_agendamento_recursos.senai.dtos.UsuarioDto;
import com.sistema_agendamento_recursos.senai.entities.UsuarioEntity;
import com.sistema_agendamento_recursos.senai.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioDto realizarLogin(UsuarioDto usuarioDto){

        Optional<UsuarioEntity> usuarioOP = repository.findByEmailAndSenha(usuarioDto.getEmail(), usuarioDto.getSenha());

        UsuarioDto usuarioDtoRetorno = new UsuarioDto();

        if (usuarioOP.isPresent()){

            // usuarioOP.get() = objeto do tipo UsuarioEntity
            // usuarioDto = objeto do tipo UsuarioDto
            // -- achei o usuário e senha
            usuarioDtoRetorno = converterEntityParaDto(usuarioOP.get());

            return usuarioDtoRetorno;
        }

        return usuarioDtoRetorno;

    }

    public List<UsuarioDto> obterListaUsuarios() {

        List<UsuarioDto> listaDto = new ArrayList<>();

        List<UsuarioEntity> listaUsuario = repository.findAll();

        for (UsuarioEntity usuarioEntity : listaUsuario) {
            listaDto.add(converterEntityParaDto(usuarioEntity));
        }

        return listaDto;

    }

    public void usuarioInserir(UsuarioDto usuarioDto) {

        LocalDate hoje = LocalDate.now();
        Optional<UsuarioEntity> usuarioOP = repository.findByEmail(usuarioDto.getEmail());

        if (usuarioOP.isPresent()){
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este e-mail.");
        }

        if (usuarioDto.getDataNascimento().isAfter(hoje)) {
            throw new IllegalArgumentException("A data de nascimento não pode ser futura");
        }

        if (usuarioDto.getDataNascimento().isBefore(hoje.minusYears(500))){
            throw new IllegalArgumentException("A data de nascimento inválida");
        }

        repository.save(converterDtoParaEntity(usuarioDto));
    }

    public UsuarioDto obterUsuarioPorId(Long id) {

        UsuarioDto usuarioDto = new UsuarioDto();

        Optional <UsuarioEntity> usuarioOp = repository.findById(id);

        if (usuarioOp.isPresent()) {
            usuarioDto = converterEntityParaDto(usuarioOp.get());
        }

        return usuarioDto;
    }

    public void usuarioAtualizar(UsuarioDto usuarioDto) {

        Optional<UsuarioEntity> usuarioOP = repository.findById(usuarioDto.getId());

        if (usuarioOP.isPresent()) {

            // usuarioOP.get() --> usuario com os dados do banco de dados
            // usuarioDto --> dados do usuário que vieram do formulário

            UsuarioEntity usuario = usuarioOP.get();
            usuario.setNome(usuarioDto.getNome());
            usuario.setEmail(usuarioDto.getEmail());
            usuario.setMatricula(usuarioDto.getMatricula());
            usuario.setDataNascimento(usuarioDto.getDataNascimento());
            //--Não fazer a atualização da senha quando não vier
            if (!usuarioDto.getSenha().isEmpty()) {
                usuario.setSenha(usuarioDto.getSenha());
            }

            repository.save(usuario);
        }
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private UsuarioDto converterEntityParaDto (UsuarioEntity usuario) {
        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setId(usuario.getId());
        usuarioDto.setNome(usuario.getNome());
        usuarioDto.setEmail(usuario.getEmail());
        usuarioDto.setMatricula(usuario.getMatricula());
        usuarioDto.setDataNascimento(usuario.getDataNascimento());
        return usuarioDto;
    }

    private UsuarioEntity converterDtoParaEntity (UsuarioDto usuarioDto) {
        UsuarioEntity usuario = new UsuarioEntity();
        //usuario.setId();
        usuario.setId(usuarioDto.getId());
        usuario.setNome(usuarioDto.getNome());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setSenha(usuarioDto.getSenha());
        usuario.setMatricula(usuarioDto.getMatricula());
        usuario.setDataNascimento(usuarioDto.getDataNascimento());

        return usuario;
    }
}

