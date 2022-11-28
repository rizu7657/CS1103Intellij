package Unit3;

public class Tape {
    private Cell currentCell;

    Tape() {
        this.currentCell = new Cell();
        this.currentCell.next = null;
        this.currentCell.prev = null;
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
        while (currentCell.content != ' ') {
            moveLeft();
        }

        do {
            moveRight();
            tapeContents += currentCell.content;
        } while (currentCell.content != ' ');

        return tapeContents;
    }
}
