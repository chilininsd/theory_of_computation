/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nfa;

import java.util.HashMap;
import java.util.Map;
import model.Symbol;
import nodes.OrNode;
import nodes.RegexNode;
import nodes.SimpleNode;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import regex.FiniteAutomaton;
import regex.RegexParser;

/**
 *
 * @author reuben
 */
public class GraphTest {

    public GraphTest()
    {
    }

    /**
     * Test of add method, of class Machine.
     */
    @Test
    public void testAddSimpleConcatAndOrs()
    {
        RegexNode regex1 = new SimpleNode(new Symbol('A', 0), 0);
        RegexNode regex2 = new OrNode(new Symbol('|', 0), 0);
        RegexNode regex3 = new SimpleNode(new Symbol('b', 0), 0);
        RegexNode regex4 = new OrNode(new Symbol('|', 0), 0);
        RegexNode regex5 = new SimpleNode(new Symbol('a', 0), 0);
        Graph instance = new Graph();
        instance.add(regex1);
        instance.add(regex2);
        instance.add(regex3);
        instance.add(regex4);
        instance.add(regex5);

        Map<String, NfaNode> transitions = instance.getStart().getTransitions();
        Map<String, NfaNode> expected = new HashMap<>();
        NfaNode s1 = new NfaNode("A");
        NfaNode s2 = new NfaNode("b");
        NfaNode s3 = new NfaNode("a");
        expected.put("A", s1);
        expected.put("b", s2);
        expected.put("a", s3);

        assertEquals(expected, transitions);
    }

    @Test
    public void testAddConcatAndOrs()
    {
        RegexNode regex1 = createRegexNode("D");
        RegexNode regex2 = createRegexNode("b");
        RegexNode regex3 = createRegexNode("|");
        RegexNode regex4 = createRegexNode("b");
        RegexNode regex5 = createRegexNode("K");
        RegexNode regex6 = createRegexNode("b");
        Graph instance = createGraph(regex1, regex2, regex3, regex4, regex5, regex6);

        NfaNode start = instance.getStart();
        NfaNode expected = new NfaNode("start");

        NfaNode D = new NfaNode("D");
        NfaNode b1 = new NfaNode("b");
        NfaNode b2 = new NfaNode("b");
        NfaNode K = new NfaNode("K");
        NfaNode b3 = new NfaNode("b");

        D.addTransition("b", b1);
        b2.addTransition("K", K);
        
        K.addTransition("b", b3);
        
        expected.addTransition("D", D);
        expected.addTransition("b", b2);

        assertEquals(start, expected);
    }
    
    @Test
    public void testCurrentSimpleAddGraphAddGraph()
    {
        Graph single = FiniteAutomaton.constructMachine(RegexParser.parse("1").getContents());
        single.addGraph(FiniteAutomaton.constructMachine(RegexParser.parse("0|1|2").getContents()));
        single.addGraph(FiniteAutomaton.constructMachine(RegexParser.parse("345").getContents()));
    }
    
    @Test
    public void testCurrentGraphAddSimple()
    {
        Graph graph = FiniteAutomaton.constructMachine(RegexParser.parse("(0|1|2)3").getContents());
    }
    
    @Test
    public void testCurrentGraphOrSimple()
    {
        Graph graph = FiniteAutomaton.constructMachine(RegexParser.parse("(0|1|2)|3").getContents());
    }
    
    @Test
    public void testCurrentGraphOrGraph()
    {
        Graph graph = FiniteAutomaton.constructMachine(RegexParser.parse("(0|1|2)|(3|4|5)").getContents());
    }
    
    @Test
    public void testCurrentSimpleOrGraph()
    {
        Graph graph = FiniteAutomaton.constructMachine(RegexParser.parse("1|(3|4|5)").getContents());
    }
    
    public Graph createGraph(RegexNode ... n)
    {
        Graph g = new Graph();
        for (RegexNode reg : n)
        {
            g.add(reg);
        }
        return g;
    }
    
    public RegexNode createRegexNode(String name)
    {
        if (name.equals("|"))
            return new OrNode(new Symbol(name.charAt(0), 0), 0);
        else
            return new SimpleNode(new Symbol(name.charAt(0), 0), 0);
    }
}
