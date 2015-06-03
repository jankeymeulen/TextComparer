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
public class DoExitAction extends AbstractAction
{

    public DoExitAction()
    {
        super("Exit");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Q"));
    }

    public void actionPerformed(ActionEvent arg0)
    {
        System.exit(0);
    }
}
