/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import emitters.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.event.*;
//import mapcreator.ButtonPanel;

/**
 *
 * @author Kevin
 */

public class EmitterEditor extends JFrame implements ActionListener, ChangeListener{
    
    private MenuPane leftPart, rightPart, topPart;
    private DrawPane middlePart;
    private JButton save, formColor, speedAccelX, speedAccelY;
    private JTextField name;
    private JSlider radius, red, green, blue, alpha, minX, maxX, accX, minY, maxY, accY, spreadX, spreadY;
    private JSlider distX, distY;
    private JComboBox<String> nrOfPar, parChoosen, shapeChoosen;
    private Emitter emit;
    private String[] nums = {"1","2","3","4","5","6","7","8","9","10"};
    private String[] parTypes = {"Particle 1", "Particle 2", "Particle 3", "Particle 4", "Particle 5",
                            "Particle 6", "Particle 7", "Particle 8", "Particle 9","Particle 10"};
    private String[] shapes = {"Circle", "Square"};
    
    public EmitterEditor() {
        initEmitter();
        leftPart = new MenuPane();
        leftPart.setBounds(0,0,150,400-28);
        leftPart.setBackground(Color.GRAY);
        rightPart = new MenuPane();
        rightPart.setBounds(450,0,150,400-28);
        rightPart.setBackground(Color.GRAY);
        topPart = new MenuPane();
        topPart.setBounds(150,0,300,100);
        topPart.setBackground(Color.GRAY);
        middlePart = new DrawPane(emit);
        middlePart.setBounds(150, 100, 300, 300-28);
        middlePart.setBackground(Color.WHITE);
        
        initLeft();
        initRightSpeedX();
        initTop();
        initMiddle();
        
        add(leftPart);
        add(rightPart);
        add(topPart);
        add(middlePart);
        
        setTitle("Emitter Editor");
        setLayout(null);
        setSize(606,400);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == save) {
            save();
        } else if(e.getSource() == formColor) {
            remove(rightPart);
            rightPart = new MenuPane();
            rightPart.setBounds(450,0,150,400-28);
            rightPart.setBackground(Color.GRAY);
            add(rightPart);
            initRightColor();
            revalidate();
            repaint();
        } else if(e.getSource() == speedAccelX) {
            remove(rightPart);
            rightPart = new MenuPane();
            rightPart.setBounds(450,0,150,400-28);
            rightPart.setBackground(Color.GRAY);
            add(rightPart);
            initRightSpeedX();
            revalidate();
            repaint();
        } else if(e.getSource() == speedAccelY) {
            remove(rightPart);
            rightPart = new MenuPane();
            rightPart.setBounds(450,0,150,400-28);
            rightPart.setBackground(Color.GRAY);
            add(rightPart);
            initRightSpeedY();
            revalidate();
            repaint();
        } else if(e.getSource() == nrOfPar) {
            emit.setNrOfPar(nrOfPar.getSelectedIndex()+1); //Index goes from 0-4, therefore add 1
        } else if(e.getSource() == parChoosen) {
            remove(rightPart);
            rightPart = new MenuPane();
            rightPart.setBounds(450,0,150,400-28);
            rightPart.setBackground(Color.GRAY);
            add(rightPart);
            initRightColor();
            shapeChoosen.setSelectedIndex(emit.getShape(parChoosen.getSelectedIndex()));
            revalidate();
            repaint();
        } else if(e.getSource() == shapeChoosen) {
            emit.setShape(parChoosen.getSelectedIndex(), shapeChoosen.getSelectedIndex());
        }
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == radius) {
            emit.setRadius(parChoosen.getSelectedIndex(), radius.getValue());
        } else if(e.getSource() == red) {
            emit.setColorRed(parChoosen.getSelectedIndex(), red.getValue());
        } else if(e.getSource() == green) {
            emit.setColorGreen(parChoosen.getSelectedIndex(), green.getValue());
        } else if(e.getSource() == blue) {
            emit.setColorBlue(parChoosen.getSelectedIndex(), blue.getValue());
        } else if(e.getSource() == alpha) {
            emit.setColorAlpha(parChoosen.getSelectedIndex(), alpha.getValue());
        } else if(e.getSource() == minX) {
            emit.setVelX(parChoosen.getSelectedIndex(), 0, minX.getValue());
        } else if(e.getSource() == maxX) {
            emit.setVelX(parChoosen.getSelectedIndex(), 1, maxX.getValue());
        } else if(e.getSource() == accX) {
            emit.setVelX(parChoosen.getSelectedIndex(), 2, accX.getValue());
        } else if(e.getSource() == minY) {
            emit.setVelY(parChoosen.getSelectedIndex(), 0, minY.getValue());
        } else if(e.getSource() == maxY) {
            emit.setVelY(parChoosen.getSelectedIndex(), 1, maxY.getValue());
        } else if(e.getSource() == accY) {
            emit.setVelY(parChoosen.getSelectedIndex(), 2, accY.getValue());
        } else if(e.getSource() == spreadX) {
            emit.setSpreadX(parChoosen.getSelectedIndex(), spreadX.getValue());
        } else if(e.getSource() == spreadY) {
            emit.setSpreadY(parChoosen.getSelectedIndex(), spreadY.getValue());
        } else if(e.getSource() == distX) {
            emit.setDistX(parChoosen.getSelectedIndex(), distX.getValue());
        } else if(e.getSource() == distY) {
            emit.setDistY(parChoosen.getSelectedIndex(), distY.getValue());
        }
    }
    
    private void initEmitter() {
        //pos[], nrOfPar, col[], shapes[], radius[], velX[][], velY[][], name, amount
        int[] pos = {150,136};
        int nrOfParTypes = 10;
        Color[] col = new Color[nrOfParTypes];
        for(int i = 0; i < nrOfParTypes; i++) {
            col[i] = Color.black;
        }
        
        int[] shapes = new int[nrOfParTypes];
        int[] radius = new int[nrOfParTypes];
        int[] spreadX = new int[nrOfParTypes];
        int[] spreadY = new int[nrOfParTypes];
        int[] distX = new int[nrOfParTypes];
        int[] distY = new int[nrOfParTypes];
        int[][] velX = new int[nrOfParTypes][3];
        int[][] velY = new int[nrOfParTypes][3];
        String name ="";
        int amount = 5000;
        emit = new Emitter(pos, nrOfParTypes, col, shapes, radius,spreadX,spreadY, distX,distY,velX,velY,name,amount);
    }
    
    private void initLeft() {
        
        leftPart.setLayout(null);
        //Save
        save = new JButton("Save");
        save.setBounds(15,20,120,30);
        save.setToolTipText("Save the Emitter with the selected values");
        save.addActionListener(this);
        leftPart.add(save);
        //NrOfPar
        JLabel nrOfParText = new JLabel("Nr of pars");
        nrOfParText.setBounds(10,70,60,25);
        nrOfParText.setForeground(Color.BLACK);
        leftPart.add(nrOfParText);
        nrOfPar = new JComboBox<String>(nums);
        nrOfPar.setMaximumRowCount(10);
        nrOfPar.addActionListener(this);
        nrOfPar.setBounds(75,75,50,20);
        nrOfPar.setToolTipText("Number of particles to be used in the emitter");
        leftPart.add(nrOfPar);
        //Particle
        parChoosen = new JComboBox<String>(parTypes);
        parChoosen.setBounds(15, 115, 120, 20);
        parChoosen.setToolTipText("Selects particle for edit");
        parChoosen.addActionListener(this);
        leftPart.add(parChoosen);
        //Shape
        JLabel shapeText = new JLabel("Shape: ");
        shapeText.setBounds(10, 155, 50,20);
        shapeText.setForeground(Color.BLACK);
        leftPart.add(shapeText);
        shapeChoosen = new JComboBox<String>(shapes);
        shapeChoosen.addActionListener(this);
        shapeChoosen.setBounds(60, 155, 70,20);
        leftPart.add(shapeChoosen);
        //Form&Color
        formColor = new JButton("Form & Color");
        formColor.addActionListener(this);
        formColor.setBounds(15,195,120,30);
        leftPart.add(formColor);
        //Speed&Acceleration
        speedAccelX = new JButton("Velocity X");
        speedAccelX.addActionListener(this);
        speedAccelX.setBounds(15,245,120,30);
        leftPart.add(speedAccelX);
        speedAccelY = new JButton("Velocity Y");
        speedAccelY.addActionListener(this);
        speedAccelY.setBounds(15,295,120,30);
        leftPart.add(speedAccelY);
    }
    
    private void initRightSpeedY() {
        rightPart.setLayout(null);
        //Min vY
        JLabel minVYText = new JLabel("Min velocity Y: ");
        minVYText.setBounds(30,15,100,20);
        minVYText.setForeground(Color.BLACK);
        rightPart.add(minVYText);
        minY = new JSlider();
        
        minY.setMaximum(20);
        minY.setMinimum(-20);
        minY.setMinorTickSpacing(1);
        minY.setMajorTickSpacing(5);
        minY.setBounds(10, 35, 130,45);
        minY.setPaintTicks(true);
        minY.setPaintLabels(true);
        minY.setValue(emit.getVelY(parChoosen.getSelectedIndex(), 0));
        rightPart.add(minY);
        minY.addChangeListener(this);
        //Max vX
        JLabel maxVYText = new JLabel("Max velocity Y: ");
        maxVYText.setBounds(30,85,100,20);
        maxVYText.setForeground(Color.BLACK);
        rightPart.add(maxVYText);
        maxY = new JSlider();
        
        maxY.setMaximum(20);
        maxY.setMinimum(-20);
        maxY.setMinorTickSpacing(1);
        maxY.setMajorTickSpacing(5);
        maxY.setBounds(10, 105, 130,45);
        maxY.setPaintTicks(true);
        maxY.setPaintLabels(true);
        maxY.setValue(emit.getVelY(parChoosen.getSelectedIndex(), 1));
        rightPart.add(maxY);
        maxY.addChangeListener(this);
        //Accel X
        JLabel accYText = new JLabel("Acceleration Y: ");
        accYText.setBounds(30,155,100,20);
        accYText.setForeground(Color.BLACK);
        rightPart.add(accYText);
        accY = new JSlider();
        
        accY.setMaximum(20);
        accY.setMinimum(-20);
        accY.setMinorTickSpacing(1);
        accY.setMajorTickSpacing(5);
        accY.setBounds(10, 175, 130,45);
        accY.setPaintTicks(true);
        accY.setPaintLabels(true);
        accY.setValue(emit.getVelY(parChoosen.getSelectedIndex(), 2));
        rightPart.add(accY);
        accY.addChangeListener(this);
        
        //spreadY
        JLabel spreadYText = new JLabel("Spread from Y: ");
        spreadYText.setBounds(30,225,100,20);
        spreadYText.setForeground(Color.BLACK);
        rightPart.add(spreadYText);
        spreadY = new JSlider();
        
        spreadY.setMaximum(10);
        spreadY.setMinimum(0);
        spreadY.setMinorTickSpacing(1);
        spreadY.setMajorTickSpacing(5);
        spreadY.setBounds(10, 245, 130,45);
        spreadY.setPaintTicks(true);
        spreadY.setPaintLabels(true);
        spreadY.setValue(emit.getSpreadY(parChoosen.getSelectedIndex()));
        rightPart.add(spreadY);
        spreadY.addChangeListener(this);
        
        //distY
        JLabel distYText = new JLabel("Distance from Y: ");
        distYText.setBounds(30,295,100,20);
        distYText.setForeground(Color.BLACK);
        rightPart.add(distYText);
        distY = new JSlider();
        
        distY.setMaximum(100);
        distY.setMinimum(-100);
        distY.setMinorTickSpacing(5);
        distY.setMajorTickSpacing(25);
        distY.setBounds(10, 315, 130,45);
        distY.setPaintTicks(true);
        distY.setPaintLabels(true);
        distY.setValue(emit.getDistY(parChoosen.getSelectedIndex()));
        rightPart.add(distY);
        distY.addChangeListener(this);
    }
    
    private void initRightSpeedX() {
        rightPart.setLayout(null);
        //Min vX
        JLabel minVXText = new JLabel("Min velocity X: ");
        minVXText.setBounds(30,15,100,20);
        minVXText.setForeground(Color.BLACK);
        rightPart.add(minVXText);
        minX = new JSlider();
        
        minX.setMaximum(20);
        minX.setMinimum(-20);
        minX.setMinorTickSpacing(1);
        minX.setMajorTickSpacing(5);
        minX.setBounds(10, 35, 130,45);
        minX.setPaintTicks(true);
        minX.setPaintLabels(true);
        minX.setValue(emit.getVelX(parChoosen.getSelectedIndex(), 0));
        rightPart.add(minX);
        minX.addChangeListener(this);
        //Max vX
        JLabel maxVXText = new JLabel("Max velocity X: ");
        maxVXText.setBounds(30,85,100,20);
        maxVXText.setForeground(Color.BLACK);
        rightPart.add(maxVXText);
        maxX = new JSlider();
        
        maxX.setMaximum(20);
        maxX.setMinimum(-20);
        maxX.setMinorTickSpacing(1);
        maxX.setMajorTickSpacing(5);
        maxX.setBounds(10, 105, 130,45);
        maxX.setPaintTicks(true);
        maxX.setPaintLabels(true);
        maxX.setValue(emit.getVelX(parChoosen.getSelectedIndex(), 1));
        rightPart.add(maxX);
        maxX.addChangeListener(this);
        //Accel X
        JLabel accXText = new JLabel("Acceleration X: ");
        accXText.setBounds(30,155,100,20);
        accXText.setForeground(Color.BLACK);
        rightPart.add(accXText);
        accX = new JSlider();
        
        accX.setMaximum(20);
        accX.setMinimum(-20);
        accX.setMinorTickSpacing(1);
        accX.setMajorTickSpacing(5);
        accX.setBounds(10, 175, 130,45);
        accX.setPaintTicks(true);
        accX.setPaintLabels(true);
        accX.setValue(emit.getVelX(parChoosen.getSelectedIndex(), 2));
        rightPart.add(accX);
        accX.addChangeListener(this);
        
        //spreadX
        JLabel spreadXText = new JLabel("Spread from X: ");
        spreadXText.setBounds(30,225,100,20);
        spreadXText.setForeground(Color.BLACK);
        rightPart.add(spreadXText);
        spreadX = new JSlider();
        
        spreadX.setMaximum(10);
        spreadX.setMinimum(0);
        spreadX.setMinorTickSpacing(1);
        spreadX.setMajorTickSpacing(5);
        spreadX.setBounds(10, 245, 130,45);
        spreadX.setPaintTicks(true);
        spreadX.setPaintLabels(true);
        spreadX.setValue(emit.getSpreadX(parChoosen.getSelectedIndex()));
        rightPart.add(spreadX);
        spreadX.addChangeListener(this);
        
        JLabel distXText = new JLabel("DIstance from X: ");
        distXText.setBounds(30,295,100,20);
        distXText.setForeground(Color.BLACK);
        rightPart.add(distXText);
        distX = new JSlider();
        
        distX.setMaximum(100);
        distX.setMinimum(-100);
        distX.setMinorTickSpacing(5);
        distX.setMajorTickSpacing(25);
        distX.setBounds(10, 315, 130,45);
        distX.setPaintTicks(true);
        distX.setPaintLabels(true);
        distX.setValue(emit.getDistX(parChoosen.getSelectedIndex()));
        rightPart.add(distX);
        distX.addChangeListener(this);
    }
    
    private void initRightColor() {
        rightPart.setLayout(null);
        //Radius
        JLabel radText = new JLabel("Radius: ");
        radText.setBounds(50,15,60,20);
        radText.setForeground(Color.BLACK);
        rightPart.add(radText);
        radius = new JSlider();
        
        radius.setMaximum(10);
        radius.setMinimum(0);
        radius.setMinorTickSpacing(1);
        radius.setMajorTickSpacing(2);
        radius.setBounds(10, 35, 130,45);
        radius.setPaintTicks(true);
        radius.setPaintLabels(true);
        radius.setValue(emit.getRadius(parChoosen.getSelectedIndex()));
        rightPart.add(radius);
        radius.addChangeListener(this);
        //Red
        JLabel redText = new JLabel("Red value: ");
        redText.setBounds(40,85,80,20);
        redText.setForeground(Color.BLACK);
        rightPart.add(redText);
        red = new JSlider();
        
        red.setMaximum(255);
        red.setMinimum(0);
        red.setMinorTickSpacing(25);
        red.setMajorTickSpacing(100);
        red.setBounds(10, 105, 130,45);
        red.setPaintTicks(true);
        red.setPaintLabels(true);
        red.setValue(emit.getColorRed(parChoosen.getSelectedIndex()));
        rightPart.add(red);
        red.addChangeListener(this);
        //Green
        JLabel greenText = new JLabel("Green value: ");
        greenText.setBounds(35,155,85,20);
        greenText.setForeground(Color.BLACK);
        rightPart.add(greenText);
        green = new JSlider();
        
        green.setMaximum(255);
        green.setMinimum(0);
        green.setMinorTickSpacing(25);
        green.setMajorTickSpacing(100);
        green.setBounds(10, 175, 130,45);
        green.setPaintTicks(true);
        green.setPaintLabels(true);
        green.setValue(emit.getColorGreen(parChoosen.getSelectedIndex()));
        rightPart.add(green);
        green.addChangeListener(this);
        //Blue
        JLabel blueText = new JLabel("Blue value: ");
        blueText.setBounds(35,225,85,20);
        blueText.setForeground(Color.BLACK);
        rightPart.add(blueText);
        blue = new JSlider();
        
        blue.setMaximum(255);
        blue.setMinimum(0);
        blue.setMinorTickSpacing(25);
        blue.setMajorTickSpacing(100);
        blue.setBounds(10, 245, 130,45);
        blue.setPaintTicks(true);
        blue.setPaintLabels(true);
        blue.setValue(emit.getColorBlue(parChoosen.getSelectedIndex()));
        rightPart.add(blue);
        blue.addChangeListener(this);
        //Alpha
        JLabel alphaText = new JLabel("Alpha value: ");
        alphaText.setBounds(35,295,85,20);
        alphaText.setForeground(Color.BLACK);
        rightPart.add(alphaText);
        alpha = new JSlider();
        
        alpha.setMaximum(255);
        alpha.setMinimum(0);
        alpha.setMinorTickSpacing(25);
        alpha.setMajorTickSpacing(100);
        alpha.setBounds(10, 315, 130,45);
        alpha.setPaintTicks(true);
        alpha.setPaintLabels(true);
        alpha.setValue(emit.getColorAlpha(parChoosen.getSelectedIndex()));
        rightPart.add(alpha);
        alpha.addChangeListener(this);
    }
    
    public void initTop() {
        topPart.setLayout(null);
        //Name label
        JLabel nameText = new JLabel("Name: ");
        nameText.setBounds(50,30,40,20);
        nameText.setForeground(Color.BLACK);
        topPart.add(nameText);
        //Name textfield
        name = new JTextField();
        name.setBounds(95,30,130,20);
        topPart.add(name);
    }
    
    public void initMiddle() { 
        //Nothing atm
    }
    
     private void save() {
        FileWriter fileWriter = null; 
            try {
                String emitterPath = "data/Dungeon/emitters/emitterids.inf";
                fileWriter = new FileWriter(emitterPath, true);
                
            } catch (IOException ex) {}
            
            PrintWriter outputFile = new PrintWriter(fileWriter);
            int nrOfPars = emit.getNrOfPar();
            outputFile.print(nrOfPars + " ");
            for(int i=0;i<nrOfPars;i++) {
                outputFile.print(emit.getColorRed(i) + " ");
                outputFile.print(emit.getColorGreen(i) + " ");
                outputFile.print(emit.getColorBlue(i) + " ");
                outputFile.print(emit.getColorAlpha(i) + " ");
            }
            for(int i=0;i<nrOfPars;i++) {
                outputFile.print(emit.getShape(i) + " ");
            }
            for(int i=0;i<nrOfPars;i++) {
                outputFile.print(emit.getRadius(i) + " ");
            }
            for(int i=0;i<nrOfPars;i++) {
                outputFile.print(emit.getSpreadX(i) + " ");
            }
            for(int i=0;i<nrOfPars;i++) {
                outputFile.print(emit.getSpreadY(i) + " ");
            }
            for(int i=0;i<nrOfPars;i++) {
                outputFile.print(emit.getDistX(i) + " ");
            }
            for(int i=0;i<nrOfPars;i++) {
                outputFile.print(emit.getDistY(i) + " ");
            }
            for(int i=0;i<nrOfPars;i++) {
                for(int j=0;j<3;j++){
                    outputFile.print(emit.getVelX(i, j) + " ");
                }
            }
            for(int i=0;i<nrOfPars;i++) {
                for(int j=0;j<3;j++){
                    outputFile.print(emit.getVelY(i,j) + " ");
                }
            }
            outputFile.print(name.getText() + " ");
            outputFile.print(emit.getAmount() + "");
            outputFile.println();
            outputFile.close();

    }
}

class MenuPane extends JPanel {
    public MenuPane() {
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color[] colors = new Color[10];
        colors[0] = colors[1] = colors[8] = colors[9] = Color.BLACK;
        colors[2] = colors[3] = colors[6] = colors[7] = Color.DARK_GRAY;
        colors[4] = colors[5] = Color.GRAY;
        for (int i = 0; i < 10; i++) {
            g.setColor(colors[i]);
            g.drawRoundRect(i, i, getWidth() - 2 * i - 1, getHeight() - 2 * i - 1, 10, 10);
        }

    }
    
   
    
}

class DrawPane extends JPanel implements Runnable{
    
    private Emitter emitter;
    private Thread runner;
    
    public DrawPane(Emitter emit) {
        emitter = emit;
        runner = new Thread(this);
        runner.start();
    }
    
    
    
    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
        Color[] colors = new Color[10];
        colors[0] = colors[1] = colors[8] = colors[9] = Color.BLACK;
        colors[2] = colors[3] = colors[6] = colors[7] = Color.DARK_GRAY;
        colors[4] = colors[5] = Color.GRAY;
        for (int i = 0; i < 10; i++) {
            g.setColor(colors[i]);
            g.drawRoundRect(i, i, getWidth() - 2 * i - 1, getHeight() - 2 * i - 1, 10, 10);
        }
        if(emitter.getList().size() >0)
            emitter.draw(g);

    }

    @Override
    public void run() {
        while(true) {
            
            try {
                Thread.sleep(16);
            } catch (InterruptedException ex) {
                
            }
            emitter.update();
            repaint();
        }
    }
    
    
    
}