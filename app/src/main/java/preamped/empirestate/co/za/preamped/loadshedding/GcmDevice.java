package preamped.empirestate.co.za.preamped.loadshedding;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by George Kapoya on 2015-04-30.
 */
public class GcmDevice implements List<GcmDevice> {

    public   String deviceID;

    public GcmDevice(String deviceID) {
        this.deviceID = deviceID;
    }


    @Override
    public void add(int location, GcmDevice object) {

    }

    @Override
    public boolean add(GcmDevice object) {
        return false;
    }

    @Override
    public boolean addAll(int location, Collection<? extends GcmDevice> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends GcmDevice> collection) {
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
    public GcmDevice get(int location) {
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
    public Iterator<GcmDevice> iterator() {
        return null;
    }

    @Override
    public int lastIndexOf(Object object) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<GcmDevice> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<GcmDevice> listIterator(int location) {
        return null;
    }

    @Override
    public GcmDevice remove(int location) {
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
    public GcmDevice set(int location, GcmDevice object) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @NonNull
    @Override
    public List<GcmDevice> subList(int start, int end) {
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
