/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nodes;

import model.Symbol;

/**
 * A node representing a regex |
 * @author reuben
 */
public class OrNode extends SimpleNode {

    public OrNode(Symbol or, int depth)
    {
        super(or, depth);
    }
}
