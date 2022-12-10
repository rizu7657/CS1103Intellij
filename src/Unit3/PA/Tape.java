package Unit3.PA;

public class Tape {
    public Tape() {
        crCell = new Cell();
        crCell.content = ' ';
        crCell.next = null;
        crCell.prev = null;
    }

    private Cell crCell;

    public Cell getCurrentCell() {
        return crCell;
    }

    public void setContent(char content) {
        crCell.content = content;

    }

    public char getCurrent() {
        return crCell.content;

    }

    public void moveLeft() {
        if (crCell.prev == null) {
            Cell newPrev = new Cell();
            newPrev.content = ' ';
            newPrev.prev = null;
            newPrev.next = crCell;
            crCell.prev = newPrev;
        }
        crCell = crCell.prev;
    }

    public void moveRight() {
        if (crCell.next == null) {
            Cell newPrev = new Cell();
            newPrev.content = ' ';
            newPrev.next = null;
            newPrev.prev = crCell;
            crCell.next = newPrev;
        }
        crCell = crCell.next;
    }

    public String getTapeContents() {
        String tapeContent = "";
        Cell newPointer = crCell;
        while (newPointer.prev != null) {
            newPointer = newPointer.prev;
        }
        while (newPointer != null) {
            tapeContent = tapeContent + newPointer.content;
            newPointer = newPointer.next;
        }
        return tapeContent;

    }
}
