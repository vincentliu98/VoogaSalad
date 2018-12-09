package graphUI.graphData;

import javafx.scene.input.KeyCode;
import javafx.util.Pair;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import phase.api.GameEvent;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class handles parsing XML files and return a phaseDataMap in Map<String, SinglePhaseData> type
 *
 * @author jl729
 */

public class PhaseGraphXMLParser implements PhaseGraphXMLParserAPI {
    private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();
    private final String PHASE_NAME_TAG = "name";
    private final String NODES_TAG = "nodes";
    private final String NODE_NAME_TAG = "nodeName";
    private final String X_TAG = "X";
    private final String Y_TAG = "Y";
    private final String CONNECTIONS_TAG = "connections";
    private final String FROM_TAG = "from";
    private final String TO_TAG = "to";
    private final String TYPE_TAG = "type";
    private Element root;

    /**
     * Create a parser for XML files of given type.
     */
    public PhaseGraphXMLParser() {
    }

    @Override
    public Map<String, SinglePhaseData> parseFile(File file) throws IOException, SAXException {
        Map<String, SinglePhaseData> map = new HashMap<>();
        root = DOCUMENT_BUILDER.parse(file).getDocumentElement();
        NodeList phases = root.getChildNodes();
        for (int i = 0; i < phases.getLength(); i++) {
            Node child = phases.item(i);
            parseXML(map, child);
        }
        return map;
    }

    private void parseXML(Map<String, SinglePhaseData> map, Node child) {
        if (child.getNodeType() == Node.ELEMENT_NODE) {
            List<String> nodesName = new ArrayList<>();
            Map<String, Pair<Double, Double>> nodesPos = new HashMap<>();
            Map<Pair<String, String>, GameEvent> nodesConnect = new HashMap<>();

            Element phase = (Element) child;
            String phaseName = phase.getElementsByTagName(PHASE_NAME_TAG).item(0).getChildNodes().item(0).getNodeValue();

            parseNodes(phase, nodesName, nodesPos);
            parseConnections(phase, nodesConnect);

            map.put(phaseName, new SinglePhaseData(phaseName, nodesName, nodesPos, nodesConnect));
        }
    }

    private void parseNodes(Element phase, List<String> nodesName, Map<String, Pair<Double, Double>> nodesPos) {
        var nodes = phase.getElementsByTagName(NODES_TAG).item(0);
        var nodesList = nodes.getChildNodes();
        for (int j = 0; j < nodesList.getLength(); j++) {
            Node temp = nodesList.item(j);

            if (temp.getNodeType() == Node.ELEMENT_NODE) {
                Element node = (Element) temp;
                System.out.println("node tag name: " + node.getTagName());
                var name = node.getElementsByTagName(NODE_NAME_TAG).item(0).getChildNodes().item(0).getNodeValue();
                nodesName.add(name);
                nodesPos.put(name, new Pair<>(Double.parseDouble(node.getElementsByTagName(X_TAG).item(0).getChildNodes().item(0).getNodeValue()),
                        Double.parseDouble(node.getElementsByTagName(Y_TAG).item(0).getChildNodes().item(0).getNodeValue())));
            }
        }
    }

    private void parseConnections(Element phase, Map<Pair<String, String>, GameEvent> nodesConnect) {
        var connections = phase.getElementsByTagName(CONNECTIONS_TAG).item(0);
        var connectionsList = connections.getChildNodes();
        for (int k = 0; k < connectionsList.getLength(); k++) {
            Node temp1 = connectionsList.item(k);
            if (temp1.getNodeType() == Node.ELEMENT_NODE) {
                Element connection = (Element) temp1;
                String from = connection.getElementsByTagName(FROM_TAG).item(0).getChildNodes().item(0).getNodeValue();
                String to = connection.getElementsByTagName(TO_TAG).item(0).getChildNodes().item(0).getNodeValue();
                String eventType = connection.getElementsByTagName(TYPE_TAG).item(0).getChildNodes().item(0).getNodeValue();
                GameEvent gameEvent = null;
                if (eventType.startsWith("KeyPress")) {
                    gameEvent = new GameEvent.KeyPress(KeyCode.valueOf(eventType.substring(9)));
                } else {
                    gameEvent = new GameEvent.MouseClick();
                }
                nodesConnect.put(new Pair<>(from, to), gameEvent);
            }
        }
    }

    /**
     * Boilerplate code needed to make a documentBuilder
     */
    private static DocumentBuilder getDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
