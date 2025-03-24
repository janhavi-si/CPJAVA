import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Java file path:");
        String filePath = scanner.nextLine();

        FlowchartGenerator.generateFlowchart(filePath);
    }
}
