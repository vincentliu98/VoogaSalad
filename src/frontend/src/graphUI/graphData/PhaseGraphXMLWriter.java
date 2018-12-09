package graphUI.graphData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Map;

/**
 *
 * @author jl729
 * @author Inchan Hwang
 */
public class PhaseGraphXMLWriter implements PhaseGraphXMLWriterAPI{

    private Map<String, SinglePhaseData> phaseGraph;
    private Document doc;
    private File outFile;

    public PhaseGraphXMLWriter(Map<String, SinglePhaseData> phaseGraph_, File outFile_) {
        phaseGraph = phaseGraph_;
        outFile = outFile_;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generate() {
        try {
            Element rootElement = doc.createElement("frontEndData");
            doc.appendChild(rootElement);

            phaseGraph.keySet().forEach(c -> rootElement.appendChild(encodeSinglePhase(phaseGraph.get(c))));

            TransformerFactory.newInstance().newTransformer().transform(
                    new DOMSource(doc), new StreamResult(outFile));
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    private Element encodeSinglePhase(SinglePhaseData singlePhaseData) {
        var parent = doc.createElement("phase");

        Element name = encodeName(singlePhaseData);
        Element nodes = encodeNodes(singlePhaseData);
        Element connections = encodeConnections(singlePhaseData);

        parent.appendChild(name);
        parent.appendChild(nodes);
        parent.appendChild(connections);

        return parent;
    }

    private Element encodeName(SinglePhaseData singlePhaseData) {
        var name = doc.createElement("name");
        name.appendChild(doc.createTextNode(singlePhaseData.getPhaseName()));
        return name;
    }

    private Element encodeConnections(SinglePhaseData singlePhaseData) {
        var connections = doc.createElement("connections");
        singlePhaseData.getNodesConnect().keySet().forEach(p -> {
            var connection = doc.createElement("connection");
            var from = doc.createElement("from");
            var to = doc.createElement("to");
            var type = doc.createElement("type");
            from.appendChild(doc.createTextNode(p.getKey()));
            to.appendChild(doc.createTextNode(p.getValue()));
            type.appendChild(doc.createTextNode(singlePhaseData.getNodesConnect().get(p).toString()));
            connection.appendChild(from);
            connection.appendChild(to);
            connection.appendChild(type);

            connections.appendChild(connection);
        });
        return connections;
    }

    private Element encodeNodes(SinglePhaseData singlePhaseData) {
        var nodes = doc.createElement("nodes");
        singlePhaseData.getNodesName().forEach(n -> {
            var node = doc.createElement("node");
            var nodeName = doc.createElement("nodeName");
            var nodeX = doc.createElement("X");
            var nodeY = doc.createElement("Y");
            nodeName.appendChild(doc.createTextNode(n));
            nodeX.appendChild(doc.createTextNode(singlePhaseData.getNodesPos().get(n).getKey().toString()));
            nodeY.appendChild(doc.createTextNode(singlePhaseData.getNodesPos().get(n).getValue().toString()));

            node.appendChild(nodeName);
            node.appendChild(nodeX);
            node.appendChild(nodeY);

            nodes.appendChild(node);
        });
        return nodes;
    }
}

