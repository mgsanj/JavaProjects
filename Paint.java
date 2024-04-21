import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
/**
 * A program that creates a canvas for the user to paint on
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version November 15, 2023
 */

public class Paint extends JComponent implements Runnable {
    private Image image; // the canvas
    private Graphics2D graphics2D;  // this will enable drawing
    private int curX; // current mouse x coordinate
    private int curY; // current mouse y coordinate
    private int oldX; // previous mouse x coordinate
    private int oldY; // previous mouse y coordinate

    private Color curColor = Color.white;

    private Color backColor = Color.white;

    //top panel
    JButton clearButton; // a button to clear
    JButton fillButton; // a button to fill
    JButton eraseButton; // a button to erase
    JButton randomButton; // a button to random
    //bottom panel
    JTextField hexText;
    JButton hexButton;
    JTextField rText;
    JTextField gText;
    JTextField bText;
    JButton rgbButton;


    Paint paint; // variable of the type ColorPicker

    /* action listener for buttons */
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //top
            if (e.getSource() == clearButton) {
                paint.clear();
                hexText.setText("#");
                rText.setText("");
                gText.setText("");
                bText.setText("");
            }
            if (e.getSource() == fillButton) {
                paint.fill();
                hexText.setText("#");
                rText.setText("");
                gText.setText("");
                bText.setText("");
            }
            if (e.getSource() == eraseButton) {
                paint.erase();
            }
            if (e.getSource() == randomButton) {
                paint.random();
                hexText.setText(String.format("#%02x%02x%02x", curColor.getRed(),
                        curColor.getGreen(), curColor.getBlue()));
                rText.setText(Integer.toString(curColor.getRed()));
                gText.setText(Integer.toString(curColor.getGreen()));
                bText.setText(Integer.toString(curColor.getBlue()));
            }
            //bottom
            if (e.getSource() == hexButton) {
                String hexVal = hexText.getText();
                paint.hex(hexVal);
                rText.setText(Integer.toString(curColor.getRed()));
                gText.setText(Integer.toString(curColor.getGreen()));
                bText.setText(Integer.toString(curColor.getBlue()));
            }
            if (e.getSource() == rgbButton) {
                if (rText.getText().isEmpty()) {
                    rText.setText("0");
                }
                if (gText.getText().isEmpty()) {
                    gText.setText("0");
                }
                if (bText.getText().isEmpty()) {
                    bText.setText("0");
                }
                int rVal = Integer.parseInt(rText.getText());
                int gVal = Integer.parseInt(gText.getText());
                int bVal = Integer.parseInt(bText.getText());
                System.out.println(rVal + gVal + bVal);
                paint.rgb(rVal, gVal, bVal);
                hexText.setText(String.format("#%02x%02x%02x", rVal,
                        gVal, bVal));
                rText.setText(Integer.toString(rVal));
                gText.setText(Integer.toString(gVal));
                bText.setText(Integer.toString(bVal));
            }

        }
    };

    /* set up paint colors */
    public void clear() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        curColor = Color.white;
        backColor = Color.white;
        graphics2D.setPaint(Color.black);
        repaint();

    }

    public void fill() {
        graphics2D.setPaint(curColor);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        backColor = curColor;
        graphics2D.setPaint(Color.black);
        curColor = Color.black;
        repaint();
    }

    public void erase() {
        graphics2D.setPaint(backColor);
        repaint();
    }

    public void random() {
        Random random = new Random();
        Color color = new Color(random.nextInt(256), random.nextInt(256),
                random.nextInt(256));
        curColor = color;
        graphics2D.setPaint(color);
        repaint();
    }

    public void hex(String hexVal) {
        try {
            graphics2D.setPaint(Color.decode(hexVal));
            curColor = Color.decode(hexVal);
            repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Not a valid Hex Value", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void rgb(int rVal, int gVal, int bVal) {
        try {
            graphics2D.setPaint(new Color(rVal, gVal, bVal));
            curColor = new Color(rVal, gVal, bVal);
            repaint();
        } catch (IllegalArgumentException exp) {
            JOptionPane.showMessageDialog(null, "Not a valid RGB Value", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    public Paint() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                /* set oldX and oldY coordinates to beginning mouse press*/
                oldX = e.getX();
                oldY = e.getY();
            }
        });


        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                /* set current coordinates to where mouse is being dragged*/
                curX = e.getX();
                curY = e.getY();

                /* draw the line between old coordinates and new ones */
                graphics2D.setStroke(new BasicStroke(5));
                graphics2D.drawLine(oldX, oldY, curX, curY);

                /* refresh frame and reset old coordinates */
                repaint();
                oldX = curX;
                oldY = curY;

            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Paint());
    }

    public void run() {
        /* set up JFrame */
        JFrame frame = new JFrame("Challenge Problem");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        paint = new Paint();
        content.add(paint, BorderLayout.CENTER);

        //Top Panel
        clearButton = new JButton("Clear");
        clearButton.addActionListener(actionListener);
        fillButton = new JButton("Fill");
        fillButton.addActionListener(actionListener);
        eraseButton = new JButton("Erase");
        eraseButton.addActionListener(actionListener);
        randomButton = new JButton("Random");
        randomButton.addActionListener(actionListener);

        JPanel panel = new JPanel();
        panel.add(clearButton, BorderLayout.NORTH);
        panel.add(fillButton, BorderLayout.NORTH);
        panel.add(eraseButton, BorderLayout.NORTH);
        panel.add(randomButton, BorderLayout.NORTH);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //Bottom Panel: Hex TextField, Hex Button, R TextField, G TextField, B TextField, RGB Button
        hexText = new JTextField("#", 10);
        hexButton = new JButton("Hex");
        hexButton.addActionListener(actionListener);
        rText = new JTextField("", 5);
        gText = new JTextField("", 5);
        bText = new JTextField("", 5);
        rgbButton = new JButton("RGB");
        rgbButton.addActionListener(actionListener);

        JPanel panel2 = new JPanel();
        panel2.add(hexText, BorderLayout.SOUTH);
        panel2.add(hexButton, BorderLayout.SOUTH);
        panel2.add(rText, BorderLayout.SOUTH);
        panel2.add(gText, BorderLayout.SOUTH);
        panel2.add(bText, BorderLayout.SOUTH);
        panel2.add(rgbButton, BorderLayout.SOUTH);

        content.add(panel, BorderLayout.NORTH);
        content.add(panel2, BorderLayout.SOUTH);


    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            /* this lets us draw on the image (ie. the canvas)*/
            graphics2D = (Graphics2D) image.getGraphics();
            /* gives us better rendering quality for the drawing lines */
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            /* set canvas to white with default paint color */
            graphics2D.setPaint(Color.white);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            curColor = Color.black;
            graphics2D.setPaint(Color.black);
            repaint();
        }
        g.drawImage(image, 0, 0, null);

    }
}
