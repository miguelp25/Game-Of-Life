import java.util.ArrayList;

public class Cell {
    public static ArrayList<Cell> aliveList = new ArrayList<>();
    public static ArrayList<Cell> changedList = new ArrayList<>();
    private int id;
    private boolean status; //Is alive?
    private int[] dimensions = new int[2];
    public int position_x;
    public int position_y;
    private boolean nextStatus; //Is it going to live?

    public Cell(int ID, boolean life, int width, int height, int position_x, int position_y){
        id = ID;
        status = life;
        dimensions[0] = width;
        dimensions[1] = height;
        nextStatus = false;
        this.position_x = position_x;
        this.position_y = position_y;

        if(life) aliveList.add(this);
    }

    public static void resetAliveList(){
        aliveList = new ArrayList<>();
    }

    public static void resetChangedList(){
        changedList = new ArrayList<>();
    }

    public void setNextStatus(boolean stat){
        nextStatus = stat;
    }

    public void update(){
        if(nextStatus){
            aliveList.add(this);
        }

        status = nextStatus;
    }

    public boolean getStatus(){
        return status;
    }

    public boolean getNextStatus(){
        return nextStatus;
    }

    public int getXDimension(){
        return dimensions[0];
    }

    public int getYDimension(){
        return dimensions[1];
    }

    @Override
    public String toString() {
        return String.format("Cell\n" +
                "-------------------------------\n" +
                "ID: " + id + "\n" +
                "Status: " + status + "\n" +
                "position: [" + position_x + ", " + position_y + "] \n" +
                "dimensions: [" + dimensions[0] + ", " + dimensions[1] + "] \n\n");
    }
}
