/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nodes;

import model.Symbol;

/**
 * A node for star regexes
 * @author reuben
 */
public class StarNode extends ParenNode {
    
    private Symbol open;
    private Symbol close;
    private Symbol star;
    private int depth;

    public StarNode(Symbol open, Symbol close, Symbol star, int depth)
    {
        super(open, close, depth);
        this.star = star;
    }
    
    @Override
    public int getEndIndex()
    {
        return star.getIndex();
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
        s.append(")").append("*");
        return s.toString();
    }
}
