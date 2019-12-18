package de.thm.project.hamster;

public class Territorium {

    private final int rows;
    private final int columns;

    private String[][] map;

    public Territorium(String[] mapAsStringArray) {
        String fullMap = mapAsStringArray[mapAsStringArray.length - 1];
        fullMap = fullMap.split("territorium: ")[1];

        String[] splittedMap = fullMap.split(" ");

        this.columns = Integer.parseInt(splittedMap[0]);
        this.rows = Integer.parseInt(splittedMap[1]);

        this.map = new String[rows][columns];

        int cnt = 2;
        for(int row = 0; row < this.rows; row++) {
            for(int col = 0; col < this.columns; col++) {
                this.map[row][col] = splittedMap[cnt];
                cnt++;
            }
        }
    }

    public boolean isWall(int row, int col) {
        boolean isWall = false;
        if(getMapInfo(row, col).equals("x")) {
            isWall = true;
        }
        return isWall;
    }

    public String getMapInfo(int row, int col) {
        if(row < 0 || row >= rows || col < 0 || col >= columns) {
            return "x";
        }
        else {
            return this.map[row][col];
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
