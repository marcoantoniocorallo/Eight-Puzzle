package ap.eightpuzzle;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Advanced Programming - Assignment 1.1
 *
 * EightBoard Bean Requirements:
 * Main class of the application. Extends JFrame, displays a grid of 3x3 tiles, 
 * an EightController label, a RESTART button, and a FLIP button.
 * When the RESTART button is clicked, a random configuration (i.e., a random permutation of [0, 9])
 * is passed to all beans registered as listener to the Restart event.
 * At startup the board initializes the grid with the nine EightBoard beans, 
 * passing to them their position as initial value of Position. 
 * All tiles and the controller are registered as listeners to the Restart event, 
 * and such an event is fired to complete the initialization of the board with a random configuration. 
 * Also, the controller is registered as VetoableChangeListener to all the tiles.
 * 
 * Design choices:
 * - Also the board reuses the ChangePropertyListener list inherited from Component;
 * - The method for the generation of the new configuration returns an ArrayList that represents
 *   the tiles in a positional way: the i-th element is the label for the i-th tile.
 *   This is mainly due to the fact the tile access to the map through the position, 
 *   instead the controller through the label. 
 *   In this way both the controller and the tile can easily access to the map.
 * - The "restart" has been implemented as a Bound property, 
 *   it fires a PropertyChange to the registered listeners
 *   The alternatives I thought was:
 *     1. Simply invoke the "restart" method of the tiles and the controller;
 *     2. Register the tiles and the controller as ActionListeners;
 *   But:
 *     1. The specification explicitly required the registration of the event listeners and
 *     2. the ActionPerformed by the ActionListeners doesn't take arguments (i.e. the configuration);
 * - The board is a PropertyChangeListener to the controller: the controller fires an event when
 *   a tile is (correctly) moved, specifying the hole position and the label to assign to it.
 *   This is the way I choose to implement the passing of the label to the old hole.
 * - The "START" text in the controller disappear after the click; 
 *   this property is used as a flag to know if the game is started
 * - The flip event is implemented through a VetoablePropertyChange, the controller is triggered
 *   and will decide if the flip is legal; if it is, swap the labels;
 * 
 * Event source:
 * - a restart event
 * - a flip event
 * 
 * Event listener:
 * - a tile has been correctly moved and the hole's label has to be updated
 * 
 * @author marco
 */
public class EightBoard extends javax.swing.JFrame 
        implements PropertyChangeListener{
            
    /**
     * Creates new form MainFrame
     * Initializes tiles, controller and buttons
     * Register the tiles and the controller for the restart event
     */
    public EightBoard() {
        initComponents();
        
        // Register controller as vetoable to all the tiles
        tile1.addVetoableChangeListener(controller);
        tile2.addVetoableChangeListener(controller);
        tile3.addVetoableChangeListener(controller);
        tile4.addVetoableChangeListener(controller);
        tile5.addVetoableChangeListener(controller);
        tile6.addVetoableChangeListener(controller);
        tile7.addVetoableChangeListener(controller);
        tile8.addVetoableChangeListener(controller);
        tile9.addVetoableChangeListener(controller);
        
        // Register the tiles as listeners for the restart event        
        this.addPropertyChangeListener(tile1);
        this.addPropertyChangeListener(tile2);
        this.addPropertyChangeListener(tile3);
        this.addPropertyChangeListener(tile4);
        this.addPropertyChangeListener(tile5);
        this.addPropertyChangeListener(tile6);
        this.addPropertyChangeListener(tile7);
        this.addPropertyChangeListener(tile8);
        this.addPropertyChangeListener(tile9);
        this.addPropertyChangeListener(controller);
        
        // Register the board as listener for the controller
        controller.eightAddPropertyChangeListener(this);
        
        
        this.vetos.addVetoableChangeListener(controller);
    }
    
    /**
     * Generates a new configuration for the tiles, that is a random permutation of [1,9]
     * @return a list of labels: the i-th label will be binded to the (i+1)-th tile
     */
    private ArrayList<Integer> newConfiguration(){
        Random r = new Random();
        ArrayList<Integer> A = new ArrayList<>();
        
        while (A.size() < 9){
            int rand = r.nextInt(1, 10);
            if (!A.contains(rand))
                A.add(rand);
        }
        
        return A;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tile1 = new ap.eightpuzzle.EightTile(1);
        tile2 = new ap.eightpuzzle.EightTile(2);
        tile3 = new ap.eightpuzzle.EightTile(3);
        tile4 = new ap.eightpuzzle.EightTile(4);
        tile6 = new ap.eightpuzzle.EightTile(6);
        tile7 = new ap.eightpuzzle.EightTile(7);
        tile8 = new ap.eightpuzzle.EightTile(8);
        tile9 = new ap.eightpuzzle.EightTile(9);
        tile5 = new ap.eightpuzzle.EightTile(5);
        controller = new ap.eightpuzzle.EightController();
        flip = new javax.swing.JButton();
        restart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tile1_onClick(evt);
            }
        });

        tile2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tile2_onClick(evt);
            }
        });

        tile3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tile3_onClick(evt);
            }
        });

        tile4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tile4_onClick(evt);
            }
        });

        tile6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tile6_onClick(evt);
            }
        });

        tile7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tile7_onClick(evt);
            }
        });

        tile8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tile8_onClick(evt);
            }
        });

        tile9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tile9_onClick(evt);
            }
        });

        tile5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tile5_onClick(evt);
            }
        });

        controller.setText("START");
        controller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startGame(evt);
            }
        });

        flip.setText("FLIP");
        flip.setBackground(Color.darkGray);
        flip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flip(evt);
            }
        });

        restart.setText("RESTART");
        restart.setBackground(Color.darkGray);
        restart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restart(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tile1, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tile2, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tile3, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tile7, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                            .addComponent(tile4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                            .addComponent(restart, javax.swing.GroupLayout.PREFERRED_SIZE, 91, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(controller, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                            .addComponent(tile8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tile5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tile6, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                            .addComponent(tile9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(flip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tile3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(tile2, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(tile1, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tile4, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(tile6, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(tile5, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tile8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(tile9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(tile7, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(restart, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(controller, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(flip, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tile1_onClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tile1_onClick
        if (!this.controller.getText().equals("START"))
            tile1.onClick();
    }//GEN-LAST:event_tile1_onClick

    private void tile3_onClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tile3_onClick
        if (!this.controller.getText().equals("START"))
            tile3.onClick();
    }//GEN-LAST:event_tile3_onClick

    private void tile2_onClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tile2_onClick
        if (!this.controller.getText().equals("START"))
            tile2.onClick();
    }//GEN-LAST:event_tile2_onClick

    private void tile4_onClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tile4_onClick
        if (!this.controller.getText().equals("START"))
            tile4.onClick();
    }//GEN-LAST:event_tile4_onClick

    private void tile5_onClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tile5_onClick
        if (!this.controller.getText().equals("START"))
            tile5.onClick();
    }//GEN-LAST:event_tile5_onClick

    private void tile6_onClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tile6_onClick
        if (!this.controller.getText().equals("START"))
            tile6.onClick();
    }//GEN-LAST:event_tile6_onClick

    private void tile7_onClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tile7_onClick
        if (!this.controller.getText().equals("START"))
            tile7.onClick();
    }//GEN-LAST:event_tile7_onClick

    private void tile8_onClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tile8_onClick
        if (!this.controller.getText().equals("START"))
            tile8.onClick();
    }//GEN-LAST:event_tile8_onClick

    private void tile9_onClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tile9_onClick
        if (!this.controller.getText().equals("START"))
            tile9.onClick();
    }//GEN-LAST:event_tile9_onClick

    /**
     * Disable the "start" button and enable "restart" and "flip";
     * generates the initial configuration and start the game!
     * @param evt the "start" button clicked
     */
    private void startGame(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startGame
        
        // Start button only available at the beginning
        if (!this.controller.getText().equals("START"))
            return;
        
        // set buttons' background
        flip.setBackground(Color.LIGHT_GRAY);
        restart.setBackground(Color.LIGHT_GRAY);
        
        // remove the label "START"
        controller.setText("");
        
        // generate tiles: start the game!
        this.restart(evt);
    }//GEN-LAST:event_startGame

    /**
     * Fire the listeners for a "restart" event occurred, passing them the new tiles configuration
     * @param evt the event happened
     */
    private void restart(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restart
        
        // Restart button only available after the beginning of the game
        if (this.controller.getText().equals("START"))
            return;
        
        ArrayList<Integer> layout = newConfiguration();
        this.firePropertyChange("restart", null, layout);
    }//GEN-LAST:event_restart

    private void flip(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flip
        
        // Flip button only available after the beginning of the game
        if (this.controller.getText().equals("START"))
            return;
        
        try{
            this.vetos.fireVetoableChange("flip", tile1.getLabel(), tile2.getLabel());
            
            // no veto -> flip tile1 and tile2
            String tmp = tile1.getLabel();
            tile1.updateLabel(Integer.valueOf(tile2.getLabel()));
            tile2.updateLabel(Integer.valueOf(tmp));
        } catch (PropertyVetoException ex) {  } // do nothing
    }//GEN-LAST:event_flip
  
    /**
     * If a move occurs, the controller passes the information for updating the hole tile;
     * @param evt an event <"label", movedtile.label, hole.position>
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (!propertyName.equals("label"))
            return; 
        
        int label = (int) evt.getOldValue();
        int tile = (int) evt.getNewValue();

        switch (tile) {
            case 1: tile1.updateLabel(label); break;
            case 2: tile2.updateLabel(label); break;
            case 3: tile3.updateLabel(label); break;
            case 4: tile4.updateLabel(label); break;
            case 5: tile5.updateLabel(label); break;
            case 6: tile6.updateLabel(label); break;
            case 7: tile7.updateLabel(label); break;
            case 8: tile8.updateLabel(label); break;
            case 9: tile9.updateLabel(label); break;
            default:
                throw new IllegalArgumentException("Wrong tile index: "+tile);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EightBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EightBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EightBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EightBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EightBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ap.eightpuzzle.EightController controller;
    private javax.swing.JButton flip;
    private javax.swing.JButton restart;
    private ap.eightpuzzle.EightTile tile1;
    private ap.eightpuzzle.EightTile tile2;
    private ap.eightpuzzle.EightTile tile3;
    private ap.eightpuzzle.EightTile tile4;
    private ap.eightpuzzle.EightTile tile5;
    private ap.eightpuzzle.EightTile tile6;
    private ap.eightpuzzle.EightTile tile7;
    private ap.eightpuzzle.EightTile tile8;
    private ap.eightpuzzle.EightTile tile9;
    // End of variables declaration//GEN-END:variables
    
    // Vetoable listeners for the "flip" (the controller only)
    private VetoableChangeSupport vetos = new VetoableChangeSupport( this );
}
