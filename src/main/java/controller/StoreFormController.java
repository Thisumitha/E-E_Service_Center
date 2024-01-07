package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StoreFormController implements Initializable {

    public JFXTextField searchText;
    public BorderPane pane;
    public VBox layout;

    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    public void backButton(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.setResizable(true);
            stage.setTitle("DashBoard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         List<String> types=new ArrayList<>(itemBo.getTypes());

        for (int i = 0; i <types.size() ; i++) {


            System.out.println(types.get(i));
            try {
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/category.fxml"));
                HBox  hBox=fxmlLoader.load();

                CategoryComponentController categoryComponentController=fxmlLoader.getController();
                categoryComponentController.setData(types.get(i));
                layout.getChildren().add(hBox);
            } catch (IOException |NullPointerException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
