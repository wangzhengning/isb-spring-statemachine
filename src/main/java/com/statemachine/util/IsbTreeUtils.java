package com.statemachine.util;

import java.util.*;

/**
 * Created by zn.wang on 17/4/23.
 */
public class IsbTreeUtils <T>{

    private final IsbTreeUtils.Node<T> root = new IsbTreeUtils.Node<T>(null);
    private final Map<Object, IsbTreeUtils.Node<T>> map = new HashMap<Object, IsbTreeUtils.Node<T>>();
    private final List<IsbTreeUtils.DataWrap<T>> notMapped = new ArrayList<IsbTreeUtils.DataWrap<T>>();

    public IsbTreeUtils.Node<T> getRoot() {
        return root;
    }

    public void add(T data, Object id, Object parent) {
        notMapped.add(new IsbTreeUtils.DataWrap<T>(data, id, parent));
        tryMapping();
    }

    private void tryMapping() {
        int size = notMapped.size();
        Iterator<IsbTreeUtils.DataWrap<T>> iter = notMapped.iterator();
        while(iter.hasNext()) {
            IsbTreeUtils.DataWrap<T> next = iter.next();
            if (next.parent == null) {
                IsbTreeUtils.Node<T> n = new IsbTreeUtils.Node<T>(next.data);
                map.put(next.id, n);
                root.getChildren().add(n);
                iter.remove();
            } else {
                if (map.containsKey(next.parent)) {
                    IsbTreeUtils.Node<T> n = new IsbTreeUtils.Node<T>(next.data);
                    IsbTreeUtils.Node<T> node = map.get(next.parent);
                    map.put(next.id, n);
                    node.getChildren().add(n);
                    iter.remove();
                }
            }
        }
        if (notMapped.size() < size) {
            tryMapping();
        }
    }

    private static class DataWrap<T> {
        final T data;
        final Object id;
        final Object parent;
        public DataWrap(T data, Object id, Object parent) {
            this.data = data;
            this.id = id;
            this.parent = parent;
        }
    }

    public static class Node<T>{
        private final T data;
        private final List<IsbTreeUtils.Node<T>> children;

        public Node(T data) {
            this(data, null);
        }
        public Node(T data, List<IsbTreeUtils.Node<T>> children) {
            this.data = data;
            this.children = children != null ? children : new ArrayList<IsbTreeUtils.Node<T>>();
        }

        public T getData() {
            return data;
        }

        public List<IsbTreeUtils.Node<T>> getChildren() {
            return children;
        }


    }
}



