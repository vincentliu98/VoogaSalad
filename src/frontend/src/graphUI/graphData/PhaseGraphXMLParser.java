package graphUI.graphData;

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
 * This class handles parsing XML files of phase data and call front end to restore phase data
 *
 * @author jl729
 */

public class PhaseGraphXMLParser {
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

    public Map<String, SinglePhaseData> parseFile(File file) throws IOException, SAXException {
        root = DOCUMENT_BUILDER.parse(file).getDocumentElement();
        NodeList phases = root.getChildNodes();
        Map<String, SinglePhaseData> map = new HashMap<>();

        for (int i = 0; i < phases.getLength(); i++) {
            Node child = phases.item(i);
            System.out.println("Child name: " + child.getNodeName());

            if (child.getNodeType() == Node.ELEMENT_NODE) {
                Element phase = (Element) child;

                String phaseName = phase.getElementsByTagName(PHASE_NAME_TAG).item(0).getChildNodes().item(0).getNodeValue();
                var nodes = phase.getElementsByTagName(NODES_TAG);
                List<String> nodesName = new ArrayList<>();
                Map<String, Pair<Double, Double>> nodesPos = new HashMap<>();
                for (int j = 0; j < nodes.getLength(); j++) {
                    Element node = (Element) nodes.item(j);
                    System.out.println("node tag name: " + node.getTagName());
                    var name = node.getElementsByTagName(NODE_NAME_TAG).item(0).getChildNodes().item(0).getNodeValue();
                    nodesName.add(name);
                    nodesPos.put(name, new Pair<>(Double.parseDouble(node.getElementsByTagName(X_TAG).item(0).getChildNodes().item(0).getNodeValue()),
                            Double.parseDouble(node.getElementsByTagName(Y_TAG).item(0).getChildNodes().item(0).getNodeValue())));
                }

                var connections = phase.getElementsByTagName(CONNECTIONS_TAG);

                Map<Pair<String, String>, GameEvent> nodesConnect = new HashMap<>();
                for (int k = 0; k < nodes.getLength(); k++) {
                    Element connection = (Element) connections.item(k);
                    String from = connection.getElementsByTagName(FROM_TAG).item(0).getChildNodes().item(0).getNodeValue();
                    String to = connection.getElementsByTagName(TO_TAG).item(0).getChildNodes().item(0).getNodeValue();
                    String eventType = connection.getElementsByTagName(TYPE_TAG).item(0).getChildNodes().item(0).getNodeValue();
                    GameEvent gameEvent = null;
                    switch (eventType) {
                        case "MouseClick":
                            gameEvent = new GameEvent.MouseClick();
                            break;
                        case "KeyPress":
                            gameEvent = new GameEvent.MouseClick();
                            break;
                    }
                    nodesConnect.put(new Pair<>(from, to), gameEvent);
                }
                map.put(phaseName, new SinglePhaseData(phaseName, nodesName, nodesPos, nodesConnect));
            }
        }
        return map;
    }

    /**
     * Boilerplate code needed to make a documentBuilder
     */
    public static DocumentBuilder getDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
