package Unit6.PA;

import java.awt.BorderLayout;

import java.awt.Color;

import java.awt.Dimension;

import java.awt.Font;

import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.RenderingHints;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.PrintWriter;

import java.util.ArrayList;

import java.util.Scanner;

import javax.imageio.ImageIO;

import javax.swing.BorderFactory;

import javax.swing.JColorChooser;

import javax.swing.JLabel;

import javax.swing.JMenu;

import javax.swing.JMenuBar;

import javax.swing.JMenuItem;

import javax.swing.JOptionPane;

import javax.swing.JPanel;

import javax.swing.JTextField;

import javax.swing.KeyStroke;

/**
 * a panel with a sizable drawing area where strings are drawn
 * <p>
 * is illustrable. Objects represent the strings in the strings
 * <p>
 * a DrawTextItem type. The panel's input box enables
 * <p>
 * allowing the user to choose the string that will be drawn when the
 * <p>
 * The sketching area is selected by the user.
 * <p>
 * <p>
 * <p>
 * NEW FEATURES:
 * <p>
 * 1. improved right-click undo functionality (remove item)
 * <p>
 * 2. Added capability for unlimited level undoing
 * <p>
 * 3. Text is displayed with any backdrop color, border, font, etc. with each left click.
 * <p>
 * 4. All new features are supported by the save and open command
 */

/**
 *
 */


public class DrawTextPanel extends JPanel {

    // Currently, this class can only display one string at a time.

    //a time! The DrawTextItem object has the information for that string.

    // known as theString. (If null, nothing is displayed. 

    // A variable of type should be used in place of this one.

    // ArrayListDrawStringItem> with multiple item storage.

    private ArrayList<DrawTextItem> theStrings; // modified to a DrawTextItem ArrayList!

    private Color currentTextColor = Color.BLACK; // Adding color to fresh strings.

    private Canvas canvas; // the work space.

    private JTextField input; // where the user enters the string to add to the canvas

    private SimpleFileChooser fileChooser; // for enabling file selection by the user

    private JMenuBar menuBar; // a menu bar containing controls for this panel's panel

    private MenuHandler menuHandler; // a listener that reacts each time a user chooses a menu option

    private JMenuItem undoMenuItem; // the edit menu's "Remove Item" option

    /**
     * The drawing area is an object of type Canvas.
     * All of the DrawTextItems are simply displayed on the canvas.
     * strings that are kept in the ArrayList.

     */

    private class Canvas extends JPanel {

        Canvas() {

            setPreferredSize(new Dimension(800, 600));

            setBackground(Color.WHITE);

            setFont(new Font("Serif", Font.BOLD, 24));

        }

        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,

                    RenderingHints.VALUE_ANTIALIAS_ON);

            if (theStrings != null)

                for (DrawTextItem s : theStrings)

                    s.draw(g);

        }

    }

    /**
     * The ActionListener is registered with an object of the type MenuHandler.
     * for all of the menu bar commands. The object MenuHandler
     * When the user selects a command, the program merely calls doMenuCommand().
     * out of the menu.

     */

    private class MenuHandler implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            doMenuCommand(evt.getActionCommand());

        }

    }

    /**
     * a DrawTextPanel is created. The panel features a sizable sketching space,
     * a text field with a string input capability. once the
     * when a user clicks a drawing area, a string is added.
     * the region where the user's mouse cursor was.

     */

    public DrawTextPanel() {

        fileChooser = new SimpleFileChooser();

        undoMenuItem = new JMenuItem("Remove Item");

        undoMenuItem.setEnabled(false);

        menuHandler = new MenuHandler();

        setLayout(new BorderLayout(3, 3));

        setBackground(Color.BLACK);

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        canvas = new Canvas();

        add(canvas, BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        bottom.add(new JLabel("Text to add: "));

        input = new JTextField("Hello World!", 40);

        bottom.add(input);

        add(bottom, BorderLayout.SOUTH);

        canvas.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                doMousePress(e);

            }

        });

    }

    /**
     *When the user clicks the drawing area, this method is invoked.
     * The drawing area gains a new string. the heart of
     * Where the user clicked is where the string is.
     * the mouse event that was produced when the user clicked, @param e

     */

    public void doMousePress(MouseEvent e) {

        if (e.isMetaDown()) { //right click to remove an item

            removeItem();

            return;

        }

        String text = input.getText().trim();

        if (text.length() == 0) {

            input.setText("Hello World!");

            text = "Hello World!";

        }

        DrawTextItem s = new DrawTextItem(text, e.getX(), e.getY());

        s.setTextColor(currentTextColor); // Default is null, meaning default color of the canvas (black).

// A FEW ADDITIONAL TEXT ITEMS OPTIONS INCLUDE:

//

        int randomChoice = (int) (Math.random() * 5);

        int fontStyle;

        switch (randomChoice) {

            case 0:
                fontStyle = Font.ITALIC;
                break;

            case 1:
                fontStyle = Font.BOLD;
                break;

            default:
                fontStyle = Font.ITALIC + Font.BOLD;

        }

        s.setFont(new Font("Serif", fontStyle, (int) (Math.random() * 12 + 8)));

//create different types of magnification

        s.setMagnification((int) (Math.random() * 4 + 1));

//make an arbitrary border

        if (Math.random() > 0.3)

            s.setBorder(true);

//produce random make the rotation angle random (0 to 360)

        s.setRotationAngle(Math.random() * 360);

//make transparent text at random (0 to 1)

        s.setTextTransparency(Math.random() * 0.25);

//create a background color at random

        if (Math.random() > 0.5)

            s.setBackground(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));

//make a transparent background at random (0 to 1)

        s.setBackgroundTransparency(Math.random() * 0.90 + 0.10);

        if (theStrings == null)

            theStrings = new ArrayList<DrawTextItem>();

        theStrings.add(s); // Set this string as the ONLY string to be drawn on the canvas!

        undoMenuItem.setEnabled(true);

        canvas.repaint();

    }

    /**
     * returns a menu bar with commands that can be used to modify this panel. The food
     * This panel is intended to appear in the same window as bar.

     */

    public JMenuBar getMenuBar() {

        if (menuBar == null) {

            menuBar = new JMenuBar();

            String commandKey; // for making keyboard accelerators for menu commands

            if (System.getProperty("mrj.version") == null)

                commandKey = "control "; // On non-Mac OS, the command key

            else

                commandKey = "meta "; // for Mac OS, the command key

            JMenu fileMenu = new JMenu("File");

            menuBar.add(fileMenu);

            JMenuItem saveItem = new JMenuItem("Save...");

            saveItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "N"));

            saveItem.addActionListener(menuHandler);

            fileMenu.add(saveItem);

            JMenuItem openItem = new JMenuItem("Open...");

            openItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "O"));

            openItem.addActionListener(menuHandler);

            fileMenu.add(openItem);

            fileMenu.addSeparator();

            JMenuItem saveImageItem = new JMenuItem("Save Image...");

            saveImageItem.addActionListener(menuHandler);

            fileMenu.add(saveImageItem);

            JMenu editMenu = new JMenu("Edit");

            menuBar.add(editMenu);

            undoMenuItem.addActionListener(menuHandler); // The constructor generated the undoItem object.

            undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "Z"));

            editMenu.add(undoMenuItem);

            editMenu.addSeparator();

            JMenuItem clearItem = new JMenuItem("Clear");

            clearItem.addActionListener(menuHandler);

            editMenu.add(clearItem);

            JMenu optionsMenu = new JMenu("Options");

            menuBar.add(optionsMenu);

            JMenuItem colorItem = new JMenuItem("Set Text Color...");

            colorItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "T"));

            colorItem.addActionListener(menuHandler);

            optionsMenu.add(colorItem);

            JMenuItem bgColorItem = new JMenuItem("Set Background Color...");

            bgColorItem.addActionListener(menuHandler);

            optionsMenu.add(bgColorItem);

        }

        return menuBar;

    }

    /**
     * Use a menu bar command to complete an action.
     * command's text can be found in @param.

     */

    private void doMenuCommand(String command) {

        if (command.equals("Save...")) { // save all the string info to a file

            saveFile();

        } else if (command.equals("Open...")) { // read a previously saved file, and reconstruct the list of strings

            openFile();

            canvas.repaint(); // (you'll need this to make the new list of strings take effect)

        } else if (command.equals("Clear")) { // remove all strings

            theStrings = null; // Remove the ONLY string from the canvas.

            undoMenuItem.setEnabled(false);

            canvas.repaint();

        } else if (command.equals("Remove Item"))

            removeItem();

        else if (command.equals("Set Text Color...")) {

            Color c = JColorChooser.showDialog(this, "Select Text Color", currentTextColor);

            if (c != null)

                currentTextColor = c;

        } else if (command.equals("Set Background Color...")) {

            Color c = JColorChooser.showDialog(this, "Select Background Color", canvas.getBackground());

            if (c != null) {

                canvas.setBackground(c);

                canvas.repaint();

            }

        } else if (command.equals("Save Image...")) { // save a PNG image of the drawing area

            File imageFile = fileChooser.getOutputFile(this, "Select Image File Name", "textimage.png");

            if (imageFile == null)

                return;

            try {

// Due to the absence of the image, I will create a new BufferedImage and

// the BufferedImage should be updated with the same information as the panel.

// An image known as a BufferedImage is one that is kept in memory rather than displayed.

// A BufferedImage can be written to a file in an easy way.

                BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(),

                        BufferedImage.TYPE_INT_RGB);

                Graphics g = image.getGraphics();

                g.setFont(canvas.getFont());

                canvas.paintComponent(g); // draws the canvas onto the BufferedImage, not the screen!

                boolean ok = ImageIO.write(image, "PNG", imageFile); // write to the file

                if (ok == false)

                    throw new Exception("PNG format not supported (this shouldn't happen!).");

            } catch (Exception e) {

                JOptionPane.showMessageDialog(this,

                        "Sorry, an error occurred while trying to save the image:\n" + e);

            }

        }

    }

    /**
     * Remove the final item from the canvas one by one when Command equals "Remove Item." Right-click and press Ctrl-Z.
     * are both approved.

     */

    private void removeItem() {

        if (theStrings.size() > 0)

            theStrings.remove(theStrings.size() - 1); // remove the most recently added string

        if (theStrings.size() == 0)

            undoMenuItem.setEnabled(false);

        canvas.repaint();

    }

    /**
     *Creating a text file from the active canvas

     */

    private void saveFile() {

        File saveAs = fileChooser.getOutputFile(this, "Save As", "Text Collage.txt");

        try {

            PrintWriter out = new PrintWriter(saveAs);

            out.println("New text collage file");

            out.println(canvas.getBackground().getRed());

            out.println(canvas.getBackground().getGreen());

            out.println(canvas.getBackground().getBlue());

            if (theStrings != null)

                for (DrawTextItem s : theStrings) {

                    out.println("theString:");

                    out.println(s.getString());

                    out.println(s.getX());

                    out.println(s.getY());

                    out.println(s.getFont().getName());

                    out.println(s.getFont().getStyle());

                    out.println(s.getFont().getSize());

                    out.println(s.getTextColor().getRed());

                    out.println(s.getTextColor().getGreen());

                    out.println(s.getTextColor().getBlue());

                    out.println(s.getTextTransparency());

                    if (s.getBackground() == null) {

                        out.println("-1");

                        out.println("-1");

                        out.println("-1");

                    } else {

                        out.println(s.getBackground().getRed());

                        out.println(s.getBackground().getGreen());

                        out.println(s.getBackground().getBlue());

                    }

                    out.println(s.getBackgroundTransparency());

                    out.println(s.getBorder());

                    out.println(s.getMagnification());

                    out.println(s.getRotationAngle());

                }

            out.close();

        } catch (FileNotFoundException e) {

            JOptionPane.showMessageDialog(this, "Can't write to the file \"" + saveAs + "\".");

            System.out.println("Error message: " + e);

        }

    }

    /**
     * Read the text and background color of a text file that has been saved.
     * strings.

     */

    private void openFile() {

        File openFile = fileChooser.getInputFile(this, "Open Saved File");

        try {

            Scanner in = new Scanner(openFile);

            if (!in.nextLine().equals("New text collage file")) {

                JOptionPane.showMessageDialog(this, "Not a valid file \"" + openFile + "\".");

                return;

            }

            Color savedBg = new Color(in.nextInt(), in.nextInt(), in.nextInt());

            ArrayList<DrawTextItem> newStrings = new ArrayList<DrawTextItem>();

            DrawTextItem newItem;

            in.nextLine(); //skip to the next line before read a new line

            while (in.hasNext() && in.nextLine().equals("theString:")) {

                newItem = new DrawTextItem(in.nextLine(),

                        in.nextInt(), in.nextInt());

                in.nextLine(); //skip to the next line before read a new line

                newItem.setFont(new Font(in.nextLine(), in.nextInt(), in.nextInt()));

                newItem.setTextColor(new Color(in.nextInt(), in.nextInt(), in.nextInt()));

                newItem.setTextTransparency(in.nextDouble());

                int r = in.nextInt();

                int g = in.nextInt();

                int b = in.nextInt();

                if (r == -1)

                    newItem.setBackground(null);

                else

                    newItem.setBackground(new Color(r, g, b));

                newItem.setBackgroundTransparency(in.nextDouble());

                newItem.setBorder(in.nextBoolean());

                newItem.setMagnification(in.nextDouble());

                newItem.setRotationAngle(in.nextDouble());

                in.nextLine(); //skip to the next line before read a new line

                newStrings.add(newItem);

            }

//If there was no exception, change the background and strings.

            canvas.setBackground(savedBg);

            theStrings = newStrings;

        } catch (FileNotFoundException e) {

            JOptionPane.showMessageDialog(this, "Can't read the file \"" + openFile + "\".");

            System.out.println("Error message: " + e);

        }

    }

}