package gameoflife;

import java.awt.FlowLayout;
import java.awt.*;
import javax.swing.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import static javax.swing.SwingUtilities.invokeLater;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 *
 * @author Loux
 */
public class GameOfLifeGUI extends javax.swing.JFrame {

    private final GameOfLife gol_;
    private javax.swing.JButton initButton_, tickButton_, playButton_, stopButton_, exitButton_;
    private javax.swing.JSlider sizeSlider_;
    private JPanel golPanel_;

    public GameOfLifeGUI() {
        initComponents();
        gol_ = new GameOfLife();
    }

    private void initComponents() {
        initButton_ = new javax.swing.JButton();
        tickButton_ = new javax.swing.JButton();
        playButton_ = new javax.swing.JButton();
        stopButton_ = new javax.swing.JButton();
        exitButton_ = new javax.swing.JButton();
        sizeSlider_ = new javax.swing.JSlider(JSlider.HORIZONTAL, 5, 100, 20);
        initButton_.setText("Init");
        initButton_.addActionListener((java.awt.event.ActionEvent evt) -> {
            jToggleInitButtonActionPerformed(evt);
        });
        tickButton_.setText("Tick");
        tickButton_.addActionListener((java.awt.event.ActionEvent evt) -> {
            jToggleTickButtonActionPerformed(evt);
        });
        playButton_.setText("Play");
        playButton_.addActionListener((java.awt.event.ActionEvent evt) -> {
            jTogglePlayButtonActionPerformed(evt);
        });
        stopButton_.setText("Stop");
        stopButton_.addActionListener((java.awt.event.ActionEvent evt) -> {
            jToggleStopButtonActionPerformed(evt);
        });
        exitButton_.setText("Exit");
        exitButton_.addActionListener((java.awt.event.ActionEvent evt) -> {
            jToggleExitButtonActionPerformed(evt);
        });
        final JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(initButton_);
        buttons.add(tickButton_);
        buttons.add(playButton_);
        buttons.add(stopButton_);
        buttons.add(exitButton_);
        buttons.add(sizeSlider_);
        buttons.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        golPanel_ = new JPanel();
        golPanel_.setPreferredSize(new Dimension(800, 800));

        add(buttons, BorderLayout.SOUTH);
        add(golPanel_, BorderLayout.CENTER);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
    }

    private abstract class GameOfLifeSwingWorker extends SwingWorker<Integer, Cell[][]> {

        @Override
        protected void process(java.util.List<Cell[][]> chunks) {
            Graphics2D g = (Graphics2D) golPanel_.getGraphics();
            g.setPaint(Color.GRAY);
            int size = (int) golPanel_.getPreferredSize().getWidth() / gol_.getGeometry().getWidth();
            for (Cell[][] cells_ : chunks) {
                for (int i = 0; i < cells_.length; i++) {
                    for (int j = 0; j < cells_[i].length; j++) {
                        int x = i * size;
                        int y = j * size;
                        Cell cell = cells_[i][j];
                        if (cell.isAlive()) {
                            g.setPaint(Color.MAGENTA);
                        } else {
                            g.setPaint(Color.WHITE);
                        }
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
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(GameOfLifeGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private GameOfLifeSwingWorker worker_;
    private int it_ = 0;

    private void jToggleInitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        worker_ = new GameOfLifeSwingWorker() {

            @Override
            protected Integer doInBackground() throws Exception {
                it_ = 0;
                gol_.setGeometry(new Geometry.Rectangle(sizeSlider_.getValue(), sizeSlider_.getValue()));
                gol_.setRandomInitialSeed();
                publish(gol_.getGeometry().getCells());
                return it_;
            }

        };
        worker_.execute();
    }

    private void jToggleTickButtonActionPerformed(java.awt.event.ActionEvent evt) {
        worker_ = new GameOfLifeSwingWorker() {

            @Override
            protected Integer doInBackground() throws Exception {
                it_ ++;
                gol_.nextGeneration(new Strategy.Default());
                publish(gol_.getGeometry().getCells());
                return it_;
            }

        };
        worker_.execute();
    }

    private void jTogglePlayButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (worker_ != null) {
            jToggleStopButtonActionPerformed(evt);
        }
        worker_ = new GameOfLifeSwingWorker() {

            @Override
            protected Integer doInBackground() throws Exception {
                boolean isEvolving = true;
                while (!isCancelled() && isEvolving) {
                    isEvolving = gol_.nextGeneration(new Strategy.Default());
                    publish(gol_.getGeometry().getCells());
                    TimeUnit.MILLISECONDS.sleep(100);
                    it_++;
                }
                return it_;
            }

        };
        worker_.execute();
    }

    private void jToggleStopButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (worker_ != null) {
            worker_.cancel(true);
            worker_ = null;
        }
    }

    private void jToggleExitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(1);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        invokeLater(() -> {
            (new GameOfLifeGUI()).setVisible(true);
        });
    }

}
