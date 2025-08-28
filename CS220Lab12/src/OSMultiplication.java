public class OSMultiplication {
    public static int[] powerOf2 = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768};
    public static void main(String[] args) {

        long startNaive, endNaive, startFast, endFast;

        startNaive = System.currentTimeMillis();

        for (int i=0; i < Integer.MAX_VALUE; i++ ) {
            naiveMult(22000, 113000);
        }
        endNaive = System.currentTimeMillis();

        startFast = System.currentTimeMillis();
        for (int i=0; i < Integer.MAX_VALUE; i++ ) {
            fastMult(22000, 113000);
        }
        endFast = System.currentTimeMillis();

        System.out.println("Naive Multiplication: " + (endNaive - startNaive) + " msec");
        System.out.println("Fast Multiplication: " + (endFast - startFast) + " msec");


        System.out.println(String.format("Fast multiplication is %.2f%% faster then naive multiplication",
                ((double)(endNaive - startNaive) - (endFast - startFast) / (endNaive - startNaive)) * 100));
    }



    public static int naiveMult(int x, int y) {
        int product = 0;
        for (int i = 0; i < y; i ++) {
            product += x;
        }
        return product;
    }
    public static int fastMult(int x, int y) {
        int product = 0;
        //loop through the bits of y (16 bits ==> 16 times)
        for (int i = 0; i < 16; i++) {
            //check if the ith bit of y is 1
            if((y & powerOf2[i]) != 0) {
                product += x;
                // shift x to the left by 1 bit

            }
            x += x;
        }
        return product;
    }

}
