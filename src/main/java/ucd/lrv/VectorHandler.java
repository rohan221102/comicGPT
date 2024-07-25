package ucd.lrv;

import java.math.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VectorHandler {
        private static double[] storedVector; // to store the vector from main
        public static double[][] processedVectors; // storing the vectores from poses csv.

        public static void setProcessedVector(double[][] vector) {
                processedVectors = vector;
        }

        public static double[][] getProcessedVector() {
                return processedVectors;
        }

        public static void setVector(double[] vector) {
                storedVector = vector;
        }

        public static double[] getVector() {
                return storedVector;
        }

        public static double dotProduct(double[] arr1, double[] arr2) {
                double epsilon = 0.0001 + Math.random() * 0.0008;
                if (arr1.length != arr2.length)
                        throw new IllegalArgumentException("Arrays must have the same length");
                double product = 0.0;
                for (int i = 0; i < arr1.length; i++)
                        product += arr1[i] * arr2[i];
                return product + epsilon;
        }

        public static void generateEmbeddings() throws IOException {
                ConfigurationFile conf = ConfigurationFile.initialiseConfigurationFile("ConfigurationFile.conf");
                GPTConnection embedding = new GPTConnection(conf.getApiKey(), conf.getEmbeddingsModel());
        }

        // retrieval of vector string, private ensuring correct accessibility
        public static int processVector(boolean isPose) throws IOException {
                String path = "";
                if (isPose)
                        path = "processed_poses.csv";
                else
                        path = "processed_settings.csv";
                FileReader fr = new FileReader(path);
                BufferedReader br = new BufferedReader(fr);
                List<double[]> listOfDoubleArrays = new ArrayList<>();
                String line = "";
                br.readLine(); // header manipulation
                // obtaining string vectors and making sure
                while ((line = br.readLine()) != null) {
                        String[] fields = line.split(","); // Splitting into fields
                        String currField = fields[2];
                        listOfDoubleArrays.add(manipulateStringArray(currField));
                }
                br.close();
                fr.close();

                // list to 2d array
                double[][] processedVectors = new double[listOfDoubleArrays.size()][];
                processedVectors = listOfDoubleArrays.toArray(processedVectors);
                setProcessedVector(processedVectors);
                return getMaxDotProduct();
        }

        public static int getMaxDotProduct() {
                double maxDotProduct = -Double.MAX_VALUE;
                int associatedPoseIndex = -1; // Index of pose max dot product

                // Iterate over all vectors
                for (int i = 0; i < VectorHandler.processedVectors.length; i++) {
                        double[] currentVector = VectorHandler.processedVectors[i];
                        double currentDotProduct = VectorHandler.dotProduct(storedVector, currentVector);
                        if (currentDotProduct > maxDotProduct) {
                                maxDotProduct = currentDotProduct;
                                associatedPoseIndex = i;
                        }
                }

                // Output results
                System.out.println("Maximum Dot Product: " + maxDotProduct);
                System.out.println("Associated Pose Index: " + associatedPoseIndex);
                return associatedPoseIndex;
        }

        public static double[] manipulateStringArray(String str) {
                String[] strArr = str.split("``");
                double[] arr = new double[strArr.length];
                for (int i = 0; i < strArr.length; i++)
                        arr[i] = Double.parseDouble(strArr[i]);
                return arr;
        }

        public static String indexToName(int index, boolean isPose) throws IOException {
                // Get the i'th line from the file
                String path = "";
                if (isPose)
                        path = "processed_poses.csv";
                else
                        path = "processed_settings.csv";
                FileReader fr = new FileReader(path);
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                for (int i = 1; i < index; i++)
                        line = br.readLine();
                br.close();
                return line.split(",")[0];
        }

        private static String convertDoubleArray(double[] arr) {
                StringBuilder sb = new StringBuilder();
                for (double d : arr) {
                        sb.append(d);
                        sb.append("``");
                }
                return sb.toString();
        }
}
