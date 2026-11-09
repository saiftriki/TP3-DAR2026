package sharedPackage;

import java.io.Serializable;

public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;

    private double op1;
    private String op;
    private double op2;


    public Operation(double op1, String op, double op2) {
        this.op1 = op1;
        this.op = op;
        this.op2 = op2;
    }

    public double getOp1() {return op1;}
    public String getOp() {return op;}
    public double getOp2() {return op2;}


    @Override
    public String toString() {
        return op1 + " " + op + " " + op2;
    }
}
