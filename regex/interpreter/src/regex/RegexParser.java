/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import model.Regex;
import model.Symbol;
import nodes.OrNode;
import nodes.ParenNode;
import nodes.RegexNode;
import nodes.SimpleNode;
import nodes.StarNode;

/**
 *
 * @author reuben
 */
public class RegexParser {
    
    public static enum Operator { CONCAT, OR, STAR }
    
    public static Regex parse(String regex)
    {
        RegexParser r = new RegexParser();
        Map<Integer, List<Symbol>> table = r.generateParseTable(regex);
        List<RegexNode> nodes = r.generateNodes(table);
        List<RegexNode> depth0Nodes = r.fillNodes(nodes, regex.length());
        Collections.sort(depth0Nodes);
        return new Regex(depth0Nodes);
    }

    private Map<Integer, List<Symbol>> generateParseTable(String regex)
    {
        Map<Integer, List<Symbol>> parseTable = new HashMap<>();

        int depth = 0;
        for (int i = 0; i < regex.length(); i++)
        {
            Symbol t = new Symbol(regex.charAt(i), i);
            parseTable = addToParseTable(parseTable, t, depth);
            if (t.getSymbol() == '(') ++depth;
            else if (peekAtNextChar(regex, i) == ')') --depth;
        }
        return parseTable;
    }
        
    private char peekAtNextChar(String s, int i)
    {
        int next = ++i;
        if (next < s.length()) return s.charAt(next);
        else return '\0';
    }
    
    private Map<Integer, List<Symbol>> addToParseTable(Map<Integer, List<Symbol>> map, Symbol t, int depth)
    {
        List<Symbol> list = map.get(depth);
        if (list == null)
        {
            List<Symbol> newList = new ArrayList<>();
            newList.add(t);
            map.put(depth, newList);
        }
        else
        {
            list.add(t);
            map.put(depth, list);
        }
        return map;
    }
    
    private List<RegexNode> generateNodes(Map<Integer, List<Symbol>> table)
    {
        List<RegexNode> singles = new ArrayList<>();
        List<RegexNode> parens = new ArrayList<>();
        for (Entry<Integer, List<Symbol>> entry : table.entrySet())
        {
            List<Symbol> symbols = entry.getValue();
            
            //endeavoring to match nodes that have open and close parens
            int open = -1;
            for (int i = 0; i < symbols.size(); ++i)
            {
                Symbol current = symbols.get(i);
                switch (current.getSymbol())
                {
                    case '(':
                        open = i;
                        break;
                    case ')':
                        Symbol nextSymbol = peekAtNextSymbol(symbols, i);
                        if (nextSymbol != null && nextSymbol.getSymbol() == '*')
                        {
                            ++i;
                            parens.add(new StarNode(symbols.get(open), current, nextSymbol, entry.getKey()));
                        }
                        else
                            parens.add(new ParenNode(symbols.get(open), current, entry.getKey()));
                        break;
                    case '*':
                        //should have already been handled
                        break;
                    case '|':
                        singles.add(new OrNode(current, entry.getKey()));
                        break;
                    default:
                        singles.add(new SimpleNode(current, entry.getKey()));
                        break;
                }
            }
        }
        //for ordering purposes
        parens.addAll(singles);
        return parens;
    }
        
    private Symbol peekAtNextSymbol(List<Symbol> list, int i)
    {
        int next = ++i;
        if (next < list.size()) return list.get(next);
        else return null;
    }

    private List<RegexNode> fillNodes(List<RegexNode> nodes, int lengthOfRegex)
    {
        RegexNode[] orderedNodes = new RegexNode[lengthOfRegex];
        
        for (RegexNode regexNode : nodes)
        {
            orderedNodes[regexNode.getIndex()] = regexNode;
        }
        
        List<RegexNode> depth0 = new ArrayList<>();
        for (RegexNode regexNode : nodes)
        {
            if (regexNode instanceof ParenNode && regexNode.getDepth() == 0)
            {
                ParenNode p = (ParenNode)regexNode;
                List<RegexNode> contents = fill(orderedNodes, p.getBeginIndex(), p.getEndIndex());
                p.setContents(contents);
                depth0.add(p);
            }
            else if (regexNode != null && regexNode.getDepth() == 0)
            {
                depth0.add(regexNode);
            }
        }
        return depth0;
    }

    /**
     * this could be the most beautiful function i've ever written. combining iterative recursion, inheritance/polymorphism,
     * 0(1) extraction with a raw array and it worked <strike>flawlessly</strike> almost flawlessly the first time :-)...
     * yeah, i spent about 5 minutes reveling in the moment...
     */
    private List<RegexNode> fill(RegexNode[] nodes, int beginIndex, int endIndex)
    {
        int begin = ++beginIndex;
        int end = --endIndex;
        
        List<RegexNode> contents = new ArrayList<>();
        for (int i = begin; i <= end; i++)
        {
            RegexNode current = nodes[i];
            if (current instanceof SimpleNode) 
            {
                contents.add(current);
            }
            else if (current instanceof ParenNode)
            {
                ParenNode p = (ParenNode)current;
                p.setContents(fill(nodes, p.getBeginIndex(), p.getEndIndex()));
                contents.add(p);
                i = p.getEndIndex();
            }
        }
        return contents;
    }
}
