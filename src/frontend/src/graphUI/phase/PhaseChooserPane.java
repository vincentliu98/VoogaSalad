package graphUI.phase;

import api.SubView;
import graphUI.graphData.PhaseGraphXMLWriter;
import graphUI.graphData.SinglePhaseData;
import graphUI.groovy.GroovyPaneFactory.GroovyPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import phase.api.PhaseDB;
import utils.ErrorWindow;

import java.io.File;
import java.util.*;
import java.util.function.Supplier;

/**
 * PhaseChooserPane
 *  - Parent Pane of PhasePane.
 *
 * It is the entire tab that contains the listView of all the Phase on the right and the pane of nodes on the right
 *
 * @author Amy
 * @author jl729
 */

public class PhaseChooserPane implements SubView<GridPane> {
    private GridPane view;
    private PhaseDB phaseDB;
    private Supplier<GroovyPane> genGroovyPane;
    private ObservableList<String> phaseList;
    private ListView<String> phaseListView;
    private List<PhasePane> phasePanes;
    private Map<String, SinglePhaseData> phaseDataMap;
    private PhaseGraphXMLWriter phaseGraphXMLWriter;

    public PhaseChooserPane(PhaseDB phaseDB, Supplier<GroovyPane> genGroovyPane) {
        this.phaseDB = phaseDB;
        this.genGroovyPane = genGroovyPane;
        phaseList = FXCollections.observableArrayList();
        phasePanes = new ArrayList<>();

        phaseDataMap = new HashMap<>();

        initializeView();
        setupLeft();
    }

    public Map<String, SinglePhaseData> getPhaseDataMap() {
        return phaseDataMap;
    }

    public void setPhaseDataMap(Map<String, SinglePhaseData> phaseDataMap) {
        this.phaseDataMap = phaseDataMap;
    }

    public void checkMapUpdate(String message){
        System.out.println(message + "\nPhase Data Map: " + phaseDataMap);
    }

    public void reset(Map<String, SinglePhaseData> phaseDataMap){
        this.phaseDataMap = phaseDataMap;
        phaseDB.phases().forEach(phase -> phaseDB.removeGraph(phase));
        phaseList.clear();
        phasePanes.clear();

        phaseDataMap.keySet().forEach(a -> {
            phaseList.add(a);
            recoverPhasePane(a, phaseDataMap);
        });

        System.out.println("map" + phaseDataMap);
        phaseListView.setItems(phaseList);
    }

    private void initializeView() {
        view = new GridPane();
        view.getStyleClass().add("phasePane");
        var col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        var col2 = new ColumnConstraints();
        col2.setPercentWidth(85);
        view.getColumnConstraints().addAll(col1, col2);
    }

    private void setupLeft() {
        var vbox = new VBox();
        var stack = new StackPane();
        var createPhaseBtn =  new Button("New PHASE");
        stack.getChildren().add(createPhaseBtn);
        vbox.getStyleClass().add("vboxPhase");
        createPhaseBtn.getStyleClass().add("phaseBtn");
        phaseListView = new ListView<>(phaseList);
        phaseListView.setMinHeight(570);
        vbox.setSpacing(15);
        vbox.getChildren().addAll(stack, phaseListView);
        phaseListView.getSelectionModel().selectedIndexProperty().addListener((e, o, n) -> {
            clearRightPane();
            view.add(phasePanes.get(n.intValue()).getView(), 1, 0);
        });
        createPhaseBtn.setOnMouseClicked(this::handlePhaseCreation);
        view.add(vbox, 0, 0);
    }

    private void clearRightPane() {
        var toRemove = new ArrayList<Node>();
        for(var node : view.getChildren()) {
            if(GridPane.getColumnIndex(node) == 1) {
                toRemove.add(node);
            }
        }
        view.getChildren().removeAll(toRemove);
    }

    private void handlePhaseCreation(MouseEvent e) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setContentText("Please enter the name of this phase graph:");
        dialog.showAndWait().ifPresent(name -> {
            createNewPhasePane(name);
        });
    }

    private void createNewPhasePane(String name) {
        var tryGraph = phaseDB.createGraph(name);
        if(tryGraph.isSuccess()) {
            try {
                var graph = tryGraph.get();

                var singlePhaseData = new SinglePhaseData(name);
                phaseDataMap.put(name, singlePhaseData);
                System.out.println(phaseDataMap);

                var phasePane = new PhasePane(phaseDB, genGroovyPane, graph, singlePhaseData, this);
                phaseList.add(name);
                phasePanes.add(phasePane);
                phaseListView.getSelectionModel().select(phaseList.size()-1);
            } catch (Throwable t) {
                new ErrorWindow("Error", "t.toString()").showAndWait();
            }
        }
    }

    private void recoverPhasePane(String name, Map<String, SinglePhaseData> phaseDataMap) {
        var tryGraph = phaseDB.createGraph(name);
        if(tryGraph.isSuccess()) {
            try {
                var graph = tryGraph.get();

                var singlePhaseData = phaseDataMap.get(name);
                phaseDataMap.put(name, singlePhaseData);

                var phasePane = new PhasePane(phaseDB, genGroovyPane, graph, singlePhaseData, this);
                phasePanes.add(phasePane);

                phasePane.recoverData();

                phaseListView.getSelectionModel().select(phaseList.size()-1);
            } catch (Throwable t) {
                new ErrorWindow("Error", "t.toString()").showAndWait();
            }
        }
    }

    public File generateXML() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        File file = fileChooser.showSaveDialog(new Stage());
        phaseGraphXMLWriter = new PhaseGraphXMLWriter(phaseDataMap, file);
        phaseGraphXMLWriter.generate();
        return file;
    }

    public void saveXML(File oldFile){
        phaseGraphXMLWriter = new PhaseGraphXMLWriter(phaseDataMap, oldFile);
        phaseGraphXMLWriter.generate();
    }
    @Override
    public GridPane getView() {
        return view;
    }
}
