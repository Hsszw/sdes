import java.util.concurrent.TimeUnit;

public class BruteForceCracker {
    private static final int THREAD_COUNT = 4; // 线程数量
    private static final int MAX_KEY = 0b1111111111;  // 密钥的最大值

    public static void main(String[] args) throws InterruptedException {
        int plaintext = 0b10101010;//明文
        int ciphertext = 0b11000001;//密文

        long startTime = System.currentTimeMillis(); // 开始时间

        // 每个线程处理的密钥范围
        int keyRange = MAX_KEY / THREAD_COUNT;

        // 创建并启动多个线程进行暴力破解
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            int startKey = i * keyRange;
            int endKey = (i == THREAD_COUNT - 1) ? MAX_KEY : ((i + 1) * keyRange - 1);

            threads[i] = new Thread(new KeyFinder(startKey, endKey, plaintext, ciphertext));
            threads[i].start();
        }

        // 等待所有线程执行完毕
        for (Thread thread : threads) {
            thread.join();
        }

        long endTime = System.currentTimeMillis(); // 结束时间
        long totalTime = endTime - startTime; // 总耗时

        System.out.println("Time taken: " + totalTime + " ms");
    }

    static class KeyFinder implements Runnable {
        private final int startKey;
        private final int endKey;
        private final int plaintext;
        private final int ciphertext;

        public KeyFinder(int startKey, int endKey, int plaintext, int ciphertext) {
            this.startKey = startKey;
            this.endKey = endKey;
            this.plaintext = plaintext;
            this.ciphertext = ciphertext;
        }

        @Override
        public void run() {
            for (int key = startKey; key <= endKey; key++) {
                int decryptedText = SDES.decrypt(ciphertext, key);
                if (decryptedText == plaintext) {
                    System.out.println("Key found: " + String.format("%10s", Integer.toBinaryString(key)).replace(' ', '0'));
                    break;
                }
            }
        }
    }
}