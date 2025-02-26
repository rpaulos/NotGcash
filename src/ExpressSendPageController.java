
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

public class ExpressSendPageController {

    @FXML
    private Button btn_sendBackToHome;

    @FXML
    private Button btn_submitExpressSend;

    @FXML
    private TextField tf_amountToSend;

    @FXML
    private TextField tf_numberToSendTo;

    @FXML
    private TextField tf_optionalMessage;

    private Stage stage;
    private Scene scene; 
    private Parent root;

    public static String number;
    public static float myBalance;

    private static boolean isEmpty(TextField field) {
        System.out.println("myBalance: " + myBalance);
        return field == null || field.getText().trim().isEmpty();
    }

    //Handles the Express Send feature of GCASH
    public void expressSendHandler(ActionEvent event) throws IOException {

        //Checks if the tf_numberToSendTo and tf_amountToSend are empty
        if (isEmpty(tf_numberToSendTo) || isEmpty(tf_amountToSend)) {
            try {
                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ErrorPopUp.fxml"));
                Parent root = fxmlLoader.load();

                ErrorPopUpController controller = fxmlLoader.getController();
                controller.setErrorMessage("An error has occurred while processing action. Make sure to answer all fields before submitting.");
                
                Stage newStage = new Stage();
                newStage.setTitle("Error: Empty field");
                newStage.setScene(new Scene(root));
                newStage.centerOnScreen();
                newStage.show();
                            
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            //Get the numberToSendTo and amountToSend
            String numberToSendTo = tf_numberToSendTo.getText();
            float amountToSend = Float.parseFloat(tf_amountToSend.getText());

            //Checks if there's enough balance to send
            if (myBalance < amountToSend) {

                try {
                    //Load the error pop up
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ErrorPopUp.fxml"));
                    Parent root = fxmlLoader.load();
    
                    ErrorPopUpController controller = fxmlLoader.getController();
                    //Set new message for insufficient balance
                    controller.setErrorMessage("Insufficient Balance.");
                    
                    Stage newStage = new Stage();
                    newStage.setTitle("Error: Insufficient Balance");
                    newStage.setScene(new Scene(root));
                    newStage.centerOnScreen();
                    newStage.show();
                                
                } catch (Exception e) {
                    e.printStackTrace();
                }

            //Checks if the numberToSendTo is the same as the number the user added
            } else if (numberToSendTo.equals(number)) {

                try {  
                    //Load the error pop up
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ErrorPopUp.fxml"));
                    Parent root = fxmlLoader.load();
    
                    ErrorPopUpController controller = fxmlLoader.getController();
                    //Set new message for sending money to yourself
                    controller.setErrorMessage("Error: Cannot express send money to your own account. Please enter a ddiferent account.");
                    
                    Stage newStage = new Stage();
                    newStage.setTitle("Transaction Error");
                    newStage.setScene(new Scene(root));
                    newStage.centerOnScreen();
                    newStage.show();
                                
                } catch (Exception e) {
                    e.printStackTrace();
                }

            //Calls the expressSend and negateBalance DB Handler to go through the transaction
            } else {

                // try {
                //     //Calls the expressSend method in order to add money to the numberToSendTo
                //     DatabaseHandler.expressSend(numberToSendTo, amountToSend);

                //     //Calls the negateBalance method in order to subtract the money the user sent
                //     Float negateFromBalance = amountToSend;
                //     DatabaseHandler.negateBalance(negateFromBalance, number);

                //     //Loads the success pop up
                //     FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuccessPopUp.fxml"));
                //     Parent root = fxmlLoader.load();
    
                //     SuccessPopUpController controller = fxmlLoader.getController();

                //     //Set new message for completing transaction
                //     controller.setSuccessMessage("Transaction completed");
                    
                //     Stage newStage = new Stage();
                //     newStage.setTitle("Success: Transaction Successful");
                //     newStage.setScene(new Scene(root));
                //     newStage.centerOnScreen();
                //     newStage.show();
                                
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }

                //TODO: Pass amountToSend to the ReceiptPageController
                ReceiptPageController.amountSent = amountToSend;
                ReceiptPageController.numberSentTo = numberToSendTo;

                //Calls the expressSend method in order to add money to the numberToSendTo
                DatabaseHandler.expressSend(numberToSendTo, amountToSend);

                //Calls the negateBalance method in order to subtract the money the user sent
                Float negateFromBalance = amountToSend;
                DatabaseHandler.negateBalance(negateFromBalance, number);

                //Loads the receipt page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ReceiptPage.fxml"));

                root = loader.load();

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    //Handles the back button to the home page
    public void sendBackToHomeHandler(ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));

        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
