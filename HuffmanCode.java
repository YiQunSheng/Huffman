import apple.laf.JRSUIUtils;

import java.io.*;

/**
 * Created by shengyiqun on 2017/11/6.
 * Thanks a lot my textbook help me finish the most difficult part-- HuffmanTree and the heap.
 */
public class HuffmanCode {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/shengyiqun/Desktop/text1.txt");
        String mytext = null;
        byte[] bytes = input(file);
        for (int i = 0;i<bytes.length;i++) {
            mytext = new String(bytes, 0, bytes.length);
        }
        System.out.println(mytext);

        int[] frequency;//Frenquence of every Char in file;
        frequency = getFrequency(mytext);
        Tree tree = getHuffmanTree(frequency);
        String[] codes = getCode(tree.root);


        String out = Codeing(mytext, codes);
        System.out.println(out.substring(0,5));//from beginning to ending - 1; total = end-begin,test
        File file1 = new File("/Users/shengyiqun/Desktop/text2.zip");
        FileOutputStream outputStream = new FileOutputStream(file1);
        outputStream.write(out.getBytes());
        outputStream.close();

    }



    public static class Tree implements Comparable<Tree> {
        Node root;
        public Tree(Tree t1, Tree t2) {
            root = new Node();
            root.left = t1.root;
            root.right = t2.root;
            root.weight = t1.root.weight + t2.root.weight;
        }

        public Tree(char element,int weight) {
            root = new Node(element,weight);
        }

        @Override
        public int compareTo(Tree tree) {
            if (root.weight<tree.root.weight)
                return 1;
            else if(root.weight==tree.root.weight)
                return 0;
            else return -1;

        }
        public  class Node {
            char element;
            int weight;
            Node left;
            Node right;
            String code = "";

            public Node() {

            }

            public Node(char element, int weight) {
                this.element = element;
                this.weight = weight;
            }
        }//节点的数据结构
    }

    public static int[] getFrequency(String text) {
        int[] counts = new int[256];//例如 counts[5]代表ASCII中字符5
        for(int i=0;i<text.length();i++) {
            counts[(int)text.charAt(i)]++;//获取字符ASCII码，并且转成int
        }
        return counts;
    }

    public static Tree getHuffmanTree(int[] counts) {
        Heap<Tree> heap = new Heap<>();
        for (int i = 0;i<counts.length;i++) {
            if(counts[i]>0)
                heap.add(new Tree((char)i,counts[i]));
        }
        while (heap.getSize() > 1) {
            Tree t1 = heap.remove();
            Tree t2 = heap.remove();
            heap.add(new Tree(t1, t2));
        }
        return heap.remove();
    }

    private static void assignCode(Tree.Node root, String[] codes) {
        if (root.left != null) {

            root.left.code = root.code + "0";
            assignCode(root.left, codes);

            root.right.code = root.code + "1";
            assignCode(root.right, codes);
        } else {
            codes[(int)root.element]=root.code;
        }

    }

    public static String[] getCode(Tree.Node root) {
        if(root==null) return null;
        String[] codes = new String[256];
        assignCode(root,codes);
        return codes;
    }

    public static byte[] input(File file) throws IOException{
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        int lenth;
        String text =bytes.toString();
        return bytes;
    }

    public static String Codeing(String mytext, String[] codes) {
        String out = "";
        for (int i = 0;i<mytext.length();i++) {
            char ch = mytext.charAt(i);
            out+=codes[((int)ch)];
        }
        System.out.println("the coding is "+out.toString());
        out = out.toString();

        return out;
    }

    public static void Translate(String out, String[] codes) {
        for(int i=0;i<out.length();i++)
            for(int j=i;j<out.length();j++) {
                String test = out.substring(i, j);
                for(int k=0;k<codes.length;k++) {
                    if (test .equals(codes[k]) ) {
                        System.out.print((char)k);
                        i=j;
                    }
                }
            }
    }

    public static void Dictionary(String[] codes,int[] frequency) {
        System.out.printf("%-15s%-15s%-15s%-15s\n","ASCII Code","Characher","Frequency","Code");
        for(int i=0;i<codes.length;i++) {
            if(frequency[i]!=0)
                System.out.printf("%-15s%-15s%-15s%-15s\n",i,(char)i+"",frequency[i],codes[i]);
        }
    }


}
