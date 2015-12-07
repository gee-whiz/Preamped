package preamped.empirestate.co.za.preamped.loadshedding;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by George Kapoya on 2015-05-04.
 */
public class Users implements List<Users>{

    public String  userID;

    public Users(String userID) {
        this.userID = userID;
    }

    @Override
    public void add(int location, Users object) {

    }

    @Override
    public boolean add(Users object) {
        return false;
    }

    @Override
    public boolean addAll(int location, Collection<? extends Users> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Users> collection) {
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
    public Users get(int location) {
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
    public Iterator<Users> iterator() {
        return null;
    }

    @Override
    public int lastIndexOf(Object object) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<Users> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<Users> listIterator(int location) {
        return null;
    }

    @Override
    public Users remove(int location) {
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
    public Users set(int location, Users object) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @NonNull
    @Override
    public List<Users> subList(int start, int end) {
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
