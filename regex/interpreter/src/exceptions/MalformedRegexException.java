/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author reuben
 */
public class MalformedRegexException extends Exception {
    public MalformedRegexException(String symbol)
    {
        super("Regex was not formed correctly, invalid symbol = \"" + symbol + "\"");
    }
}
