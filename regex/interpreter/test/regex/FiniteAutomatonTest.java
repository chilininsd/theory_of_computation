/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package regex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.Test;

/**
 *
 * @author reuben
 */
public class FiniteAutomatonTest {
    
    Method testRegex;
    
    public FiniteAutomatonTest() throws NoSuchMethodException
    {
        Method method = FiniteAutomaton.class.getDeclaredMethod("checkRegex", String.class);
        method.setAccessible(true);
        testRegex = method;
    }

    /**
     * Test of main method, of class FiniteAutomaton.
     */
    @Test
    public void testMain() throws Exception
    {

    }
    
    @Test public void testMalformedRegex1() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        String t1 = "(1(0|1|2)|(1|2|3|4|5|6|7|8|9)):(0|1|2|3|4|5)(0|1|2|3|4|5|6|7|8|9)( |~)(am|pm)";
        testRegex.invoke(null, t1);
    }
    
    @Test public void testMalformedRegex2() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        String t2 = "((0|1|~)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9|~)|2(0|1|2|3|4)(0|1|2|3|4|5|6|7|8|9)|25(0|1|2|3|4|5)).";
        testRegex.invoke(null, t2);
    }
    
    @Test public void testMalformedRegex3() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        String t3 = "<((\"(a|b|'|=| |/|>|<)*\")|('(a|b|\"|=| |/|<|>)*')|(a|b|=| |/))*>";
        testRegex.invoke(null, t3);
    }
}
