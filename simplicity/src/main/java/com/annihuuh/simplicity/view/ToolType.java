
package com.annihuuh.simplicity.view;

/**
 *
 * @author annihuuh
 */
public enum ToolType {
    PAUSE("Pause"), CONTINUE("Continue"), DESTROY("Destroy"), 
    ZOOMIN("Zoom in"), ZOOMOUT("Zoom out");
    
    private String name;
    
    private ToolType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    } 
}
