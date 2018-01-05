/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import characters.*;
/**
 *
 * @author Kevin
 */

public class JointConnector extends JFrame implements ActionListener{
    
    private JTextField firstJoint, secondJoint;
    private JButton connect, cancel;
    private JPanel upperPanel,lowerPanel;
    private Body current;
    
    public JointConnector(Body currentWork) {
        setLayout(new GridLayout(2,1));
        upperPanel = new JPanel();
        lowerPanel = new JPanel();
        add(upperPanel);
        add(lowerPanel);
        current = currentWork;
        firstJoint = new JTextField();
        secondJoint = new JTextField();
        connect = new JButton("Connect");
        connect.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        upperPanel.setLayout(new GridLayout(1,2));
        upperPanel.add(firstJoint);
        upperPanel.add(secondJoint);
        lowerPanel.setLayout(new GridLayout(1,2));
        lowerPanel.add(connect);
        lowerPanel.add(cancel);
        setSize(200,150);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == connect) {
            current.connectTo(Integer.valueOf(firstJoint.getText()),
                                      Integer.valueOf(secondJoint.getText()));
            System.out.println("Added connection between" + firstJoint.getText() +":"+
                                                            secondJoint.getText());
            dispose();
        }
        else if(ae.getSource() == cancel) {
            dispose();
        }
    }
}
