import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Helper class that mainly manages File IO for Google Code Jam.
 * @author Oscar McClintock
 *
 */
public class CodeJamHelper {
    //comment
    enum ProblemSize {small, large}

    private String inputFilename;
    private String outputFilename;
  
    private BufferedReader input;
    private BufferedWriter output;
  
    private char[] currentLine;
    private int nextCharacter;
    private int caseNumber;
    /**
     * Constructs a CodeJamHelper that is ready to perform IO on the correct
     * files for this problem.
     * @param letter The character for this problem, case insensitive.
     * @param size The CodeJamHelper.ProblemSize identifying if this is the large or small problem.
     * @param attempt The number of attempts to solve the problem already. NB this starts at 0.  -1 indicates a practice problem.
     * @throws IOException If there is a problem setting up access to the required files.
     * @throws URISyntaxException If the input file cannot be located.
     */
    public CodeJamHelper (char letter, ProblemSize size, int attempt) throws IOException, URISyntaxException {
        nextCharacter = 0;
        currentLine = new char[0];
        caseNumber = 1;
        
        //construct the input and output filenames
        letter = Character.toUpperCase(letter);
        inputFilename = Character.toString(letter);
        inputFilename += "-";
        if(size == ProblemSize.small) inputFilename += "small";
        if(size == ProblemSize.large) inputFilename += "large";
        inputFilename += "-";
        if(attempt == -1)inputFilename += "practice";
        else inputFilename += "attempt" + attempt;
        outputFilename = inputFilename;
        inputFilename += ".in";
        outputFilename += ".out";
        
        //setup file IO
        try {
            URI inputFileLocation = CodeJamHelper.class.getResource(inputFilename).toURI();
            input = new BufferedReader(new FileReader(new File(inputFileLocation)));
            output = new BufferedWriter(new FileWriter(outputFilename));
        } catch (IOException e) {
              System.out.println("something went wrong opening the one of the files T.T");
              e.printStackTrace();
              throw e;
        }
        catch (URISyntaxException e)
        {
            System.out.println("Signs point to can't find file");
            e.printStackTrace();
            throw e;
        }
    }
    //File IO
    //Reading methods
    /**
     * Returns the remainder of the current line of text or the next line if there is nothing left on the current line.
     * @return A String containing the line of text or null if there is no more text to read
     * @throws IOException If an I/O error occurs
     */
    public String readLine() throws IOException
    {
        if (nextCharacter != 0)
        {
            char[] restOfLine = Arrays.copyOfRange(currentLine, nextCharacter, currentLine.length - 1);
            nextCharacter = 0;
            return new String(restOfLine);
        }
        return input.readLine();
    }
    /**
     * Convenience method to convert the result of readLine to an integer.
     * @return the integer value of the next line
     * @throws IOException if an I/O error occurs
     */
    public int readLineAsInt() throws IOException
    {
        return Integer.parseInt(readLine());
    }
    /**
     * Reads characters from the current line until the next space or end of line.
     * @return A String containing the next word.
     * @throws IOException If an IO error occurs
     */
    public String readWord() throws IOException
    {
        if(nextCharacter == 0) currentLine = readLine().toCharArray();
        if(currentLine == null)return null; //run out of file so return
        String word = "";
        while(nextCharacter != currentLine.length){
            if(currentLine[nextCharacter] == ' '){
                while(currentLine[nextCharacter] == ' ') nextCharacter ++;
                break;
            }
            word += currentLine[nextCharacter];
            nextCharacter++;
        }
        if (word == "") System.out.println("readWord ended up with an empty word somehow");
        if (nextCharacter == currentLine.length)  nextCharacter = 0;
        return word;
    }
    /**
     * Convenience method to convert the result of readWord to an integer.
     * @return the integer value of the next word.
     * @throws IOException If an IO error occurs.
     */
    public int readWordAsInt() throws IOException
    {
        return Integer.parseInt(readWord());
    }
    //Writing methods
    /**
     * Writes one line of text to the output file followed by a new line.
     * @param line The String to be written.
     * @throws IOException if an IO error occurs.
     */
    public void writeLine(String line) throws IOException
    {
        output.write(line);
        output.newLine();
    }
    /**
     * 
     * @param answer
     * @throws IOException
     */
    public void writeCase(String answer) throws IOException
    {
        writeLine("Case #" + caseNumber + ": " + answer);
        caseNumber++;
    }
    /**
     * Writes a String to the output file followed by a space.
     * @param word the String to be written.
     * @throws IOException if an IO error occurs.
     */
    public void writeWord(String word) throws IOException
    {
        output.write(word);
        output.write(' ');
    }
    /**
     * Writes a single character to the output file
     * @param character The character to write.
     * @throws IOException if an IO error occurs.
     */
    public void writeCharacter(char character) throws IOException
    {
        output.write(character);
    }
    
    /**
     * Closes the input and output file handlers.
     * @throws IOException if an IO error occurs.
     */
    public void closeFileHandlers() throws IOException
    {
        input.close();
        output.close();
    }
    
  }//class
