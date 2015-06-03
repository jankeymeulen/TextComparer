/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 *
 * @author jan
 */
public class ResetRefineAction extends AbstractAction
{

    private Model model;

    ResetRefineAction(Model model)
    {
        super("Reset Refinement");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_T);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Z"));
        this.model = model;
    }

    public void actionPerformed(ActionEvent e)
    {
        model.resetCompare();
    }
}
