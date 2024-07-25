package ucd.lrv;

public interface RequestInterface {
    // completion endpoint
    public void setKey(String key);

    public void setModel(String model);

    public String getCompletion(String prompt);

    public double[] getEmbedding(String prompt);
}
