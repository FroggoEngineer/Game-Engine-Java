/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
/**
 *
 * @author Kevin
 */
public class CreateBlock extends JFrame implements ActionListener {
    
    private JComboBox comboBreaker;
    private JPanel left, right, bottom;
    private JButton save, cancel;
    
    
    public CreateBlock() {
        setLayout(new GridLayout(1,2));
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
