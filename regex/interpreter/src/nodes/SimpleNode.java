/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nodes;

import model.Symbol;

/**
 * A base class for for all single characters that aren't involved with parenthesized regexes
 * @author reuben
 */
public class SimpleNode extends RegexNode {
    
    private Symbol simple;
    private int depth;

    public SimpleNode(Symbol simple, int depth)
    {
        this.simple = simple;
        this.depth = depth;
    }

    @Override
    public int getIndex()
    {
        return simple.getIndex();
    }

    @Override
    public int getDepth()
    {
        return depth;
    }
    
    @Override
    public String toString()
    {
        return simple.getSymbol()+ "";
    }

    @Override
    public int compareTo(Object o)
    {
        RegexNode r = (RegexNode)o;
        return -Integer.compare(r.getIndex(), this.getIndex());
    }
}
