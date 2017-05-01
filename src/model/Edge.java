
package model;

/**
 *
 * @author Mr.Chanok Pathompatai
 */

public class Edge {
    
    Vertex1 v1;
    Vertex1 v2;

    public Edge(Vertex1 v1, Vertex1 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Vertex1 getV1() {
        return v1;
    }

    public Vertex1 getV2() {
        return v2;
    }
    
}
