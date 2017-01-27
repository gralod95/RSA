/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcaclient;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author macbook
 */
public class Brain {
    public static int getNewRandom(){
        Random ran = new SecureRandom();
        return ran.nextInt(100);
    }
    public static int modExp(int b, int e, int m){//число/степень/модуль
       BigInteger b1, e1, m1;

	// create a BigInteger exponent

        b1 = new BigInteger(Integer.toString(b));
        e1 = new BigInteger(Integer.toString(e));
        m1 = new BigInteger(Integer.toString(m));
        // perform modPow operation on bi1 using bi2 and exp
	b1 = b1.modPow(e1, m1);
        int res = b1.intValue();
        return res;
    }
    
    public static void getM(String setB){
       // DiffiHellmanClient.B = Integer.valueOf(setB);
        //DiffiHellmanClient.K = modExp(DiffiHellmanClient.B, DiffiHellmanClient.a, DiffiHellmanClient.p);
        //System.out.println("k = "+DiffiHellmanClient.K );
    }
    
    public static int hashCode(int code, int i){
        return modExp(code+i,RCAClient.e,RCAClient.n);
    }
}
