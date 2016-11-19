import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qianyuzhong on 11/18/16.
 */
public class Controller {
    public static void main(String[] strs) throws Exception {
//        String expression = "(funcall (lambda (x y) (+ x y 100)) 40 20)";
//        String expression = "(+ (nth 3 '(7 9 (23 1) 12)) (+ 1 (+ 2 3) 2) (+ 2 3))";
//        String expression = "(funcall (lambda (x y) (+  (nth x '(7 9 (23 1) 12)) (+ 1 (+ 2 3) 2) (+ y 3))) 3 100)";
//        String expression = "(funcall (lambda (x y) (+ (car (nth x '(7 9 (23 1) 12))) (+ 1 (+ 2 3) 2) (+ y 3))) 2 100)";
//        String expression = "(funcall (lambda (x y z) (sort '(x y 100 z) '<)) 40 20 12)";
//        String expression = "(funcall (lambda (x y z) (reverse '(x y 100 z))) 40 20 12)";
//        String expression = "(funcall (lambda (x y z) (car (cdr '(x y 100 z)))) 40 20 12)";
        String expression = "(funcall (lambda (x y z) (append (cdr '(x y 100 z)) 'a)) 40 20 12)";
        String regularExpression = funcall(expression);

        System.out.println(regularExpression);
        String result = parse(regularExpression);
        System.out.println(result);
    }

    public static final String NIL = "NIL";
    public static String funcall(String obj) throws Exception {
        if (!obj.startsWith("(") || !obj.endsWith(")")) return NIL;
        obj = obj.trim();
        obj = obj.substring(1, obj.length() - 1);
        int first = 0;
        int end = 0;
        int count = 0;
        first = obj.indexOf('(');
        count++;
        String operator = obj.substring(0, first).trim();
        if (!operator.equals("funcall")) {
            throw new UndefinedFunction("*** - EVAL: undefined function " + operator);
        }
        List<String> res = new ArrayList<>();
        for (int i = first+1; i < obj.length(); i++) {
            if (obj.charAt(i) == '(') {
                count++;
            } else if (obj.charAt(i) == ')') {
                count--;
            }
            if (count == 0) {
                end = i;
                res.add(obj.substring(first + 1, end));
                res.add(obj.substring(end + 1, obj.length()).trim());
                break;
            }
        }
        return lambda(res.get(0), res.get(1));
    }

    public static String lambda(String exp, String para) throws Exception {
        int first = 0;
        int end = 0;
        int count = 0;
        first = exp.indexOf('(');
        count++;
        List<String> res = new ArrayList<>();
        for (int i = first+1; i < exp.length(); i++) {
            if (exp.charAt(i) == '(') {
                count++;
            } else if (exp.charAt(i) == ')') {
                count--;
            }
            if (count == 0) {
                if (exp.charAt(i) == ' ') {
                    end = i - 1;
                    res.add(exp.substring(first + 1, end));
                    first = i + 1;
                }
            }
        }
        res.add(exp.substring(first, exp.length()));
        String []p = res.get(0).split("\\s+");
        List<String> v = new ArrayList<>();
        first = 0;
        end = 0;
        count = 0;
        for (int i = 0; i < para.length(); i++) {
            if (para.charAt(i) == '(') {
                count++;
            } else if (para.charAt(i) == ')') {
                count--;
            }
            if (count == 0) {
                if (para.charAt(i) == ' ') {
                    end = i - 1;
                    v.add(para.substring(first, end + 1));
                    first = i + 1;
                }
            }
        }
        v.add(para.substring(first, para.length()));
        if (v.size() != p.length) throw new Operators.TooManyArguments("*** - EVAL/APPLY: too many arguments given to :LAMBDA");
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < p.length; i++) {
            map.put(p[i], v.get(i));
        }
        String result = res.get(1);
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = map.get(key);
            result = result.replaceAll(key, value);
        }
        System.out.println(result);
        return result;
    }

    static class UndefinedFunction extends Exception {
        public UndefinedFunction(String msg){
            super(msg);
        }
    }

    public static String parse(String expression) throws Exception {
        expression = expression.trim().replaceAll("//s+", " ");
        int len = expression.length();
        int index = 0;
        String operator = "";
        List<String> parameters = new ArrayList<>();
        while(index < len) {
            if(index == 0 && expression.charAt(index) == '(') {
                index++;
                StringBuilder sb = new StringBuilder();
                while(index < len && expression.charAt(index) != ' ' && expression.charAt(index) != ')') {
                    sb.append(expression.charAt(index++));
                }
                operator = sb.toString().toUpperCase();
            } else if(expression.charAt(index) == '(') {
                int count = 1, begin = index++;
                while(index < len && count > 0) {
                    if(expression.charAt(index) == '(') count++;
                    else if(expression.charAt(index) == ')') count--;
                    index++;
                }
                String exp = expression.substring(begin, index);
                parameters.add(parse(exp));
            } else if(expression.charAt(index) == '\'') {
                int begin = index++;
                if(expression.charAt(index) == '(') {
                    int count = 1;
                    index = index + 1;
                    while (index < len && count > 0) {
                        if(expression.charAt(index) == '(') count++;
                        else if(expression.charAt(index) == ')') count--;
                        index++;
                    }
                } else {
                    while(index < len && expression.charAt(index) != ' ') index++;
                }
                parameters.add(expression.substring(begin, index));
            } else if(expression.charAt(index) == ' ') {
                index++;
                continue;
            } else if(expression.charAt(index) == ')') {
                index++;
                continue;
            }else {
                int begin = index++;
                while(index < len && (expression.charAt(index) != ' ' && expression.charAt(index) != ')')) index++;
                parameters.add(expression.substring(begin, index));
            }
        }

        String result = callFunction(operator, parameters);
        System.out.println("result: " + result);
        return result;
    }

    public static String callFunction(String operator, List<String> parameters) throws Exception {
        String result;
        switch(operator) {
            case "+":
                result = Operators.add(parameters);
                break;
            case "-":
                result = Operators.subtract(parameters);
                break;
            case "*":
                result = Operators.multiply(parameters);
                break;
            case "/":
                result = Operators.divide(parameters);
                break;
            case "APPEND":
                result = Operators.append(parameters);
                break;
            case "ATOM":
                result = Operators.atom(parameters);
                break;
            case "CAR":
                result = Operators.car(parameters);
                break;
            case "CDR":
                result = Operators.cdr(parameters);
                break;
            case "CONS":
                result = Operators.cons(parameters);
                break;
            case "CONSP":
                result = Operators.consp(parameters);
                break;
            case "EQL":
                result = Operators.eql(parameters);
                break;
            case ">":
                result = Operators.largeThan(parameters);
                break;
            case "<":
                result = Operators.lessThan(parameters);
                break;
            case "LENGTH":
                result = Operators.length(parameters);
                break;
            case "LIST":
                result = Operators.list(parameters);
                break;
            case "MEMBER":
                result = Operators.member(parameters);
                break;
            case "NOT":
                result = Operators.not(parameters);
                break;
            case "NTH":
                result = Operators.nth(parameters);
                break;
            case "NULLFUNC":
                result = Operators.nullFunc(parameters);
                break;
            case "NUMBERP":
                result = Operators.numberp(parameters);
                break;
            case "REVERSE":
                result = Operators.reverse(parameters);
                break;
            case "SORT":
                result = Operators.sort(parameters);
                break;
            default:
                result = "To be develop or Invalid operator name!";
        }
        return result;
    }
}
