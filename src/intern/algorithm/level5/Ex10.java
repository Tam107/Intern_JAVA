package intern.algorithm.level5;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ex10 {
    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Alice");
        params.put("balance", "100");

        String templatePath = "template.txt";
        String outputPath = "output.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(templatePath))) {
            writer.write("Hello, ${name}! Your balance is ${balance} USD.");
        } catch (Exception e) {
            System.out.println("Cannot write " + e.getMessage());
            return;
        }
        try {
            renderTemplate(templatePath, params, outputPath);
            System.out.println("Template rendered successfully. Checkout the output file " + outputPath);
            System.out.println("output Content " + readFile(outputPath));
        } catch (Exception e) {
            System.out.println("Error with render template" + e.getMessage());
        }
    }

    private static void renderTemplate(String templatePath, Map<String, String> params, String outputPath) throws IOException {
        String templateContent = readFile(templatePath);
        String resultContent = replacePlaceholder(templateContent, params);
        writeFile(outputPath, resultContent);
    }

    private static void writeFile(String outputPath, String resultContent) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write(resultContent);
        } catch (IOException e) {
            throw new IOException("Error writing to output file: " + outputPath, e);
        }
    }

    private static String replacePlaceholder(String templateContent, Map<String, String> params) {
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(templateContent);
        StringBuilder result = new StringBuilder();
        int lastIndex = 0;
        while (matcher.find()) {
            result.append(templateContent, lastIndex, matcher.start());
            String paramName = matcher.group(1);
            String replacement = params.getOrDefault(paramName, "${" + paramName + "}");
            result.append(replacement);
            lastIndex = matcher.end();
        }

        result.append(templateContent, lastIndex, templateContent.length());
        return result.toString();

    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new IOException("Template file not found: " + filePath, e);
        }
        return content.toString();
    }
}
