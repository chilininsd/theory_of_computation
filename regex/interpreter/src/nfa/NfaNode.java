/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A node in the graph
 * @author reuben
 */
public class NfaNode {
    
    protected Map<String, NfaNode> transitions;
    protected boolean isFinal;
    protected String name;
    public static String EPSILON = "epsilon";
    private static int uniqueifier = 0;
    
    public NfaNode(String name)
    {
        transitions = new HashMap<>();
        isFinal = true;
        this.name = name+uniqueifier++;
    }
    
    public NfaNode(){}

    public Map<String, NfaNode> getTransitions()
    {
        return transitions;
    }

    public void setTransitions(Map<String, NfaNode> transitions)
    {
        this.transitions = transitions;
    }

    public boolean isFinal()
    {
        return isFinal;
    }

    public void setIsFinal(boolean isFinal)
    {
        this.isFinal = isFinal;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 41 * hash + this.name.charAt(0);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {   
        NfaNode that = (NfaNode)obj;
        if (this.transitions.equals(that.getTransitions()) && this.name.equals(that.name)) return true;
        else return false;
    }
    
    /**
     * adds a transition to the map of transitions. Appends a "uniqueifying" incrementing number to avoid java collapsing duplicate keys
     * @param input - the key
     * @param to - the value
     */
    public void addTransition(String input, NfaNode to)
    {
        transitions.put(input+uniqueifier++, to);
    }
    
    /**
     * adds an epsilon transition.
     * @param to 
     */
    public void addEpsilonTransition(NfaNode to)
    {
        //hack due to java's lack of multimap :-\
        transitions.put(EPSILON+uniqueifier++, to);
    }
    
    /**
     * Returns all epsilon transitions from this state along with the successive epsilon transitions
     * @return - all the epsilon transitions
     */
    public Set<Entry<String, NfaNode>> getEpsilonTransitions()
    {
        Set<Entry<String, NfaNode>> epsilons = new HashSet<>();
        for (Map.Entry<String, NfaNode> entry : transitions.entrySet())
        {
            if (entry.getKey().contains(EPSILON))
                epsilons.addAll(entry.getValue().getEpsilonTransitions());
        }
        return epsilons;
    }
    
    /**
     * returns all transitions from this map
     * @return 
     */
    public Set<Entry<String, NfaNode>> getAllTransitions()
    {
        Set<Entry<String, NfaNode>> entries = new HashSet<>();
        entries.addAll(transitions.entrySet());
        entries.addAll(getEpsilonTransitions());
        return entries;
    }
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(name.charAt(0)).append("\n");
        for (Entry<String, NfaNode> entry : transitions.entrySet())
        {
            s.append(entry.getKey().charAt(0)).append("->").append(entry.getValue().name.charAt(0)).append("\n");
        }
        return s.toString();
    }
}
