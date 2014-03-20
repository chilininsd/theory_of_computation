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

import exceptions.MalformedRegexException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import model.Regex;
import nfa.Graph;
import nodes.ParenNode;
import nodes.RegexNode;
import nodes.SimpleNode;
import nodes.StarNode;

/**
 *
 * @author reuben
 */
public class FiniteAutomaton {
    
    public static List<String> match(String reg, List<String> testCases)
    {
        FiniteAutomaton fa = new FiniteAutomaton();
        Regex r = RegexParser.parse(reg);
        Graph g = fa.constructMachine(r.getContents(), false);
        List<String> returnVals = new LinkedList<>();
        for (String string : testCases)
        {
            if (g.match(string))
                returnVals.add("yes");
            else
                returnVals.add("no");
        }
        return returnVals;
    }
    
    public static void main(String[] args) throws MalformedRegexException
    {
        String reg = "<((\"(a|b|'|=| |/|>|<)*\")|('(a|b|\"|=| |/|<|>)*')|(a|b|=| |/))*>";
        checkRegex(reg);
        List<String> testCases = Arrays.asList(new String[]{"<>", "<b a='>'>", "<b/>", "<a b=\"ab'\" aba='/'>", "<a b=a<>", "<b aaa='>", "<a b='aba<' />"});
        
    }

    private static void checkRegex(String string) throws MalformedRegexException
    {
        Set<String> validSymbols = new HashSet<>();
        String[] symbols = new String[] {"a", "b", "m", "p", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "\"", "\'", "/", "<", ">", "=", " ", "~", "|", "(", ")", "*"};
        validSymbols.addAll(Arrays.asList(symbols));
        
        for (int i = 0; i < string.length(); i++)
        {
            if (!validSymbols.contains(string.charAt(i)+""))
            {
                throw new MalformedRegexException(string.charAt(i)+"");
            }
        }
    }
    
    private Graph constructMachine(List<RegexNode> nodes, boolean isStar)
    {
        Graph m = new Graph();
        for (RegexNode regex : nodes)
        {
            if (regex instanceof SimpleNode)
            {
                m.add(regex);
            }
            else if (regex instanceof ParenNode)
            {
                ParenNode p = (ParenNode)regex;
                m.addGraph(constructMachine(p.getContents(), regex instanceof StarNode));
            }
            
        }
        m.setIsStar(isStar);
        return m.finalizeGraph();
    }
}
