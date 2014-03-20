package nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import nodes.OrNode;
import nodes.RegexNode;
import nodes.SimpleNode;

/**
 *
 * @author reuben
 */
public class Graph extends NfaNode {

    private NfaNode start;
    private Set<NfaNode> finalStates;
    private NfaNode currentState;
    private boolean isStar;

    public Graph()
    {
        transitions = new HashMap<>();
        finalStates = new HashSet<>();
        start = new NfaNode("start");
        start.setIsStart(true);
        start.setIsFinal(false);
        currentState = start;
        isStar = false;
    }
    
    public void setStart(NfaNode start)
    {
        this.start = start;
    }

    public NfaNode getStart()
    {
        return start;
    }

    public Set<NfaNode> getFinalStates()
    {
        return finalStates;
    }
    
    public void setFinalStates(Set<NfaNode> finalStates)
    {
        this.finalStates = finalStates;
    }

    public void setCurrentState(NfaNode currentState)
    {
        this.currentState = currentState;
    }
        
    public void setIsStar(boolean isStar)
    {
        this.isStar = isStar;
    }
    
    @Override
    public String toString()
    {
        return start.toString();
    }

    //a breadth first graph traversal for each symbol of the string correctly modeling epsilon transitions
    public boolean match(String pattern)
    {
        Queue<Entry<String, NfaNode>> queue = new LinkedList<>();
        queue.addAll(start.getAllTransitions());
        if (finalStates.contains(start))
            queue.add(createEntry("~", start));
        
        Entry<String, NfaNode> entry = null;
        for (int i = 0; i < pattern.length(); i++)
        {
            Set<Entry<String, NfaNode>> toAdd = new HashSet<>();
            char c = pattern.charAt(i);
            while (!queue.isEmpty())
            {
                entry = queue.poll();
                
                if (entry.getKey().contains(EPSILON))
                    queue.addAll(entry.getValue().getAllTransitions());
                
                if (entry.getKey().charAt(0) == c)
                {
                    toAdd.addAll(entry.getValue().getAllTransitions());
                    if (i == pattern.length()-1 && (finalStates.contains(entry.getValue()) || epsilonTransitionsToFinal(entry.getValue())))
                        return true;
                }
            }
            queue.addAll(toAdd);
        }
        return false;
//        for (Entry<String, NfaNode> entry1 : successfulTransitions) {
//            if (entry1 != null && successfulTransitions.size() >= pattern.length() && entry1.getValue().isFinal) 
//            {
//                return true;
//            } 
//        }
//        return false;
    }

    public void add(RegexNode regex)
    {
        if (regex instanceof OrNode)
        {
            currentState = start;
        }
        else if (currentState instanceof Graph)
        {
            NfaNode newState = new NfaNode(regex.toString());
            
            Set<NfaNode> machinesFinalStates = ((Graph)currentState).getFinalStates();
            finalStates.removeAll(machinesFinalStates);
            for (NfaNode state : machinesFinalStates)
            {
                state.addTransition(regex.toString(), newState);
                state.setIsFinal(false);
            }
            
            currentState = newState;
            finalStates.add(newState);
        }
        else if (regex instanceof SimpleNode)
        {
            NfaNode newState = new NfaNode(regex.toString());
            finalStates.remove(currentState);
            
            if (regex.toString().equals("~"))
                currentState.addEpsilonTransition(newState);
            else
                currentState.addTransition(regex.toString(), newState);
            
            currentState = newState;
            finalStates.add(newState);
        }
    }

    public void addGraph(Graph machine)
    {
        if (currentState instanceof Graph)
        {
            Graph current = (Graph)currentState;
            Set<NfaNode> currentsFinals = current.getFinalStates();
            finalStates.removeAll(currentsFinals);
            for (NfaNode nfaNode : currentsFinals)
            {
                nfaNode.setIsFinal(false);
                nfaNode.addEpsilonTransition(machine.getStart());
            }
            finalStates.addAll(machine.getFinalStates());
            currentState = machine;
        }
        else
        {
            finalStates.remove(currentState);
            currentState.addEpsilonTransition(machine.getStart());
            currentState.setIsFinal(false);
            currentState = machine;
            finalStates.addAll(machine.getFinalStates());
        }
    }

    public Graph finalizeGraph()
    {
        if (isStar)
        {
            for (NfaNode nfaNode : finalStates)
            {
                nfaNode.addEpsilonTransition(start);
            }
//            start.addEpsilonTransition(start);
            start.setIsFinal(true);
            finalStates.add(start);
        }
        return this;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.start);
        hash = 67 * hash + Objects.hashCode(this.finalStates);
        hash = 67 * hash + Objects.hashCode(this.currentState);
        hash = 67 * hash + (this.isStar ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Graph other = (Graph) obj;
        if (!Objects.equals(this.start, other.start))
        {
            return false;
        }
        if (!Objects.equals(this.finalStates, other.finalStates))
        {
            return false;
        }
        if (!Objects.equals(this.currentState, other.currentState))
        {
            return false;
        }
        if (this.isStar != other.isStar)
        {
            return false;
        }
        return true;
    }

    private Entry<String, NfaNode> createEntry(String key, NfaNode value)
    {
        Map<String, NfaNode> map = new HashMap<>();
        map.put(key, value);
        return map.entrySet().iterator().next();
    }

    private boolean epsilonTransitionsToFinal(NfaNode value)
    {
        for (Entry<String, NfaNode> entry : value.getAllTransitions())
        {
            if (entry.getKey().contains(EPSILON) && finalStates.contains(entry.getValue())) 
                return true;
        }
        return false;
    }
    
    

}
