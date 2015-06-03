/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

/**
 *
 * @author jan
 */
public class OpenDstAction extends AbstractAction
{

    private final Model model;

    public OpenDstAction(Model model)
    {
        super("Open Destination File");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        this.model = model;
    }

    public void actionPerformed(ActionEvent arg0)
    {
        JFileChooser c = new JFileChooser(new File("."));

        if (c.showOpenDialog(model.getMainWindow()) == JFileChooser.APPROVE_OPTION)
        {
            model.setDstFile(c.getSelectedFile());
        }
    }
}
