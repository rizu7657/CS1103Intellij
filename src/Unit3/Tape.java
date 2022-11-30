package Unit3;

public class Tape {
    private Cell currentCell;
    private boolean beginningReached;

    Tape() {
        this.currentCell = new Cell();
        this.currentCell.next = null;
        this.currentCell.prev = null;
        this.currentCell.content = ' ';
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void moveLeft() {

        if (currentCell.prev == null) {
            currentCell.prev = new Cell();
            currentCell.prev.content = ' ';
            currentCell.prev.next = currentCell;
        }
        currentCell = currentCell.prev;
    }

    public void moveRight() {

        if (currentCell.next == null) {
            currentCell.next = new Cell();
            currentCell.next.content = ' ';
            currentCell.next.prev = currentCell;
        }
        currentCell = currentCell.next;

    }

    public char getContent() {
        return currentCell.content;
    }

    public void setContent(char content) {
        this.currentCell.content = content;
    }

    public String getTapeContents() {
        String tapeContents = "";

        Cell beginningOfCell = getBeginningPointer(currentCell);
        tapeContents = getCellChainContents(tapeContents, beginningOfCell);

        return tapeContents;
    }

    private String getCellChainContents(String s, Cell c) {

        if (c.next == null) {
            return s;
        }
        return getCellChainContents(s += c.content, c.next);
    }

    private Cell getBeginningPointer(Cell cell) {

        if (cell.prev == null) {
            return cell;
        }
        return getBeginningPointer(cell.prev);
    }
}
