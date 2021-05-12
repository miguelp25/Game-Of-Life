public class Cell {
    private static int count;
    private int id;
    private boolean status; //Is alive?
    private int[] dimensions = new int[2];
    private boolean nextStatus; //Is it going to live?

    public Cell(int ID, boolean life, int width, int height){
        id = ID;
        status = life;
        dimensions[0] = width;
        dimensions[1] = height;
        nextStatus = false;
        count++;
    }

    public void setNextStatus(boolean stat){
        nextStatus = stat;
    }

    public void update(boolean stat){
        status = stat;
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
                "dimensions: [" + dimensions[0] + ", " + dimensions[1] + "] \n\n");
    }
}
