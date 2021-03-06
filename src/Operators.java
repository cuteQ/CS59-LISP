import java.util.*;


/**
 * Created by qianyuzhong on 11/15/16.
 */
public class Operators {
    private static final String NIL = "NIL";
    private static final String T = "T";

    public static String add(List<String> parameters) throws WrongParameter {
        double result = 0;
        for(String str: parameters) {
            result += Double.parseDouble(str);
        }
        return String.valueOf(result);
    }

    public static String subtract(List<String> parameters) {
        double result = 0;
        for(String str: parameters) {
            result -= Double.parseDouble(str);
        }
        return String.valueOf(result);
    }

    public static String multiply(List<String> parameters) {
        double result = 0;
        for(String str: parameters) {
            result *= Double.parseDouble(str);
        }
        return String.valueOf(result);
    }

    public static String divide(List<String> parameters) throws DividedByZero {
        double result = 0;
        for(String str: parameters) {
            result /= Double.parseDouble(str);
        }
        return String.valueOf(result);
    }

    public static String car(List<String> parameters) {
        String list = parameters.get(0);
        if(list.startsWith("'")) list = list.substring(1);
        list = list.substring(1, list.length() - 1);
        list = list.replaceAll("\\s+", " ");
        return list.split(" ")[0].toUpperCase();
    }

    public static String cdr(List<String> parameters) {
        String list = parameters.get(0);
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

    public static String cons(List<String> parameters) {
        String obj1 = parameters.get(0);
        String obj2 = parameters.get(1);
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

    public static String atom(List<String> parameters) throws TooManyArguments {
        String obj = parameters.get(0);
        if(obj.startsWith("(") && obj.endsWith(")")) return NIL;
        if(obj.split(" ").length > 1) throw new TooManyArguments("*** - EVAL: too many arguments given to ATOM: (ATOM '1 2)");
        return T;
    }

    public static String not(List<String> parameters) {
        String obj = parameters.get(0);
        if(obj.equals(NIL) || obj.equals("()")) return T;
        else return NIL;
    }

    public static String lessThan(List<String> parameters) throws NotAValue {
        String obj1 = parameters.get(0);
        String obj2 = parameters.get(1);
        if(obj1.startsWith("'")) obj1 = obj1.substring(1);
        if(obj2.startsWith("'")) obj2 = obj2.substring(1);
        if(!obj1.matches("-?\\d+(\\.\\d+)?")) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable" + obj1.toUpperCase() + "has no value");
        else if(!obj2.matches("-?\\d+(\\.\\d+)?")) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable" + obj2.toUpperCase() + "has no value");
        else if(Double.parseDouble(obj1) < Double.parseDouble(obj2)) return T;
        else return NIL;
    }

    public static String largeThan(List<String> parameters) throws NotAValue {
        String obj1 = parameters.get(0);
        String obj2 = parameters.get(1);
        if(obj1.startsWith("'")) obj1 = obj1.substring(1);
        if(obj2.startsWith("'")) obj2 = obj2.substring(1);
        if(!obj1.matches("-?\\d+(\\.\\d+)?")) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable" + obj1.toUpperCase() + "has no value");
        else if(!obj2.matches("-?\\d+(\\.\\d+)?")) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable" + obj2.toUpperCase() + "has no value");
        else if(Double.parseDouble(obj1) > Double.parseDouble(obj2)) return T;
        else return NIL;
    }

    public static String eql(List<String> parameters) throws NotAValue {
        String obj1 = parameters.get(0);
        String obj2 = parameters.get(1);
        if(!obj1.startsWith("'") && !obj1.matches("-?\\d+(\\.\\d+)?")) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable" + obj1.toUpperCase() + "has no value");
        if(!obj2.startsWith("'") && !obj2.matches("-?\\d+(\\.\\d+)?")) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable" + obj2.toUpperCase() + "has no value");
        if(obj1.startsWith("'")) obj1 = obj1.substring(1);
        if(obj2.startsWith("'")) obj2 = obj2.substring(1);
        if(obj1.equals(obj2)) return T;
        return NIL;
    }

    public static String member(List<String> parameters) {
        String item = parameters.get(0);
        String obj = parameters.get(1);
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


    public static String append(List<String> parameters) throws Exception{
        String obj = "";
        for (int i = 0; i < parameters.size(); i++) {
            if (i != 0) {
                obj += " " + parameters.get(i);
            } else {
                obj += parameters.get(i);
            }
        }
        StringBuilder sb = new StringBuilder();
        obj = obj.trim();
        int first = 0;
        int end = 0;
        int count = 0;
        int numOfParent = 0;
        sb.append("(");
        for (int i = 0; i < obj.length(); i++) {
            if (String.valueOf(obj.charAt(i)).equals("'")) {
                if (count == 0) {
                    first = i + 1;
                }
            }
            if (obj.charAt(i) == '(') {
                count++;
            } else if (obj.charAt(i) == ')') {
                count--;
            }
            if (count == 0) {
                if (obj.charAt(i) == ' ') {
                    end = i - 1;
                    String res = obj.substring(first, end + 1);
                    if (res.startsWith("(") && res.endsWith(")")) {
                        res = res.substring(1, res.length() - 1);
                    } else {
                        numOfParent++;
                        if (numOfParent > 1) throw new NotAList("*** - APPEND: " + res + " is not a list");
                    }
                    sb.append(res + " ");
                    first = i + 1;
                }
            }
        }
        String res = obj.substring(first, obj.length());
        if (res.startsWith("(") && res.endsWith(")")) {
            res = res.substring(1, res.length() - 1);
        } else {
            numOfParent++;
            if (numOfParent > 1) throw new NotAList("*** - APPEND: " + res + " is not a list");
        }
        sb.append(res + ")");
        return sb.toString();
    }

    public static String list(List<String> parameters) {
        String input = "";
        for (int i = 0; i < parameters.size(); i++) {
            if (i != 0) {
                input += " " + parameters.get(i);
            } else {
                input += parameters.get(i);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("(");
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
                    sb.append(input.substring(first, end + 1).trim() + " ");
                    first = i + 1;
                }
            }
        }
        sb.append(input.substring(first, input.length()));
        sb.append(")");
        return sb.toString();
    }

    public static String nullFunc(List<String> parameters) {
        String obj = parameters.get(0);
        if (obj.equals("()") || obj.equals("nil") || obj.equals("NIL")) {
            return T;
        } else {
            return NIL;
        }
    }

    public static String reverse(List<String> parameters) {
        String list = parameters.get(0);
        if (list == null || list.length() == 0) {
            return NIL;
        }
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<String>();
        if (list.startsWith("'")) {
            list = list.substring(2, list.length() - 1);
        }
        list = list.trim();
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
                    result.add(0, list.substring(first, end + 1).trim());
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

    public static String sort(List<String> parameters) {
        String list = parameters.get(0);
        String operator = parameters.get(1);
        if (list == null || list.length() == 0) {
            return NIL;
        }
        if (list.startsWith("'")) {
            list = list.substring(2, list.length() - 1);
        }
        if (operator.startsWith("'")) operator = operator.substring(1);
        String num[] = list.split("\\s+");
        Double []result = new Double[num.length];
        for (int i = 0; i < num.length; i++) {
            result[i] = Double.parseDouble(num[i]);
        }
        if (operator.charAt(0) == '>') {
            Arrays.sort(result, Collections.<Double>reverseOrder());
        } else if (operator.charAt(0) == '<') {
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

    public static String nth(List<String> parameters) throws Exception{
        String index = parameters.get(0);
        String list = parameters.get(1);
        if (list == null || list.length() == 0) {
            return NIL;
        }
        if (index.startsWith("'")) index = index.substring(1);
        if (list.startsWith("'")) list = list.substring(1);
        if(!index.matches("\\+?\\d+")) throw new NTHNotANonNegativeInteger("*** - NTH: " + index + " is not a non-negative integer");
        list = list.substring(1, list.length() - 1);
        List<String> result = new ArrayList<>();

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

    public static String numberp(List<String> parameters) throws Exception {
        String obj = parameters.get(0);
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

    public static String consp(List<String> parameters) throws Exception{
        String obj = parameters.get(0);
        if (obj.equals("NIL") || obj.equals("nil")) return NIL;
        obj = obj.trim();
        if (!obj.startsWith("'")) {
            if (obj.split("\\s+").length > 1) {
                throw new TooManyArguments("*** - EVAL: too many arguments given to CONSP: (CONSP " + obj + ")");
            }
            for (int i = 0; i < obj.length(); i++) {
                char c = obj.charAt(i);
                if (!(c >= '0' && c <= '9')) throw new NotAValue("*** - SYSTEM::READ-EVAL-PRINT: variable " + obj + " has no value");
            }
            return NIL;
        } else {
            obj = obj.substring(1);
            if (!obj.startsWith("(") || !obj.endsWith(")")) {
                if (obj.split("\\s+").length > 1) {
                    throw new TooManyArguments("*** - EVAL: too many arguments given to CONSP: (CONSP " + obj + ")");
                }
                return NIL;
            }
            if (obj.equals("()")) return NIL;
            return T;
        }
    }

    public static String length(List<String> parameters) throws TooManyArguments {
        String obj = parameters.get(0);
        if(obj.startsWith("'")) obj = obj.substring(1);
        if(obj.startsWith("\"") && obj.endsWith("\"")) {
            if(obj.split("\"").length > 2) throw new TooManyArguments("*** - EVAL: too many arguments given to LENGTH: " + obj);
            return String.valueOf(obj.length() - 2);
        } else {
            if(obj.startsWith("(") && obj.endsWith(")")) return countElements(obj);
            else throw new TooManyArguments("*** - EVAL: too many arguments given to LENGTH: " + obj);
        }
    }

    private static String countElements(String obj) throws TooManyArguments {
        obj = obj.substring(1, obj.length() - 1);
        int result = 0;
        obj = obj.replaceAll("\\s+", " ");
        int count = 0;
        int i = 0;
        while(i < obj.length()) {
            if(count == 0) {
                while (i < obj.length() && (obj.charAt(i) != '(' && obj.charAt(i) != ')' && obj.charAt(i) != ' ')) {
                    i++;
                }
                if(i == obj.length()) break;

                if(obj.charAt(i) == '(') {
                    count++;
                    i++;
                    continue;
                } else if(obj.charAt(i) == ')') {
                    count--;
                    break;
                } else if(obj.charAt(i) == ' ') i++;
                result++;
            } else {
                if (obj.charAt(i) == '(') count++;
                else if(obj.charAt(i) == ')') count--;
                i++;
            }
        }
        if(count != 0) throw new TooManyArguments("*** - EVAL: too many arguments given to LENGTH: " + obj);
        return String.valueOf(result);
    }

    static class WrongParameter extends Exception {
        public WrongParameter(String msg){
            super(msg);
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

    static class TooFewArguments extends Exception{
        public TooFewArguments(String msg) {
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

    static class NotAList extends Exception{
        public NotAList(String msg) {
            super(msg);
        }
    }

    static class UndefinedFunction extends Exception {
        public UndefinedFunction(String msg){
            super(msg);
        }
    }
}
