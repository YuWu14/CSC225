/* HuffDecoder.java

   Starter code for compressed file decoder. You do not have to use this
   program as a starting point if you don't want to, but your implementation
   must have the same command line interface. Do not modify the HuffFileReader
   or HuffFileWriter classes (provided separately).
   
   B. Bird - 03/19/2019
   (Add your name/studentID/date here)
*//*YUWU
V00917423*/

import java.io.*;
import java.util.*;

class HuffNode{
    byte[] character;

    HuffNode left;
    HuffNode right;
}

public class HuffDecoder{

    private HuffFileReader inputReader;
    private BufferedOutputStream outputFile;

    /* Basic constructor to open input and output files. */
    public HuffDecoder(String inputFilename, String outputFilename) throws FileNotFoundException {
        inputReader = new HuffFileReader(inputFilename);
        outputFile = new BufferedOutputStream(new FileOutputStream(outputFilename));
    }


    public void decode() throws IOException{

        /* This is where actual decoding should happen. */
        HuffNode treeRoot = new HuffNode();

        // This while loop takes O(s) running time when s = numbers of symbols in the symbol table.
        while (true){
            HuffFileSymbol y = inputReader.readSymbol();
            if(y == null){
                break;
            }
            else{
                byte[] x = y.symbol;
            
                int[] the_bits = y.symbolBits;
                HuffNode root = treeRoot;
                for (int i = 0; i<the_bits.length; i++){ // This will be constant time in long terms              
                    if (the_bits[i] == 0){
                        if(root.left == null){
                            HuffNode root1 = new HuffNode();
                            root.left = root1;
                            root = root.left;
                        }else{
                            root = root.left;
                        }
                    }else{
                        if(root.right == null){
                            HuffNode root1 = new HuffNode();
                            root.right = root1;
                            root = root.right;
                        }else{
                            root = root.right;}
                    }                  
                }
                root.character = y.symbol;
            }
                
            
        }
        HuffNode root2 = treeRoot;
        // This while loop takes O(n) running time when s = numbers in the bit stream.
        while(true){
            int m = inputReader.readStreamBit();
        
            if(m == -1){
                break;
            }
            else{
                if(m == 1){
                    root2 = root2.right;
                    if(root2.character != null){
                        
                        for(int i = 0; i<root2.character.length;i++){ // This will be constant time in long terms                            
                            outputFile.write(root2.character[i]);}
                        root2 = treeRoot;
                    }

                }else{
                    root2 = root2.left;
                    if(root2.character != null){
                        for(int i = 0; i<root2.character.length;i++){ // This will be constant time in long terms
                            
                            outputFile.write(root2.character[i]);}
                        root2 = treeRoot;
                    }
                }
            }
        }
        // The worst case running time will be O(s+n) given by the two above while loop

        /* The outputFile.write() method can be used to write individual bytes to the output file.*/
        outputFile.close();
    }


    public static void main(String[] args) throws IOException{
        if (args.length != 2){
            System.err.println("Usage: java HuffDecoder <input file> <output file>");
            return;
        }
        String inputFilename = args[0];
        String outputFilename = args[1];

        try {
            HuffDecoder decoder = new HuffDecoder(inputFilename, outputFilename);
            decoder.decode();
        } catch (FileNotFoundException e) {
            System.err.println("Error: "+e.getMessage());
        }
    }
}
