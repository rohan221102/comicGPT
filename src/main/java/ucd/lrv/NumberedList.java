package ucd.lrv;

import java.io.IOException;

public class NumberedList {

        private int count = 0;
        private String response;
        private String[] lines;

        public NumberedList(String response) {
                this.response = response;
                this.count = response.split("\n").length;
                this.lines = response.split("\n");

                /*
                 * suggestion
                 * this.response = response;
                 * this.lines = response.split("\\n");
                 * this.count = this.lines.length;
                 */
        }

        public static boolean isNumberedList(String response) {
                String[] lines = response.split("\n");
                for (String line : lines) {
                        if (!line.matches("^[0-9]+\\.\\s+.*"))
                                return false;
                }

                return true;
        }

        /**
         * Returns the number of lines in the response. Using natural indexing rather
         * than 0-indexing to be conistent with the user's perspective.
         *
         * @param lineNumber
         * @return the number of lines in the response
         */
        public String getLine(int lineNumber) {
                return lines[lineNumber - 1];
        }

        public int getCount() {
                return count;
        }

        public String getResponse() {
                return response;
        }

        public void makeXML(ConfigurationFile conf) throws IOException {
                XMLGenerator xml = new XMLGenerator();

                String[] captions = new PromptHandler(conf.getApiKey(), conf.getModel(), conf.getCompletionsURL())
                                .generateCaptions(this);

                // Rigidity
                while (captions.length != this.count) {
                        captions = new PromptHandler(conf.getApiKey(), conf.getModel(), conf.getCompletionsURL())
                                        .generateCaptions(this);
                }

                String consolidatedString = "";
                for (int i = 0; i < this.count; i++)
                        consolidatedString += this.getLine(i + 1).substring(i == 0 ? 3 : 0);

                consolidatedString = consolidatedString.replace("DELIMITED_HERE", "");
                String outputOfResponse = "";
                while (!isNumberedList(outputOfResponse) && outputOfResponse.split("\n").length != this.count) {
                        GPTConnection chat = new GPTConnection(conf.getApiKey(), conf.getModel());
                        outputOfResponse = chat.getCompletion(
                                        "Given the following numbered list, generate a triplet which contains a suggest pose for each speaker and a setting, for example: 1. (screaming, laughing, new york) 2. (yelling, crying, park)"
                                                        + consolidatedString,
                                        conf.getCompletionsURL());
                        outputOfResponse = outputOfResponse.replace("NEWLINE", "\n");
                }

                System.out.println(outputOfResponse);

                NumberedList numberedList = new NumberedList(outputOfResponse);

                String[][] poses = new String[count][2];

                for (int i = 0; i < count; i++) {
                        GPTConnection embedding = new GPTConnection(conf.getApiKey(),
                                        conf.getEmbeddingsModel());
                        double[] response = embedding.getEmbedding(
                                        numberedList.getLine(i + 1).split(",")[0],
                                        conf.getEmbeddingLen(), conf.getEmbeddingsURL());
                        VectorHandler.setVector(response);
                        poses[i][0] = VectorHandler.indexToName(VectorHandler.processVector(true), true);
                        response = embedding.getEmbedding(numberedList.getLine(i + 1).split(",")[1],
                                        conf.getEmbeddingLen(), conf.getEmbeddingsURL());
                        VectorHandler.setVector(response);
                        poses[i][1] = VectorHandler.indexToName(VectorHandler.processVector(true), true);
                }

                String[] settings = new String[count];
                for (int i = 0; i < count; i++) {
                        GPTConnection embedding = new GPTConnection(conf.getApiKey(),
                                        conf.getEmbeddingsModel());
                        double[] response = embedding.getEmbedding(numberedList.getLine(i + 1).split(",")[2],
                                        conf.getEmbeddingLen(),
                                        conf.getEmbeddingsURL());
                        VectorHandler.setVector(response);
                        settings[i] = VectorHandler.indexToName(VectorHandler.processVector(false), false);
                }
                xml.generateXML(this, "Prosef", "Connor", captions, poses, settings);
        }
}