package gameoflife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Loux
 */
public class GeometryPanel extends JPanel {

    private Geometry geometry_;
    private final int preferredHeight_, preferredWidth_;

    public GeometryPanel(int preferredHeight, int preferredWidth) {
        preferredHeight_ = preferredHeight;
        preferredWidth_ = preferredWidth;
        initPanel(preferredHeight_, preferredWidth_);
    }

    private void initPanel(int preferredHeight, int preferredWidth) {
        setPreferredSize(new Dimension(preferredHeight, preferredWidth));
    }

    public void setGeometry(Geometry geometry) {
        geometry_ = geometry;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.GRAY);
        int cellHeight = preferredHeight_ / geometry_.getHeight();
        int cellWidth = preferredWidth_ / geometry_.getWidth();
        for (int layer = 0; layer < geometry_.getDepth(); layer++) {
            for (int row = 0; row < geometry_.getHeight(); row++) {
                for (int column = 0; column < geometry_.getWidth(); column++) {
                    int x = row * cellHeight;
                    int y = column * cellWidth;
                    Cell cell = geometry_.getCells()[layer][row][column];
                    if (cell.isAlive()) {
                        g2D.setPaint(Color.BLACK);
                    } else {
                        g2D.setPaint(Color.WHITE);
                    }
                    g2D.fillRect(x, y, cellHeight, cellWidth);
                }

            }
        }
    }

}
