package graphUI.phase;

import api.SubView;
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
import phase.api.PhaseGraph;
import utils.ErrorWindow;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * PhaseChooserPane
 * - Parent Pane of PhasePane.
 * <p>
 * It is the entire tab that contains the listView of all the Phase on the right and the pane of nodes on the right
 *
 * @author Amy
 * @author jl729
 */

public class PhaseChooserPane implements SubView<GridPane> {
    private GridPane view;
    private PhaseDB phaseDB;
    private Function<BlockGraph, GroovyPane> genGroovyPane;
    private ObservableList<PhasePane> phasePaneList;
    private ListView<String> phaseNameListView;

    public PhaseChooserPane(PhaseDB phaseDB, Function<BlockGraph, GroovyPane> genGroovyPane) {
        this.phaseDB = phaseDB;
        this.genGroovyPane = genGroovyPane;
        phasePaneList = FXCollections.observableArrayList();
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
        var createPhaseBtn = new Button("New PHASE");
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
        createPhaseBtn.setOnMouseClicked(this::createPhasePane);
        view.add(vbox, 0, 0);
        phaseDB.phaseGraphs().forEach(this::createPhasePane);
    }

    private void clearRightPane() {
        var toRemove = new ArrayList<Node>();
        for (var node : view.getChildren()) {
            if (GridPane.getColumnIndex(node) == 1) {
                toRemove.add(node);
            }
        }
        view.getChildren().removeAll(toRemove);
    }

    private void createPhasePane(MouseEvent e) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setContentText("Please enter the name of this phase graph:");
        dialog.showAndWait().ifPresent(this::createPhasePane);
    }

    private void createPhasePane(String name) {
        var tryGraph = phaseDB.createPhaseGraph(name);
        if (tryGraph.isSuccess()) {
            try {
                createPhasePane(tryGraph.get());
            } catch (Throwable t) {
                t.printStackTrace();
                ErrorWindow.display("Error", t.toString());
            }
        }
    }

    private void createPhasePane(PhaseGraph graph) {
        phasePaneList.add(new PhasePane(phaseDB, genGroovyPane, graph));
        phaseNameListView.getSelectionModel().select(phasePaneList.size() - 1);
    }

    @Override
    public GridPane getView() {
        return view;
    }
}
