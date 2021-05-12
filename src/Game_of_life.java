import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.*;
import java.util.Random;

public class Game_of_life extends JFrame {
    private JFrame frame;
    private DrawPanel drawPanel;
    private JPanel Menu;
    private JPanel SceneManager = new JPanel(new CardLayout());

    private int i = 0, j = 0, width = 700, height = 700, xCellNum = width/10, yCellNum = height/10;
    private Random rand = new Random();
    private boolean fileError;
    private Cell[][] Universo = new Cell[yCellNum][xCellNum];
    private int speed = 1;

    private File f = new File("./", "Input.txt");

    public static void main(String[] args) {
        new Game_of_life();
    }

    public Game_of_life() {
        if(!f.exists()){
            generateRandomUniverse();
        }else{
            importUniverse();
        }

        prepareGUI();

        System.out.println("!!!");
        changeScene("Game of life");

        while(true){
            nextStatusCells();
            UpdateCells();
            updateGUI();
            try{
                Thread.sleep(1000/speed);
            } catch (Exception exc){}
        }

    }

    public void generateRandomUniverse(){
        try {
            f.createNewFile();
            FileWriter fw = new FileWriter( f.getAbsoluteFile( ) );
            BufferedWriter bw = new BufferedWriter( fw );
            for(i=0;i<yCellNum;i++){
                for(j=0;j<xCellNum;j++){
                    bw.write( "0" );
                }
                bw.newLine();
            }
            bw.close();
            for(i=0;i<yCellNum;i++){
                for(j=0;j<xCellNum;j++){
                    Universo[i][j] = new Cell(i+j,Math.random()<0.1, 10, 10);
                }
            }
            System.out.println("First time running, generation was random and input.txt was created.\nFrom now on, generation will follow input.txt as it's source");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void importUniverse(){
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            for(i=0;i<yCellNum;i++) {
                //sb.append(line);
                //sb.append(System.lineSeparator());
                line = br.readLine();
                for (j = 0; j < xCellNum; j++) {
                    if (line.charAt(j) == '0') {
                        Universo[i][j] = new Cell(i+j, false, 10, 10);
                    } else {
                        Universo[i][j] = new Cell(i+j, true, 10, 10);
                    }
                }
            }
            System.out.println("Generation followed input.txt setup correctly");
        }catch(IOException ioe){ioe.printStackTrace();}
    }

    private void prepareGUI(){
        frame = new JFrame("Game of Life @Miguelp25");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel(xCellNum, yCellNum, Universo);
        Menu = new JPanel();

        //SceneManager.add(drawPanel, "Game of life");
        SceneManager.add(Menu, "Menu");
        SceneManager.add(drawPanel, "Game of life");

        frame.getContentPane().add(BorderLayout.CENTER, SceneManager);

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
    }

    public void changeScene(String name){
        CardLayout cardLayout = (CardLayout)(SceneManager.getLayout());

        cardLayout.show(SceneManager, name);
    }

    public void updateGUI(){
        frame.repaint();
    }

    public void nextStatusCells(){
        for(i=0;i<yCellNum;i++){
            for(j=0;j<xCellNum;j++){
                if(Universo[i][j].getStatus() && CheckNeighbors(Universo, i, j) < 2){
                    Universo[i][j].setNextStatus(false); //Cell dies because of lack of neighbors
                }

                if(Universo[i][j].getStatus() && CheckNeighbors(Universo, i, j) == 2 || CheckNeighbors(Universo, i, j) == 3){
                    Universo[i][j].setNextStatus(true);//Alive Cell continues being alive
                }

                if(Universo[i][j].getStatus() && CheckNeighbors(Universo, i, j) > 3){
                    Universo[i][j].setNextStatus(false); // Cell dies because of overpopulation
                }
                if(!Universo[i][j].getStatus() && CheckNeighbors(Universo, i, j) == 3){
                    Universo[i][j].setNextStatus(true); //Cell revives because of reproduction
                }
            }
        }
    }

    public int CheckNeighbors(Cell[][] Universo, int i, int j){
        int count = 0, k, l;

        for(k=-1;k<2;k++){
            for(l=-1;l<2;l++){
                if(i+k >= 0 && i+k < yCellNum && j+l >= 0 && j+l < xCellNum && Universo[i+k][j+l].getStatus()){
                    if(k != 0 || l !=0) {
                        count++;
                    }
                }

            }
        }
        return count;
    }

    public void UpdateCells(){
        for(i=0;i<yCellNum;i++) {
            for (j = 0; j < xCellNum; j++) {
                Universo[i][j].update();
            }
        }
        //updateGUI();
    }
}
