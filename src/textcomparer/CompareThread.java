/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package textcomparer;

import java.util.ArrayList;

/**
 *
 * @author jan
 */
public class CompareThread extends Thread {

    private Comparison comparison;
    private ArrayList<Comparison> results;
    private Model model;

    public Comparison getComparison()
    {
        return comparison;
    }

    public ArrayList<Comparison> getResults()
    {
        return results;
    }

    @Override
    public void run()
    {
        results = comparison.findMatches(model.getMaxDist(),model.getMinLength(),
                model.getMaxWindow(),model.getMaxSkip(),model.getMinWords(),model.isAdaptiveSkips());
        model.setResults(results);
    }

    public CompareThread(Model model, Comparison comparison)
    {
        this.comparison = comparison;
        this.model = model;
    }



}
