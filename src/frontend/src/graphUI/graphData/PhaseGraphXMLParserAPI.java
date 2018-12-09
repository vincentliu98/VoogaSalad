package graphUI.graphData;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface PhaseGraphXMLParserAPI {
    /**
     * Parse the file to form a phaseDataMap in the type Map<String, SinglePhaseData>
     * @param file
     * @return
     */
    Map<String, SinglePhaseData> parseFile(File file)  throws IOException, SAXException;
}
