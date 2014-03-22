/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;
import nodes.RegexNode;

/**
 * A regex object, containing a list of regex nodes
 * @author reuben
 */
public class Regex {
    
    private List<RegexNode> contents;

    public Regex(List<RegexNode> depth0Nodes)
    {
        this.contents = depth0Nodes;
    }
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        for (RegexNode regexNode : contents)
        {
            s.append(regexNode.toString());
        }
        return s.toString();
    }

    public List<RegexNode> getContents()
    {
        return contents;
    }
}
