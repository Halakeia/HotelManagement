package hotel.bao.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class GenericAssembler <T> implements RepresentationModelAssembler<T, EntityModel<T>> {
    /**
     * Builds a URI for a given controller and ID.
     * @param controllerClass
     * @param id
     * @return
     * @param <C>
     */
    protected <C> String buildUri(Class<C> controllerClass, Long id) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment(controllerClass.getSimpleName().replace("Controller", "").toLowerCase())
                .pathSegment(String.valueOf(id))
                .toUriString();
    }
}
