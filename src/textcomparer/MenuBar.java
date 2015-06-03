/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package textcomparer;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 *
 * @author jan
 */
public class MenuBar extends JMenuBar {

    private final Model model;

    public MenuBar(Model model)
    {
        this.model = model;

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        fileMenu.add(new OpenSrcAction(model));
        fileMenu.add(new OpenDstAction(model));
        fileMenu.add(new SaveMatchAction(model));
        fileMenu.add(new DoExitAction());
        this.add(fileMenu);

        JMenu compareMenu = new JMenu("Compare");
        compareMenu.setMnemonic('C');
        compareMenu.add(new SetParametersAction(model));
        compareMenu.add(new DoNewCompareAction(model));
        compareMenu.add(new RefineCompareAction(model));
        compareMenu.add(new ResetRefineAction(model));

        this.add(compareMenu);

        this.add(Box.createHorizontalGlue());
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        helpMenu.add(new ShowAboutAction(model));
        this.add(helpMenu);

    }

    

}
