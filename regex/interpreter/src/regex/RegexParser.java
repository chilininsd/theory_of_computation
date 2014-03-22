/*
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
    
    /**
     * Parses the given string into a regex object.
     * 
     * If I would have done some more research on a regex parsing procedure this class could be about 150 lines less but...
     * it was kind of fun to figure it out without any instruction
     * @param regex
     * @return 
     */
    public static Regex parse(String regex)
    {
        RegexParser r = new RegexParser();
        
        //necessary for strings with escaped characters in them
        regex = regex.replace("\\", "");
        
        Map<Integer, List<Symbol>> table = r.generateParseTable(regex);
        List<RegexNode> nodes = r.generateNodes(table);
        List<RegexNode> depth0Nodes = r.fillNodes(nodes, regex.length());
        Collections.sort(depth0Nodes);
        return new Regex(depth0Nodes);
    }

    /**
     * Provides the program with an understanding of the "depths" of the nesting of the regex
     * @param regex - the regex to generate table for
     * @return - a table of depth to list of symbols at that depth
     */
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
        
    /**
     * utility function that does what it says
     * @param stringToPeekAt
     * @param index
     * @return 
     */
    private char peekAtNextChar(String stringToPeekAt, int index)
    {
        int next = ++index;
        if (next < stringToPeekAt.length()) return stringToPeekAt.charAt(next);
        else return '\0';
    }
    
    /**
     * Performs basic null checks and additions in an abstracted manner to avoid distractions from the main procedure
     * @param map
     * @param t
     * @param depth
     * @return 
     */
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
    
    /**
     * Turns the parse table into a list of regex nodes
     * @param table
     * @return 
     */
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
        
    /**
     * Does what it says
     * @param list
     * @param i
     * @return 
     */
    private Symbol peekAtNextSymbol(List<Symbol> list, int i)
    {
        int next = ++i;
        if (next < list.size()) return list.get(next);
        else return null;
    }

    /**
     * Reconstructs the "flat" structure of the text regex with identifiable objects
     * @param nodes
     * @param lengthOfRegex
     * @return 
     */
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
