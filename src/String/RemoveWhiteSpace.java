package String;

public class RemoveWhiteSpace {
    public static void main(String[] args) {
        String inputString = "Java is fun to learn";

        String stringWithoutSpaces = inputString.replaceAll("\\s+", "");
        System.out.println("Original String" + inputString);
        System.out.println("String without white space " + stringWithoutSpaces);
    }
}
