/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nodes;

/**
 *
 * @author reuben
 */
public abstract class RegexNode implements Comparable {
    
    public abstract int getIndex();
    public abstract int getDepth();
    
    @Override
    public abstract int compareTo(Object o);
    
}
