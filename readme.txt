In this project, we implemented some basic functions: +, -, *, /, <, >, append， atom, car, cdr, cons, consp, eql, list, member, not, nth, numberp, reverse, sort and etc.

We realize lambda function and allow you call the function recursive as long as your input is valid.

About input:
We do not handle many invalid inputs, so when you write the lisp code, you'd better input the right format or content. 

We also assume that every element in lisp is seperated by one or more spaces.

Here are some tests case we've run:
1. (+ 1 2) => 3.0
2. (+ 1 (2 3) 3) => 9.0
3. (+ (nth 3 '(7 9 (23 1) 12)) (+ 1 (+ 2 3) 2) (+ 2 3)) => 25.0
4. (funcall (lambda (x y) (+ x y 100)) 40 20) => 160.0
5. (funcall (lambda (x y) (+  (nth x '(7 9 (23 1) 12)) (+ 1 (+ 2 3) 2) (+ y 3))) 3 100) => 123.0
6. (append '(1 2) '(3 (d) 4)) => (1 2 3 (d) 4)
7. (funcall (lambda (x y) (+ (car (nth x '(7 9 (23 1) 12))) (+ 1 (+ 2 3) 2) (+ y 3))) 2 100) => 134.0
8. (funcall (lambda (x y z) (sort '(x y 100 z) '<)) 40 20 12) => (12.0 20.0 40.0 100.0)
9. (funcall (lambda (x y z) (list '(x y) 100 z)) 40 10 50) => ((40 10) 100 50)
10.(funcall (lambda (x y z) (car (cdr '(x y 100 z)))) 40 20 12) => 20

P.S.: For convience: we change all type of numeric value to double.