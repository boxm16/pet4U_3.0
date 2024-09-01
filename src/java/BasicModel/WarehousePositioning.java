/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicModel;

import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class WarehousePositioning {

    private LinkedHashMap<String, Object> P;
    private LinkedHashMap<String, Object> O;
    private LinkedHashMap<String, Object> KS;
    private LinkedHashMap<String, Object> N;
    private LinkedHashMap<String, Object> M;

    private LinkedHashMap<String, Object> Y;
    private LinkedHashMap<String, Object> A;

    public WarehousePositioning() {

        this.P = new LinkedHashMap<>();
        this.P.put("emptySpace01", "");
        this.P.put("emptySpace02", "");
        this.P.put("emptySpace03", "");
        this.P.put("emptySpace04", "");
        this.P.put("01Π-01", "--");
        this.P.put("01Π-02", "--");
        this.P.put("01Π-03", "--");
        this.P.put("01Π-04", "--");
        this.P.put("01Π-05", "--");
        this.P.put("01Π-06", "--");
        this.P.put("01Π-07", "--");
        this.P.put("01Π-08", "--");
        this.P.put("01Π-09", "--");
        this.P.put("01Π-10", "--");
        this.P.put("01Π-11", "--");
        this.P.put("01Π-12", "--");
        this.P.put("01Π-13", "--");
        this.P.put("01Π-14", "--");
        this.P.put("01Π-15", "--");
        this.P.put("01Π-16", "--");
        this.P.put("emptySpace1", "");
        this.P.put("emptySpace2", "");
        this.P.put("01Π-17", "--");
        this.P.put("01Π-18", "--");
        this.P.put("01Π-19", "--");
        this.P.put("01Π-20", "--");
        this.P.put("01Π-21", "--");
        this.P.put("01Π-22", "--");
        this.P.put("01Π-23", "--");
        this.P.put("01Π-24", "--");
        this.P.put("01Π-25", "--");
        this.P.put("01Π-26", "--");
        this.P.put("01Π-27", "--");

        this.O = new LinkedHashMap<>();

        this.O.put("emptySpace01", "");
        this.O.put("emptySpace02", "");
        this.O.put("emptySpace03", "");
        this.O.put("emptySpace04", "");
        this.O.put("02Ο-01", "--");
        this.O.put("02Ο-02", "--");
        this.O.put("02Ο-03", "--");
        this.O.put("02Ο-04", "--");
        this.O.put("02Ο-05", "--");
        this.O.put("02Ο-06", "--");
        this.O.put("02Ο-07", "--");
        this.O.put("02Ο-08", "--");
        this.O.put("02Ο-09", "--");
        this.O.put("02Ο-10", "--");
        this.O.put("02Ο-11", "--");
        this.O.put("02Ο-12", "--");
        this.O.put("02Ο-13", "--");
        this.O.put("02Ο-14", "--");
        this.O.put("02Ο-15", "--");
        this.O.put("02Ο-16", "--");
        this.O.put("emptySpace1", "");
        this.O.put("emptySpace2", "");
        this.O.put("02Ο-17", "--");
        this.O.put("02Ο-18", "--");
        this.O.put("02Ο-19", "--");
        this.O.put("02Ο-20", "--");
        this.O.put("02Ο-21", "--");
        this.O.put("02Ο-22", "--");
        this.O.put("02Ο-23", "--");
        this.O.put("02Ο-24", "--");
        this.O.put("02Ο-25", "--");
        this.O.put("02Ο-26", "--");
        this.O.put("02Ο-27", "--");

        this.KS = new LinkedHashMap<>();

        this.KS.put("emptySpace01", "");
        this.KS.put("emptySpace02", "");
        this.KS.put("emptySpace03", "");
        this.KS.put("emptySpace04", "");
        this.KS.put("03Ξ-01", "--");
        this.KS.put("03Ξ-02", "--");
        this.KS.put("03Ξ-03", "--");
        this.KS.put("03Ξ-04", "--");
        this.KS.put("03Ξ-05", "--");
        this.KS.put("03Ξ-06", "--");
        this.KS.put("03Ξ-07", "--");
        this.KS.put("03Ξ-08", "--");
        this.KS.put("03Ξ-09", "--");
        this.KS.put("03Ξ-10", "--");
        this.KS.put("03Ξ-11", "--");
        this.KS.put("03Ξ-12", "--");
        this.KS.put("03Ξ-13", "--");
        this.KS.put("03Ξ-14", "--");
        this.KS.put("03Ξ-15", "--");
        this.KS.put("03Ξ-16", "--");
        this.KS.put("emptySpace1", "");
        this.KS.put("emptySpace2", "");
        this.KS.put("03Ξ-17", "--");
        this.KS.put("03Ξ-18", "--");
        this.KS.put("03Ξ-19", "--");
        this.KS.put("03Ξ-20", "--");
        this.KS.put("03Ξ-21", "--");
        this.KS.put("03Ξ-22", "--");
        this.KS.put("03Ξ-23", "--");
        this.KS.put("03Ξ-24", "--");
        this.KS.put("03Ξ-25", "--");
        this.KS.put("03Ξ-26", "--");
        this.KS.put("03Ξ-27", "--");

        this.N = new LinkedHashMap<>();

        this.N.put("emptySpace01", "");
        this.N.put("emptySpace02", "");
        this.N.put("emptySpace03", "");
        this.N.put("emptySpace04", "");
        this.N.put("04Ν-01", "--");
        this.N.put("04Ν-02", "--");
        this.N.put("04Ν-03", "--");
        this.N.put("04Ν-04", "--");
        this.N.put("04Ν-05", "--");
        this.N.put("04Ν-06", "--");
        this.N.put("04Ν-07", "--");
        this.N.put("04Ν-08", "--");
        this.N.put("04Ν-09", "--");
        this.N.put("04Ν-10", "--");
        this.N.put("04Ν-11", "--");
        this.N.put("04Ν-12", "--");
        this.N.put("04Ν-13", "--");
        this.N.put("04Ν-14", "--");
        this.N.put("04Ν-15", "--");
        this.N.put("04Ν-16", "--");
        this.N.put("emptySpace1", "");
        this.N.put("emptySpace2", "");
        this.N.put("04Ν-17", "--");
        this.N.put("04Ν-18", "--");
        this.N.put("04Ν-19", "--");
        this.N.put("04Ν-20", "--");
        this.N.put("04Ν-21", "--");
        this.N.put("04Ν-22", "--");
        this.N.put("04Ν-23", "--");
        this.N.put("04Ν-24", "--");
        this.N.put("04Ν-25", "--");
        this.N.put("04Ν-26", "--");
        this.N.put("04Ν-27", "--");

        this.M = new LinkedHashMap<>();

        this.M.put("emptySpace01", "");
        this.M.put("emptySpace02", "");
        this.M.put("emptySpace03", "");
        this.M.put("emptySpace04", "");
        this.M.put("05Μ-01", "--");
        this.M.put("05Μ-02", "--");
        this.M.put("05Μ-03", "--");
        this.M.put("05Μ-04", "--");
        this.M.put("05Μ-05", "--");
        this.M.put("05Μ-06", "--");
        this.M.put("05Μ-07", "--");
        this.M.put("05Μ-08", "--");
        this.M.put("05Μ-09", "--");
        this.M.put("05Μ-10", "--");
        this.M.put("05Μ-11", "--");
        this.M.put("05Μ-12", "--");
        this.M.put("05Μ-13", "--");
        this.M.put("05Μ-14", "--");
        this.M.put("05Μ-15", "--");
        this.M.put("05Μ-16", "--");
        this.M.put("05Μ-17", "--");
        this.M.put("05Μ-18", "--");
        this.M.put("05Μ-19", "--");
        this.M.put("05Μ-20", "--");
        this.M.put("05Μ-21", "--");
        this.M.put("05Μ-22", "--");
        this.M.put("05Μ-23", "--");
        this.M.put("05Μ-24", "--");
        this.M.put("05Μ-25", "--");
        this.M.put("05Μ-26", "--");
        this.M.put("05Μ-27", "--");
        this.M.put("05Μ-28", "--");
        this.M.put("05Μ-29", "--");

        this.Y = new LinkedHashMap<>();
        this.Y.put("20Υ-01", "--");

        this.A = new LinkedHashMap<>();

        this.A.put("07Α-01", "--");
        this.A.put("07Α-02", "--");
        this.A.put("07Α-03", "--");
        this.A.put("07Α-04", "--");
        this.A.put("07Α-05", "--");
        this.A.put("07Α-06", "--");
        this.A.put("07Α-07", "--");
        this.A.put("07Α-08", "--");
        this.A.put("07Α-09", "--");
        this.A.put("07Α-10", "--");
        this.A.put("07Α-11", "--");
        this.A.put("07Α-12", "--");
        this.A.put("07Α-13", "--");
        this.A.put("07Α-14", "--");
        this.A.put("07Α-15", "--");

    }

    public LinkedHashMap<String, Object> getP() {
        return P;
    }

    public void setP(LinkedHashMap<String, Object> P) {
        this.P = P;
    }

    public LinkedHashMap<String, Object> getO() {
        return O;
    }

    public void setO(LinkedHashMap<String, Object> O) {
        this.O = O;
    }

    public LinkedHashMap<String, Object> getKS() {
        return KS;
    }

    public void setKS(LinkedHashMap<String, Object> KS) {
        this.KS = KS;
    }

    public LinkedHashMap<String, Object> getN() {
        return N;
    }

    public void setN(LinkedHashMap<String, Object> N) {
        this.N = N;
    }

    public LinkedHashMap<String, Object> getM() {
        return M;
    }

    public void setM(LinkedHashMap<String, Object> M) {
        this.M = M;
    }

    public LinkedHashMap<String, Object> getY() {
        return Y;
    }

    public void setY(LinkedHashMap<String, Object> Y) {
        this.Y = Y;
    }
    

    public LinkedHashMap<String, Object> getA() {
        return A;
    }

    public void setA(LinkedHashMap<String, Object> A) {
        this.A = A;
    }

}
