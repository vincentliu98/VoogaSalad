package graphUI.graphData;

public interface PhaseGraphXMLWriterAPI {
    /**
     * Generate XML file according to the following format
     * <?xml version="1.0" encoding="UTF-8" standalone="no"?>
     *     <frontEndData>
     *         <phase>
     *             <name>1</name>
     *             <nodes>
     *                 <node>
     *                     <nodeName>1</nodeName>
     *                     <X>100.0</X>
     *                     <Y>50.0</Y>
     *                 </node>
     *                 <node>
     *                     <nodeName>2</nodeName>
     *                     <X>639.0</X>
     *                     <Y>270.0</Y>
     *                 </node>
     *             </nodes>
     *          <connections>
     *                  <connection>
     *                      <from>1</from>
     *                      <to>2</to>
     *                      <type>MouseClick</type>
     *                  </connection>
     *           </connections>
     *        </phase>
     *     </frontEndData>
     */
    void generate();
}
