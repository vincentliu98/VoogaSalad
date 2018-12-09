package graphUI.phase;

import api.SubView;
import graphUI.graphData.SinglePhaseData;
import graphUI.groovy.GroovyPaneFactory.GroovyPane;
import groovy.api.BlockGraph;
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
import phase.api.PhaseDB;
import utility.ObservableUtils;
import utils.ErrorWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * PhaseChooserPane
 *  - Parent Pane of PhasePane.
 *
 * It is the entire tab that contains the listView of all the Phase on the right and the pane of nodes on the right
 *
 * @author Amy
 */

public class PhaseChooserPane implements SubView<GridPane> {
    private GridPane view;
    private PhaseDB phaseDB;
    private Function<BlockGraph, GroovyPane> genGroovyPane;
    private ObservableList<PhasePane> phasePaneList;
    private ListView<String> phaseNameListView;
    private Map<String, SinglePhaseData> phaseDataMap;

    public PhaseChooserPane(PhaseDB phaseDB, Function<BlockGraph, GroovyPane> genGroovyPane) {
        this.phaseDB = phaseDB;
        this.genGroovyPane = genGroovyPane;
        phasePaneList = FXCollections.observableArrayList();
        phaseDataMap = new HashMap<>();
        initializeView();
        setupLeft();
    }


    public void setPhaseDataMap(Map<String, SinglePhaseData> phaseDataMap) {
        this.phaseDataMap = phaseDataMap;
    }

    public void checkMapUpdate(){
        System.out.println("Updated map" + phaseDataMap);
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
        phaseNameListView = new ListView<>(phaseDB.phaseNames());
        phaseNameListView.setMinHeight(570);
        vbox.setSpacing(15);
        vbox.getChildren().addAll(stack, phaseNameListView);
        phaseNameListView.getSelectionModel().selectedIndexProperty().addListener((e, o, n) -> {
            clearRightPane();
            view.add(phasePaneList.get(n.intValue()).getView(), 1, 0);
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
            var tryGraph = phaseDB.createPhaseGraph(name);
            if(tryGraph.isSuccess()) {
                try {
                    var graph = tryGraph.get();

                    var singlePhaseData = new SinglePhaseData(name);
                    phaseDataMap.put(name, singlePhaseData);
                    System.out.println(phaseDataMap);

                    var phasePane = new PhasePane(phaseDB, genGroovyPane, graph, singlePhaseData, this);
                    phasePaneList.add(phasePane);
                    phaseNameListView.getSelectionModel().select(phaseNameListView.getItems().size()-1);
                } catch (Throwable t) {
                    new ErrorWindow("Error", "t.toString()").showAndWait();
                }
            }
        });
    }

    private void removeSelectedPane() {

    }

    @Override
    public GridPane getView() {
        return view;
    }
}
