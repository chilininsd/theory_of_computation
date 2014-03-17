/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nfa;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author reuben
 */
public class State {
    
    protected Map<String, State> transitions;
    protected boolean isStart;
    protected boolean isFinal;
    protected String name;
    
    public State(String name)
    {
        transitions = new HashMap<>();
        isStart = false;
        isFinal = true;
        this.name = name;
    }
    
    public State(){}

    public Map<String, State> getTransitions()
    {
        return transitions;
    }

    public void setTransitions(Map<String, State> transitions)
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
        State that = (State)obj;
        if (this.transitions.equals(that.getTransitions()) && this.name.equals(that.name)) return true;
        else return false;
    }
    
    
}
