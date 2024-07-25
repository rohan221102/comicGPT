package ucd.lrv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationFile {

        /**
         * Read a file called ConfigurationFile.config
         * Handle all of the configuration settings for the program, including the API
         * key
         * Store all of these as an object, and use getters to access them
         * 
         * Example file:
         * 
         * COMPLETIONS_URL https://api.openai.com/v1/chat/completions
         * EMBEDDINGS_URL https://api.openai.com/v1/embeddings
         * MODELS_URL https://api.openai.com/v1/models
         * API_KEY sk-gol4pk1m5BwUEtSTJeSwxDktKVDNeTXmyJ66T3BlbkF
         * MODEL gpt-3.5-turbo-0301
         * EMBEDDINGS_MODEL text-embedding-ada-002
         * EMBEDDING_LEN 1536
         * PROMPT ""
         * IS_EMBEDDING false
         * EXPECTED "" // ONLY FOR TEST CASES
         */

        private String completionsURL;
        private String embeddingsURL;
        private String modelsURL;
        private String apiKey;
        private String model;
        private String embeddingsModel;
        private int embeddingLen;
        private String prompt;
        private boolean isEmbedding;
        private String expected; // ONLY FOR TEST CASES

        public ConfigurationFile(String completionsURL, String embeddingsURL, String modelsURL, String apiKey,
                        String model, String embeddingsModel, int embeddingLen, String prompt, boolean isEmbedding,
                        String expected) {
                this.completionsURL = completionsURL;
                this.embeddingsURL = embeddingsURL;
                this.modelsURL = modelsURL;
                this.apiKey = apiKey;
                this.model = model;
                this.embeddingsModel = embeddingsModel;
                this.embeddingLen = embeddingLen;
                this.prompt = prompt;
                this.isEmbedding = isEmbedding;
                this.expected = expected;
        }

        public String getCompletionsURL() {
                return completionsURL;
        }

        public String getEmbeddingsURL() {
                return embeddingsURL;
        }

        public String getModelsURL() {
                return modelsURL;
        }

        public String getApiKey() {
                return apiKey;
        }

        public String getModel() {
                return model;
        }

        public String getEmbeddingsModel() {
                return embeddingsModel;
        }

        public int getEmbeddingLen() {
                return embeddingLen;
        }

        public String getPrompt() {
                return prompt;
        }

        public boolean isEmbedding() {
                return isEmbedding;
        }

        public String getExpected() {
                return expected;
        }

        // setters

        private void setCompletionsURL(String completionsURL) {
                this.completionsURL = completionsURL;
        }

        private void setEmbeddingsURL(String embeddingsURL) {
                this.embeddingsURL = embeddingsURL;
        }

        private void setModelsURL(String modelsURL) {
                this.modelsURL = modelsURL;
        }

        private void setApiKey(String apiKey) {
                this.apiKey = apiKey;
        }

        private void setModel(String model) {
                this.model = model;
        }

        private void setEmbeddingsModel(String embeddingsModel) {
                this.embeddingsModel = embeddingsModel;
        }

        private void setEmbeddingLen(int embeddingLen) {
                this.embeddingLen = embeddingLen;
        }

        public void setPrompt(String prompt) {
                this.prompt = prompt;
        }

        private void setEmbedding(boolean isEmbedding) {
                this.isEmbedding = isEmbedding;
        }

        private void setExpected(String expected) {
                this.expected = expected;
        }

        public static ConfigurationFile initialiseConfigurationFile(String filename) {
                try {
                        BufferedReader br = new BufferedReader(new FileReader(filename));
                        String line;
                        ConfigurationFile config = new ConfigurationFile("", "", "", "", "", "", 0, "", false, "");
                        while ((line = br.readLine()) != null) {
                                String[] parts = line.split(" ");
                                switch (parts[0]) {
                                        case "COMPLETIONS_URL":
                                                config.setCompletionsURL(parts[1]);
                                                break;
                                        case "EMBEDDINGS_URL":
                                                config.setEmbeddingsURL(parts[1]);
                                                break;
                                        case "MODELS_URL":
                                                config.setModelsURL(parts[1]);
                                                break;
                                        case "API_KEY":
                                                config.setApiKey(parts[1]);
                                                break;
                                        case "MODEL":
                                                config.setModel(parts[1]);
                                                break;
                                        case "EMBEDDINGS_MODEL":
                                                config.setEmbeddingsModel(parts[1]);
                                                break;
                                        case "EMBEDDING_LEN":
                                                config.setEmbeddingLen(Integer.parseInt(parts[1]));
                                                break;
                                        case "PROMPT":
                                                config.setPrompt(line.substring(line.indexOf("\"") + 1,
                                                                line.lastIndexOf("\"")));
                                                break;
                                        case "IS_EMBEDDING":
                                                config.setEmbedding(Boolean.parseBoolean(parts[1]));
                                                break;
                                        case "EXPECTED":
                                                config.setExpected(line.substring(line.indexOf("\"") + 1,
                                                                line.lastIndexOf("\"")));
                                                break;
                                }
                        }
                        br.close();
                        return config;
                } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                }
        }

        public static void printConfigurationFile(ConfigurationFile config) {
                System.out.println("COMPLETIONS_URL: " + config.getCompletionsURL());
                System.out.println("EMBEDDINGS_URL: " + config.getEmbeddingsURL());
                System.out.println("MODELS_URL: " + config.getModelsURL());
                System.out.println("API_KEY: " + config.getApiKey());
                System.out.println("MODEL: " + config.getModel());
                System.out.println("EMBEDDINGS_MODEL: " + config.getEmbeddingsModel());
                System.out.println("EMBEDDING_LEN: " + config.getEmbeddingLen());
                System.out.println("PROMPT: " + config.getPrompt());
                System.out.println("IS_EMBEDDING: " + config.isEmbedding());
        }
};
