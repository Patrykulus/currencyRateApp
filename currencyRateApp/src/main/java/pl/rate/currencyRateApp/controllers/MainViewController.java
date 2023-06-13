package pl.rate.currencyRateApp.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.springframework.stereotype.Controller;
import pl.rate.currencyRateApp.clients.NbpApiClient;
import pl.rate.currencyRateApp.models.Code;
import pl.rate.currencyRateApp.models.ExchangeRate;
import pl.rate.currencyRateApp.services.ExchangeRateService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MainViewController {


    private LocalDate startDate;

    private LocalDate endDate;

    private Code currencyCode;

    private ExchangeRateService exchangeRateService;
    @FXML
    TextArea myLine;

    @FXML
    ComboBox<Code> currencyCodeComboBox;
    @FXML
    DatePicker startDatePicker;

    @FXML
    DatePicker endDatePicker;

    @FXML
    AreaChart<?,?> currencyChart;

    @FXML
    Text infoLabel;



    @FXML
    public void initialize(){
        endDate = LocalDate.now();
        startDate = endDate.minusDays(1);

        endDatePicker.setValue(LocalDate.now());
        startDatePicker.setValue(LocalDate.now().minusDays(1));

        currencyCodeComboBox.getItems().addAll(Code.values());

        exchangeRateService = ExchangeRateService.getInstance(new NbpApiClient());



        startDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate oldDate, LocalDate newDate) {
                startDate = newDate;
                DrawChart();
                myLine.setText(exchangeRateService.getExchangeRates(currencyCode,startDate,endDate).toString());
            }
        });

        endDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate oldDate, LocalDate newDate) {
                endDate = newDate;
                DrawChart();
                if(endDate.isBefore(startDate)){
                    startDate = endDate.minusDays(1);
                    startDatePicker.setValue(startDate);
                }
            }
        });

        startDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate maxDate = endDate.minusDays(1);
                        if (item.isAfter(maxDate)) {
                            setDisable(true);
                        }
                    }
                };
            }
        });

        endDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate maxDate = LocalDate.now();
                        if (item.isAfter(maxDate)) {
                            setDisable(true);
                        }
                    }
                };
            }
        });
    }


    public void comboBoxChange(ActionEvent actionEvent) {
        currencyCode = currencyCodeComboBox.getValue();
        DrawChart();
    }
    public void DrawChart(){
        infoLabel.setText(currencyCode.toString() + " from: " + startDate.toString() + " to: " + endDate.toString() );

        List<ExchangeRate> seriesList = exchangeRateService.getExchangeRates(currencyCode,startDate,endDate);
        XYChart.Series series = new XYChart.Series();
        for(ExchangeRate serie : seriesList){
            series.getData().add(new XYChart.Data(serie.getDate().toString(), serie.getRate()));
        }
        currencyChart.getData().setAll(series);
    }

}
