package preamped.empirestate.co.za.preamped.loadshedding;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by George Kapoya on 2015-04-14.
 */
public class SavedLocations implements List<SavedLocations> {

    public String locName;
    public  int id;
    public Double latitude;
    public Double longitude;

 public SavedLocations(String locName,int id,double latitude,double longitude){
     this.locName = locName;
     this.id = id;
     this.latitude = latitude;
     this.longitude = longitude;
 }

    public  SavedLocations(){

    }

    @Override
    public void add(int location, SavedLocations object) {

    }

    @Override
    public boolean add(SavedLocations object) {
        return false;
    }

    @Override
    public boolean addAll(int location, Collection<? extends SavedLocations> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends SavedLocations> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public SavedLocations get(int location) {
        return null;
    }

    @Override
    public int indexOf(Object object) {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NonNull
    @Override
    public Iterator<SavedLocations> iterator() {
        return null;
    }

    @Override
    public int lastIndexOf(Object object) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<SavedLocations> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<SavedLocations> listIterator(int location) {
        return null;
    }

    @Override
    public SavedLocations remove(int location) {
        return null;
    }

    @Override
    public boolean remove(Object object) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public SavedLocations set(int location, SavedLocations object) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @NonNull
    @Override
    public List<SavedLocations> subList(int start, int end) {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
        return null;
    }


}
