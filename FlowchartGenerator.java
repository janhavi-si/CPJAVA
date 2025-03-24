import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FlowchartGenerator {

    public static void generateFlowchart(String filePath) {
        try {
            // Read Java code from the specified file
            String javaCode = new String(Files.readAllBytes(Paths.get(filePath)));

            // Convert Java code to PlantUML with actual commands
            String plantUMLCode = convertToPlantUML(javaCode);

            // Save the .puml file
            String outputFile = "flowchart.puml";
            FileWriter writer = new FileWriter(outputFile);
            writer.write(plantUMLCode);
            writer.close();

            System.out.println("✅ Flowchart file created: " + outputFile);

            // Generate flowchart image
            Process process = Runtime.getRuntime().exec(new String[]{"java", "-jar", "plantuml.jar", "-tpng", outputFile});
            process.waitFor();

            System.out.println("✅ Flowchart image generated: flowchart.png");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String convertToPlantUML(String javaCode) {
        StringBuilder plantUML = new StringBuilder();
        plantUML.append("@startuml\n");
        plantUML.append("start\n");

        Stack<String> stack = new Stack<>();
        String[] lines = javaCode.split("\n");

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("if")) {
                plantUML.append("if (").append(extractCondition(line)).append(") then (Yes)\n");
                stack.push("if");
            } else if (line.startsWith("else")) {
                plantUML.append("else (No)\n");
            } else if (line.startsWith("for") || line.startsWith("while")) {
                plantUML.append("while (").append(extractCondition(line)).append(") is (true)\n");
                stack.push("loop");
            } else if (line.startsWith("}")) {
                if (!stack.isEmpty() && stack.peek().equals("if")) {
                    plantUML.append("endif\n");
                    stack.pop();
                } else if (!stack.isEmpty() && stack.peek().equals("loop")) {
                    plantUML.append("endwhile\n");
                    stack.pop();
                }
            } else if (line.startsWith("System.out.println")) {
                plantUML.append(":Print ").append(extractPrintStatement(line)).append(";\n");
            } else if (line.matches(".*=.*;")) {  // Variable assignment
                plantUML.append(":").append(extractAssignment(line)).append(";\n");
            }
        }

        plantUML.append("stop\n");
        plantUML.append("@enduml\n");
        return plantUML.toString();
    }

    private static String extractCondition(String line) {
        return line.replaceAll("if|while|for|\\{|\\)", "").trim();
    }

    private static String extractPrintStatement(String line) {
        return line.replaceAll("System.out.println\\(|\\);", "").trim();
    }

    private static String extractAssignment(String line) {
        return "Set " + line.replace(";", "").trim();
    }
}
