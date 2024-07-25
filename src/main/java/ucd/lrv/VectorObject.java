package ucd.lrv;

public class VectorObject {

        private double[] embedding;
        private int index;
        private boolean isPose;

        public VectorObject(double[] embedding, int index, boolean isPose) {
                this.embedding = embedding;
                this.index = index;
                this.isPose = isPose;
        }

        public double[] getEmbedding() {
                return embedding;
        }

        public int getIndex() {
                return index;
        }

        public boolean isPose() {
                return isPose;
        }

        public void setPose(boolean pose) {
                isPose = pose;
        }
}
