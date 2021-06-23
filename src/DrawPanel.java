import javax.swing.*;
import java.awt.*;

class DrawPanel extends JPanel {
    private int yCellNum;
    private int xCellNum;
    private Cell[][] Universo;

    DrawPanel(int xCellNum, int yCellNum, Cell[][] Universo){
        this.xCellNum = xCellNum;
        this.yCellNum = yCellNum;
        this.Universo = Universo;
    }

    public void paintComponent(Graphics g) {
        int i, j;
        g.setColor(Color.BLACK);
        g.fillRect(0,0, Game_of_life.width, Game_of_life.height);

        for(i = 0; i < Cell.aliveList.size(); i++){
            Cell cell = Cell.aliveList.get(i);
            g.setColor(Color.WHITE);
            g.fillRect(cell.getXDimension()*cell.position_x,cell.getYDimension()*cell.position_y,Game_of_life.CellWidth, Game_of_life.CellHeight);
        }

        if(Game_of_life.developerMode){
            drawDeveloperMode(g);
        }
    }

    private void drawDeveloperMode(Graphics g){
        g.setColor(new Color(0.2f,0.2f,0.2f,0.6f));
        g.fillRect(0, 0, Game_of_life.width/5, Game_of_life.height/3);

        g.setColor(Color.GREEN);
        g.drawString("Universe: [" + Game_of_life.yCellNum + ", " + Game_of_life.xCellNum + "]", 5, 15);
        g.drawString("Iterations per second: " + Game_of_life.fps, 5, 30);
        g.drawString("Alive Cells: " + Cell.aliveList.size(), 5, 45);
    }
}