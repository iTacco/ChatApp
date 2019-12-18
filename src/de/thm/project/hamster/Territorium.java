package de.thm.project.hamster;

public class Territorium {

    private final int rows;
    private final int columns;

    private String[][] map;

    public Territorium(String[] mapAsStringArray) {
        this.rows = Integer.parseInt(mapAsStringArray[0]);
        this.columns = Integer.parseInt(mapAsStringArray[1]);

        this.map = new String[rows][columns];

        int cnt = 2;
        for(int row = 0; row < this.rows; row++) {
            for(int col = 0; col < this.columns; col++) {
                this.map[row][col] = mapAsStringArray[cnt];
                cnt++;
            }
        }
    }

    public String getMapInfo(int row, int col) {
        return this.map[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
