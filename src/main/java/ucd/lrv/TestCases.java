package ucd.lrv;

public class TestCases {
        final static String ANSI_RESET = "\u001B[0m";
        final static String ANSI_RED = "\u001B[31m";
        final static String ANSI_GREEN = "\u001B[32m";
        final static String ANSI_BOLD = "\u001B[1m";

        public static void testFunctionalitySprint2() {
                ConfigurationFile testCase_1 = ConfigurationFile
                                .initialiseConfigurationFile("testCases/TestCase_1.conf");
                ConfigurationFile testCase_2 = ConfigurationFile
                                .initialiseConfigurationFile("testCases/TestCase_2.conf");
                ConfigurationFile testCase_3 = ConfigurationFile
                                .initialiseConfigurationFile("testCases/TestCase_3.conf");

                // TEST CASE 1
                System.out.println("Test Case 1: Detect DOS: ");
                System.out.println("Expected:" + testCase_1.getExpected());
                GPTConnection test1_chat = new GPTConnection(testCase_1.getApiKey(), testCase_1.getModel());
                String test1_response = test1_chat.getCompletion(testCase_1.getPrompt(),
                                testCase_1.getCompletionsURL());
                System.out.println("Actual: " + test1_response);
                System.out.println("Result: "
                                + (test1_response.contains(testCase_1.getExpected()) ? ANSI_BOLD + ANSI_GREEN + "PASS"
                                                : ANSI_BOLD + ANSI_RED + "FAIL"));
                System.out.println(ANSI_RESET);

                // TEST CASE 2
                System.out.println("Test Case 2: Detect Numbered List: ");
                System.out.println("Expected:" + testCase_2.getExpected());
                GPTConnection test2_chat = new GPTConnection(testCase_2.getApiKey(), testCase_2.getModel());
                String test2_response = test2_chat.getCompletion(testCase_2.getPrompt(),
                                testCase_2.getCompletionsURL());
                String test2_detection = NumberedList.isNumberedList(test2_response) ? "PASS" : "FAIL";
                System.out.println("Actual: " + test2_detection);
                System.out.println("Result: "
                                + (test2_detection.contains(testCase_2.getExpected()) ? ANSI_BOLD + ANSI_GREEN + "PASS"
                                                : ANSI_BOLD + ANSI_RED + "FAIL"));
                System.out.println(ANSI_RESET);
                NumberedList newNumberedList = new NumberedList(test2_response);
                System.out.println("Number of lines: " + newNumberedList.getCount());
                System.out.println("First line: " + newNumberedList.getLine(1));
                System.out.println("Second line: " + newNumberedList.getLine(2));
                System.out.println();

                // TEST CASE 3
                System.out.println("Test Case 3: Detect Normal Input: ");
                System.out.println("Expected:" + "FAIL (Detection of numbered list)");
                GPTConnection test3_chat = new GPTConnection(testCase_3.getApiKey(), testCase_3.getModel());
                String test3_response = test3_chat.getCompletion(testCase_3.getPrompt(),
                                testCase_3.getCompletionsURL());
                String test3_detection = NumberedList.isNumberedList(test3_response) ? "PASS" : "FAIL";
                System.out.println("Actual: " + test3_detection);
                System.out.println("Result: "
                                + (test3_detection.contains("FAIL") ? ANSI_BOLD + ANSI_GREEN + "PASS"
                                                : ANSI_BOLD + ANSI_RED + "FAIL"));
                System.out.println(ANSI_RESET);
        }

        public static void testFunctionalitySprint3() {
                System.out.println("If the output is GENERATED XML FILE: output.xml, the test has passed.");
        }

        public static void testFunctionalitySprint4() {
                // VectorHandler
                System.out.println("VectorHandler Test Cases:");
                double[] testVector = { 1.0, 2.0, 3.0, 4.0, 5.0 };
                VectorHandler.setVector(testVector);
                System.out.println("Expected: [1.0, 2.0, 3.0, 4.0, 5.0]");
                System.out.println("Actual: " + VectorHandler.getVector());

                // NumberedList
                System.out.println("NumberedList Test Cases:");
                String testResponse = "1. Test\n2. Test\n3. Test\n";
                NumberedList testNumberedList = new NumberedList(testResponse);
                System.out.println("Expected: 3");
                System.out.println("Actual: " + testNumberedList.getCount());
                System.out.println("Expected: 1. Test");
                System.out.println("Actual: " + testNumberedList.getLine(1));

                // Dot product
                System.out.println("DotProduct Test Cases:");
                double[] testVector1 = { 1.0, 2.0, 3.0, 4.0, 5.0 };
                double[] testVector2 = { 1.0, 2.0, 3.0, 4.0, 5.0 };
                System.out.println("Expected: 55.0");
                System.out.println("Actual: " + VectorHandler.dotProduct(testVector1, testVector2));

        }
}
