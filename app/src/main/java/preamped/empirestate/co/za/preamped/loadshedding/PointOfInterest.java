package preamped.empirestate.co.za.preamped.loadshedding;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by George  kapoya on 2015-05-13.
 */
public class PointOfInterest implements List<PointOfInterest> {

    public   String locationName;
    public Double latitude;
    public Double longitude;
    public   String thumbnailUrl;
    public   String markerText;
    public   List<PointOfInterest> PointOfIntrestList = new ArrayList<>();



    public PointOfInterest( Double latitude, Double longitude,String locationName, String markerText, String thumbnailUrl) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.markerText = markerText;
        this.thumbnailUrl = thumbnailUrl;
    }


    @Override
    public void add(int location, PointOfInterest object) {

    }

    @Override
    public boolean add(PointOfInterest object) {
        return false;
    }

    @Override
    public boolean addAll(int location, Collection<? extends PointOfInterest> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends PointOfInterest> collection) {
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
    public PointOfInterest get(int location) {
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
    public Iterator<PointOfInterest> iterator() {
        return null;
    }

    @Override
    public int lastIndexOf(Object object) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<PointOfInterest> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<PointOfInterest> listIterator(int location) {
        return null;
    }

    @Override
    public PointOfInterest remove(int location) {
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
    public PointOfInterest set(int location, PointOfInterest object) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @NonNull
    @Override
    public List<PointOfInterest> subList(int start, int end) {
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
