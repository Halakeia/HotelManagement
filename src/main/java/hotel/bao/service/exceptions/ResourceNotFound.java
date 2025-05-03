package hotel.bao.service.exceptions;

public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(){
        super();
    }

    public ResourceNotFound(String message){
        super(message);
    }
}
