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
public class SetParametersAction extends AbstractAction
{
    private final Model model;

    SetParametersAction(Model model)
    {
        super("Set Parameters");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_P);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        this.model = model;
    }

    public void actionPerformed(ActionEvent e)
    {
        ParameterDialog dialog = new ParameterDialog(model);
        dialog.setVisible(true);
    }
}
