/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package textcomparer;

/**
 *
 * @author jan
 */
public class Match implements Comparable {

    private int srcStart, srcEnd, dstStart, dstEnd;
    private String source, dest;

    public int compareTo(Object t)
    {
        Match m = (Match) t;
        
        return this.srcStart - m.srcStart;
    }

    public Match(int srcStart, int srcEnd, int dstStart, int dstEnd, String source, String dest)
    {
        this.srcStart = srcStart;
        this.srcEnd = srcEnd;
        this.dstStart = dstStart;
        this.dstEnd = dstEnd;
        this.source = source;
        this.dest = dest;
    }

    public String getDest()
    {
        return dest;
    }

    public int getDstEnd()
    {
        return dstEnd;
    }

    public int getDstStart()
    {
        return dstStart;
    }

    public String getSource()
    {
        return source;
    }

    public int getSrcEnd()
    {
        return srcEnd;
    }

    public int getSrcStart()
    {
        return srcStart;
    }



}
