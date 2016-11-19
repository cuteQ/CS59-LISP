package basicFunction;

import java.security.spec.ECFieldF2m;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by qianyuzhong on 11/15/16.
 */
public class Operators {
    private static final String NIL = "NIL";
    private static final String T = "T";
    // Case '+'
    public static int add(int num1, int num2) {
        System.out.print("int");
        return num1 + num2;
    }

    public static double add(double num1, double num2) {
        System.out.print("double");
        return num1 + num2;
    }

    public static int subtract(int num1, int num2) {
        System.out.print("int");
        return num1 - num2;
    }

    public static double substract(double num1, double num2) {
        System.out.print("double");
        return num1 - num2;
    }

    public static int multiply(int num1, int num2) {
        System.out.print("int");
        return num1 * num2;
    }

    public static double multiply(double num1, double num2) {
        System.out.print("double");
        return num1 * num2;
    }

    public static int divide(int num1, int num2) throws DividedByZero {
        System.out.print("int");
        if(num2 == 0) throw new DividedByZero("*** - /: division by zero");
        return num1 / num2;
    }

    public static double divide(double num1, double num2) {
        System.out.print("double");
        return num1 / num2;
    }

    public static String car(String list) {
        if(list.startsWith("'")) list = list.substring(1);
        list = list.substring(1, list.length() - 1);
        list = list.replaceAll("\\s+", " ");
        return list.split(" ")[0].toUpperCase();
    }

    public static String cdr(String list) {
        if(list.startsWith("'")) list = list.substring(1);
        list = list.substring(1, list.length() - 1);
        String[] lists = list.replaceAll("\\s+", " ").split(" ");
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for(int i = 1; i < lists.length; i++) {
            sb.append(lists[i]);
            if(i != lists.length - 1) sb.append(" ");
        }
        sb.append(")");
        return sb.toString().toUpperCase();
    }

    public static String cons(String obj1, String obj2) {
        if(obj1.startsWith("'")) obj1 = obj1.substring(1);
        if(obj2.startsWith("'")) obj2 = obj2.substring(1);
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(obj1);
        boolean flag = false;
        if(obj2.split(" ").length == 1 && !obj2.startsWith("(")) flag = true;
        if(flag) sb.append(" . "); else sb.append(" ");
        if(obj2.startsWith("(")) {
            sb.append(obj2.substring(1, obj2.length() - 1));
        } else sb.append(obj2);
        sb.append(")");
        return sb.toString().toUpperCase();
    }

    public static String atom(String obj) throws TooManyArguments {
        if(obj.startsWith("(") && obj.endsWith(")")) return NIL;
        if(obj.split(" ").length > 1) throw new TooManyArguments("*** - EVAL: too many arguments given to ATOM: (ATOM '1 2)");
        return T;
    }

    public static String not(String obj) {
        if(obj.equals(NIL) || obj.equals("()")) return T;
        else return NIL;
    }

    public static String lessThan(String obj1, String obj2) throws NotAValue {
        if(obj1.startsWith("'")) obj1 = obj1.substring(1);
        if(obj2.startsWith("'")) obj2 = obj2.substring(1);
        if(!obj1.matches("-?\\d+(\\.\\d+)?")) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable" + obj1.toUpperCase() + "has no value");
        else if(!obj2.matches("-?\\d+(\\.\\d+)?")) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable" + obj2.toUpperCase() + "has no value");
        else if(Double.parseDouble(obj1) < Double.parseDouble(obj2)) return T;
        else return NIL;
    }

    public static String largeThan(String obj1, String obj2) throws NotAValue {
        if(obj1.startsWith("'")) obj1 = obj1.substring(1);
        if(obj2.startsWith("'")) obj2 = obj2.substring(1);
        if(!obj1.matches("-?\\d+(\\.\\d+)?")) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable" + obj1.toUpperCase() + "has no value");
        else if(!obj2.matches("-?\\d+(\\.\\d+)?")) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable" + obj2.toUpperCase() + "has no value");
        else if(Double.parseDouble(obj1) > Double.parseDouble(obj2)) return T;
        else return NIL;
    }

    public static String eql(String obj1, String obj2) throws NotAValue {
        if(!obj1.startsWith("'") && !obj1.matches("-?\\d+(\\.\\d+)?")) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable" + obj1.toUpperCase() + "has no value");
        if(!obj2.startsWith("'") && !obj2.matches("-?\\d+(\\.\\d+)?")) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable" + obj2.toUpperCase() + "has no value");
        if(obj1.startsWith("'")) obj1 = obj1.substring(1);
        if(obj2.startsWith("'")) obj2 = obj2.substring(1);
        if(obj1.equals(obj2)) return T;
        return NIL;
    }

//    public static String append(String obj, String obj2) {
//
//    }

    public static String nth(String index, String list) throws Exception {
        if(index.startsWith("'")) index = index.substring(1);
        if(list.startsWith("'")) list = list.substring(1);
        if(!index.matches("\\+?\\d+")) throw new NTHNotANonNegativeInteger("*** - NTH: " + index + " is not a non-negative integer");
        if(!list.startsWith("(") || !list.endsWith(")")) throw new NTHNotAList("*** - NTH: " + list + " is not a list");
        int num = Integer.parseInt(index);
        String[] elements = list.substring(1, list.length() - 1).split(" ");
        if(num >= elements.length) return NIL;
        return elements[num];
    }

    public static String member(String item, String obj) {
        if(item.startsWith("'")) item = item.substring(1);
        if(obj.startsWith("'")) obj = obj.substring(1);
        if(item.startsWith("(")) return NIL;
        String[] elements = obj.substring(1, obj.length() - 1).split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < elements.length; i++) {
            if(item.equals(elements[i])) {
                sb.append("(");
                for(int j = i; j < elements.length; j++) {
                    sb.append(elements[j]);
                    if(j != elements.length - 1) sb.append(" ");
                }
                sb.append(")");
            }
        }
        return sb.length() == 0 ? NIL: sb.toString();
    }

    public static int length(String obj) throws TooManyArguments {
        if(obj.startsWith("'")) obj = obj.substring(1);
        if(obj.startsWith("\"") && obj.endsWith("\"")) {
            if(obj.split("\"").length > 2) throw new TooManyArguments("*** - EVAL: too many arguments given to LENGTH: " + obj);
            return obj.length() - 2;
        } else {
            if(obj.startsWith("(") && obj.endsWith(")")) return countElements(obj);
            else throw new TooManyArguments("*** - EVAL: too many arguments given to LENGTH: " + obj);
        }
    }

    private static int countElements(String obj) {
        obj = obj.substring(1, obj.length() - 1);
        int result = 0;
        Stack<Character> stack = new Stack<Character>();
        boolean letterAppear = false;
        for(int i = 0; i < obj.length(); i++) {

            if(obj.charAt(i) == '(')
        }
    }

    static class DividedByZero extends Exception {
        public DividedByZero(String msg){
            super(msg);
        }
    }

    static class TooManyArguments extends Exception{
        public  TooManyArguments(String msg) {
            super(msg);
        }
    }

    static class NotAValue extends Exception{
        public NotAValue(String msg) {
            super(msg);
        }
    }

    static class NTHNotANonNegativeInteger extends Exception{
        public NTHNotANonNegativeInteger(String msg) {
            super(msg);
        }
    }

    static class NTHNotAList extends Exception{
        public NTHNotAList(String msg) {
            super(msg);
        }
    }

    static class LengthTooManyArg extends Exception {
        public LengthTooManyArg(String msg) {
            super(msg);
        }
    }

    public static void main(String[] strs) throws Exception {
        int num1 = 21;
        double num2 = 2.0;
        String s = "(s         s b c (d   e))";
        s = s.replaceAll("\\s+", " ");
        System.out.println(divide(num1, num2));
        System.out.println(car(s));
        System.out.println(cdr(s).toUpperCase());
        System.out.println(cons("1", "(2)"));
        System.out.println(eql("1", "'2.0"));
        System.out.println(nth("1", "'(a)"));
        System.out.println(member("1", "'( (1) (1) 1 (2 2) 2)"));
    }
}
