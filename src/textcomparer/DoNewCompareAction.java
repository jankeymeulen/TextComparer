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
public class DoNewCompareAction extends AbstractAction
{

    private Model model;

    DoNewCompareAction(Model model)
    {
        super("Compare");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_C);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl C"));
        this.model = model;
    }

    public void actionPerformed(ActionEvent e)
    {
        model.doNewCompare();
    }
}
