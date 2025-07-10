package hotel.bao;

//import hotel.bao.view.Screen;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HotelBaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBaoApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(Screen screen) {
//		return args -> {
//			screen.start();
//		};
//	}
}