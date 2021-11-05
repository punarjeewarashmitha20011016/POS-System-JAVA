package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Validator {
    public static Object validate(LinkedHashMap<TextField,Pattern>map, JFXButton button){
        for (TextField txt:map.keySet()
             ) {
            Pattern pattern = map.get(txt);
            if (!pattern.matcher(txt.getText()).matches()) {
                if (!txt.getText().equals("")) {
                    setErrorBorderColor(txt, button);
                }
                return txt;
            }
            setCorrectBorderColor(txt, button);
        }
        return true;
    }

    public static void setCorrectBorderColor(TextField textfield, JFXButton button) {
        textfield.setStyle("-fx-border-color: green");
        button.setDisable(false);
    }

    public static void setErrorBorderColor(TextField textfield, JFXButton button) {
        textfield.setStyle("-fx-border-color: red");
        button.setDisable(true);
    }
}
