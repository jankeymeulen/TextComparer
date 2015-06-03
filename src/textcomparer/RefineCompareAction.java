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
class RefineCompareAction extends AbstractAction
{
    private final Model model;

    public RefineCompareAction(Model model)
    {
        super("Refine comparison");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_R);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl R"));
        this.model = model;
    }

    public void actionPerformed(ActionEvent e)
    {
        model.refineCompare();
    }
}
