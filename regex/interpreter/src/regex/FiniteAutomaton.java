/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package regex;

import exceptions.MalformedRegexException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import model.Regex;

/**
 *
 * @author reuben
 */
public class FiniteAutomaton {
    
    public static void main(String[] args) throws MalformedRegexException
    {
        checkRegex(args[0]);
        Regex r = RegexParser.parse(args[0]);
    }

    private static void checkRegex(String string) throws MalformedRegexException
    {
        Set<String> validSymbols = new HashSet<>();
        String[] symbols = new String[] {"a", "b", "m", "p", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "\"", "\'", "/", "<", ">", "=", " ", "~", "|", "(", ")", "*"};
        validSymbols.addAll(Arrays.asList(symbols));
        
        for (int i = 0; i < string.length(); i++)
        {
            if (!validSymbols.contains(string.charAt(i)+""))
            {
                throw new MalformedRegexException(string.charAt(i)+"");
            }
        }
    }
}
