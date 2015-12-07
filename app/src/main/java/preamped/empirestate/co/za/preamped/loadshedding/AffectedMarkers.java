package preamped.empirestate.co.za.preamped.loadshedding;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by George Kapoya on 2015-05-19.
 */
public class AffectedMarkers implements List<AffectedMarkers>   {

    public String markerName;
    public   String endTime;


    public AffectedMarkers() {

    }

    public AffectedMarkers(String markerName,String endTime) {
        this.markerName = markerName;
        this.endTime = endTime;
    }

    @Override
    public void add(int location, AffectedMarkers object) {

    }

    @Override
    public boolean add(AffectedMarkers object) {
        return false;
    }

    @Override
    public boolean addAll(int location, Collection<? extends AffectedMarkers> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends AffectedMarkers> collection) {
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
    public AffectedMarkers get(int location) {
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
    public Iterator<AffectedMarkers> iterator() {
        return null;
    }

    @Override
    public int lastIndexOf(Object object) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<AffectedMarkers> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<AffectedMarkers> listIterator(int location) {
        return null;
    }

    @Override
    public AffectedMarkers remove(int location) {
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
    public AffectedMarkers set(int location, AffectedMarkers object) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @NonNull
    @Override
    public List<AffectedMarkers> subList(int start, int end) {
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
