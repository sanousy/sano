/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sano;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HowariS
 */
public class Node {

    public String name = "";
    public String value = "";
    public List<Node> children = null;
    public List<Attribute> attributes = null;
    public Node parent = null;

    Node(String name, String value) {
        this.name = name;
        this.value = value;
        this.children = new ArrayList<>();
        this.attributes = new ArrayList<>();
        parent = null;
    }

    public String getAttributeByName(Node n, String name) {
        String nns = name.trim().toLowerCase();
        String value = "";
        for (int i = 0; i < n.attributes.size(); i++) {
            if (nns.compareToIgnoreCase(n.attributes.get(i).name.trim()) == 0) {
                value = n.attributes.get(i).value;
                break;
            }
        }
        return value;
    }
}
