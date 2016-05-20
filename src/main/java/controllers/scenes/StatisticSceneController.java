package controllers.scenes;

import static controllers.windows.main_windows.StartSceneController.CURRENT_CLIENT;

import data.DataInfo;
import data.dao.ExerciseDAO;
import data.data_sets.Exercise;
import database_service.database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by victor on 20.05.16.
 */
// TODO check by null values which are used to create charts
public class StatisticSceneController implements Initializable {

    private static final MainSceneController CONTROLLER = new MainSceneController();
    private static final Database DATABASE = Database.getInstance();
    private Exercise exerciseForLineChart;
    private Exercise exerciseForBarChart;
    private ExerciseDAO exerciseDAO;

    @FXML
    private PieChart pieChart;

    @FXML
    private LineChart<String, Integer> lineChart;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private ChoiceBox<String> exercises;

    @FXML
    private ChoiceBox<String> exercisesBarChart;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        exerciseDAO = new ExerciseDAO(DATABASE.getConnection());

        pieChart.setMaxSize(100, 100);
        Map<Exercise, Integer> freqMap = DataInfo.getTrainingFrequencyMap(DATABASE, CURRENT_CLIENT);

        list.addAll(freqMap.entrySet().stream()
                .map(entry -> new PieChart.Data(entry.getKey().getExerciseName(), entry.getValue()))
                .collect(Collectors.toList()));

        pieChart.setData(list);

        for (Exercise exercise : DataInfo.getResultExercises(DATABASE, CURRENT_CLIENT)) {
            exercises.getItems().add(exercise.getExerciseName());
            exercisesBarChart.getItems().addAll(exercise.getExerciseName());
        }

    }

    @FXML
    public void buildLineChart(ActionEvent event) {
        clear(event);
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        exerciseDAO.getAll().stream()
                .filter(ex -> ex.getExerciseName()
                        .equals(exercises.getValue())).forEach(ex -> exerciseForLineChart = ex);


        Map<String, Integer> resultMap = DataInfo.getExerciseByDate(DATABASE, CURRENT_CLIENT, exerciseForLineChart);

        for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        lineChart.getData().add(series);
    }

    // bottom - Date(String), chart - 1.expected, 2.real
    public void buildBarChart(ActionEvent event) {
        clear(event);
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        exerciseDAO.getAll().stream()
                .filter(ex -> ex.getExerciseName()
                        .equals(exercisesBarChart.getValue())).forEach(ex -> exerciseForBarChart = ex);

        Map<String, Integer> map = DataInfo.trainingsResults(DATABASE, CURRENT_CLIENT,
                exerciseForBarChart);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);


    }


    private void clear(ActionEvent event) {
        lineChart.getData().clear();
    }

    private void clearBarChart(ActionEvent event) {
        barChart.getData().clear();
    }

    @FXML
    public void showResults(ActionEvent event) {
        CONTROLLER.showResults(event);
    }

    @FXML
    public void showTrainings(ActionEvent event) {
        CONTROLLER.showTrainings(event);
    }

    @FXML
    public void showPersonalData(ActionEvent event) {
        CONTROLLER.showPersonalData(event);
    }

    @FXML
    private void exit(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

}
