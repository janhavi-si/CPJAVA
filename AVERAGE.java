class average{
    public static void main (String args[]) {
        double[] arr={10.1, 11.2, 12.3, 13.4, 14.5};
        double avg=0;
        for(int i=0;i<5;i++) {
            avg=avg+arr[i];
        }
        System.out.println("AVERAGE= " +avg/5);
    }
}