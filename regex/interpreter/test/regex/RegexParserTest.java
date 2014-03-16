/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package regex;

import model.Regex;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author reuben
 */
public class RegexParserTest {
    
    public RegexParserTest()
    {
    }

    
    /**
     *  t1a: (A|b)*(Db|bKb)(K|b)*
     *  t2a: ((i|J)(i|J))*
     *  t3a: (((eH)*(He))|(e(e|~)))*
     *  t1b: <((\"(a|b|'|=| |/|>|<)*\")|('(a|b|\"|=| |/|<|>)*')|(a|b|=| |/))*>
     *  t2b: ((0|1|~)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9|~)|2(0|1|2|3|4)(0|1|2|3|4|5|6|7|8|9)|25(0|1|2|3|4|5)).
     *  t3b: (1(0|1|2)|(1|2|3|4|5|6|7|8|9)):(0|1|2|3|4|5)(0|1|2|3|4|5|6|7|8|9)( |~)(am|pm)
     */
    
    
    
    @Test
    public void testT1a()
    {
        Regex r = RegexParser.parse("(A|b)*(Db|bKb)(K|b)*");
        String expected = "(A|b)*(Db|bKb)(K|b)*";
        assertEquals(r.toString(), expected);
    }
    
    @Test
    public void testT2a()
    {
        Regex r = RegexParser.parse("((i|J)(i|J))*");
        String expected = "((i|J)(i|J))*";
        assertEquals(r.toString(), expected);
    }

    @Test
    public void testT3a()
    {
        Regex r = RegexParser.parse("(((eH)*(He))|(e(e|~)))*");
        String expected = "(((eH)*(He))|(e(e|~)))*";
        assertEquals(r.toString(), expected);
    }
    
    @Test
    public void testT1b()
    {
        Regex r = RegexParser.parse("<((\"(a|b|'|=| |/|>|<)*\")|('(a|b|\"|=| |/|<|>)*')|(a|b|=| |/))*>");
        String expected = "<((\"(a|b|'|=| |/|>|<)*\")|('(a|b|\"|=| |/|<|>)*')|(a|b|=| |/))*>";
        assertEquals(r.toString(), expected);
    }
    
    @Test
    public void testT2b()
    {
        Regex r = RegexParser.parse("((0|1|~)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9|~)|2(0|1|2|3|4)(0|1|2|3|4|5|6|7|8|9)|25(0|1|2|3|4|5)).");
        String expected = "((0|1|~)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9|~)|2(0|1|2|3|4)(0|1|2|3|4|5|6|7|8|9)|25(0|1|2|3|4|5)).";
        assertEquals(r.toString(), expected);
    }
    
    @Test
    public void testT3b()
    {
        Regex r = RegexParser.parse("(1(0|1|2)|(1|2|3|4|5|6|7|8|9)):(0|1|2|3|4|5)(0|1|2|3|4|5|6|7|8|9)( |~)(am|pm)");
        String expected = "(1(0|1|2)|(1|2|3|4|5|6|7|8|9)):(0|1|2|3|4|5)(0|1|2|3|4|5|6|7|8|9)( |~)(am|pm)";
        assertEquals(r.toString(), expected);
    }
}
