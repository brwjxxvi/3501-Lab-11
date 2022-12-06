import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

 //Class for Nodes and their data
 class Node {
    double frequency;
    char letter;
    String binary;

    Node left;
    Node right;
}

//Class to compare Nodes
class MyComparator implements Comparator<Node> {
    public int compare(Node x, Node y) {
        return (int) ( x.frequency - y.frequency);
    }
}

public class Encoding {

     //Checks for and return the binary encoding for a given letter
     public static String letterToBinary(char currentLetter, Node node) {
        if(node != null) {
            if (node.letter == currentLetter) return node.binary;
            return (letterToBinary(currentLetter, node.left) + letterToBinary(currentLetter, node.right));
        } else return "";
    }

    //Encodes the binary value to a given node
    public static void binaryEncoding(Node node) {
        if(node == null) return;
        if(node.left != null) node.left.binary = node.binary + "0";
        if(node.right != null) node.right.binary = node.binary + "1";

        binaryEncoding(node.left);
        binaryEncoding(node.right);
    }

    public static String encrypt(String text, Node root) {

        String result = "";
        char[] charArray = text.toCharArray();

        for(int j = 0; j < charArray.length; j++) {
            String encryptedChar = letterToBinary(charArray[j], root);
            result += encryptedChar;
        }

        return result;
    }

    public static String decrypt(String encryptedText, Node root) {
        char[] text = encryptedText.toCharArray();
        Node current = root;
        String result = "";
        for(int i = 0; i < text.length; i++) {
            if(text[i] == '1') current = current.right;
            else current = current.left;
            if(current.letter != '0') {
                result += current.letter;
                current = root;
            }
        }

        return result;
    }

    public static void huffmanAlg(char[] letters, Object[] frequencies) {

        //Algorithm for Huffman Encoding:
        //Create priority queue which makes a min-heap
        int length = letters.length;
        PriorityQueue<Node> queue = new PriorityQueue<Node>(length, new MyComparator());

        //Creates nodes with letter and frequency to be added to the queue
        for(int i = 0; i < length; i++) {
            Node node = new Node();

            node.letter = letters[i];
            node.frequency = (double) frequencies[i];

            node.left = null;
            node.right = null;

            queue.add(node);
        }

        //Create root node
        Node root = null;

        //While loop extracts two minimum values from heap
        //until size is reduced to one node
        while(queue.size() > 1) {

            //First min extraction
            Node first = queue.peek();
            queue.poll();

            //Second min extraction
            Node second = queue.peek();
            queue.poll();

            //New node z 
            //Freqeuncy is equal to sum of two min node's freqeuncies 
            Node z = new Node();
            z.frequency = first.frequency + second.frequency;
            z.letter = '-';

            //First extracted node is left child
            z.left = first;
            //Second extracted node is right child
            z.right = second;
            //New node z is marked as root
            root = z;
            //Add z node to queue
            queue.add(z);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
       
        //Code for entering which text file to scan
        System.out.print("Enter an input file name to scan: ");
        Scanner input = new Scanner(System.in);
        String inputFileName = input.nextLine() + ".txt";
        FileReader reader = new FileReader(inputFileName);

        //Code for scanning text files into arrays of 
        //letters and their frequencies
        Scanner in = new Scanner(reader);
        
        StringBuilder buff = new StringBuilder();
        List<Double> intList = new ArrayList<Double>();

        while(in.hasNextLine()) {
            String line = in.next();
            String noSpace = line.replaceAll("\\s", "");
            buff.append(noSpace);

            intList.add(in.nextDouble());
        }

        char[] letters = buff.toString().toCharArray();
        Object[] frequencies = intList.toArray();

        //Algorithm for Huffman Encoding:
        //Create priority queue which makes a min-heap
        int length = letters.length;
        PriorityQueue<Node> queue = new PriorityQueue<Node>(length, new MyComparator());

        //Creates nodes with letter and frequency to be added to the queue
        for(int i = 0; i < length; i++) {
            Node node = new Node();

            node.letter = letters[i];
            node.frequency = (double) frequencies[i];

            node.left = null;
            node.right = null;

            queue.add(node);
        }

        //Create root node
        Node root = null;

        //While loop extracts two minimum values from heap
        //until size is reduced to one node
        while(queue.size() > 1) {

            //First min extraction
            Node first = queue.peek();
            queue.poll();

            //Second min extraction
            Node second = queue.peek();
            queue.poll();

            //New node z 
            //Freqeuncy is equal to sum of two min node's freqeuncies 
            Node z = new Node();
            z.frequency = first.frequency + second.frequency;
            z.letter = '-';

            //First extracted node is left child
            z.left = first;
            //Second extracted node is right child
            z.right = second;
            //New node z is marked as root
            root = z;
            //Add z node to queue
            queue.add(z);
        }

        //Assigns binary encoding for each node.
        binaryEncoding(root);

        String encrypted = "0100100010111001001101101001000101110010011011";

        /* System.out.print("Enter text to Encode with given encoding: ");
        String plaintext = "";

        plaintext = plaintext + input.nextLine();
         

        String lametext = plaintext.replaceAll("^\\s|\\W|\\d|_|:", "");
        String lowerLame = lametext.toLowerCase();
        String encrypted = encrypt(lowerLame, root);

        System.out.println("This is the text you entered: " + lametext);
        
        System.out.println("This is the encrypted text: " + encrypted.replace("null", "")); */

        System.out.println("This is the decrypted text: " + decrypt(encrypted, root));
    }
    
}
