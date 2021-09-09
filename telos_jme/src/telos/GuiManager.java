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
import com.simsilica.lemur.style.BaseStyles;

/**
 *
 * @author Beefaroni
 */
public class GuiManager {
    public static Label unitSelectionDisplay;
    public static Node guiNode;
    
    public static void createGameInterface() {
        GuiGlobals.initialize(WorldManager.main);
        BaseStyles.loadGlassStyle();
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
    }
}
