import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Random;
import java.awt.event.KeyEvent;

public class Game_of_life extends JFrame implements KeyListener {

    private DrawPanel drawPanel;
    private JPanel Menu;
    private JPanel SceneManager = new JPanel(new CardLayout());

    public static int CellWidth = 1, CellHeight = 1;
    public static int width = 1000, height = 700, xCellNum = width/CellWidth, yCellNum = height/CellHeight;

    private boolean pause = false;
    public static boolean isUniverseGenerated = false;
    private boolean fileError;
    private Cell[][] Universo = new Cell[yCellNum][xCellNum];
    private int speed = 1;

    //Every time there's a iteration Jpanel is re-drawn.
    public static long fps = 0;
    public static boolean developerMode = false;

    //private File f = new File("./", "Input.txt");

    public static void main(String[] args) {
        new Game_of_life();
    }

    public Game_of_life() {
        super("Game of Life @Miguelp25");

        prepareGUI();

        //////////////////////////
        addKeyListener(this);

        System.out.println("!!!");
        //changeScene("Game of life");
        changeScene("Menu");

        while(true){
            if(!pause && isUniverseGenerated){
                long startTime = System.nanoTime();
                nextStatusCells();
                Cell.resetAliveList();
                UpdateCells();
                updateGUI();
                Cell.resetChangedList();

                //System.out.println("That took " + (endTime - startTime) + " nanoseconds");
                try{
                    Thread.sleep(1000/speed);
                } catch (Exception exc){}
                fps = Math.round(1000000000.0 / (System.nanoTime() - startTime));
            }else{
                try{
                    Thread.sleep(100);
                } catch (Exception exc){}
            }
        }
    }

    public void generateRandomUniverse(){
        isUniverseGenerated = false;

        for(int i=0;i<yCellNum;i++){
            for(int j=0;j<xCellNum;j++){
                Universo[i][j] = new Cell(i+j,Math.random()<0.1, CellWidth, CellHeight, j, i);
            }
        }
        System.out.println("First time running, generation has been random.\n");

        isUniverseGenerated = true;
        /*
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
        */


    }

    public void importUniverse(){
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            for(int i=0;i<yCellNum;i++) {
                //sb.append(line);
                //sb.append(System.lineSeparator());
                line = br.readLine();
                for (int j = 0; j < xCellNum; j++) {
                    if (line.charAt(j) == '0') {
                        Universo[i][j] = new Cell(i+j, false, CellWidth, CellHeight, j, i);
                    } else {
                        Universo[i][j] = new Cell(i+j, true, CellWidth, CellHeight, j, i);
                    }
                }
            }
            System.out.println("Generation followed input.txt setup correctly");
        }catch(IOException ioe){ioe.printStackTrace();}
    }

    private void prepareGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel(xCellNum, yCellNum, Universo);

        prepareMenuGUI();

        SceneManager.add(Menu, "Menu");
        SceneManager.add(drawPanel, "Game of life");

        this.getContentPane().add(BorderLayout.CENTER, SceneManager);

        this.setVisible(true);
        this.setResizable(false);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
    }

    private void prepareMenuGUI(){
        // Credit to https://stackoverflow.com/questions/42964669/placing-button-panel-in-center-java-swing

        Menu = new JPanel();
        Menu.setBorder(new EmptyBorder(10, 10, 10, 10));
        Menu.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        JLabel jlabel = new JLabel("Game of life");
        jlabel.setFont(new Font("Verdana",1,20));
        Menu.add(jlabel, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttonsPanel = new JPanel(new GridBagLayout());

        String[] buttonTitles = {"Continue", "New Universe", "Import", "Tutorial", "Languages"};
        Boolean[] available = {false, true, false, false, false};
        JButton[] buttons = new JButton[5];

        for(int i = 0; i < buttonTitles.length; i++){
            buttons[i] = new JButton(buttonTitles[i]);
            buttons[i].setFont(new Font("Calibri", Font.PLAIN, 14));
            buttons[i].setBackground(new Color(available[i]? 0x8802FF : 0x939393));
            buttons[i].setForeground(Color.white);
            buttons[i].setUI(new StyledButtonUI());
            buttonsPanel.add(buttons[i], gbc);
        }

        buttons[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("isUniverseGenerated " + isUniverseGenerated);
                if(isUniverseGenerated){
                    changeScene("Game of life");
                }
            }
        });

        buttons[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateRandomUniverse();
                changeScene("Game of life");
                pause = false;
            }
        });

        gbc.weighty = 1;
        Menu.add(buttonsPanel, gbc);
    }

    public void changeScene(String name){
        CardLayout cardLayout = (CardLayout)(SceneManager.getLayout());

        cardLayout.show(SceneManager, name);
    }

    public void updateGUI(){
        this.repaint();
    }

    public void nextStatusCells(){
        for(int i = 0; i < Cell.aliveList.size(); i++){
            Cell aliveCell = Cell.aliveList.get(i);

            for(int k=-1;k<2;k++) {
                for (int l = -1; l < 2; l++) {
                    if(aliveCell.position_y+k >= 0 && aliveCell.position_y+k < yCellNum && aliveCell.position_x+l >= 0 && aliveCell.position_x+l < xCellNum) {
                        int CheckingCell_y = aliveCell.position_y + k;
                        int CheckingCell_x = aliveCell.position_x + l;

                        int neighbors = CheckNeighbors(Universo, CheckingCell_y, CheckingCell_x);

                        if (Universo[CheckingCell_y][CheckingCell_x].getStatus() && neighbors < 2) {
                            //Rezo por que esto evite duplicados siempre
                            if(Universo[CheckingCell_y][CheckingCell_x].getNextStatus()){
                                Cell.changedList.add(Universo[CheckingCell_y][CheckingCell_x]);
                            }
                            Universo[CheckingCell_y][CheckingCell_x].setNextStatus(false); //Cell dies because of lack of neighbors
                        }

                        if (Universo[CheckingCell_y][CheckingCell_x].getStatus() && neighbors == 2 || neighbors == 3) {
                            if(!Universo[CheckingCell_y][CheckingCell_x].getNextStatus()){
                                Cell.changedList.add(Universo[CheckingCell_y][CheckingCell_x]);
                            }
                            Universo[CheckingCell_y][CheckingCell_x].setNextStatus(true);//Alive Cell continues being alive
                        }

                        if (Universo[CheckingCell_y][CheckingCell_x].getStatus() && neighbors > 3) {
                            if(Universo[CheckingCell_y][CheckingCell_x].getNextStatus()){
                                Cell.changedList.add(Universo[CheckingCell_y][CheckingCell_x]);
                            }
                            Universo[CheckingCell_y][CheckingCell_x].setNextStatus(false); // Cell dies because of overpopulation
                        }
                        if (!Universo[CheckingCell_y][CheckingCell_x].getStatus() && neighbors == 3) {
                            if(!Universo[CheckingCell_y][CheckingCell_x].getNextStatus()){
                                Cell.changedList.add(Universo[CheckingCell_y][CheckingCell_x]);
                            }
                            Universo[CheckingCell_y][CheckingCell_x].setNextStatus(true); //Cell revives because of reproduction
                        }
                    }
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
        //maybe we could squish a bit of performance here.
        //maybe next time :)
        for(int i = 0; i < Universo.length; i++){
            for(int j = 0; j < Universo[0].length; j++){
                Universo[i][j].update();
            }
        }

        /*
        for(int i = 0; i < Cell.changedList.size(); i++){
            Cell CelltoUpdate = Cell.changedList.get(i);

            CelltoUpdate.update();
        }
        */
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_PLUS :
                if(e.isControlDown()) {
                    if(speed <= 128) {
                        System.out.println("Subiendo velocidad");
                        if(pause){
                            speed = 1;
                            pause = false;
                        }else{
                            speed *= 2;
                        }
                    }
                }
                break;
            case KeyEvent.VK_MINUS:
                if(e.isControlDown()) {
                    if(speed >= 2) {
                        System.out.println("Bajando velocidad");
                        speed *= 0.5;
                    }else{
                        pause = true;
                    }
                }
                break;

            case KeyEvent.VK_I:
                if(e.isControlDown()) {
                    developerMode = !developerMode;
                }
                break;

            case KeyEvent.VK_SPACE :
                pause = !pause;
                break;

            case KeyEvent.VK_ESCAPE :
                pause = true;
                Menu.updateUI();
                changeScene("Menu");
                break;
        }

        System.out.println("Velocidad: " + speed + ", pause: " + pause);
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("keyTyped");
    }

    public void keyTyped(KeyEvent e) {
        //System.out.println("keyTyped");
    }
}
