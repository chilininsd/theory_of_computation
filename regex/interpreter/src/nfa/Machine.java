/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nodes.OrNode;
import nodes.RegexNode;
import nodes.SimpleNode;

/**
 *
 * @author reuben
 */
public class Machine extends State {

    public static final String EPSILON = "epsilon";
    private State start;
    private Set<State> finalStates;
    private State currentState;
    private boolean isStar;

    public Machine()
    {
        transitions = new HashMap<>();
        finalStates = new HashSet<>();
        start = new State("start");
        start.setIsStart(true);
        start.setIsFinal(false);
        currentState = start;
        isStar = false;
    }

    public boolean matches(String s)
    {
        return false;
    }

    public void add(RegexNode regex)
    {
        if (regex instanceof OrNode)
        {
            currentState = start;
        }
        else if (currentState instanceof Machine)
        {
            State newState = new State(regex.toString());
            
            Set<State> machinesFinalStates = ((Machine)currentState).getFinalStates();
            finalStates.removeAll(machinesFinalStates);
            for (State state : machinesFinalStates)
            {
                Map<String, State> trans = state.getTransitions();
                trans.put(regex.toString(), newState);
                state.setTransitions(transitions);
                state.setIsFinal(false);
                /**
                 * watch for this not actually affecting the nodes!!
                 * 
                 */
            }
            
            currentState = newState;
            finalStates.add(newState);
        }
        else if (regex instanceof SimpleNode)
        {
            State newState = new State(regex.toString());
            Map<String, State> currentTransitions = currentState.getTransitions();
            currentTransitions.put(regex.toString(), newState);
            currentState.setTransitions(currentTransitions);
            currentState.setIsFinal(false);
            finalStates.remove(currentState);
            currentState = newState;
            finalStates.add(newState);
        }
    }

    public void addMachine(Machine machine)
    {
        Map<String, State> currentTransitions = currentState.getTransitions();
        currentTransitions.put(EPSILON, machine.getStart());
        currentState.setTransitions(currentTransitions);
        currentState.setIsFinal(false);
        finalStates.remove(currentState);
        currentState = machine.getStart();
        finalStates.addAll(machine.getFinalStates());
    }

    public void setIsStar(boolean isStar)
    {
        this.isStar = isStar;
    }

    public Machine finalizeMachine()
    {
        return this;
    }

    public void setStart(State start)
    {
        this.start = start;
    }

    public State getStart()
    {
        return start;
    }

    public Set<State> getFinalStates()
    {
        return finalStates;
    }

    public boolean isIsStar()
    {
        return isStar;
    }
    
    public void setFinalStates(Set<State> finalStates)
    {
        this.finalStates = finalStates;
    }

    public void setCurrentState(State currentState)
    {
        this.currentState = currentState;
    }
    
    
}
