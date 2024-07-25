package ucd.lrv;

import java.util.ArrayList;

public class Context {
    // curl https://api.openai.com/v1/chat/completions \

    // HEADER: defined in initialiseConnection, stored in a HTTP URL connection
    // object
    // -H "Content-Type: application/json" \
    // -H "Authorization: Bearer $OPENAI_API_KEY" \

    // body: init in getCompletion, model from ConfigurationFile through Connection
    // object)
    // -d '{
    // "model": "gpt-3.5-turbo",
    // "messages": [
    // {
    // "role": "system",
    // "content": "You are a helpful assistant."
    // },
    // {
    // "role": "user",
    // "content": "Hello!"
    // }
    // ]
    // }'
/*
    private static Context instance;  // Singleton testing
*/

    /**/
    private String apiKey;
    private String gptModel;
    private ArrayList<Message> messages;
    private String initString = "If you cannot fulfill the request, please respond with NOT ALLOWED, including sports events and profanity. "
            + "If you are going to provide a numbered list, please do not provide any preamble and just provide the numbered list.";

    public Context(String apiKey, String gptModel) {
        this.apiKey = apiKey;
        this.gptModel = gptModel;
        messages = new ArrayList<Message>();
        messages.add(new Message("system", initString));
    }

    // current integration of singleton to make it one single instance unmodifiable
    /*
        private Context(String apiKey, String gptModel) {
            this.apiKey = apiKey;
            this.gptModel = gptModel;
            messages = new ArrayList<Message>();
            messages.add(new Message("system", initString));
        }

        public static Context getInstance(String apiKey, String gptModel) {
            if (instance == null) {
                instance = new Context(apiKey, gptModel);
            }
            return instance;
        }*/
    public void setAPIKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAPIKey() {
        return apiKey;
    }

    public void setModel(String model) {
        gptModel = model;
    }

    public String getModel() {
        return gptModel;
    }

    public String getRequestBody() {
        String requestBody = "{\"model\": \"" + gptModel + "\", \"messages\": [";
        for (Message message : messages) {
            requestBody += message.toString() + ",";
        }
        requestBody = requestBody.substring(0, requestBody.length() - 1);
        requestBody += "]}";
        return requestBody;
    }

    // adds a message with role user to the messages array
    public void addPrompt(String prompt) {
        messages.add(new Message("user", prompt));
    }

    // adds a message with role assistant to the messages array
    public void addResponse(String prompt) {
        messages.add(new Message("assistant", prompt));
    }

}
