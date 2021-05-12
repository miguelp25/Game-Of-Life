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
        for(i=0;i<yCellNum;i++){
            for(j=0;j<xCellNum;j++){
                if(Universo[i][j].getStatus()){
                    g.setColor(Color.WHITE); //Alive
                }else{
                    g.setColor(Color.BLACK); //Dead
                }
                g.fillRect(Universo[i][j].getXDimension()*j,Universo[i][j].getYDimension()*i,10,10);
                g.fillRect(Universo[i][j].getXDimension()*j,Universo[i][j].getYDimension()*i,10,10);
            }
        }
    }
}