public class ParallelMinSearch extends SensorSearchEngine {

    public ParallelMinSearch(double[] sensorData, int startIdx, int endIdx) {
        super(sensorData, startIdx, endIdx);
    }

    @Override
    public double processGrid() {
        double minVal = Double.MAX_VALUE;
        for (int i = startIdx; i < endIdx; i++) {
            if (sensorData[i] < minVal) {
                minVal = sensorData[i];
            }
        }
        return minVal;
    }
}