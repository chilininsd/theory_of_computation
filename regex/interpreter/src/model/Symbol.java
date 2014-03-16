/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author reuben
 */
public class Symbol {

    private char symbol;
    private int index;

    public Symbol(char symbol, int depth)
    {
        this.symbol = symbol;
        this.index = depth;
    }

    public char getSymbol()
    {
        return symbol;
    }

    public void setSymbol(char symbol)
    {
        this.symbol = symbol;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int depth)
    {
        this.index = depth;
    }

    @Override
    public String toString()
    {
        return symbol + "," + index;
    }
}
