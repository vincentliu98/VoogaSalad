package social;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreen {
    public static final String LOGO_PATH = "duke_logo.png";
    public static final String MOTO = "Your Home for Games";


    private GridPane myPane;
    private Scene myScene;
    private Stage myStage;

    public LoginScreen() { }

    public Stage launchLogin(){
        myStage = new Stage();

        initPane();
        initScene();
        initLogo();
        initMoto();
        initFields();

        myStage.setScene(myScene);
        myStage.setTitle("Login");
        return myStage;
    }

    private void initPane(){
        myPane = new GridPane();
        myPane.setAlignment(Pos.TOP_CENTER);
        myPane.setVgap(15.0D);
        myPane.setPadding(new Insets(40.0D, 70.0D, 40.0D, 70.0D));

        for(int i = 0; i < 4; ++i) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(25.0D);
            myPane.getColumnConstraints().add(col);
        }

        myPane.setGridLinesVisible(false);
    }

    private void initScene(){
        myScene = new Scene(myPane, 400.0D, 500.0D);
    }

    private void initLogo(){
        Image logoStream = new Image(LOGO_PATH);
        ImageView logo = new ImageView(logoStream);
        logo.setFitWidth(100.0D);
        logo.setPreserveRatio(true);

        HBox imageBox = new HBox();
        imageBox.getChildren().add(logo);
        imageBox.setAlignment(Pos.CENTER);

        myPane.add(imageBox, 1, 0, 2, 1);
    }

    private void initMoto(){
        Text motoText = new Text(MOTO);
        HBox motoBox = new HBox();
        motoBox.getChildren().add(motoText);
        motoBox.setAlignment(Pos.CENTER);

        myPane.add(motoBox, 0, 1, 4, 1);
    }

    private void initFields(){
        TextField usernameField = new TextField();
        usernameField.setPromptText("username");
        TextField passwordField = new TextField();
        passwordField.setPromptText("password");
        Button btn = new Button("LOGIN");
        btn.setPrefWidth(260.0D);
        //CheckBox cBox = new CheckBox("Remember me"); TODO: Do we need this?
        Text register = new Text("Register");
        register.setOnMouseClicked(e -> {
            RegisterScreen myRegistration = new RegisterScreen();
            myRegistration.launchRegistration().show();
        });
        //Text forgotPassword = new Text("Forgot your password?");

        btn.setOnMouseClicked(e -> {
            // throw exceptions for invalid password, username
            // assuming a valid user was retrieved from the database (myUser)
            User myUser = new User(10, "bloop");// TODO: Remove later (just a placeholder)
            myUser.changeAvatar(new Image("/profile-images/ocean.jpeg"));
            // TODO: Remove - just for testing that loading the gamestate works
            myUser.saveGameState("SwordAndArrow.xml", "\n" +
                    "<game>\n" +
                    "    <gameplay.Node>\n" +
                    "        <myPhaseName>A</myPhaseName>\n" +
                    "        <myName>b</myName>\n" +
                    "        <myExecution>selected = $clicked</myExecution>\n" +
                    "    </gameplay.Node>\n" +
                    "    <gameplay.Node>\n" +
                    "        <myPhaseName>A</myPhaseName>\n" +
                    "        <myName>d</myName>\n" +
                    "        <myExecution>$clicked.props.hp = $clicked.props.hp - selected.props.dmg\n" +
                    "            if($clicked.props.hp &lt;= 0) { GameMethods.removeEntity($clicked) }\n" +
                    "            GameMethods.toNextPlayer()\n" +
                    "            GameMethods.$goto(&apos;A&apos;)</myExecution>\n" +
                    "    </gameplay.Node>\n" +
                    "    <gameplay.Node>\n" +
                    "        <myPhaseName>A</myPhaseName>\n" +
                    "        <myName>c</myName>\n" +
                    "        <myExecution>GameMethods.moveEntity(selected, $clicked)\n" +
                    "            GameMethods.toNextPlayer()\n" +
                    "            GameMethods.$goto(&apos;A&apos;)</myExecution>\n" +
                    "    </gameplay.Node>\n" +
                    "    <gameplay.Node>\n" +
                    "        <myPhaseName>A</myPhaseName>\n" +
                    "        <myName>A</myName>\n" +
                    "        <myExecution></myExecution>\n" +
                    "    </gameplay.Node>\n" +
                    "    <gameplay.Edge>\n" +
                    "        <myPhaseName>A</myPhaseName>\n" +
                    "        <myStartNodeName>b</myStartNodeName>\n" +
                    "        <myEndNodeName>A</myEndNodeName>\n" +
                    "        <myTrigger class=\"phase.api.GameEvent$KeyPress\">\n" +
                    "            <code>ESCAPE</code>\n" +
                    "        </myTrigger>\n" +
                    "        <myGuard>GameMethods.$return(true)</myGuard>\n" +
                    "    </gameplay.Edge>\n" +
                    "    <gameplay.Edge>\n" +
                    "        <myPhaseName>A</myPhaseName>\n" +
                    "        <myStartNodeName>b</myStartNodeName>\n" +
                    "        <myEndNodeName>d</myEndNodeName>\n" +
                    "        <myTrigger class=\"phase.api.GameEvent$MouseClick\"/>\n" +
                    "        <myGuard>GameMethods.$return(GameMethods.isEntity($clicked) &amp;&amp; !GameMethods.getCurrentPlayer().isMyEntity($clicked) &amp;&amp; GameMethods.distance($clicked, selected) &lt;= selected.props.attackRange)</myGuard>\n" +
                    "    </gameplay.Edge>\n" +
                    "    <gameplay.Edge>\n" +
                    "        <myPhaseName>A</myPhaseName>\n" +
                    "        <myStartNodeName>b</myStartNodeName>\n" +
                    "        <myEndNodeName>c</myEndNodeName>\n" +
                    "        <myTrigger class=\"phase.api.GameEvent$MouseClick\"/>\n" +
                    "        <myGuard>GameMethods.$return(GameMethods.isTile($clicked) &amp;&amp; GameMethods.distance($clicked, selected) &lt;= 1)</myGuard>\n" +
                    "    </gameplay.Edge>\n" +
                    "    <gameplay.Edge>\n" +
                    "        <myPhaseName>A</myPhaseName>\n" +
                    "        <myStartNodeName>A</myStartNodeName>\n" +
                    "        <myEndNodeName>b</myEndNodeName>\n" +
                    "        <myTrigger class=\"phase.api.GameEvent$MouseClick\"/>\n" +
                    "        <myGuard>if(((GameMethods.isEntity($clicked)) &amp;&amp; (GameMethods.getCurrentPlayer().isMyEntity($clicked)))) {\n" +
                    "            $return = true\n" +
                    "\n" +
                    "            }\n" +
                    "            else {\n" +
                    "            $return = false\n" +
                    "\n" +
                    "            }\n" +
                    "        </myGuard>\n" +
                    "    </gameplay.Edge>\n" +
                    "    <gameplay.Phase>\n" +
                    "        <myStartNodeName>A</myStartNodeName>\n" +
                    "        <myCurrentNodeName>A</myCurrentNodeName>\n" +
                    "        <myNodeNames>\n" +
                    "            <string>b</string>\n" +
                    "            <string>d</string>\n" +
                    "            <string>c</string>\n" +
                    "            <string>A</string>\n" +
                    "        </myNodeNames>\n" +
                    "    </gameplay.Phase>\n" +
                    "    <winCondition>if(GameMethods.hasNoEntities(GameMethods.getCurrentPlayerName())) {   GameMethods.endGame(GameMethods.getCurrentPlayerName() + &apos; LOST!&apos;)}</winCondition>\n" +
                    "    <grid-width>5</grid-width>\n" +
                    "    <grid-height>5</grid-height>\n" +
                    "    <gameplay.EntityPrototype>\n" +
                    "        <name>bowman</name>\n" +
                    "        <props>\n" +
                    "            <entry>\n" +
                    "                <string>attackRange</string>\n" +
                    "                <int>3</int>\n" +
                    "            </entry>\n" +
                    "            <entry>\n" +
                    "                <string>hp</string>\n" +
                    "                <int>5</int>\n" +
                    "            </entry>\n" +
                    "            <entry>\n" +
                    "                <string>dmg</string>\n" +
                    "                <int>1</int>\n" +
                    "            </entry>\n" +
                    "        </props>\n" +
                    "        <myWidth>2</myWidth>\n" +
                    "        <myHeight>2</myHeight>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>bowman1.png</string>\n" +
                    "            <string>bowman2.png</string>\n" +
                    "            <string>bowman3.png</string>\n" +
                    "            <string>bowman4.png</string>\n" +
                    "            <string>bowman5.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector>$return = $this.props.hp-1</myImageSelector>\n" +
                    "    </gameplay.EntityPrototype>\n" +
                    "    <gameplay.EntityPrototype>\n" +
                    "        <name>swordman</name>\n" +
                    "        <props>\n" +
                    "            <entry>\n" +
                    "                <string>attackRange</string>\n" +
                    "                <int>1</int>\n" +
                    "            </entry>\n" +
                    "            <entry>\n" +
                    "                <string>hp</string>\n" +
                    "                <int>5</int>\n" +
                    "            </entry>\n" +
                    "            <entry>\n" +
                    "                <string>dmg</string>\n" +
                    "                <int>3</int>\n" +
                    "            </entry>\n" +
                    "        </props>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>swordman1.png</string>\n" +
                    "            <string>swordman2.png</string>\n" +
                    "            <string>swordman3.png</string>\n" +
                    "            <string>swordman4.png</string>\n" +
                    "            <string>swordman5.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector>$return = $this.props.hp-1</myImageSelector>\n" +
                    "    </gameplay.EntityPrototype>\n" +
                    "    <gameplay.Entity>\n" +
                    "        <myID>27</myID>\n" +
                    "        <name>swordman</name>\n" +
                    "        <props>\n" +
                    "            <entry>\n" +
                    "                <string>attackRange</string>\n" +
                    "                <int>1</int>\n" +
                    "            </entry>\n" +
                    "            <entry>\n" +
                    "                <string>hp</string>\n" +
                    "                <int>5</int>\n" +
                    "            </entry>\n" +
                    "            <entry>\n" +
                    "                <string>dmg</string>\n" +
                    "                <int>3</int>\n" +
                    "            </entry>\n" +
                    "        </props>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>0</x>\n" +
                    "            <y>1</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>swordman1.png</string>\n" +
                    "            <string>swordman2.png</string>\n" +
                    "            <string>swordman3.png</string>\n" +
                    "            <string>swordman4.png</string>\n" +
                    "            <string>swordman5.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector>$return = $this.props.hp-1</myImageSelector>\n" +
                    "    </gameplay.Entity>\n" +
                    "    <gameplay.Entity>\n" +
                    "        <myID>28</myID>\n" +
                    "        <name>bowman</name>\n" +
                    "        <props>\n" +
                    "            <entry>\n" +
                    "                <string>attackRange</string>\n" +
                    "                <int>3</int>\n" +
                    "            </entry>\n" +
                    "            <entry>\n" +
                    "                <string>hp</string>\n" +
                    "                <int>5</int>\n" +
                    "            </entry>\n" +
                    "            <entry>\n" +
                    "                <string>dmg</string>\n" +
                    "                <int>1</int>\n" +
                    "            </entry>\n" +
                    "        </props>\n" +
                    "        <myWidth>2</myWidth>\n" +
                    "        <myHeight>2</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>1</x>\n" +
                    "            <y>3</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>bowman1.png</string>\n" +
                    "            <string>bowman2.png</string>\n" +
                    "            <string>bowman3.png</string>\n" +
                    "            <string>bowman4.png</string>\n" +
                    "            <string>bowman5.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector>$return = $this.props.hp-1</myImageSelector>\n" +
                    "    </gameplay.Entity>\n" +
                    "    <gameplay.Entity>\n" +
                    "        <myID>29</myID>\n" +
                    "        <name>bowman</name>\n" +
                    "        <props>\n" +
                    "            <entry>\n" +
                    "                <string>attackRange</string>\n" +
                    "                <int>3</int>\n" +
                    "            </entry>\n" +
                    "            <entry>\n" +
                    "                <string>hp</string>\n" +
                    "                <int>5</int>\n" +
                    "            </entry>\n" +
                    "            <entry>\n" +
                    "                <string>dmg</string>\n" +
                    "                <int>1</int>\n" +
                    "            </entry>\n" +
                    "        </props>\n" +
                    "        <myWidth>2</myWidth>\n" +
                    "        <myHeight>2</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>3</x>\n" +
                    "            <y>3</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>bowman1.png</string>\n" +
                    "            <string>bowman2.png</string>\n" +
                    "            <string>bowman3.png</string>\n" +
                    "            <string>bowman4.png</string>\n" +
                    "            <string>bowman5.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector>$return = $this.props.hp-1</myImageSelector>\n" +
                    "    </gameplay.Entity>\n" +
                    "    <gameplay.Entity>\n" +
                    "        <myID>26</myID>\n" +
                    "        <name>swordman</name>\n" +
                    "        <props>\n" +
                    "            <entry>\n" +
                    "                <string>attackRange</string>\n" +
                    "                <int>1</int>\n" +
                    "            </entry>\n" +
                    "            <entry>\n" +
                    "                <string>hp</string>\n" +
                    "                <int>5</int>\n" +
                    "            </entry>\n" +
                    "            <entry>\n" +
                    "                <string>dmg</string>\n" +
                    "                <int>3</int>\n" +
                    "            </entry>\n" +
                    "        </props>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>0</x>\n" +
                    "            <y>0</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>swordman1.png</string>\n" +
                    "            <string>swordman2.png</string>\n" +
                    "            <string>swordman3.png</string>\n" +
                    "            <string>swordman4.png</string>\n" +
                    "            <string>swordman5.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector>$return = $this.props.hp-1</myImageSelector>\n" +
                    "    </gameplay.Entity>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>12</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>2</x>\n" +
                    "            <y>1</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>10</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>1</x>\n" +
                    "            <y>4</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>8</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>1</x>\n" +
                    "            <y>2</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>22</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>4</x>\n" +
                    "            <y>1</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>4</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>0</x>\n" +
                    "            <y>3</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>9</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>1</x>\n" +
                    "            <y>3</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>24</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>4</x>\n" +
                    "            <y>3</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>15</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>2</x>\n" +
                    "            <y>4</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>23</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>4</x>\n" +
                    "            <y>2</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>16</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>3</x>\n" +
                    "            <y>0</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>18</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>3</x>\n" +
                    "            <y>2</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>1</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>0</x>\n" +
                    "            <y>0</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>2</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>0</x>\n" +
                    "            <y>1</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>5</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>0</x>\n" +
                    "            <y>4</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>25</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>4</x>\n" +
                    "            <y>4</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>13</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>2</x>\n" +
                    "            <y>2</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>17</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>3</x>\n" +
                    "            <y>1</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>20</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>3</x>\n" +
                    "            <y>4</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>21</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>4</x>\n" +
                    "            <y>0</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>6</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>1</x>\n" +
                    "            <y>0</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>11</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>2</x>\n" +
                    "            <y>0</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>19</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>3</x>\n" +
                    "            <y>3</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>7</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>1</x>\n" +
                    "            <y>1</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>3</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>0</x>\n" +
                    "            <y>2</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Tile>\n" +
                    "        <myID>14</myID>\n" +
                    "        <name>box</name>\n" +
                    "        <props/>\n" +
                    "        <myWidth>1</myWidth>\n" +
                    "        <myHeight>1</myHeight>\n" +
                    "        <myCoord>\n" +
                    "            <x>2</x>\n" +
                    "            <y>3</y>\n" +
                    "        </myCoord>\n" +
                    "        <myImagePaths>\n" +
                    "            <string>square.png</string>\n" +
                    "        </myImagePaths>\n" +
                    "        <myImageSelector></myImageSelector>\n" +
                    "    </gameplay.Tile>\n" +
                    "    <gameplay.Player>\n" +
                    "        <myName>classB</myName>\n" +
                    "        <myStats/>\n" +
                    "        <myEntityIDs>\n" +
                    "            <int>28</int>\n" +
                    "            <int>29</int>\n" +
                    "        </myEntityIDs>\n" +
                    "    </gameplay.Player>\n" +
                    "    <gameplay.Player>\n" +
                    "        <myName>classA</myName>\n" +
                    "        <myStats/>\n" +
                    "        <myEntityIDs>\n" +
                    "            <int>26</int>\n" +
                    "            <int>27</int>\n" +
                    "        </myEntityIDs>\n" +
                    "    </gameplay.Player>\n" +
                    "    <gameplay.Turn>\n" +
                    "        <myCurrentPhaseName>A</myCurrentPhaseName>\n" +
                    "        <playersOrder>\n" +
                    "            <string>classB</string>\n" +
                    "            <string>classA</string>\n" +
                    "        </playersOrder>\n" +
                    "    </gameplay.Turn>\n" +
                    "</game>\n");
            EventBus.getInstance().sendMessage(EngineEvent.CHANGE_USER, myUser);
            myStage.close();
        });

        myPane.add(usernameField, 0, 2, 4, 1);
        myPane.add(passwordField, 0, 3, 4, 1);
        //myPane.add(cBox, 0, 4, 2, 1);
        myPane.add(btn, 0, 5, 4, 1);
        myPane.add(register, 0, 6);
        // grid.add(forgotPassword, 2, 6);
    }
}
