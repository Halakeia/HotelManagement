package hotel.bao.assemblers;

import hotel.bao.controller.EstadiaController;
import hotel.bao.dtos.EstadiaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class EstadiaAssembler extends GenericAssembler<EstadiaDTO> {

    @Override
    public EntityModel<EstadiaDTO> toModel(EstadiaDTO estadia) {
        EntityModel<EstadiaDTO> estadiaModel = EntityModel.of(estadia);

        // Link para o próprio recurso
        estadiaModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(EstadiaController.class)
                                .findById(estadia.getId()))
                .withSelfRel());

        // Link para listar todas as estadias
        estadiaModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(EstadiaController.class)
                                .findAll(null))
                .withRel("todas-as-estadias"));

        // Link para atualizar
        estadiaModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(EstadiaController.class)
                                .update(null, estadia.getId()))
                .withRel("atualizar"));
        // Link para deletar
        estadiaModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(EstadiaController.class)
                                .delete(estadia.getId()))
                .withRel("deletar"));

        return estadiaModel;
    }
}