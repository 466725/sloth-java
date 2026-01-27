
static int n1 = 0, n2 = 1;

static void printFibonacci(int count) {
    if (count > 0) {
        System.out.print(" " + n1);
        int n3 = n1 + n2;
        n1 = n2;
        n2 = n3;
        printFibonacci(count - 1);
    }
}

static void main() {
    printFibonacci(15);
}