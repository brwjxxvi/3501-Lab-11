import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
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

public class Lab11 {

/* Task 2
 * Algorithm for Huffman Encodings:
 */

    //Recursive method for printing Huffman Encoding
    public static void printCode(Node root, String s) {

        if(root.left == null && root.right == null && Character.isLetter(root.letter)) {
            System.out.println(root.letter + " " + s);
            return;
        }

        root.binary = s;

        printCode(root.left, s + "0");
        printCode(root.right, s + "1");
    }

    public static void main(String[] args) throws FileNotFoundException {

        //Code for entering which text file to scan
        System.out.print("Enter an input file name to scan: ");
        Scanner input = new Scanner(System.in);
        String inputFileName = input.nextLine() + ".txt";
        FileReader reader = new FileReader(inputFileName);
        //Code for creating an output file
        System.out.print("Enter an output file name: ");
        String outputFileName = input.nextLine();
        PrintStream out = new PrintStream(new File(outputFileName + ".txt"));

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

        //Prints out data to given output file
        System.setOut(out);
        printCode(root, "");
        out.close();
    }

}