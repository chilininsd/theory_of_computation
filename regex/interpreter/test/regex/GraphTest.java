/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.Symbol;
import nfa.Graph;
import nodes.OrNode;
import nodes.RegexNode;
import nodes.SimpleNode;
import regex.FiniteAutomaton;
import regex.FiniteAutomaton;

/**
 *
 * @author reuben
 */
public class GraphTest {

    public static String myDir = "./../testcases/mine/";
    public static String otherDir = "./../testcases/other/";

    public GraphTest()
    {

    }

    public static void main(String[] args) throws IOException
    {
//        testAll();
        testOtherT2();
        testMyT1();
        testOtherT1();
        testMyT2();
        testMyT3();
        testOtherT3();
        testMyT4();
        testMyT5();
    }

    public static void testAll() throws IOException
    {
        for (int i = 1; i <= 3; i++)
        {
            runTest(myDir, "t" + i + ".txt");
            runTest(otherDir, "t" + i + ".txt");
        }
    }

    public static void testMyT1() throws IOException
    {
        runTest(myDir, "t1.txt");
    }

    public static void testMyT2() throws IOException
    {
        runTest(myDir, "t2.txt");
    }

    public static void testMyT3() throws IOException
    {
        runTest(myDir, "t3.txt");
    }
    
    public static void testMyT4() throws IOException
    {
        runTest(myDir, "t4.txt");
    }
    
    public static void testMyT5() throws IOException
    {
        runTest(myDir, "t5.txt");
    }

    public static void testOtherT1() throws IOException
    {
        runTest(otherDir, "t1.txt");
    }

    public static void testOtherT2() throws IOException
    {
        runTest(otherDir, "t2.txt");
    }

    public static void testOtherT3() throws IOException
    {
        runTest(otherDir, "t3.txt");
    }

    private static List<String> parseTestFile(String directory, String t1txt) throws IOException
    {
        Path file = Paths.get(directory + t1txt);
        List<String> fileContents = new LinkedList<>();
        try (BufferedReader reader = Files.newBufferedReader(file, Charset.defaultCharset()))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                fileContents.add(line);
            }
        }
        return fileContents;
    }

    private static void runTest(String directory, String testFile) throws IOException
    {
        List<String> testCases = parseTestFile(directory, testFile);
        String regex = testCases.remove(0);
        List<String> answers = parseTestFile(directory, testFile.replaceFirst("t", "a"));

        List<String> results = FiniteAutomaton.match(regex, testCases);

        for (int i = 0; i < answers.size(); i++)
        {
            if (!results.get(i).equals(answers.get(i)))
            {
                System.err.println("Failure in test file " + testFile);
                System.err.println("For regex: " + regex);
                System.err.println("Test case: " + testCases.get(i) + " failed");
                System.err.println("Should have been: " + answers.get(i) + " but was " + results.get(i));
                System.err.println();
            }
        }
    }
}
