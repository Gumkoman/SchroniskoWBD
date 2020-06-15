/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schronisko2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Macius-laptop
 */
public class LoginPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    private TextField textLogin;

    @FXML
    private TextField textHaslo;

    @FXML
    private Button buttonZaloguj;
    
    
    @FXML
    public void buttonLoginOnAction(ActionEvent event) throws IOException{
        System.out.println("ASDASDASDASDASD");
        System.out.println(textHaslo.toString().trim());
        if(textLogin.getText().trim().equals("admin")&&textHaslo.getText().trim().equals("admin")){
        
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);        
            stage.setScene(scene);
            stage.show();
            Stage stage1 = new Stage();
            stage1 = (Stage) buttonZaloguj.getScene().getWindow();
            stage1.close();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Poprawnie zalogowano jako administrator");
                alert.show();
        }else if(textHaslo.getText().trim().isEmpty()){
            System.out.println("asdasda");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Niepoprawne dane logowania");
            alert.showAndWait();
            return;
        }else{
            
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PracownikView.fxml"));
            Parent root = loader.load();
            
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Poprawnie zalogowano na konto uzytkownika");
                alert.show();
            
            PracownikViewController pracownikController = loader.getController();
            pracownikController.transferMessage(textHaslo.getText().trim());
            
            Scene scene = new Scene(root);        
            stage.setScene(scene);
            stage.show();
            
            //usuwanie okna
            Stage stage1 = new Stage();
            stage1 = (Stage) buttonZaloguj.getScene().getWindow();
            stage1.close();
        
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
