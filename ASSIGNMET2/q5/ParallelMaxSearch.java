public class ParallelMaxSearch extends SensorSearchEngine {

    public ParallelMaxSearch(double[] sensorData, int startIdx, int endIdx) {
        super(sensorData, startIdx, endIdx);
    }

    @Override
    public double processGrid() {
        double maxVal = -Double.MAX_VALUE;
        for (int i = startIdx; i < endIdx; i++) {
            if (sensorData[i] > maxVal) {
                maxVal = sensorData[i];
            }
        }
        return maxVal;
    }
}