import java.security.spec.ECFieldF2m;
import java.util.*;


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

    public static String list(String input) {
        StringBuilder sb = new StringBuilder();
        if (!input.startsWith("'")) {
            sb.append("(");
        }
        input = input.trim();
        int first = 0;
        int end = 0;
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (String.valueOf(input.charAt(i)).equals("'")) {
                if (count == 0) {
                    first = i + 1;
                }
            }
            if (input.charAt(i) == '(') {
                count++;
            } else if (input.charAt(i) == ')') {
                count--;
            }
            if (count == 0) {
                if (input.charAt(i) == ' ') {
                    end = i - 1;
                    sb.append(input.substring(first, end + 1) + " ");
                    first = i + 1;
                }
            }
        }
        sb.append(input.substring(first, input.length()));
        if (!input.startsWith("'")) {
            sb.append(")");
        }
        return sb.toString();
    }

    public static String nullFunc(String obj) {
        if (obj.equals("()") || obj.equals("nil") || obj.equals("NIL")) {
            return T;
        } else {
            return NIL;
        }
    }

    public static String reverse(String list) {
        if (list == null || list.length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<String>();
        if (list.startsWith("'")) {
            list = list.substring(2, list.length() - 1);
        }
        int len = list.length();
        int first = 0;
        int end = 0;
        int count = 0;
        for (int i = 0; i < len; i++) {
            if (list.charAt(i) == '(') {
                count++;
            } else if (list.charAt(i) == ')') {
                count--;
            }
            if (count == 0) {
                if (list.charAt(i) == ' ') {
                    end = i - 1;
                    result.add(0, list.substring(first, end + 1));
                    first = i + 1;
                }
            }
        }
        result.add(0, list.substring(first, len));
        sb.append("(");
        for (int i = 0; i < result.size(); i++) {
            if (i != 0) {
                sb.append(" " + result.get(i));
            } else {
                sb.append(result.get(i));
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static String sort(String list, String operator) {
        if (list == null || list.length() == 0) {
            return null;
        }
        if (list.startsWith("'")) {
            list = list.substring(2, list.length() - 1);
        }
        String num[] = list.split("\\s+");
        Double []result = new Double[num.length];
        for (int i = 0; i < num.length; i++) {
            result[i] = Double.parseDouble(num[i]);
        }
        if (operator.charAt(1) == '>') {
            Arrays.sort(result, Collections.<Double>reverseOrder());
        } else if (operator.charAt(1) == '<') {
            Arrays.sort(result);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < result.length; i++) {
            if (i != 0) {
                sb.append(" " + result[i]);
            } else {
                sb.append(result[i]);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static String nth(String index, String list) throws Exception{
        if (list == null || list.length() == 0) {
            return null;
        }
        if(index.startsWith("'")) index = index.substring(1);
        if(list.startsWith("'")) list = list.substring(1);
        if(!index.matches("\\+?\\d+")) throw new NTHNotANonNegativeInteger("*** - NTH: " + index + " is not a non-negative integer");
        if(!list.startsWith("(") || !list.endsWith(")")) throw new NTHNotAList("*** - NTH: " + list + " is not a list");
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<String>();
        if (list.startsWith("'")) {
            list = list.substring(2, list.length() - 1);
        }
        int len = list.length();
        int first = 0;
        int end = 0;
        int count = 0;
        for (int i = 0; i < len; i++) {
            if (list.charAt(i) == '(') {
                count++;
            } else if (list.charAt(i) == ')') {
                count--;
            }
            if (count == 0) {
                if (list.charAt(i) == ' ') {
                    end = i - 1;
                    result.add(list.substring(first, end + 1));
                    first = i + 1;
                }
            }
        }
        result.add(list.substring(first, len));
        int num = Integer.parseInt(index);
        return result.get(num);
    }

    public static String numberp(String obj) throws NotAValue, TooFewArguments {
        if (obj == null || obj.length() == 0) {
            throw new TooFewArguments("*** - EVAL: too few arguments given to NUMBERP: (NUMBERP)");
        }
        if (obj.startsWith("'")) return NIL;
        if (obj.equals("NIL") || obj.equals("nil") || obj.equals("()")) return NIL;
        for (int i = 0; i < obj.length(); i++) {
            char c = obj.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable " + obj + " has no value");
            }
        }
        return T;
    }


//    public static int length(String obj) throws TooManyArguments {
//        if(obj.startsWith("'")) obj = obj.substring(1);
//        if(obj.startsWith("\"") && obj.endsWith("\"")) {
//            if(obj.split("\"").length > 2) throw new TooManyArguments("*** - EVAL: too many arguments given to LENGTH: " + obj);
//            return obj.length() - 2;
//        } else {
//            if(obj.startsWith("(") && obj.endsWith(")")) return countElements(obj);
//            else throw new TooManyArguments("*** - EVAL: too many arguments given to LENGTH: " + obj);
//        }
//    }
//
//
//    private static int countElements(String obj) {
//        obj = obj.substring(1, obj.length() - 1);
//        int result = 0;
//        Stack<Character> stack = new Stack<Character>();
//        boolean letterAppear = false;
//        for(int i = 0; i < obj.length(); i++) {
//
//            if(obj.charAt(i) == '(')
//        }
//    }


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

    static class TooFewArguments extends Exception{
        public TooFewArguments(String msg) {
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
        System.out.println(nth("0", "'(a (aa (a)))"));
        System.out.println(member("1", "'( (1) (1) 1 (2 2) 2)"));
    }
}
