/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos;

import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.PasswordField;
import com.simsilica.lemur.TextField;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;

/**
 *
 * @author Alyssa Kozma
 */
public class GuiManager {
    public static Label unitSelectionDisplay;
    public static Node guiNode;
    public static Container win;
    public static TextField usernameEntry;
    public static TextField passwordEntry;
    public static Button loginButton;
    public static boolean gameInterfaceLoaded = false;
    
    public static void createGameInterface() {
        destroyLoginInterface();
        // Create a simple container for our elements
        Container myWindow = new Container();
        guiNode.attachChild(myWindow);

        // Put it somewhere that we will see it.
        // Note: Lemur GUI elements grow down from the upper left corner.
        myWindow.setLocalTranslation(300, 300, 0);
        unitSelectionDisplay = new Label("No Unit Selected");
        myWindow.addChild(unitSelectionDisplay);
        Button clickMe = myWindow.addChild(new Button("Click Me"));
        clickMe.addClickCommands(new Command<Button>() {
                @Override
                public void execute( Button source ) {
                    System.out.println("The world is yours.");
                }
            });
        WorldManager.registerActions();
        gameInterfaceLoaded = true;
    }
    public static void createLoginInterface() {
        GuiGlobals.initialize(WorldManager.main);
        BaseStyles.loadGlassStyle();
        win = new Container();
        guiNode.attachChild(win);
        win.setLocalTranslation(300, 300, 0);
        usernameEntry = new TextField("username", new ElementId("usernameEntry"));
        usernameEntry.setLocalTranslation(100, 100, 0);
        passwordEntry = new PasswordField("password", new ElementId("passwordEntry"));
        passwordEntry.setLocalTranslation(100, 0, 0);
        loginButton = new Button("Login");
        win.addChild(usernameEntry);
        win.addChild(passwordEntry);
        win.addChild(loginButton);
        loginButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button source) {
                System.out.println("login");
                WorldManager.username = usernameEntry.getText();
                WorldManager.password = passwordEntry.getText();
                WorldManager.conn.Login();
                GuiManager.destroyLoginInterface();
            }
        });
    }
    
    public static void destroyLoginInterface() {
        
        win.removeFromParent();
    }
}
