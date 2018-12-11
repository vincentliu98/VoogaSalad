package authoring;

class SavedAuthoringTools {
    private String entityDBXML, phaseDBXML;

    SavedAuthoringTools(String entityDBXML, String phaseDBXML) {
        this.entityDBXML = entityDBXML;
        this.phaseDBXML = phaseDBXML;
    }

    String entityDBXML() {
        return entityDBXML;
    }

    String phaseDBXML() {
        return phaseDBXML;
    }
}
