/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nodes;

import java.util.List;
import model.Symbol;

/**
 *
 * @author reuben
 */
public class ParenNode extends RegexNode {
    
    private Symbol open;
    private Symbol close;
    private int depth;
    protected List<RegexNode> contents;

    public ParenNode(Symbol open, Symbol close, int depth)
    {
        this.open = open;
        this.close = close;
        this.depth = depth;
    }
    
    public int getBeginIndex()
    {
        return open.getIndex();
    }
    
    public int getEndIndex()
    {
        return close.getIndex();
    }

    public List<RegexNode> getContents()
    {
        return contents;
    }

    public void setContents(List<RegexNode> contents)
    {
        this.contents = contents;
    }

    @Override
    public int getIndex()
    {
        return open.getIndex();
    }

    @Override
    public int getDepth()
    {
        return depth;
    }
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append("(");
        if (contents != null)
            for (RegexNode regexNode : contents)
            {
                s.append(regexNode.toString());
            }
        s.append(")");
        return s.toString();
    }

    @Override
    public int compareTo(Object o)
    {
        RegexNode r = (RegexNode)o;
        return -Integer.compare(r.getIndex(), this.getIndex());
    }
}
