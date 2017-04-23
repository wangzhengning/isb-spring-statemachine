package com.statemachine.util;

import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by zn.wang on 17/4/23.
 */
public abstract class IsbAbstractIterator<T> implements Iterator<T> {

    private IsbAbstractIterator.State state = IsbAbstractIterator.State.NOT_READY;

    private enum State {
        READY, NOT_READY, DONE, FAILED,
    }

    private T next;

    protected abstract T computeNext();

    protected final T endOfData() {
        state = IsbAbstractIterator.State.DONE;
        return null;
    }


    @Override
    public boolean hasNext() {
        Assert.state(state != IsbAbstractIterator.State.FAILED);
        switch (state) {
            case DONE:
                return false;
            case READY:
                return true;
            default:
        }
        return tryToComputeNext();
    }

    private boolean tryToComputeNext() {
        state = IsbAbstractIterator.State.FAILED;
        next = computeNext();
        if (state != IsbAbstractIterator.State.DONE) {
            state = IsbAbstractIterator.State.READY;
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        state = IsbAbstractIterator.State.NOT_READY;
        T result = next;
        next = null;
        return result;
    }

    public final T peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return next;
    }


    public final void remove() {
        throw new UnsupportedOperationException("remove");
    }


}
