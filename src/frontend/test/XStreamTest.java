import authoring.AuthoringTools;

public class XStreamTest {
    public static void main(String[] args) {
        var authTools = new AuthoringTools(3, 3);
        System.out.println(authTools.toAuthoringXML());
    }
}
