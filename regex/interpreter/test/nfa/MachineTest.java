/*
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

/**
 *
 * @author reuben
 */
public class MachineTest {
    
    public MachineTest()
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
        Machine instance = new Machine();
        instance.add(regex1);
        instance.add(regex2);
        instance.add(regex3);
        instance.add(regex4);
        instance.add(regex5);
        
        Map<String, State> transitions = instance.getStart().getTransitions();
        Map<String, State> expected = new HashMap<>();
        State s1 = new State("A");
        State s2 = new State("b");
        State s3 = new State("a");
        expected.put("A", s1);
        expected.put("b", s2);
        expected.put("a", s3);
        
        assertEquals(expected, transitions);
    }
    
    @Test
    public void testAddConcatAndOrs()
    {
        RegexNode regex1 = new SimpleNode(new Symbol('D', 0), 0);
        RegexNode regex2 = new SimpleNode(new Symbol('b', 0), 0);
        RegexNode regex3 = new OrNode(new Symbol('|', 0), 0);
        RegexNode regex4 = new SimpleNode(new Symbol('b', 0), 0);
        RegexNode regex5 = new SimpleNode(new Symbol('K', 0), 0);
        RegexNode regex6 = new SimpleNode(new Symbol('b', 0), 0);
        Machine instance = new Machine();
        instance.add(regex1);
        instance.add(regex2);
        instance.add(regex3);
        instance.add(regex4);
        instance.add(regex5);
        instance.add(regex6);
        
        Map<String, State> transitions = instance.getStart().getTransitions();
        Map<String, State> expected = new HashMap<>();
        
        State D = new State("D");
        State b1 = new State("b");
        State b2 = new State("b");
        State K = new State("K");
        State b3 = new State("b");
        
        Map<String, State> dTrans = new HashMap<>();
        dTrans.put("b", b1);
        D.setTransitions(dTrans);
        
        Map<String, State> b2Trans = new HashMap<>();
        b2Trans.put("K", K);
        b2.setTransitions(b2Trans);
        
        Map<String, State> KTrans = new HashMap<>();
        KTrans.put("b", b3);
        K.setTransitions(KTrans);
        
        expected.put("D", D);
        expected.put("b", b2);
        
        assertEquals(expected, transitions);
    }
    
}
