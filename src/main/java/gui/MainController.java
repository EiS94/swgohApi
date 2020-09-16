package gui;

import backend.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import help.swgoh.api.SwgohAPI;
import help.swgoh.api.SwgohAPIBuilder;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainController implements Initializable {


    static Guild g;
    static SwgohAPI api;
    static int ally;

    @FXML
    public ComboBox<String> dropdown;
    public TextArea guildInfo;
    public ProgressBar progress;
    public ComboBox<Integer> dropdownStars;
    public ComboBox<Integer> dropdownGear;
    @FXML
    Button btnGo;
    @FXML
    TextField allyCode;


    private ObservableList<String> list = FXCollections.observableArrayList();
    private ObservableList<Integer> starsList = FXCollections.observableArrayList(1,2,3,4,5,6,7);
    private ObservableList<Integer> gearList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progress.setProgress(0.0);
        dropdownGear.setItems(gearList);
        dropdownStars.setItems(starsList);
        dropdownStars.getSelectionModel().select(6);
        dropdownGear.getSelectionModel().select(11);
    }

    public void getGuildInfos(javafx.event.ActionEvent actionEvent) {
        ally = Integer.parseInt(allyCode.getCharacters().toString());
        api = new SwgohAPIBuilder().withUsername(ApiAccess.getApiUsername()).withPassword(ApiAccess.getApiPassword()).build();
        Thread t = new Thread(new ProgressHandler());
        t.start();
    }

    public void sortDropdown(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        Map<String, List<Tuple<Player, Char>>> map = g.getPlayersAbouveStarAndGear(api, dropdownStars.getValue(), dropdownGear.getValue());
        map = Utils.sortByListSize(map);
        dropdown.getItems().remove(0,dropdown.getItems().size());
        list = FXCollections.observableArrayList();
        for (String s : map.keySet()) {
            list.add(s + " (" + map.get(s).size() + ")");
        }
        dropdown.setItems(list);
        dropdown.getSelectionModel().selectFirst();
    }

    private class ProgressHandler implements Runnable {
        @Override
        public void run() {
            progress.setProgress(0.1);
            double counter = 0;
            JsonParser parser = new JsonParser();
            JsonElement e = null;
            try {
                e = parser.parse(api.getGuild(ally).get());
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
            assert e != null;
            JsonObject json = (JsonObject) e.getAsJsonArray().get(0);
            JsonArray roster = json.get("roster").getAsJsonArray();
            List<Integer> allyCodes = new LinkedList<>();
            for (JsonElement el : roster) {
                JsonObject o = (JsonObject) el;
                allyCodes.add(o.get("allyCode").getAsInt());
                counter++;
                double test = counter / roster.size() * 0.2;
                progress.setProgress(0.1 + test);
            }
            counter = 0;
            List<Player> players = new LinkedList<>();
            JsonArray array = null;
            try {
                array = parser.parse(api.getPlayers(allyCodes).get()).getAsJsonArray();
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
            while (array.size() < allyCodes.size()) {
                try {
                    array = parser.parse(api.getPlayers(allyCodes).get()).getAsJsonArray();
                } catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                }
            }
            progress.setProgress(0.8);
            for (JsonElement el : array) {
                JsonObject o = (JsonObject) el;
                players.add(Player.getPlayerFromJsonObject(o));
                progress.setProgress(0.8 + (counter++ / allyCodes.size() * 0.2));
            }
            players.sort(Comparator.comparing(Player::getGrandArenaLifeTimePoints).reversed());
            g = new Guild(players);
            guildInfo.setText("Name: " + g.getPlayers().get(0).getGuildName() + ", Members: " + g.getPlayers().size());
            Map<String, List<Tuple<Player, Char>>> map = null;
            try {
                map = g.getPlayersAbouveStarAndGear(api, 7, 12);
            } catch (ExecutionException | InterruptedException ex) {
                ex.printStackTrace();
            }
            assert map != null;
            map = Utils.sortByListSize(map);
            for (String s : map.keySet()) {
                list.add(s + " (" + map.get(s).size() + ")");
            }
            dropdown.setItems(list);
            dropdown.getSelectionModel().selectFirst();
        }
    }

}
