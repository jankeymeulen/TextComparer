/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package textcomparer;

import java.util.TreeMap;

/**
 *
 * @author jan
 */
public class DstPane extends HighLightPane {

    public DstPane(Model model)
    {
        super(model);
    }

    @Override
    int getTextSize()
    {
        return model.getDstText().size();
    }

    @Override
    void setLink(int w, int c)
    {
        model.addDstLink(w, c);
    }

    @Override
    String getWord(int w)
    {
        return model.getDstText().get(w);
    }

    @Override
    TreeMap<Integer, Integer> getHighLights()
    {
        return model.getDstWordMatches();
    }

    @Override
    Integer getCharPos(int w)
    {
        return model.getDstChar(w);
    }

    @Override
    int getSelectionCharStart()
    {
        return model.getDstSelectionCharStart();
    }

    @Override
    int getSelectionCharEnd()
    {
        return model.getDstSelectionCharEnd();
    }

    @Override
    void wordClicked()
    {
        model.dstClicked(this.getSelectionStart());
    }

    @Override
    int getSelectionWordStart()
    {
        return model.getDstSelectionWordStart();
    }

    @Override
    int getSelectionWordEnd()
    {
        return model.getDstSelectionWordStart();
    }

}
