package nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
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

    public boolean match(String pattern)
    {
        Stack<Entry<String, NfaNode>> stack = new Stack<>();
        stack.addAll(start.getAllTransitions());
        
        Entry<String, NfaNode> entry = null;
        for (int i = 0; i < pattern.length(); i++)
        {
            String c = pattern.charAt(i) + "";
            while (!stack.isEmpty())
            {
                entry = stack.pop();
                if (entry.getKey().equals(c))
                {
                    stack.addAll(entry.getValue().getAllTransitions());
                    break;
                }
            }
        }
        if (entry != null && entry.getValue().isFinal) 
            return true;
        else 
            return false;
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
            currentState.addTransition(regex.toString(), newState);
            currentState.setIsFinal(false);
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
            //do stuff
        }
        return this;
    }

}
