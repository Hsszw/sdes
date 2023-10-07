import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SDES {
    private static int[] P10 = {2, 4, 1, 6, 3, 9, 0, 8, 7, 5};
    private static int[] P8 = {5, 2, 6, 3, 7, 4, 9, 8};
    private static int[] LS1 = {1, 2, 3, 4, 0};
    private static int[] LS2 = {2, 3, 4, 0, 1};
    private static final int[][] SBox1 = {{1, 0, 3, 2}, {3, 2, 1, 0}, {0, 2, 1, 3}, {3, 1, 0, 2}};

    public static int[] keyGeneration(int key) {
        int[] k = new int[2];
        int[] k_1=new int [8];
        int[] k_2=new int [8];
        int[] k0 = new int[10];
        int[] k1 = new int[10];
        int[] k2 = new int[10];

        // Apply P10 permutation
        for (int i = 0; i < 10; i++) {
            int index = P10[i];
            k0[i] = (key >> (9 - index)) & 1;
        }

        // Left shift and apply P8 permutation to get K1
        for (int i = 0; i < 5; i++) {
            k1[i] = k0[LS1[i]];
        }
        for (int i = 5; i < 10; i++) {
            k1[i] = k0[LS1[i - 5] + 5];
        }
        for (int i = 0; i < 8; i++) {
            k_1[i] = k1[P8[i]];
            k[0] |= k_1[i] << (7 - i);
        }

        // Left shift twice and apply P8 permutation to get K2
        for (int i = 0; i < 5; i++) {
            k2[i] = k1[LS2[i]];
        }
        for (int i = 5; i < 10; i++) {
            k2[i] = k1[LS2[i - 5] + 5];
        }
        for (int i = 0; i < 8; i++) {
            k_2[i] = k2[P8[i]];
            k[1] |= k_2[i] << (7 - i);
        }

        return k;
    }
    private static int[][] S0 = {{1, 0, 3, 2}, {3, 2, 1, 0}, {0, 2, 1, 3}, {3, 1, 3, 2}};
    private static int[][] S1 = {{0, 1, 2, 3}, {2, 0, 1, 3}, {3, 0, 1, 0}, {2, 1, 0, 3}};
    public static int encrypt(int plaintext, int key) {
        int[] subkeys = keyGeneration(key);

        // Apply initial permutation (IP)
        int ip = permute(plaintext, new int[]{1, 5, 2, 0, 3, 7, 4, 6});

        // Round 1
        int fkr1 = fk(ip, subkeys[0]);
        int sw = ((fkr1 & 0b1111) << 4) | ((fkr1 >> 4) & 0b1111);

        // Round 2
        int fkr2 = fk(sw, subkeys[1]);

        // Apply final permutation (IP^{-1})
        int ciphertext = permute(fkr2, new int[]{3, 0, 2, 4, 6, 1, 7, 5});

        return ciphertext;
    }
    private static int fk(int r, int k) {
        // 将r转换成8位二进制等分成right,left两部分
        int right = r & 0b1111;
        int left = (r >> 4) & 0b1111;

        // 轮函数F
        // 将right进行扩展置换EPBox=(4,1,2,3,2,3,4,1)，得到8位二进制数epRight
        int epRight =
                ((right >> 3) & 0b1) << 0 | ((right >> 0) & 0b1) << 1 |
                        ((right >> 1) & 0b1) << 2 | ((right >> 2) & 0b1) << 3 |
                        ((right >> 1) & 0b1) << 4 | ((right >> 2) & 0b1) << 5 |
                        ((right >> 3) & 0b1) << 6 | ((right >> 0) & 0b1) << 7;

        // 将epRight和k进行异或
        int xorResult = epRight ^ k;

        // 将xorResult分成4个2位的二进制数
        int b1 = (xorResult >> 6) & 0b11;
        int b2 = (xorResult >> 4) & 0b11;
        int b3 = (xorResult >> 2) & 0b11;
        int b4 = (xorResult >> 0) & 0b11;

        // 将这4个二进制数代入S盒SBox_{1}=[(1,0,3,2);(3,2,1,0);(0,2,1,3);(3,1,0,2)]，得到4个输出
        int s1 = ((SBox1[0][b1] << 1) | SBox1[1][b1]) & 0b1111;
        int s2 = ((SBox1[2][b2] << 1) | SBox1[3][b2]) & 0b1111;
        int s3 = ((SBox1[0][b3] << 1) | SBox1[1][b3]) & 0b1111;
        int s4 = ((SBox1[2][b4] << 1) | SBox1[3][b4]) & 0b1111;

        // 将这4个输出按SPBox=(2,4,3,1)进行置换
        int spResult =
                ((s2 >> 1) & 0b1) << 3 | ((s4 >> 0) & 0b1) << 2 |
                        ((s3 >> 0) & 0b1) << 1 | ((s1 >> 1) & 0b1) << 0;

        // 将SPBox置换后的结果和left进行异或，得到新的left
        int newLeft = left ^ spResult;

        // 返回新的8位二进制数
        return (newLeft << 4) | right;
    }

    private static int permute(int value, int[] permutation) {
        int result = 0;
        for (int i = 0; i < permutation.length; i++) {
            int bit = (value >> permutation[i]) & 1;
            result |= (bit << i);
        }
        return result;
    }
    public static int decrypt(int ciphertext, int key) {
        int[] subkeys = keyGeneration(key);

        // Apply initial permutation (IP)
        int ip = permute(ciphertext, new int[]{1, 5, 2, 0, 3, 7, 4, 6});

        // Round 1
        int fkr1 = fk(ip, subkeys[1]);
        int sw = ((fkr1 & 0b1111) << 4) | ((fkr1 >> 4) & 0b1111);

        // Round 2
        int fkr2 = fk(sw, subkeys[0]);

        // Apply final permutation (IP^{-1})
        int plaintext = permute(fkr2, new int[]{3, 0, 2, 4, 6, 1, 7, 5});

        return plaintext;
    }
    //测试用main函数
    /*public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入明文（8位二进制数）：");
        int plaintext = scanner.nextInt(2);

        System.out.print("请输入密钥（10位二进制数）：");
        int key = scanner.nextInt(2);

        int ciphertext = encrypt(plaintext, key);
        int decryptedText = decrypt(ciphertext, key);

        System.out.println("明文： " + Integer.toBinaryString(plaintext));
        System.out.println("密钥： " + Integer.toBinaryString(key));
        System.out.println("密文： " + Integer.toBinaryString(ciphertext));
        System.out.println("解密后的文本： " + Integer.toBinaryString(decryptedText));

        scanner.close();
    }*/

}
