package intern.JAVACORE2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ex7 {
    public static void main(String[] args) {
        HashMap<String, Integer[]> map = new HashMap<>();
        HashMap<String, List<Double>> studentScores = new HashMap<>();

        // Thêm dữ liệu mẫu
        studentScores.put("An", Arrays.asList(8.5, 7.0, 9.0));
        studentScores.put("Bình", Arrays.asList(6.5, 8.0, 7.5));
        studentScores.put("Cường", Arrays.asList(9.5, 8.5));
        studentScores.put("Dương", Arrays.asList(5.0, 6.0, 4.5, 7.0));

        calculateStudentScore(studentScores);
    }

    private static void calculateStudentScore(HashMap<String, List<Double>> studentScores) {
        for(Map.Entry<String, List<Double>> map : studentScores.entrySet()){
            System.out.println("Student: "+ map.getKey()+" average score: "+calculateAverage(map.getValue()));
        }
    }

    private static double calculateAverage(List<Double> score){
        double total = 0;
        for (Double mark : score){
            total += mark;
        }
        return total / score.size();
    }
}
