package LOGIC;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import MODEL.*;

@SuppressWarnings("unchecked")
public class MainProgram {

    private String savedProgrammes_fileName = "SavedProgrammes.txt";

    private DetergentBox detergentBox = new DetergentBox();
    private SoftenerBox softenerBox = new SoftenerBox();
    private LiquidDetergentBox liquidDetergentBox = new LiquidDetergentBox();
    private ScentBox scentBox = new ScentBox();



    private int totalWashingTime = 0;
    private ArrayList<WashProgram> WashingProgrammes = new ArrayList<>();
    private ArrayList<DryProgram> DryingProgrammes = new ArrayList<>();

    public MainProgram() {
        loadProgrammes();
        if (WashingProgrammes.size() == 0) {
            WashingProgrammes.add(new WashProgram("Quick Rinse", 20, 50, 0));
            WashingProgrammes.add(new WashProgram("Short Wash", 35, 50, 50));
            WashingProgrammes.add(new WashProgram("Normal Wash", 75, 75, 50));
            WashingProgrammes.add(new WashProgram("Long Wash", 150, 75, 50));
            WashingProgrammes.add(new WashProgram("Softening", 25, 0, 25));
        }
        if (DryingProgrammes.size() == 0) {
            DryingProgrammes.add(new DryProgram("Normal Dry", 30, 10, 50));
            DryingProgrammes.add(new DryProgram("Quick Dry", 15, 10, 70));
            DryingProgrammes.add(new DryProgram("Normal Dry", 70, 10, 30));
        }
    }

    public void addWashingProgram(String name, int time, int detergentVolume, int softenerVolume) {
        WashingProgrammes.add(new WashProgram(name, time, detergentVolume, softenerVolume));
    }


    public void addDryingProgram(String name, int time, int scentVolume, int temperature) {
        DryingProgrammes.add(new DryProgram(name, time, scentVolume, temperature));
    }

    public ArrayList<WashProgram> getWashingProgrammes() {
        return WashingProgrammes;
    }

    public ArrayList<DryProgram> getDryingProgrammes() {
        return DryingProgrammes;
    }

    //Wczytuje programy do prania i suszenia z pliku zapisu
    private void loadProgrammes() {
        try {
            File file = new File(savedProgrammes_fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Map<String, ArrayList> map;
            map = (Map<String,ArrayList>)objectInputStream.readObject();
            WashingProgrammes = map.get("Washing");
            DryingProgrammes = map.get("Drying");
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Loading failed...");
        }

    }

    //zapisuje programy do prania i suszenia do pliku zapisu
    public void saveProgrammes() {
        try {
            File file = new File(savedProgrammes_fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            Map<String, ArrayList> map = new HashMap<>();
            map.put("Washing", WashingProgrammes);
            map.put("Drying", DryingProgrammes);
            objectOutputStream.writeObject(map);
            objectOutputStream.flush();

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("Saving failed...");
        }

    }

    private void syphonDetergent(WashProgram _washProgram, DetergentType _detergentType) throws NotEnoughResourceException {
        if (_detergentType == DetergentType.POWDER) {
            if (!detergentBox.syphonOut(_washProgram.getDetergentVolume()))
                throw new NotEnoughResourceException("Not enough detergent! ");
        } else if(_detergentType == DetergentType.LIQUID){
            if (!liquidDetergentBox.syphonOut(_washProgram.getDetergentVolume()))
                throw new NotEnoughResourceException("Not enough liquid detergent!");
        }
    }

    private void syphonSoftener(WashProgram _washProgram) throws NotEnoughResourceException {
        if (!softenerBox.syphonOut(_washProgram.getSoftenerVolume()))
            throw new NotEnoughResourceException("Not enough softener!");
    }

    private void syphonScent(DryProgram _dryProgram) throws NotEnoughResourceException {
        if (!scentBox.syphonOut(_dryProgram.getDetergentVolume())) {
            throw new NotEnoughResourceException("Not enough scent!");
        }
    }

    public void StartWashing(WashProgram _washProgram, DetergentType _detergentType, DryProgram _dryProgram) throws NotEnoughResourceException {
        if (_washProgram != null) {
            syphonDetergent(_washProgram, _detergentType);

            syphonSoftener(_washProgram);
            totalWashingTime += _washProgram.getRemainingTime();
        }
        if (_dryProgram != null) {
            syphonScent(_dryProgram);
            totalWashingTime += _dryProgram.getTime();
        }

    }

    public int currentDetergentLevel(){
            return detergentBox.checkLevel();
    }

    public int currentSoftenerLevel(){
        return softenerBox.checkLevel();
    }

    public int currentLiquidDetergentLevel(){
        return liquidDetergentBox.checkLevel();
    }

    public int currentScentLevel(){
        return scentBox.checkLevel();
    }

    public int getTotalWashingTime() {
        return totalWashingTime;
    }

    public void decreaseTime(){
        totalWashingTime--;
    }

    public void setResources(int _detergent, int _softener, int _liquidDetergent, int _scent){
        detergentBox.fillByAmount(_detergent);
        softenerBox.fillByAmount(_softener);
        liquidDetergentBox.fillByAmount(_liquidDetergent);
        scentBox.fillByAmount(_scent);
    }

    public void maxFill() {
        detergentBox.fill();
        softenerBox.fill();
        liquidDetergentBox.fill();
        scentBox.fill();
    }

}
