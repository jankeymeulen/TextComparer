/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author jan
 */
public class Main
{

    public static void main(String[] args) throws IOException
    {

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Model model = new Model();
        MainWindow frame = model.createMainWindow();

        if (args.length != 0)
        {
            if (args.length == 7)
            {
                model.setSrcFile(new File(args[0]));
                model.setDstFile(new File(args[1]));
                model.setMaxDist(new Integer(args[2]));
                model.setMinLength(new Integer(args[3]));
                model.setMaxWindow(new Integer(args[4]));
                model.setMaxSkip(new Integer(args[5]));
                model.setMinWords(new Integer(args[6]));
            }
            else
            {
                System.err.println("Usage: java -jar TextComparer.jar srcFile DstFile"
                        + "maxDist minLength maxWindow maxSkip minWords");
            }
        }

        frame.start();

    }
}


