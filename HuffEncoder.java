/* HuffEncoder.java

   Starter code for compressed file encoder. You do not have to use this
   program as a starting point if you don't want to, but your implementation
   must have the same command line interface. Do not modify the HuffFileReader
   or HuffFileWriter classes (provided separately).
   
   B. Bird - 03/19/2019
   (Add your name/studentID/date here)
*/
/*yuwu
V00917423
7/6/2019*/

import java.io.*;
import java.util.*;


class HuffNode{
    Byte character;
    int frequency;

    HuffNode left;
    HuffNode right;
}

class HuffComparator implements Comparator<HuffNode>{
    public int compare(HuffNode x, HuffNode y){
        //1 positive, 0 equal, -1 negative
        return x.frequency - y.frequency;
    }

}
public class HuffEncoder{

    private BufferedInputStream inputFile;
    private HuffFileWriter outputWriter;

    public HuffEncoder(String inputFilename, String outputFilename) throws FileNotFoundException {
        inputFile = new BufferedInputStream(new FileInputStream(inputFilename));
        outputWriter = new HuffFileWriter(outputFilename);
    }

    private static void printCode(HuffNode root3, Map<Byte,String> map,String s) throws IOException
    { 
        if (root3.left == null&& root3.right == null) { 
            map.put(root3.character,s);
            return; 
        } 
        printCode(root3.left, map,s + "0"); 
        printCode(root3.right, map,s + "1"); 

    }

    public void encode() throws IOException{

        //You may want to start by reading the entire file into a list to make it easier
        //to navigate.
        LinkedList<Byte> input_bytes = new LinkedList<Byte>();
        for(int nextByte = inputFile.read(); nextByte != -1; nextByte = inputFile.read()){
            input_bytes.add((byte)nextByte);

        }

        //Suggested algorithm:

        //Compute the frequency of each input symbol. Since symbols are one character long,
        //you can simply iterate through input_bytes to see each symbol.
        TreeMap<Byte,Integer> freqMap = new TreeMap<Byte, Integer>();// The first is the key, second is the frequency
        for(Byte byte_key : input_bytes){
            Integer byte_frequency = freqMap.get(byte_key);

            if(byte_frequency == null){
                freqMap.put(byte_key , 1);
            }else{
                freqMap.put(byte_key , (byte_frequency+1) );
            }
        }

        //Build a prefix code for the encoding scheme (if using Huffman Coding, build a 
        //Huffman tree).
        PriorityQueue<HuffNode> pq = new PriorityQueue<HuffNode>(new HuffComparator()); 

        for(Map.Entry<Byte,Integer> item : freqMap.entrySet()){
            HuffNode the_Node = new HuffNode();
            the_Node.character = item.getKey();

            the_Node.frequency = item.getValue();

            pq.add(the_Node);

        }

        HuffNode treeRoot = null;    
        while(pq.size() > 1){
            HuffNode x = pq.poll();
            HuffNode y = pq.poll();

            HuffNode root2 = new HuffNode();
            root2.character = null;
            root2.frequency = x.frequency + y.frequency;
            root2.left = x;
            root2.right = y;
            treeRoot = root2;
            pq.add(root2);
        }

        //Write the symbol table to the output file
        
        TreeMap<Byte,String> charCode = new TreeMap<Byte, String>();
        printCode(treeRoot,charCode,"");


        for(Map.Entry<Byte,String> item : charCode.entrySet()){
            byte[] b = {item.getKey()};
 
            String temp = item.getValue();
            int[] s = new int[temp.length()];

            for (int i = 0; i < temp.length(); i++){
                s[i] = temp.charAt(i) - '0';}

            HuffFileSymbol the_sym = new HuffFileSymbol(b,s);
            outputWriter.writeSymbol(the_sym);
        }


    

        //Call outputWriter.finalizeSymbols() to end the symbol table
        outputWriter.finalizeSymbols();

        //Iterate through each input byte and determine its encode bitstring representation,
        //then write that to the output file with outputWriter.writeStreamBit()
        for(Byte byte_key : input_bytes){
            for(Map.Entry<Byte,String> item : charCode.entrySet()){
                if(byte_key == item.getKey()){

                    String temp1 = item.getValue();
                    int[] s1 = new int[temp1.length()];

                    for (int i = 0; i < temp1.length(); i++){
                        s1[i] = temp1.charAt(i) - '0';       
                        outputWriter.writeStreamBit(s1[i]);}
                }
            }
        }
        //Call outputWriter.close() to end the output file
        outputWriter.close();
    }


    public static void main(String[] args) throws IOException{
        if (args.length != 2){
            System.err.println("Usage: java HuffEncoder <input file> <output file>");
            return;
        }
        String inputFilename = args[0];
        String outputFilename = args[1];

        try{
            HuffEncoder encoder = new HuffEncoder(inputFilename, outputFilename);
            encoder.encode();
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: "+e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: "+e.getMessage());
        }

    }
}

