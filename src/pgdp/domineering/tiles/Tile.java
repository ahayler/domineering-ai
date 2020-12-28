package pgdp.domineering.tiles;

public class Tile {
    private char tileChar;
    private Boolean isEmpty;
    private Boolean isVerticalSafeTile;
    private Boolean isHorizontalSafeTile;
    private Boolean isVerticalPossibilityTile;
    private int verticalPossibilityDirection;
    private Boolean isHorizontalPossibilityTile;
    private int horizontalPossibilityDirection;

    public Tile(char tileChar, Boolean isEmpty, Boolean isVerticalSafeTile, Boolean isHorizontalSafeTile,
                Boolean isVerticalPossibilityTile, int verticalPossibilityDirection,
                Boolean isHorizontalPossibilityTile, int horizontalPossibilityDirection) {
        this.tileChar = tileChar;
        this.isEmpty = isEmpty;
        this.isVerticalSafeTile = isVerticalSafeTile;
        this.isHorizontalSafeTile = isHorizontalSafeTile;
        this.isVerticalPossibilityTile = isVerticalPossibilityTile;
        this.verticalPossibilityDirection = verticalPossibilityDirection;
        this.isHorizontalPossibilityTile = isHorizontalPossibilityTile;
        this.horizontalPossibilityDirection = horizontalPossibilityDirection;
    }

    public Tile copy() {
        return new Tile(tileChar, isEmpty, isVerticalSafeTile, isHorizontalSafeTile,
                isVerticalPossibilityTile, verticalPossibilityDirection,
        isHorizontalPossibilityTile, horizontalPossibilityDirection);
    }

    public char getTileChar() {
        return tileChar;
    }

    public void setTileChar(char tileChar) {
        this.tileChar = tileChar;
    }

    public Boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }

    public Boolean isVerticalSafeTile() {
        return isVerticalSafeTile;
    }

    public void setVerticalSafeTile(Boolean verticalSafeTile) {
        isVerticalSafeTile = verticalSafeTile;
    }

    public Boolean isHorizontalSafeTile() {
        return isHorizontalSafeTile;
    }

    public void setHorizontalSafeTile(Boolean horizontalSafeTile) {
        isHorizontalSafeTile = horizontalSafeTile;
    }

    public Boolean isVerticalPossibilityTile() {
        return isVerticalPossibilityTile;
    }

    public void setVerticalPossibilityTile(Boolean verticalPossibilityTile) {
        isVerticalPossibilityTile = verticalPossibilityTile;
    }

    public int getVerticalPossibilityDirection() {
        return verticalPossibilityDirection;
    }

    public void setVerticalPossibilityDirection(int verticalPossibilityDirection) {
        this.verticalPossibilityDirection = verticalPossibilityDirection;
    }

    public Boolean isHorizontalPossibilityTile() {
        return isHorizontalPossibilityTile;
    }

    public void setHorizontalPossibilityTile(Boolean horizontalPossibilityTile) {
        isHorizontalPossibilityTile = horizontalPossibilityTile;
    }

    public int getHorizontalPossibilityDirection() {
        return horizontalPossibilityDirection;
    }

    public void setHorizontalPossibilityDirection(int horizontalPossibilityDirection) {
        this.horizontalPossibilityDirection = horizontalPossibilityDirection;
    }
}
