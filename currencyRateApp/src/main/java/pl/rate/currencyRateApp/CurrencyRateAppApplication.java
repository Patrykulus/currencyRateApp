package pl.rate.currencyRateApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;



@SpringBootApplication
public class CurrencyRateAppApplication extends Application {

	public static ConfigurableApplicationContext springContext;
	private FXMLLoader fxmlLoader;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(CurrencyRateAppApplication.class);
		fxmlLoader = new FXMLLoader();
		fxmlLoader.setControllerFactory(springContext::getBean);
	}

	@Override
	public void stop() throws Exception {
		springContext.stop();
	}

	@Override
	public void start(Stage stage) throws Exception {
		fxmlLoader.setLocation(getClass().getResource("/mainScreen.fxml"));
		Parent root = fxmlLoader.load();
		stage.setTitle("Currency App");
		Image logo = new Image("logo.png");
		stage.getIcons().add(logo);
		Scene scene = new Scene(root,1000,600);
		stage.setScene(scene);
		stage.show();
	}
}
