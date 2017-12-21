package gameoflife;

import gameoflife.Geometry.Square;
import java.awt.FlowLayout;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import static javax.swing.SwingUtilities.invokeLater;
import javax.swing.SwingWorker;

/**
 *
 * @author Loux
 */
public class GameOfLifeGUI extends javax.swing.JFrame {

    public GameOfLifeGUI() {
        initComponents();
    }

    private void initComponents() {
        javax.swing.JButton playButton = new javax.swing.JButton();
        javax.swing.JButton stopButton = new javax.swing.JButton();

        playButton.setText("Play");
        playButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            jTogglePlayButtonActionPerformed(evt);
        });

        stopButton.setText("Stop");
        stopButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            jToggleStopButtonActionPerformed(evt);
        });

        FlowLayout flowLayout = new FlowLayout();
        final JPanel buttons = new JPanel();
        buttons.setLayout(flowLayout);
        flowLayout.setAlignment(FlowLayout.TRAILING);
        buttons.add(playButton);
        buttons.add(stopButton);
        buttons.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);

        add(buttons, BorderLayout.WEST);

        golPanel = new JPanel();
        add(golPanel, BorderLayout.CENTER);
    }

    JPanel golPanel;

    private void jTogglePlayButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (worker == null) {
            worker = new SwingWorker<Integer, Cell[][]>() {
                private final GameOfLife gol_;

                {
                    Square geometry = new Geometry.Square(100);
                    gol_ = new GameOfLife(geometry);
                    gol_.setRandomInitialSeed();
                }

                @Override
                protected Integer doInBackground() throws Exception {
                    int it = 0;
                    boolean isEvolving = true;
                    while (!isCancelled() && isEvolving) {
                        isEvolving = gol_.nextGeneration(new Strategy.Default());
                        publish(gol_.getGeometry().getCells());
                        TimeUnit.MILLISECONDS.sleep(100);
                        it++;
                    }
                    return it;
                }

                @Override
                protected void process(java.util.List<Cell[][]> chunks) {
                    for (Cell[][] cells_ : chunks) {
                        Graphics2D g = (Graphics2D) golPanel.getGraphics();
                        g.setPaint(Color.GRAY);
                        int size = 10;
                        for (int i = 0; i < cells_.length; i++) {
                            for (int j = 0; j < cells_[i].length; j++) {
                                int x = i * size;
                                int y = j * size;
                                Cell cell = cells_[i][j];
                                if (cell.isAlive()) {
                                    g.setPaint(Color.YELLOW);
                                } else {
                                    g.setPaint(Color.WHITE);
                                }
                                g.drawRect(x, y, size, size);
                                g.fillRect(x, y, size, size);
                            }

                        }
                    }
                }

                @Override
                protected void done() {
                    try {
                        if (!isCancelled()) {
                            System.out.println(get());
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameOfLifeGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(GameOfLifeGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            };
            worker.execute();
        }
    }

    private SwingWorker worker;

    private void jToggleStopButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (worker != null) {
            worker.cancel(true);
            worker = null;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        invokeLater(new Runnable() {
            public void run() {
                GameOfLifeGUI frame = new GameOfLifeGUI();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}
