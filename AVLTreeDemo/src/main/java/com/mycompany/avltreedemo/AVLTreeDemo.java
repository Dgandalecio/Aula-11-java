/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.avltreedemo;

/**
 *
 * @author diogo
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class AVLTree {
    private Node root;

    private static class Node {
        int key;
        Node left, right;
        int height;

        Node(int key) {
            this.key = key;
            this.height = 1; 
        }
    }

    
    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node node, int key) {
        if (node == null) return new Node(key);

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            return node; 
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        return balance(node);
    }

  
    public void remove(int key) {
        root = remove(root, key);
    }

    private Node remove(Node node, int key) {
        if (node == null) return null;

        if (key < node.key) {
            node.left = remove(node.left, key);
        } else if (key > node.key) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node temp = getMinValueNode(node.right);
            node.key = temp.key;
            node.right = remove(node.right, temp.key);
        }

        if (node == null) return null;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        return balance(node);
    }

    private Node getMinValueNode(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private Node balance(Node node) {
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (balance < -1) {
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateLeft(Node z) {
        Node y = z.right;
        Node T2 = y.left;

        y.left = z;
        z.right = T2;

        z.height = 1 + Math.max(height(z.left), height(z.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    private Node rotateRight(Node z) {
        Node y = z.left;
        Node T3 = y.right;

        y.right = z;
        z.left = T3;

        z.height = 1 + Math.max(height(z.left), height(z.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    public void inOrderTraversal() {
        List<String> result = new ArrayList<>();
        inOrderTraversal(root, result);
        result.forEach(System.out::println);
    }

    private void inOrderTraversal(Node node, List<String> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add("Valor: " + node.key + ", Fator de Balanceamento: " + getBalance(node));
            inOrderTraversal(node.right, result);
        }
    }
}

public class AVLTreeDemo {
    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        Random random = new Random();

       
        for (int i = 0; i < 100; i++) {
            avlTree.insert(random.nextInt(1001) - 500);
        }

        System.out.println("Árvore AVL após inserção dos 100 números:");
        avlTree.inOrderTraversal();

       
        List<Integer> numbersToRemove = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            numbersToRemove.add(random.nextInt(1001) - 500);
        }

        for (int num : numbersToRemove) {
            avlTree.remove(num);
        }

        System.out.println("\n Arvore AVL apOs remocao dos 20 numeros:");
        avlTree.inOrderTraversal();
    }
}
