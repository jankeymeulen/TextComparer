/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 *
 * @author jan
 */
public class ShowAboutAction extends AbstractAction
{

    private final Model model;

    ShowAboutAction(Model model)
    {
        super("Quick Help");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_H);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl H"));
        this.model = model;
    }

    public void actionPerformed(ActionEvent e)
    {

        String textString =
                "Open a source file and a destination file using the\n"
                + "File menu. Fill in the wished parameters in the Compare\n"
                + "menu and start the process by choosing Compare. Afterwards\n"
                + "you can refine the results by setting new parameters and \n"
                + "selecting Refine Comparison, which will look for matches \n"
                + "in the already found results.";

        JOptionPane.showMessageDialog(model.getMainWindow(), textString, "Title",
                JOptionPane.INFORMATION_MESSAGE);

    }
}
