package ap.eightpuzzle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;
import java.util.HashMap;
import javax.swing.JButton;
import static java.lang.Math.sqrt;
import java.util.ArrayList;

/**
 * Advanced Programming - Assignment 1.1
 *
 * EightController Bean Requirements:
 * The EightController is a bean that has the graphical appearance of a label. 
 * It has to check that only legal moves are performed registering 
 * To this aim, it must be registered as VetoableChangeListener to all the tiles.
 * At the beginning the controller displays “START”.
 * Every time a tile is clicked, it must veto the change if the tile is the hole 
 * or it is not adjacent to the hole, displaying “KO”. Otherwise, it must display “OK”.
 * The controller must also provide support for a “restart” action that restores 
 * its internal information about the configuration when the game is restarted.
 * The controller cannot read the labels of the tiles using methods. 
 * You should decide which kind of information the controller has to keep about the configuration 
 * of the board to implement correctly the above veto policy.
 * 
 * Design choices:
 * - Controller maintains an inner map that represents the initial layout, updated at restart
 * - Unlike for EightTile, the PropertyChangeSupport list is overriden:
 *   The controller fires an event to the listener board when a tile is correctly moved 
 *   (it behaves as an event adaptor) passing the position of the hole and the label to assign to it
 *   but when these two arguments are equal, the super.firePropertyChange does nothing.
 *   So I override this method and also the add/removePropertyChangeListener, 
 *   to avoid the upcasting error at the superclass constructors;
 *     
 * Event source:
 * - a tile is *correctly* moved and the hole label has to be update
 * 
 * Event listener:
 * - a restart event
 * - a tile is clicked (constrained)
 * - a flip event (constrained)
 * 
 * @author marco
 */
public class EightController extends JButton 
        implements Serializable, VetoableChangeListener, PropertyChangeListener {
    private HashMap<Integer,Integer> layout; // map label -> position
    private static final int hole_label = 9;
    private static final int row_length = (int) sqrt(hole_label);
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    
    public EightController(){
        super();
        this.layout = new HashMap<>(hole_label);
    }
    
    /**
     * Checks if a given tile is the hole tile
     * @return true if label == hole label, false otherwise
     */
    private boolean is_hole(int label){
        return label == hole_label;
    }
    
    /**
     * Checks if a given tile is above the hole
     * @param tile the position of the tile
     * @param hole the position of the hole
     * @return true if the tile is above the hole, false otherwise
     */
    private boolean isUp(int tile, int hole){
        return tile == hole-(row_length);
    }
    
    /**
     * Checks if a given tile is under the hole
     * @param tile the position of the tile
     * @param hole the position of the hole
     * @return true if the tile is under the hole, false otherwise
     */
    private boolean isDown(int tile, int hole){
        return tile == hole+(row_length);
    }
    /**
     * Checks if a given tile is to the left of the hole
     * @param tile the position of the tile
     * @param hole the position of the hole
     * @return true if the tile is to the left of the hole, false otherwise
     */
    private boolean isLeft(int tile, int hole){
        return tile == hole-1 && tile%row_length != 0;
    }
    
    /**
     * Checks if a given tile is to the right of the hole
     * @param tile the position of the tile
     * @param hole the position of the hole
     * @return true if the tile is to the right of the hole, false otherwise
     */
    private boolean isRight(int tile, int hole){
        return tile == hole+1 && tile%row_length != 1;
    }
    
    /**
     * Check if a given label is adjacent to the hole tile
     * @param label the label to check
     * @return true if label is adjacent to the hole, false otherwise
     */
    private boolean is_hole_adjacent(int label) {
        int pos = this.layout.get(label);
        int hole = this.layout.get(this.hole_label);
        return isUp(pos,hole) || isDown(pos,hole) || isRight(pos,hole) || isLeft(pos,hole);
    }
    
    /**
     * A move is legal if the tile is not the hole and it is adjacent to the hole
     * @param  label the label of the tile moved
     * @return true if the moved tile is not the hole and is adjacent to the hole
     *         false otherwise
     */
    private boolean is_legal(int label){
        return !this.is_hole(label) && this.is_hole_adjacent(label);
    }
    
    /**
     * Update the map <label, position> representing the layout
     * @param oldLabel the tile moved
     * @param newLabel the new label for the tile
     */
    private void updateLayout(int oldLabel, int newLabel) {
        int old_pos = this.layout.get(oldLabel);
        int new_pos = this.layout.get(newLabel);
        this.layout.replace(oldLabel, new_pos);
        this.layout.replace(newLabel, old_pos);
    }
    
    /**
     * General handler for vetoable events; invokes the particular handler
     * @param evt a label change or a flip event;
     * @throws PropertyVetoException propagated from the particular handler of the events
     */
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {     
        switch (evt.getPropertyName()) {
            case "label":
                this.handle_label(evt);
                break;
            case "flip":
                this.handle_flip(evt);
                break;
            default: ; // ignore
        }
    }
    
    /**
     * Flip event: veto on it if the hole is not at the low-right corner;
     * if the flip is legal, update the inner layout map;
     * @param evt a flip event <"flip", tile1.label, tile2.label>
     * @throws PropertyVetoException if hole.label != hole.position
     */
    private void handle_flip(PropertyChangeEvent evt) throws PropertyVetoException{
        if (!this.layout.get(hole_label).equals(hole_label))
            throw new PropertyVetoException( "The hole is not in position "+hole_label, evt );
        
        // update inner layout map
        int tile1 = Integer.valueOf((String) evt.getOldValue());
        int tile2 = Integer.valueOf((String) evt.getNewValue());
        this.layout.replace(tile1, 2);
        this.layout.replace(tile2, 1);
    }
    
    /**
     * Label change event: veto on it if the move is not legal!
     * If the move is legal, update the inner layout map and pass tiles' position to the board,
     * that is, it fires a PropertyChange with <label, position> to the board!
     * @param  evt A property "label" change event
     * @throws PropertyVetoException if the label is the hole label or is not adjacent to that
     */
    private void handle_label(PropertyChangeEvent evt) throws PropertyVetoException{
        int oldLabel = (int) evt.getOldValue();
        int newLabel = (int) evt.getNewValue();
        
        if (! this.is_legal(oldLabel)){
            this.setText("KO");
            throw new PropertyVetoException(
                "Tile "+String.valueOf(oldLabel)+" is the hole or it is adjacent to the hole!", evt
            );
        }
        else{
            this.setText("OK");
            this.eightFirePropertyChange(
                    evt.getPropertyName(),
                    (int)(evt.getOldValue()),
                    (int)this.layout.get((int) evt.getNewValue())
            );
            updateLayout(oldLabel, newLabel);
        }
    }
            
    /**
     * A "restart" event is fired! Update the inner layout map
     * @param evt the event that is fired
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (!propertyName.equals("restart"))
            throw new IllegalArgumentException("Unexpected property change event of: "+propertyName);
        
        // the new layout has the form <i, tile_(i+1).label>
        ArrayList<Integer> newLayout = (ArrayList<Integer>) evt.getNewValue();
        
        for (int i=0; i < newLayout.size(); i++){
            if (this.layout.containsKey(newLayout.get(i)))
                this.layout.replace(newLayout.get(i), i+1);
            else
                this.layout.put(newLayout.get(i), i+1);
        }
        
    }
    
    /**
     * Add a listener to the PropertyChangeSupport list
     * It's a wrapper method, used to avoid dynamic dispatch errors
     * @param l the listener to add
     */
    public void eightAddPropertyChangeListener(PropertyChangeListener l){
        this.changes.addPropertyChangeListener(l);
    }
    
    /**
     * Remove a listener from the PropertyChangeSupport list
     * It's a wrapper method, used to avoid dynamic dispatch errors
     * @param l the listener to remove
     */
    public void eightRemovePropertyChangeListener(PropertyChangeListener l){
        this.changes.removePropertyChangeListener(l);
    }
    
    /**
     * Fires a property change without checking if oldValue == newValue
     * @param propertyName the name of the propety changed
     * @param oldValue the old value of the property 
     * @param newValue the new value for the property
     */
    public void eightFirePropertyChange(String propertyName, Object oldValue, Object newValue) {
        PropertyChangeEvent evt = new PropertyChangeEvent(changes, propertyName, oldValue, newValue);
        for (var listener : changes.getPropertyChangeListeners())
            listener.propertyChange(evt);
    }
    
}
