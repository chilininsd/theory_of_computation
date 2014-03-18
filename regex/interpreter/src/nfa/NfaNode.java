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
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author reuben
 */
public class NfaNode {
    
    protected Map<String, NfaNode> transitions;
    protected boolean isStart;
    protected boolean isFinal;
    protected String name;
    public static String EPSILON = "epsilon";
    private int epsilonCount = 0;
    
    public NfaNode(String name)
    {
        transitions = new HashMap<>();
        isStart = false;
        isFinal = true;
        this.name = name;
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

    public boolean isStart()
    {
        return isStart;
    }

    public void setIsStart(boolean isStart)
    {
        this.isStart = isStart;
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
        hash = 41 * hash + Objects.hashCode(this.transitions);
        hash = 41 * hash + (this.isStart ? 1 : 0);
        hash = 41 * hash + (this.isFinal ? 1 : 0);
        hash = 41 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {   
        NfaNode that = (NfaNode)obj;
        if (this.transitions.equals(that.getTransitions()) && this.name.equals(that.name)) return true;
        else return false;
    }
    
    public void addTransition(String input, NfaNode to)
    {
        transitions.put(input, to);
    }
    
    public void addEpsilonTransition(NfaNode to)
    {
        //hack due to java's lack of multimap :-\
        transitions.put(EPSILON+epsilonCount, to);
        ++epsilonCount;
    }
    
    public Set<Entry<String, NfaNode>> getEpsilonTransitions()
    {
        Set<Entry<String, NfaNode>> epsilons = new HashSet<>();
        for (Map.Entry<String, NfaNode> entry : transitions.entrySet())
        {
            if (entry.getKey().contains(EPSILON))
                epsilons.addAll(entry.getValue().getAllTransitions());
        }
        return epsilons;
    }
    
    public Set<Entry<String, NfaNode>> getAllTransitions()
    {
        Set<Entry<String, NfaNode>> entries = new HashSet<>();
        entries.addAll(transitions.entrySet());
        entries.addAll(getEpsilonTransitions());
        for (Entry<String, NfaNode> entry : transitions.entrySet())
        {
            if (entry.getKey().contains(EPSILON))
                entries.remove(entry);
        }
        return entries;
    }
    
    @Override
    public String toString()
    {
        return "(" + name +" " + transitions.toString() + ", " + isFinal + ")\n";
    }
}
