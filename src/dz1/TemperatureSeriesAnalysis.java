/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz1;

import java.util.InputMismatchException;

/**
 *
 * @author rob / Nikitin Vladimir
 */
public class TemperatureSeriesAnalysis {
    private final int memoryReSizer = 2;        /*memory reallocation ratio must be greater than 1 !!*/
    private final int startMemorySize = 20;     /* start memory size for double array , i take it from the sky */
    private final double absoluteZero = 273.00;
    private int curentSize ;            /*curent size of temp array */
    private int maxSize ;               /*max heap size allocated for array curently */
    private double [] temperatureStore;         /* here we store temperatures holded at this class*/
    
    public final void TemperatureSeriesAnalysis(){          
        curentSize = 0;
        maxSize = startMemorySize;
        temperatureStore = new double [maxSize];
    }
    TemperatureSeriesAnalysis(double temperature){
        this.TemperatureSeriesAnalysis();
        
        double [] tempTemp = new double [1];
        tempTemp[0]= temperature;
        addTemps(tempTemp);
    }
    public void TemperatureSeriesAnalysis(double [] temperature){
        this.TemperatureSeriesAnalysis();
        
        addTemps(temperature);
    }

    public final int addTemps(double[] temps) {
        if (temps.length==0){
            return curentSize; 
        }
        
        for (int i=0;i<temps.length;i++)
            if(temps[i]<absoluteZero){
                throw new InputMismatchException();
            }
        while (curentSize+temps.length>=maxSize){
            maxSize *= memoryReSizer;
        }
        double [] newTempStore = new double [maxSize];          /* new memory piece */
        for (int i=0;i<curentSize;i++){
            newTempStore[i] = temperatureStore[i];
        }
        for (int i=curentSize;i<curentSize+temps.length;i++){
            newTempStore[i] = temps[i-curentSize];
        }
        temperatureStore = newTempStore;
        curentSize += temps.length;
        return curentSize;
    }

    class TempSummaryStatistics {
        public double avgTemp;
        public double devTemp;
        public double minTemp;
        public double maxTemp;
        public TempSummaryStatistics(){
            
        }
        public TempSummaryStatistics(double avgTemp, double devTemp, double minTemp, double maxTemp){
            this.avgTemp = avgTemp;
            this.devTemp = devTemp;
            this.minTemp = minTemp;
            this.maxTemp = maxTemp;
        }
    }
    TempSummaryStatistics summaryStatistics(){
        TempSummaryStatistics statistic = new TempSummaryStatistics ();
        statistic.avgTemp = this.average();
        statistic.devTemp = this.deviation();
        statistic.minTemp = this.min();
        statistic.maxTemp = this.max();
        return statistic;
    }
    
    public double average() {
        if (curentSize == 0){
                    throw new IllegalArgumentException();
        }
        double summator = 0;
        for (int i=0;i<curentSize;i++){
            summator += temperatureStore[i];
        }
        return summator / curentSize;
    }

    public double deviation() {
        if (curentSize == 0){
                    throw new IllegalArgumentException();
        }
        double avgTemp = this.average();
        double summator = 0;
        for (int i=0;i<curentSize;i++){
            summator += (temperatureStore[i]-avgTemp)*(temperatureStore[i]-avgTemp);
        }
        return summator / curentSize;
    }

    public double min() {
        if (curentSize == 0){
                    throw new IllegalArgumentException();
        }
        double curentMin = temperatureStore[0];
        for (int i=0;i<curentSize;i++){
            if (curentMin > temperatureStore[i]){
                curentMin = temperatureStore[i];
            }
        }
        return curentMin;
    }
    public double max() {
        if (curentSize == 0){
                    throw new IllegalArgumentException();
        }
        double curentMax = temperatureStore[0];
        for (int i=0;i<curentSize;i++){
            if (curentMax > temperatureStore[i]){
                curentMax = temperatureStore[i];
            }
        }
        return curentMax;
    }
    public double findTempClosestToZero(){
        if (curentSize == 0){
                    throw new IllegalArgumentException();
        }
        double minAbs = Math.abs(temperatureStore[0]);
        for (int i=0;i<curentSize;i++){
            if (minAbs > Math.abs(temperatureStore[i])){
                minAbs = Math.abs(temperatureStore[i]);
            }
        }
        return minAbs;
    }
    public double findTempClosestToValue(double tempValue){
        if (curentSize == 0){
                    throw new IllegalArgumentException();
        }
        double minAbs = Math.abs(temperatureStore[0] - tempValue);
        double minValue = temperatureStore[0];
        for (int i=0;i<curentSize;i++){
            if (minAbs > Math.abs(temperatureStore[i]) - tempValue){
                minAbs = Math.abs(temperatureStore[i] - tempValue);
                minValue = temperatureStore[i];
            }
        }
        return minValue;
    }
    public double[] findTempsLessThen(double tempValue){
        /*Возвращает массив со значениями температуры меньше указанного tempValue. Если ряд пустой генерирует IllegalArgumentException.*/
        double [] tempLess = new double [curentSize];
        int lessIndex = 0;
        for (int i =0;i<curentSize;i++){
            if (temperatureStore[i] < tempValue){
                tempLess[lessIndex] = temperatureStore[i];
                lessIndex++;
            }
        }
        return tempLess;
    }
    public double[] findTempsGreaterThen(double tempValue){
        /*Возвращает массив со значениями температуры больше либо равно указанного tempValue. Если ряд пустой генерирует IllegalArgumentException.*/
        double [] tempGreater = new double [curentSize];
        int greaterIndex = 0;
        for (int i =0;i<curentSize;i++){
            if (temperatureStore[i] > tempValue){
                tempGreater[greaterIndex] = temperatureStore[i];
                greaterIndex++;
            }
        }
        return tempGreater;
    }
}
