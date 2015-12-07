package preamped.empirestate.co.za.preamped.loadshedding;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by George Kapoya on 2015-06-09.
 */
public class Suburbs implements List<Suburbs> {

    public  String surburbName;
    public String groupName;
    public String groupId;

    public Suburbs(String surburbName, String groupName, String groupId) {
        this.surburbName = surburbName;
        this.groupName = groupName;
        this.groupId = groupId;
    }

    public  Suburbs(){

}


    @Override
    public void add(int location, Suburbs object) {

    }

    @Override
    public boolean add(Suburbs object) {
        return false;
    }

    @Override
    public boolean addAll(int location, Collection<? extends Suburbs> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Suburbs> collection) {
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
    public Suburbs get(int location) {
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
    public Iterator<Suburbs> iterator() {
        return null;
    }

    @Override
    public int lastIndexOf(Object object) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<Suburbs> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<Suburbs> listIterator(int location) {
        return null;
    }

    @Override
    public Suburbs remove(int location) {
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
    public Suburbs set(int location, Suburbs object) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @NonNull
    @Override
    public List<Suburbs> subList(int start, int end) {
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
