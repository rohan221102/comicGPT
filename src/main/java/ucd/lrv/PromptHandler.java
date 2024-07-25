package ucd.lrv;

public class PromptHandler {

    private GPTConnection gptConnection;
    private String completionsURL;

    public PromptHandler(String apiKey, String model, String completionsURL) {
        gptConnection = new GPTConnection(apiKey, model);
        this.completionsURL = completionsURL;
    }

    public String[] generateCaptions(NumberedList numberedList) {
        String captionPrompt;
        String[] captions = new String[numberedList.getCount()];
        for (int i = 1; i <= numberedList.getCount(); i++) {
            captionPrompt = "You are Yoda, using lots and lots of Yoda-isms, narrating a debate between two opponents. Their arguments are separated by the word DELIMITED_HERE. Write a single-sentence caption for the following arguments: "
                    + numberedList.getLine(i);
            captions[i - 1] = gptConnection.getCompletion(captionPrompt, completionsURL);
        }
        return captions;
    }
}
