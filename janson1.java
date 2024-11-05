import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ShamirSecretSharing {

    // Method to decode the value from a given base
    public static int decodeValue(int base, String value) {
        return Integer.parseInt(value, base);
    }

    // Method to calculate the secret (constant term 'c') using Lagrange interpolation
    public static int lagrangeInterpolation(List<Integer> xValues, List<Integer> yValues, int k) {
        int c = 0;
        
        for (int i = 0; i < k; i++) {
            int term = yValues.get(i);
            for (int j = 0; j < k; j++) {
                if (j != i) {
                    term *= xValues.get(j);
                    term /= (xValues.get(i) - xValues.get(j));
                }
            }
            c += term;
        }

        return c;
    }

    // Method to solve the polynomial secret
    public static int solvePolynomialSecret(JSONObject jsonData) {
        JSONObject keys = jsonData.getJSONObject("keys");
        int n = keys.getInt("n");
        int k = keys.getInt("k");

        List<Integer> xValues = new ArrayList<>();
        List<Integer> yValues = new ArrayList<>();

        // Iterate over the roots and decode the y-values
        for (String key : jsonData.keySet()) {
            if (!key.equals("keys")) {
                JSONObject root = jsonData.getJSONObject(key);
                int base = root.getInt("base");
                String value = root.getString("value");

                xValues.add(Integer.parseInt(key));
                yValues.add(decodeValue(base, value));
            }
        }

        // Calculate the constant term 'c' using Lagrange interpolation
        return lagrangeInterpolation(xValues, yValues, k);
    }

    public static void main(String[] args) {
        // First test case
        String jsonInput1 = "{\n" +
                "  \"keys\": {\n" +
                "    \"n\": 4,\n" +
                "    \"k\": 3\n" +
                "  },\n" +
                "  \"1\": {\n" +
                "    \"base\": \"10\",\n" +
                "    \"value\": \"4\"\n" +
                "  },\n" +
                "  \"2\": {\n" +
                "    \"base\": \"2\",\n" +
                "    \"value\": \"111\"\n" +
                "  },\n" +
                "  \"3\": {\n" +
                "    \"base\": \"10\",\n" +
                "    \"value\": \"12\"\n" +
                "  },\n" +
                "  \"6\": {\n" +
                "    \"base\": \"4\",\n" +
                "    \"value\": \"213\"\n" +
                "  }\n" +
                "}";

        // Second test case
        String jsonInput2 = "{\n" +
                "  \"keys\": {\n" +
                "    \"n\": 10,\n" +
                "    \"k\": 7\n" +
                "  },\n" +
                "  \"1\": {\n" +
                "    \"base\": \"6\",\n" +
                "    \"value\": \"13444211440455345511\"\n" +
                "  },\n" +
                "  \"2\": {\n" +
                "    \"base\": \"15\",\n" +
                "    \"value\": \"aed7015a346d63\"\n" +
                "  },\n" +
                "  \"3\": {\n" +
                "    \"base\": \"15\",\n" +
                "    \"value\": \"6aeeb69631c227c\"\n" +
                "  },\n" +
                "  \"4\": {\n" +
                "    \"base\": \"16\",\n" +
                "    \"value\": \"e1b5e05623d881f\"\n" +
                "  },\n" +
                "  \"5\": {\n" +
                "    \"base\": \"8\",\n" +
                "    \"value\": \"316034514573652620673\"\n" +
                "  },\n" +
                "  \"6\": {\n" +
                "    \"base\": \"3\",\n" +
                "    \"value\": \"2122212201122002221120200210011020220200\"\n" +
                "  },\n" +
                "  \"7\": {\n" +
                "    \"base\": \"3\",\n" +
                "    \"value\": \"20120221122211000100210021102001201112121\"\n" +
                "  },\n" +
                "  \"8\": {\n" +
                "    \"base\": \"6\",\n" +
                "    \"value\": \"20220554335330240002224253\"\n" +
                "  },\n" +
                "  \"9\": {\n" +
                "    \"base\": \"12\",\n" +
                "    \"value\": \"45153788322a1255483\"\n" +
                "  },\n" +
                "  \"10\": {\n" +
                "    \"base\": \"7\",\n" +
                "    \"value\": \"1101613130313526312514143\"\n" +
                "  }\n" +
                "}";

        // Parse the JSON input using org.json library
        JSONObject testCase1 = new JSONObject(jsonInput1);
        JSONObject testCase2 = new JSONObject(jsonInput2);

        // Solve for both test cases
        int secret1 = solvePolynomialSecret(testCase1);
        int secret2 = solvePolynomialSecret(testCase2);

        // Print the results
        System.out.println("Secret for Test Case 1: " + secret1);
        System.out.println("Secret for Test Case 2: " + secret2);
    }
}