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
public class SaveMatchAction extends AbstractAction
{

    private final Model model;

    public SaveMatchAction(Model model)
    {
        super("Save Matches");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_W);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl W"));
        this.model = model;
    }

    public void actionPerformed(ActionEvent arg0)
    {
        JFileChooser c = new JFileChooser(new File("."));

        if(c.showSaveDialog(model.getMainWindow()) == JFileChooser.APPROVE_OPTION)
        {
            model.saveMatches(c.getSelectedFile());
        }

    }
}
