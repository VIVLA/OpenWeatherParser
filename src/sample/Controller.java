package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Controller {


    @FXML
    private Text temp;

    @FXML
    private Text feel;

    @FXML
    private Text min;

    @FXML
    private Text max;

    @FXML
    private Text pressure;

    @FXML
    private Text downfall;

    @FXML
    private TextField user_city;

    @FXML
    private Button getData;

    @FXML
    private Text error;

    @FXML
    void initialize() {
        getData.setOnAction(actionEvent -> {
            String urlAddress = "https://api.openweathermap.org/data/2.5/weather?q=" + user_city.getText().trim() +
                    "&appid=ced9cc63e0d14a0dc522391ae52fe056&units=metric&lang=ru";
            String output = getDataFromWeb(urlAddress);
            if (!output.isEmpty()) {
                JSONObject obj = new JSONObject(output);
                temp.setText("Температура: " + obj.getJSONObject("main").getDouble("temp") + " \u2103");
                feel.setText("Ощущается: " + obj.getJSONObject("main").getDouble("feels_like") + " \u2103");
                min.setText("Минимум: " + obj.getJSONObject("main").getDouble("temp_min") + " \u2103");
                max.setText("Максимум: " + obj.getJSONObject("main").getDouble("temp_max") + " \u2103");
                pressure.setText("Давление: " + obj.getJSONObject("main").getDouble("pressure") + " Па");
                downfall.setText("" + obj.getJSONArray("weather").
                        getJSONObject(0).getString("description"));
                error.setText("");

            }
        });
    }

    private String getDataFromWeb(String urlAddress) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL url = new URL(urlAddress);
            URLConnection connection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            if (!((line = bufferedReader.readLine()).isEmpty()))
                stringBuffer.append(line).append("\n");

        } catch (Exception e) {
            error.setText("No internet connection");
            temp.setText("Температура: ");
            feel.setText("Ощущается: ");
            min.setText("Минимум: ");
            max.setText("Максимум: ");
            pressure.setText("Давление: ");
            downfall.setText("");
        }
        return stringBuffer.toString();
    }
}

