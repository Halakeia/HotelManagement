package hotel.bao.assemblers;

import hotel.bao.controllers.QuartoController;
import hotel.bao.dtos.QuartoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class QuartoAssembler extends GenericAssembler<QuartoDTO> {

    @Override
    public EntityModel<QuartoDTO> toModel(QuartoDTO quarto) {
        EntityModel<QuartoDTO> quartoModel = EntityModel.of(quarto);

        // Link para o próprio recurso (self)
        quartoModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(QuartoController.class)
                                .findById(quarto.getId()))
                .withSelfRel());

        // Link para a coleção de todos os quartos
        quartoModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(QuartoController.class)
                                .findAll(null))
                .withRel("quartos"));

        // Link para atualizar
        quartoModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(QuartoController.class)
                                .update(null, quarto.getId()))
                .withRel("atualizar"));

        // Link para deletar
        quartoModel.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(QuartoController.class)
                                .delete(quarto.getId()))
                .withRel("deletar"));

//        // Link específico para estadias relacionadas a este quarto
//        quartoModel.add(WebMvcLinkBuilder.linkTo(
//            WebMvcLinkBuilder.methodOn(QuartoController.class)
//                .findEstadias(quarto.getId()))
//                .withRel("estadias"));

        return quartoModel;
    }
}