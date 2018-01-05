/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import characters.*;





/**
 *
 * @author Kevin
 */
public class SkeletonEditor extends JFrame implements ActionListener, ChangeListener {
    
    private MenuPanel tools, information;
    private DrawPanel drawing;
    private JButton newSkel, loadSkel, saveSkel;
    private JButton addJoint,conJoints,editJoints;
    private JButton saveFrame,newFrame;
    private JButton saveAni, loadAni, newAni, playAni;
    
    private JLabel nameBody, nameAni, amountFrames, currentFrame, scaleLabel, aniLabel;
    private JSlider frames, scaleSlider, aniSlider;
    private JTextField nameForAni, nameForBody;
    private JComboBox skeletonNames;
    private Toolkit t;
    
    private java.util.ArrayList<String> skeletons;
    
    public SkeletonEditor() {
        skeletons = new java.util.ArrayList<>();
        loadSkeletonsIds();
        initTools();
        initDraw();
        initInfo();
        
        setTitle("Skeleton Editor");
        setLayout(null);
        setSize(706,650);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void loadSkeletonsIds() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("data/characters/charids.inf"));
            String line;
            t = Toolkit.getDefaultToolkit();
            while ((line = br.readLine()) != null) {
                skeletons.add(line);
            }
            br.close();
        } catch (Exception ex) {
            
        }
    }
    
    private void loadSkeleton(int i) {
        drawing.newBody();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("data/characters/"+skeletons.get(i)+"/data.inf"));
            String line;
            drawing.getSkeleton().setName(br.readLine());
            int amountOfJoints = Integer.valueOf(br.readLine());
            for(int j = 0; j < amountOfJoints; j++) {
                drawing.getSkeleton().addJoint(0, 0);
            }
            int amountOfAnimations = Integer.valueOf(br.readLine());
            int skeletonScale = Integer.valueOf(br.readLine());
            drawing.getSkeleton().setScale(skeletonScale);
            for(int j = 0; j < amountOfAnimations; j++) {
                String animationName = br.readLine();
                System.out.println(animationName);
                int amountOfFrames = Integer.valueOf(br.readLine());
                int[][][] animation = new int[amountOfFrames][amountOfJoints][2];
                
                
                for(int k = 0; k < amountOfFrames; k++) {
                    int pIS = 0;
                    line = br.readLine();
                    System.out.println(line);
                    String splitline[] = line.split(" ");
                    System.out.println(splitline[1]);
                    for(int l = 0; l < amountOfJoints; l++) {
                        animation[k][l][0] = Integer.valueOf(splitline[pIS++]);
                        animation[k][l][1] = Integer.valueOf(splitline[pIS++]);
                        System.out.println(pIS);
                    }
                }
                drawing.getSkeleton().addNewAnimation(animation.clone(), animationName);
            }
            for(int j = 0; j < amountOfJoints; j++) {
                line = br.readLine();
                String splitline[] = line.split(" ");
                for(int k = 0; k < splitline.length; k++) {
                    drawing.getSkeleton().connectJoints(j, Integer.valueOf(splitline[k]));
                }
            }
            br.close();
        } catch (Exception ex) {
        }
    }
    
    private void initTools() {
        tools = new MenuPanel();
        tools.setLayout(null);
        tools.setBounds(0,0,200,650-28);
        tools.setBackground(Color.GRAY);
        add(tools);
        
        String[] skelNames = new String[skeletons.size()];
        for(int i = 0; i < skeletons.size(); i++) {
            skelNames[i] = skeletons.get(i);
        }
        skeletonNames = new JComboBox(skelNames);
        if(skeletons.size() > 3)
            skeletonNames.setMaximumRowCount(3);
        else
            skeletonNames.setMaximumRowCount(skeletons.size());
        skeletonNames.setBounds(15,70,170,30);
        skeletonNames.addActionListener(this);
        tools.add(skeletonNames);
        
        newSkel = new JButton("New body");
        newSkel.setBounds(15,20,170,30);
        newSkel.setToolTipText("Creates a new body for animations");
        newSkel.addActionListener(this);
        tools.add(newSkel);
        
        //loadSkel = new JButton("Load body");
        //loadSkel.setBounds(15,70,170,30);
        //loadSkel.setToolTipText("Loads an already created body");
        //loadSkel.addActionListener(this);
        //tools.add(loadSkel);
        
        saveSkel = new JButton("Save body");
        saveSkel.setBounds(15,120,170,30);
        saveSkel.setToolTipText("Saves current body with it's animations");
        saveSkel.addActionListener(this);
        tools.add(saveSkel);
        
        addJoint = new JButton("Add joint");
        addJoint.setBounds(15,170,170,30);
        addJoint.setToolTipText("Add or delete joints");
        addJoint.addActionListener(this);
        tools.add(addJoint);
        
        conJoints = new JButton("Connect joints");
        conJoints.setBounds(15,220,170,30);
        conJoints.setToolTipText("Connect joints");
        conJoints.addActionListener(this);
        tools.add(conJoints);
        
        editJoints = new JButton("Edit joints");
        editJoints.setBounds(15,270,170,30);
        editJoints.setToolTipText("Edit joint positions");
        editJoints.addActionListener(this);
        tools.add(editJoints);
        
        newFrame = new JButton("New frame");
        newFrame.setBounds(15,320,170,30);
        newFrame.setToolTipText("Create a new frame for the animation");
        newFrame.addActionListener(this);
        tools.add(newFrame);
        
        saveFrame = new JButton("Save frame");
        saveFrame.setBounds(15,370,170,30);
        saveFrame.setToolTipText("Save current joint positions for this frame");
        saveFrame.addActionListener(this);
        tools.add(saveFrame);
        
        newAni = new JButton("New animation");
        newAni.setBounds(15,420,170,30);
        newAni.setToolTipText("Creates a new animation");
        newAni.addActionListener(this);
        tools.add(newAni);
        
        saveAni = new JButton("Save animation");
        saveAni.setBounds(15,470,170,30);
        saveAni.setToolTipText("Save the frames as an animation");
        saveAni.addActionListener(this);
        tools.add(saveAni);
        
        loadAni = new JButton("Load animation");
        loadAni.setBounds(15,520,170,30);
        loadAni.setToolTipText("Loads an animation");
        loadAni.addActionListener(this);
        tools.add(loadAni);
        
        playAni = new JButton("Play animation");
        playAni.setBounds(15,570,170,30);
        playAni.setToolTipText("Plays the animation in a seperate window");
        playAni.addActionListener(this);
        tools.add(playAni);
    }
    
    private void initDraw() {
        drawing = new DrawPanel();
        drawing.setLayout(null);
        drawing.setBounds(200,200,500,450-28);
        drawing.setBackground(Color.GRAY);
        add(drawing);
    }
    
    private void initInfo() {
        information = new MenuPanel();
        information.setLayout(null);
        information.setBounds(200,0,500,200);
        information.setBackground(Color.GRAY);
        add(information);
        
        nameBody = new JLabel("Body name: " + drawing.getSkeleton().getName());
        nameBody.setBounds(15,10,175,30);
        information.add(nameBody);
        nameForBody = new JTextField();
        nameForBody.addActionListener(this);
        nameForBody.setBounds(200,14,150,25);
        information.add(nameForBody);
        nameAni = new JLabel("Ani.Name: ");
        nameAni.setBounds(15,40,75,30);
        information.add(nameAni);
        nameForAni = new JTextField();
        nameForAni.setBounds(80,43,150,25);
        information.add(nameForAni);
        amountFrames = new JLabel("Amount of frames: " + drawing.getAmountOfFrames());
        amountFrames.setBounds(15,75,150,30);
        information.add(amountFrames);
        
        
        
        frames = new JSlider();
        frames.setMaximum(drawing.getAmountOfFrames()-1);
        frames.setMinimum(0);
        frames.setMinorTickSpacing(1);
        frames.setMajorTickSpacing(5);
        frames.setPaintTicks(true);
        frames.setValue(0);
        frames.setBounds(150,110,150,30);
        information.add(frames);
        frames.addChangeListener(this);
        currentFrame = new JLabel("Selected frame: " + (frames.getValue()-1));
        currentFrame.setBounds(15,110,150,30);
        information.add(currentFrame);
        
        scaleSlider = new JSlider();
        scaleSlider.setMaximum(30);
        scaleSlider.setMinimum(0);
        scaleSlider.setMinorTickSpacing(1);
        scaleSlider.setMajorTickSpacing(5);
        scaleSlider.setBounds(100, 145, 150,30);
        scaleSlider.setPaintTicks(true);
        scaleSlider.setValue(10);
        information.add(scaleSlider);
        scaleSlider.addChangeListener(this);
        scaleLabel = new JLabel("Scale: " + scaleSlider.getValue());
        scaleLabel.setBounds(15,145,75,30);
        information.add(scaleLabel);
        
        aniSlider = new JSlider();
        aniSlider.setMaximum(drawing.getSkeleton().getAmountOfAnimations());
        aniSlider.setMinimum(0);
        aniSlider.setMinorTickSpacing(1);
        aniSlider.setMajorTickSpacing(5);
        aniSlider.setValue(0);
        aniSlider.addChangeListener(this);
        aniSlider.setBounds(310,75,150,30);
        information.add(aniSlider);
        aniLabel = new JLabel("Animation slider");
        aniLabel.setBounds(340,50, 150,30);
        information.add(aniLabel);
        
    }
    
    private void updateComponents() {
        nameForAni.setText(drawing.getSkeleton().getAnimationName(aniSlider.getValue()));
        frames.setMaximum(drawing.getSkeleton().getFramesForAnimation(aniSlider.getValue()).length);
        frames.setValue(0);
        currentFrame.setText("Selected frame: " + frames.getValue());
        revalidate();
        repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == newSkel) {
            drawing.newBody();
        }
        else if(ae.getSource() == saveSkel) {
            drawing.saveBody();
        }
        else if(ae.getSource() == addJoint) {
            drawing.deactiveAll();
            drawing.addJointsActive();
        }
        else if(ae.getSource() == conJoints) {
            JointConnector j = new JointConnector(drawing.getSkeleton());
        }
        else if(ae.getSource() == editJoints) {
            drawing.deactiveAll();
            drawing.editJointsActive();
        }
        else if(ae.getSource() == newAni) {
            drawing.newAnimation();
            frames.setMaximum(drawing.getAmountOfFrames()-1);
            frames.setValue(0);
            revalidate();
            repaint();
        }
        else if(ae.getSource() == saveAni) {
            drawing.saveAnimation(nameForAni.getText());
        }
        else if(ae.getSource() == playAni) {
            
        }
        else if(ae.getSource() == saveFrame) {
            drawing.saveFrame();
        }
        else if(ae.getSource() == newFrame) {
            drawing.newFrame();
            frames.setMaximum(drawing.getAmountOfFrames()-1);
            frames.setValue(drawing.getAmountOfFrames()-1);
            amountFrames.setText("Amount of frames: " + drawing.getAmountOfFrames());
            revalidate();
            repaint();
        }
        else if(ae.getSource() == nameForBody) {
            drawing.getSkeleton().setName(nameForBody.getText());
            nameBody.setText("Body name: " + drawing.getSkeleton().getName());
            revalidate();
            repaint();
        }
        else if(ae.getSource() == skeletonNames) {
            loadSkeleton(skeletonNames.getSelectedIndex());
            drawing.loadAnimation(0);
            drawing.repaint();
            remove(information);
            information = null;
            initInfo();
            information.revalidate();
            information.repaint();
        }
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        if(ce.getSource() == frames) {
            currentFrame.setText("Selected frame: " + frames.getValue());
            drawing.setFrame(frames.getValue());
            revalidate();
            repaint();
        }
        else if(ce.getSource() == scaleSlider) {
            scaleLabel.setText("Scale: " + scaleSlider.getValue());
            drawing.setScale(scaleSlider.getValue());
            revalidate();
            repaint();
        }
        else if(ce.getSource() == aniSlider) {
            drawing.loadAnimation(aniSlider.getValue());
            updateComponents();
        }
    }
}
class MenuPanel extends JPanel {
    
    public MenuPanel() {
        
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

class DrawPanel extends JPanel implements Runnable {
    
    private Thread runner;
    private boolean addJoints, editJoints, pressed;
    private Body currentWork;
    private Joint pointer;
    private int[][][] animation;
    private int currentFrame=0, amountOfFrames=1;
    
    public DrawPanel() {
        
        currentWork = new Body();
        addMouseListener(l);
        addMouseMotionListener(l);
        addMouseWheelListener(l);
        runner = new Thread(this);
        runner.start();
        animation = new int[20][20][2]; //Frame, joint, vector position
    }
    
    public void saveFrame() {
        System.out.println(currentFrame);
        for(int i=0; i<currentWork.getAmountOfJoints(); i++) {
            animation[currentFrame][i][0] = currentWork.getJoint(i).getX();
            animation[currentFrame][i][1] = currentWork.getJoint(i).getY();
        }
    }
    
    public void newAnimation() {
        amountOfFrames = 1;
        animation = new int[20][20][2];
            for(int i = 0; i < currentWork.getAmountOfJoints(); i++) {
                animation[0][i][0] = currentWork.getFramesForAnimation(0)[0][i][0];
                animation[0][i][1] = currentWork.getFramesForAnimation(0)[0][i][1];
            }
        setFrame(0);
    }
    
    public void loadAnimation(int i) {
        animation = currentWork.getFramesForAnimation(i);
        amountOfFrames = currentWork.getFramesForAnimation(i).length;
        setFrame(0);
    }
    
    public void setAnimation(int i) {
        
    }
    
    public void saveBody() {
        FileWriter fileWriter = null;
        try {
            String charPath = "data/characters/charids.inf";
            fileWriter = new FileWriter(charPath, true);

        } catch (IOException ex) {
            
        }

        PrintWriter outputFile = new PrintWriter(fileWriter);
        outputFile.print(currentWork.getName());
        outputFile.println();
        outputFile.close();

        boolean dirFlag = false;

        // create File object
        File stockDir = new File("data/characters/" + currentWork.getName());

        try {
            dirFlag = stockDir.mkdir();
        } catch (SecurityException Se) {
            System.out.println("Error while creating directory in Java:" + Se);
        }

        if (dirFlag) {
            System.out.println("Directory created successfully");
        } else {
            System.out.println("Directory was not created successfully");
        }




        boolean flag = false;

        File stockFile = new File("data/characters/" + currentWork.getName() + "/data.inf");

        try {
            flag = stockFile.createNewFile();
        } catch (IOException ioe) {
            System.out.println("Error while Creating File in Java" + ioe);
        }


        try {
            String charPath = "data/characters/" + currentWork.getName() + "/data.inf";
            fileWriter = new FileWriter(charPath, false);

        } catch (IOException ex) {
            
        }
        outputFile = new PrintWriter(fileWriter);

        outputFile.println(currentWork.getName());
        outputFile.println(currentWork.getAmountOfJoints());
        outputFile.println(currentWork.getAmountOfAnimations());
        outputFile.println(currentWork.getScale());
        
        for (int i = 0; i < currentWork.getAmountOfAnimations(); i++) {
            outputFile.println(currentWork.getAnimationName(i));
            outputFile.println(currentWork.getFramesForAnimation(i).length);
            for (int j = 0; j < currentWork.getFramesForAnimation(i).length; j++) {
                for (int k = 0; k < currentWork.getAmountOfJoints(); k++) {
                    outputFile.print(currentWork.getFramesForAnimation(i)[j][k][0] + " ");
                    outputFile.print(currentWork.getFramesForAnimation(i)[j][k][1] + " ");
                }
                outputFile.println();
            }
        }
        
        for(int i = 0; i < currentWork.getAmountOfJoints(); i++) {
            outputFile.print(currentWork.getJoint(i).getId() + " ");
            for(int j = 0; j < currentWork.getJoint(i).getConnections().size(); j++) {
                outputFile.print(currentWork.getJoint(i).getConnections().get(j).getId() + " ");
            }
            outputFile.println();
        }
        outputFile.println();
        outputFile.close();
    }
    
    public void setScale(int scaleValue) {
        currentWork.setScale(scaleValue);
    }
    
    public int getAmountOfFrames() {
        return amountOfFrames;
    }
    
    public int getCurrentFrame() {
        return currentFrame;
    }
    
    public void setFrame(int frame) {
        currentFrame = frame;
        for(int i = 0; i<currentWork.getAmountOfJoints(); i++) {
            currentWork.getJoint(i).moveTo(animation[currentFrame][i][0], 
                                            animation[currentFrame][i][1]);
        }
        
    }
    
    public void saveAnimation(String name) {
        int[][][] newAnimation = new int[amountOfFrames][currentWork.getAmountOfJoints()][2];
        for(int i = 0; i < amountOfFrames; i++) {
            for(int j = 0; j < currentWork.getAmountOfJoints(); j++) {
                newAnimation[i][j][0] = animation[i][j][0];
                newAnimation[i][j][1] = animation[i][j][1];
            }
        }
        currentWork.addNewAnimation(newAnimation, name);
    }
    
    public void newFrame() {
        if(amountOfFrames<20) {
            amountOfFrames++;
            for(int i = 0; i<currentWork.getAmountOfJoints(); i++) {
                animation[currentFrame+1][i][0] = animation[currentFrame][i][0];
                animation[currentFrame+1][i][1] = animation[currentFrame][i][1];
            }
            setFrame(amountOfFrames);
        }   
    }
    
    public void addJointsActive() {
        addJoints = true;
    }
    
    public void editJointsActive() {
        editJoints = true;
    }
    
    public Skeleton getSkeleton() {
        return currentWork;
    }
    
    public void newBody() {
        currentWork = new Skeleton("Temp");
    }
    
    public void deactiveAll() {
        addJoints = false;
        editJoints = false;
    }
    
    MouseAdapter l = new MouseAdapter() {
        
        @Override
        public void mouseClicked(MouseEvent me) {
            System.out.println();
            if(addJoints) {
                currentWork.addJoint(me.getX(), me.getY());
                System.out.println("Adding joint at " + me.getX() + ":" + me.getY());
            }
            
        }
        
        @Override
        public void mousePressed(MouseEvent me) {
            if(editJoints) {
                pointer = currentWork.getJointAtPosition(me.getX(), me.getY());
                pressed = true;
            }
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            if(editJoints && pressed) {
                pointer.moveTo(me.getX(), me.getY());
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            if(editJoints) {
                pressed = false;
                pointer = null;
            }
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            
        }
    };
    
    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        setBackground(Color.white);
        super.paintComponent(g);
        currentWork.draw(g);
        Color[] colors = new Color[10];
        colors[0] = colors[1] = colors[8] = colors[9] = Color.BLACK;
        colors[2] = colors[3] = colors[6] = colors[7] = Color.DARK_GRAY;
        colors[4] = colors[5] = Color.GRAY;
        for (int i = 0; i < 10; i++) {
            g.setColor(colors[i]);
            g.drawRoundRect(i, i, getWidth() - 2 * i - 1, getHeight() - 2 * i - 1, 10, 10);
        }
    }

    @Override
    public void run() {
        while(true) {
            
            try {
                Thread.sleep(16);
            } catch (InterruptedException ex) {
                
            }
            repaint();
        }
    }
    
    
    
}