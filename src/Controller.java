import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dnalwqer on 18/11/2016.
 */
public class Controller {
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
        for (String item : res) {
            System.out.println(item);
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

    public static void main(String []args) throws Exception{
        String res = Controller.funcall("(funcall (lambda (x y) (+ x y 100)) 40 20)");
    }
}
