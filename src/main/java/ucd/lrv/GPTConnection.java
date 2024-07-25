package ucd.lrv;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class GPTConnection {
    private Context context;
    private String apiKey;
    private String gptModel;
    private String url;

    // add initString to method signature
    GPTConnection(String apiKey, String gptModel) {
        // this.apiKey = apiKey;
        // this.gptModel = gptModel;
        // this.url = url;
        context = new Context(apiKey, gptModel);
//        this.context = Context.getInstance(apiKey,gptModel); testing for singleton

    }

    public void setKey(String key) {
        context.setAPIKey(key);
    }

    public void setModel(String model) {
        context.setModel(model);
    }

    /**
     * Sends a prompt to the OpenAI API and returns the response as a JSON string
     * 
     * @param prompt the prompt to send to the API
     * @param url    the URL of the API endpoint
     * @return the response from the API as a JSON string, or stacktrace if an error
     *         occurs
     */
    public String getCompletion(String prompt, String url) {
        // replace this with context object
        // String requestBody = "{\"model\": \"" + gptModel + "\", \"messages\":
        // [{\"role\": \"user\", \"content\": \""
        // + "If you cannot fulfill the request, please respond with NOT ALLOWED,
        // including sports events and profanity. "
        // + "If you are going to provide a numbered list, please do not provide any
        // preamble and just provide the numbered list. "
        // + prompt + "\"}]}";

        context.addPrompt(prompt);
        String requestBody = context.getRequestBody();

        // getResponse (removed due to middle man status)
        HttpURLConnection connection = initialiseConnection(url);
        sendRequestToAPI(connection, requestBody);
        String jsonString = getResponseFromAPI(connection);

        int firstChar = jsonString.indexOf("content") + 11;
        int lastChar = jsonString.indexOf("\"", firstChar);
        String response = jsonString.substring(firstChar, lastChar);
        context.addResponse(response);
        return response;
    }

    public double[] getEmbedding(String prompt, int embdeddingsLength, String url) {
        String requestBody = "{\"input\": \"" + prompt + "\", \"model\": \"" + context.getModel() + "\"}";

        HttpURLConnection connection = initialiseConnection(url);
        sendRequestToAPI(connection, requestBody);
        String jsonString = getResponseFromAPI(connection);
        double[] embeddings = parseVector(jsonString, embdeddingsLength);
        return embeddings;
    }

    private HttpURLConnection initialiseConnection(String url) {
        try {
            URL object_url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) object_url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + context.getAPIKey());
            connection.setRequestProperty("Content-type", "application/json");
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendRequestToAPI(HttpURLConnection connection, String prompt) {
        try {
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(prompt);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getResponseFromAPI(HttpURLConnection connection) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null)
                response.append(line);
            response = new StringBuffer(response.toString().replace("\\n", "\n"));
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private double[] parseVector(String jsonString, int embdeddingsLength) {
        double[] embeddings = new double[embdeddingsLength];
        int firstChar = jsonString.indexOf("[");
        int lastChar = jsonString.lastIndexOf("]") + 1;
        jsonString = jsonString.substring(firstChar, lastChar);
        JSONArray jsonArray = new JSONArray(jsonString);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        jsonArray = jsonObject.getJSONArray("embedding");

        for (int i = 0; i < embdeddingsLength; i++)
            embeddings[i] = jsonArray.getDouble(i);

        return embeddings;
    }
}
