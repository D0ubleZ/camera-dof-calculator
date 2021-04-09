package com.example.myapplication.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LensManager implements Iterable<Lens>{
    private int check = 0;
    List<Lens> Lenses = new ArrayList<>();

    public int getCheck() {
        return check;
    }
    public void setCheck(int check) {
        this.check = check;
    }

    private static LensManager Instance;
    private LensManager() {}
    public static LensManager getInstance(){
        if (Instance == null)
            Instance = new LensManager();
        return Instance;
    }
    /* add new lens */
    public void add(Lens L){
        Lenses.add(L);
    }
    /* retrieving a specific lens by its index */
    public Lens getLens(int index){
        return Lenses.get(index);
    }
    public void remove(int n) {
        Lenses.remove(n);
    }
    @Override
    /* iterable over all lenses */
    public Iterator<Lens> iterator() {
        return Lenses.iterator();
    }
    public int size(){
        return Lenses.size();
    }

    public String output(int n){
        return Lenses.get(n).getMake()+" "
                    + Double.toString(Lenses.get(n).getMax_aperture())
                    + "mm F" + Integer.toString(Lenses.get(n).getFocal_length());

    }
}

