package graphUI.phase;

import api.SubView;
import graphUI.groovy.GroovyPaneFactory.GroovyPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import phase.api.PhaseDB;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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
    private Supplier<GroovyPane> genGroovyPane;
    private ObservableList<String> phaseList;
    private ListView<String> phaseListView;
    private List<PhasePane> phasePanes;

    public PhaseChooserPane(PhaseDB phaseDB, Supplier<GroovyPane> genGroovyPane) {
        this.phaseDB = phaseDB;
        this.genGroovyPane = genGroovyPane;
        phaseList = FXCollections.observableArrayList();
        phasePanes = new ArrayList<>();
        initializeView();
        setupLeft();
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
            var tryGraph = phaseDB.createGraph(name);
            if(tryGraph.isSuccess()) {
                try {
                    var graph = tryGraph.get();
                    var phasePane = new PhasePane(phaseDB, genGroovyPane, graph);
                    phaseList.add(name);
                    phasePanes.add(phasePane);
                    phaseListView.getSelectionModel().select(phaseList.size()-1);
                } catch (Throwable t) {
                    displayError(t.toString());
                }
            }
        });
    }

    //TODO: Make Utilities Package; Move displayError methods to it.
    private void displayError(String msg) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something's wrong");
        alert.setContentText(msg);
        alert.showAndWait();
    }


    @Override
    public GridPane getView() {
        return view;
    }
}
