package gameoflife;

import gameoflife.Geometry.Square;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Loux
 */
public class GameOfLife extends javax.swing.JFrame {

    public GameOfLife() {
        initComponents();
    }

    private javax.swing.JButton tickButton_;
    private javax.swing.JButton regressButton_;
    private Grid grid_;

    private void initComponents() {
        tickButton_ = new javax.swing.JButton();
        regressButton_ = new javax.swing.JButton();
        Square geometry = new Geometry.Square(20);
        geometry.setRandomInitialSeed();
        grid_ = new Grid(geometry);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tickButton_.setText("tick");
        tickButton_.addActionListener((java.awt.event.ActionEvent evt) -> {
            jToggleTickButtonActionPerformed(evt);
        });
        
        regressButton_.setText("reverse");
        regressButton_.addActionListener((java.awt.event.ActionEvent evt) -> {
            jToggleRegressButtonActionPerformed(evt);
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(grid_);
        grid_.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 320, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 255, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tickButton_)
                                        .addComponent(regressButton_)
                                        .addComponent(grid_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 80, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(tickButton_)
                                .addComponent(regressButton_)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(grid_, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    private void jToggleTickButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Strategy strategy = new Strategy.Default();
        grid_.nextGeneration(strategy);
        grid_.repaint();
    }
    
    private void jToggleRegressButtonActionPerformed(java.awt.event.ActionEvent evt) {
        grid_.regress();
        grid_.repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GameOfLife gol = new GameOfLife();
                gol.setVisible(true);
            }
        });
    }

}
