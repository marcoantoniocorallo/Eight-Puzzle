package ap.eightpuzzle;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 * Advanced Programming - Assignment 1.1
 *
 * EightTile Bean Requirements:
 * Tiles must inherit from JButton.
 * A tile has two private properties: Position and Label, 
 * which hold integer values in the range [1, 9]. 
 * Position is a constant setted at startup and it identifies a specific position on the board;
 * Label is, as property, both bound and constrained.
 * The background color of a tile must be grey if the Label is 9, green if Position = Label 
 * and yellow otherwise. 
 * The text of the tile must be equal to the value of Label, 
 * with the exception of the current hole, whose text is empty.
 * When the player clicks on a tile the Label property is changed to 9 if this change is not vetoed
 * and the tile must pass its previous label value to the current hole, which updates its label.
 * If instead the change is vetoed, the tile "flashes".
 * Tiles must also provide support for a "restart" action, with a method that takes as argument
 * a permutation of [0,9] and sets the label to an initial value. 
 * This action will be triggered by the Restart event of the board, 
 * thus tiles have to register as listeners for this event.
 * the change of the Label property to 9 should be vetoed if the tile is the
 * current hole, or if the tile is not adjacent to the current hole.
 *
 * Design choices: 
 * - EightTile extends JButton, that inherits from JComponent a VetoableChangeSupport list;
 *   Jcomponent, in turn, inherits a PropertyChangeSupport from Container;
 *   EightTile uses this listener lists.
 *   This choice is mainly due to JButton's constructor (method UpdateUI):
 *   it causes a npe invoking the method addPropertyChange of this (upcasting).
 *   An alternative could be the overloading of these methods with dummy parameters...
 * 
 * - The getter of the label returns a string value, even if the field is an integer;
 *   this is due to a compilation requirement for the overriding.
 *   Again, an alternative could be the overloading through a dummy parameter;
 * - The "hole label" is stored in a final variable for future extensions (ie. the famous 16 version)
 *    
 * Event source:
 * - a tile is clicked
 * 
 * Event listener:
 * - a restart event
 * 
 * @author marco
 */
public class EightTile extends JButton 
        implements Serializable, PropertyChangeListener {
    private final int position;             // position in the board
    private int label;                      // current number
    private static final int hole_label = 9;
    
    /********** Constructors **********/
    
    // No-arguments constructor required for being a Bean
    public EightTile(){ 
        this(0); 
    }
    
    // Constructor that passes the position to the tile; the label is not defined
    public EightTile(int position){
        super();
        this.position = position;
        this.setBackground(Color.darkGray);
    }
    
    /********** Getters and Setters **********/
    
    /**
     * returns a string to override the AbstractButton's method (compilation error otherwise);
     * @return the string representation of this.Label
     */
    //@Override
    public String getLabel() {
        return Integer.toString(label);
    }
    
    /**
     * @param label value to set
     */
    public void setLabel(int label){
        this.label=label;
    }

    /**
     * @return a string representation of this.position
     */
    public String getPosition() {
        return Integer.toString(position);
    }
    
    /**
     * Set text according to the specification:
     *          text = "" if label is the hole_label
     *          text = this.Label otherwise
     */
    public void setText() {
        super.setText(this.is_hole() ? "" : this.getLabel());
    }
    
    /********** Auxiliary methods **********/
    
    /**
     * set the label, update the text and the background
     * @param label label to set
     */
    public void updateLabel(int label){
        this.setLabel(label);
        this.setText();
        this.setBackground(this.chooseBgColor());
    }
    
    /**
     * When a tile is clicked, set the bound and constrained property label to the hole label;
     * if the update is vetoed, the tile must flashes red;
     * if the update is not vetoed, notify the listeners;
     */
    public void onClick(){
        int old = Integer.parseInt(this.getLabel());
        try{
            this.fireVetoableChange("label", old, hole_label);
            
            // no veto -> update
            this.updateLabel(hole_label);
        } 
        catch(PropertyVetoException e){
            
            // the tile flashes red
            this.flashes();
        }
    }
    
    /**
     * Choose the background's color according to the specification
     * @return  Color.GRAY if this.Label == hole label
     *          Color.GREEN if this.Label == this.Position
     *          Color.YELLOW otherwise
     */
    private Color chooseBgColor(){
        return  this.is_hole() ? Color.GRAY : 
                (this.getLabel().equals(this.getPosition()) ? Color.GREEN : Color.YELLOW);
    }
    
    /**
     * Checks if this is the hole tile
     * @return true if this.label == hole label, false otherwise
     */
    private boolean is_hole(){
        return this.getLabel().equals(String.valueOf(hole_label));
    }
    
    /**
     * Set the background red for half second, then reset it
     */
    private void flashes(){
        this.setBackground(Color.RED);
        new Timer(500, e -> setBackground(this.chooseBgColor()) ).start();
    }

    /**
     * A restart event is triggered! Update the label for this tile
     * @param newLayout 
     */
    public void restart(ArrayList<Integer> newLayout){
        this.updateLabel(newLayout.get(this.position-1));
    }
    
    /**
     * A restart event is triggered! Update the label for this tile
     * @param evt 
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (!propertyName.equals("restart"))
            throw new IllegalArgumentException("Unexpected property change event of: "+propertyName);
        
        this.restart((ArrayList<Integer>) evt.getNewValue());
    }
        
}
