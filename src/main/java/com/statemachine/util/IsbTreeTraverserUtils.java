package com.statemachine.util;

import org.springframework.util.Assert;

import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * Created by zn.wang on 17/4/23.
 */
public abstract class IsbTreeTraverserUtils<T> {

    /**
     * Return the children of the specified node. Must not contain null.
     *
     * @param root the node
     * @return child iterables
     */
    public abstract Iterable<T> children(T root);


    /**
     * 后序遍历
     */
    public final Iterable<T> postOrderTraversal(final T root) {
        Assert.notNull(root);
        return new Iterable<T>() {
            @Override
            public Iterator iterator() {
                return postOrderIterator(root);
            }
        };
    }

    Iterator<T> postOrderIterator(T root) {
        return new PostOrderIterator(root);
    }

    private final class PostOrderIterator extends IsbAbstractIterator<T> {

        private final ArrayDeque<IsbTreeTraverserUtils.PostOrderNode<T>> stack;

        PostOrderIterator(T root) {
            this.stack = new ArrayDeque<IsbTreeTraverserUtils.PostOrderNode<T>>();
            stack.addLast(expand(root));
        }

        @Override
        protected T computeNext() {
            while (!stack.isEmpty()) {
                IsbTreeTraverserUtils.PostOrderNode<T> top = stack.getLast();
                if (top.childIterator.hasNext()) {
                    T child = top.childIterator.next();
                    stack.addLast(expand(child));
                } else {
                    stack.removeLast();
                    return top.root;
                }
            }
            return endOfData();
        }

        private IsbTreeTraverserUtils.PostOrderNode<T> expand(T t) {
            return new IsbTreeTraverserUtils.PostOrderNode<T>(t, children(t).iterator());
        }
    }


    private static final class PostOrderNode<T> {
        final T root;
        final Iterator<T> childIterator;

        PostOrderNode(T root, Iterator<T> childIterator) {
            Assert.notNull(root);
            Assert.notNull(childIterator);
            this.root = root;
            this.childIterator = childIterator;
        }
    }


}
