import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoadPageController {

    @FXML
    private Button btn_loadBackToHome;

    @FXML
    private Button btn_submitLoad;

    @FXML
    private TextField tf_numberToSendTo;

    @FXML
    private TextField tf_optionalMessage;

    private Stage stage;
    private Scene scene; 
    private Parent root;

    public void loadBackToHomeHandler(ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));

        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
