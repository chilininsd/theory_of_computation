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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
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

    /**
     * The main workhorse of the project, takes in the regex and the testcases, creates a corresponding
     * regex object and machine with which to match
     * @param reg - the string regex
     * @param testCases - the test cases
     * @return list of responses to test cases
     */
    public static List<String> match(String reg, List<String> testCases)
    {
        FiniteAutomaton fa = new FiniteAutomaton();
        Regex parsedRegex = RegexParser.parse(reg);
        Graph graph = fa.constructMachine(parsedRegex.getContents(), false);
        List<String> returnVals = new LinkedList<>();
        for (String string : testCases)
        {
            if (graph.match(string))
            {
                returnVals.add("yes");
            }
            else
            {
                returnVals.add("no");
            }
        }
        return returnVals;
    }

    public static void main(String[] args) throws MalformedRegexException
    {
        Scanner scanny = new Scanner(System.in);

        String regex = scanny.nextLine();
        List<String> testCases = new ArrayList<>();
        while (scanny.hasNextLine())
        {
            testCases.add(scanny.nextLine());
        }
        
        checkRegex(regex);
        
        List<String> answers = FiniteAutomaton.match(regex, testCases);
        for (String answer : answers)
        {
            System.out.println(answer);
        }
    }

    /**
     * Sanitization function...found two errors with it, not bad :-\
     * @param potentiallyGoodRegex
     * @throws MalformedRegexException 
     */
    private static void checkRegex(String potentiallyGoodRegex) throws MalformedRegexException
    {
        Set<String> validSymbols = new HashSet<>();
        String[] symbols = new String[]
        {
            "a", "b", "m", "p", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "\"", "\'", "/", "<", ">", "=", " ", "~", "|", "(", ")", "*"
        };
        validSymbols.addAll(Arrays.asList(symbols));

        for (int i = 0; i < potentiallyGoodRegex.length(); i++)
        {
            if (!validSymbols.contains(potentiallyGoodRegex.charAt(i) + ""))
            {
                throw new MalformedRegexException(potentiallyGoodRegex.charAt(i) + "");
            }
        }
    }

    /**
     * Recursively builds the NFA machine formed by the regex
     * @param nodes - could be the initial regex or could be the contents of a parenthesized regex within the main one
     * @param isStar - necessary to know whether to perform additional operations once the machine is constructed or not
     * @return - the machine
     */
    private Graph constructMachine(List<RegexNode> nodes, boolean isStar)
    {
        Graph machine = new Graph();
        for (RegexNode regex : nodes)
        {
            if (regex instanceof SimpleNode)
            {
                machine.add(regex);
            }
            else if (regex instanceof ParenNode)
            {
                ParenNode paren = (ParenNode) regex;
                machine.addGraph(constructMachine(paren.getContents(), regex instanceof StarNode));
            }

        }
        machine.setIsStar(isStar);
        return machine.starifyMachine();
    }
}
