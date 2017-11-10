import java.io.*;

/**
 * Created by shengyiqun on 2017/11/7.
 */
public class Read {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/shengyiqun/Desktop/text2.zip");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[100];
        int lenth;
        while ((lenth = fileInputStream.read(bytes) )!= -1) {
            System.out.println(new String(bytes,0,lenth));
        }
        System.out.println("test git");

    }
}
