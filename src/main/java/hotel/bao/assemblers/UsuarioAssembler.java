package hotel.bao.assemblers;

import hotel.bao.controllers.UsuarioController;
import hotel.bao.dtos.UsuarioDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAssembler extends GenericAssembler<UsuarioDTO> {

    @Override
    public EntityModel<UsuarioDTO> toModel(UsuarioDTO usuario) {
        EntityModel<UsuarioDTO> usuarioModel = EntityModel.of(usuario);

        // Link para o próprio recurso (self)
        String selfUri = buildUri(UsuarioController.class, usuario.getId());
        usuarioModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(UsuarioController.class)
                                .findById(usuario.getId()))
                .withSelfRel());

        // Link para a coleção de todos os usuários
        usuarioModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(UsuarioController.class)
                                .findAll(null))
                .withRel("usuarios"));

        // Links para ações específicas
        usuarioModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(UsuarioController.class)
                                .update(usuario.getId(), null))
                .withRel("atualizar"));

        usuarioModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(UsuarioController.class)
                                .delete(usuario.getId()))
                .withRel("deletar"));

        return usuarioModel;
    }
}