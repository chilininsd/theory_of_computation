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
    
    public static Graph toMachine(String reg)
    {
        FiniteAutomaton fa = new FiniteAutomaton();
        Regex r = RegexParser.parse(reg);
        return fa.constructMachine(r.getContents());
    }
    
    public static void main(String[] args) throws MalformedRegexException
    {
        String reg = "((0|1|~)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9|~)|2(0|1|2|3|4)(0|1|2|3|4|5|6|7|8|9)|25(0|1|2|3|4|5)).";
        checkRegex(reg);
        Graph m = FiniteAutomaton.toMachine(reg);
        List<String> testCases = Arrays.asList(new String[]{"1.", "255.", "127.", "10.", "a.", "990.", "~"});
        for (String string : testCases)
        {
            if (m.match(string))
                System.out.println("yes");
            else
                System.out.println("no");
        }
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
    
    private Graph constructMachine(List<RegexNode> nodes)
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
                m.addGraph(constructMachine(p.getContents()));
                m.setIsStar(regex instanceof StarNode);
            }
        }
        return m.finalizeGraph();
    }
}
