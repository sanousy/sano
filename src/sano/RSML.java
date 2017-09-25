package sano;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileNotFoundException;
import java.io.IOException;

public class RSML {

    public Node root = null;
    int i = 0;
    int cap = 0;
    boolean docEnd = false;
    int level = 0;
    Node current = null;
    Node parent = null;
    StringBuilder content = null;
    boolean debug = false;

    public void read(String inFile) throws FileNotFoundException, IOException {
        root = new Node("", "");
        TextFile input = new TextFile(inFile, TextFile.Type.READ);
        content = input.readAllText();

        boolean newTag = false;
        boolean contentStart = true;
        boolean contentFirst = false;
        boolean specialChar = false;
        Stack<Node> st = new Stack<>();
        System.out.println(st.Top());
        cap = content.length();
        String tagName = "";
        i = 0;

        while (!docEnd) {
            switch (cc()) {
                case '-':
                    if (specialChar) {
                        specialChar = false;
                    } else {
                        tagName = getTagName();
                        parent = st.peep();
                        current = new Node(tagName, "");
                        String paretnName = "";
                        if (parent != null) {
                            paretnName = parent.name;
                            current.parent = parent;
                            parent.children.add(current);
                            if (debug) {
                                System.out.println(tagName + " is going to be son of parent:" + paretnName);
                            }
                        } else {
                            root.children.add(current);
                            if (debug) {
                                System.out.println("Adding " + current.name + " as a root");
                            }
                        }

                        st.push(current);
                        newTag = true;
                        contentStart = false;
                        specialChar = false;
                        current.value = "";
                    }
                    break;
                case '[':
                    if (specialChar) {
                        specialChar = false;
                    } else {
                        contentFirst = true;
                        contentStart = true;
                    }
                    break;
                case ']':
                    if (specialChar) {
                        specialChar = false;
                    } else {
                        current = st.pop();
                        parent = st.peep();
                        if (debug) {
                            System.out.println(current.name + " is closed now; with value :" + current.value);
                        }

                        newTag = false;
                        contentStart = false;
                        tagName = "";
                    }
                    break;
                case '\\':
                    specialChar = true;
                    break;
                default:
                    if (specialChar) {
                        specialChar = false;
                    } else {
                        if (newTag && !contentStart) {
                            getAttributes();
                        }
                    }
                    break;
            }
            if (contentStart) {
                if (contentFirst) {
                    contentFirst = false;
                } else if (!specialChar) {
                    current.value += cc();
                }
            }
            ++i;
            if (i >= cap) {
                docEnd = true;
            }
        }

        input.close();

    }

    char cc() {
        char ret = 0;

        try {
            ret = content.charAt(i);
        } catch (Exception e) {

        }

        return ret;
    }

    String getTagName() {
        String tagName = "";
        boolean gotIt = false;
        ++i;
        do {
            if (i < cap - 1) {
                if ("[]- ".indexOf(cc()) == -1) {
                    tagName += cc();
                    i++;
                } else {
                    if (cc() != ' ') {
                        i--;
                    }
                    gotIt = true;
                }
            } else {
                docEnd = true;
            }
        } while (!docEnd && !gotIt);
        return tagName.toLowerCase();
    }

    void getAttributes() {
        String name = "";
        String temp = "";

        boolean quote = false;
        boolean noMore = false;
        boolean newAtt = false;
        boolean special = false;
        do {
            char c = cc();
            if (i < cap - 1) {
                switch (c) {
                    case '\'':
                        if (quote) {
                            current.attributes.add(new Attribute(name.toLowerCase(), temp));
                            newAtt = false;
                            temp = "";
                            quote = false;
                        } else {
                            quote = true;
                        }
                        break;

                    case ' ':
                    case '\r':
                    case '\n':
                    case '\t':
                        if (quote) {
                            temp += c;
                        } else {
                            // ignore speces
                        }
                        break;
                    case '[':
                        noMore = true;
                        if (newAtt) {
                            current.attributes.add(new Attribute(name.toLowerCase(), temp));
                            newAtt = false;
                        }
                        i--;

                        break;
                    case '=':
                        if (quote) {
                            temp += c;
                        } else {
                            name = temp;
                            temp = "";
                        }
                        break;
                    default:
                        newAtt = true;
                        temp += c;
                }
            }
            /* if ("[-".indexOf(c) == -1) {
                if (c == '=') {
                    equal = true;
                    name = temp.trim().toLowerCase();
                    temp = "";
                    i++;
                } else if (c == ' ') {
                    maybe = true;
                    i++;
                } else {
                    if (c == '\'') {
                        i++;
                    } else if (maybe) {
                        maybe = false;
                        equal = false;
                        temp = temp.trim().toLowerCase();

                        if (!temp.equals("")) {
                            current.attributes.add(new Attribute(name.toLowerCase(), temp));
                        }
                        temp = "";
                    } else {
                        temp += c;
                        i++;
                    }
                }
            } else {
                temp = temp.trim().toLowerCase();

                if (!temp.equals("")) {
                    current.attributes.add(new Attribute(name, temp));
                }
                gotIt = true;
                i--;
            }
        } else {
                docEnd = true;
            }

    }
    while (!docEnd && !gotIt
             */

            if (!noMore) {
                i++;
            }
            if (i > content.length()) {
                docEnd = true;
            }
        } while (!noMore && !docEnd);
        current = current;
    }

}
