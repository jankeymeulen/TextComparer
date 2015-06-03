/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textcomparer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;

/**
 *
 * @author jan
 */
public class TextIO
{

    private ArrayList<String> text;
    private boolean normalize;

    public ArrayList<String> getText()
    {
        return text;
    }

    public void openFile(File file) throws FileNotFoundException, IOException
    {
        text = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

        String line = null;
        while ((line = reader.readLine()) != null)
        {
            for (String word : line.split(" "))
            {
                if (normalize)
                {
                    word = Normalizer.normalize(word, Form.NFD);

                    StringBuffer w = new StringBuffer();

                    for (Character c : word.toCharArray())
                    {
                        if (Character.isLetterOrDigit(c))
                        {
                            w.append(c);
                        }
                    }

                    word = w.toString().toLowerCase();

                }

                if (!word.equals(""))
                {
                    text.add(word);
                }
            }
        }
    }

    public TextIO()
    {
        normalize = true;
    }

    public TextIO(boolean normalize)
    {
        this.normalize = normalize;
    }

    void saveFile(File saveFile, String data) throws UnsupportedEncodingException, FileNotFoundException, IOException
    {
        BufferedWriter out;
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile), "UTF-8"));
        out.append(data);
        out.close();
    }
}
