package org.example;

import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class Formula {
    private String formula;

    public void setFormula(String formula) {
        this.formula = formula;
    }


    /**
     * Метод проверяет правильность постановок скобок
     * @return true в случае правильной постановке скобок
     */
    public boolean isCorrectFormula() {
        boolean rezult = true;
        int size = formula.length();
        String word;
        Stack<String> formula_stack = new Stack<>();
        int i1 = 0, i2 = 1;

        while (size > 0 && rezult) {
            word = formula.substring(i1, i2);
            if (word.equals("(")) formula_stack.push(word);
            else {
                if (word.equals(")") && formula_stack.size() > 0 && formula_stack.peek().equals("("))
                    formula_stack.pop();
                else {
                    if (word.equals(")") && formula_stack.size() > 0 && formula_stack.peek().equals(")"))
                        rezult = false;
                    if (word.equals(")") && formula_stack.size() == 0) rezult = false;
                }
            }
            size--;
            i1++;
            i2++;
        }
        return rezult && formula_stack.isEmpty();
    }


    /**
     * Метод переводит заданную формулу в постфиксную форму
     * @return представленее заданной формулы в постфиксном виде
     */
    public Queue<String> postfixFormula() {
        int size = formula.length();
        Character character;
        String number = "";
        Stack<String> rezult_stack = new Stack<String>();
        Queue<String> rezult = new LinkedList<String>();
        int i1 = 0;
        while (size > 0) {
            character = formula.charAt(i1);

            if (Character.isLetter(character))
            {
                String a=String.valueOf(character);
                rezult.add(a);

            }
            else if (Character.isDigit(character)) {
                while ((size > 0)&&(Character.isDigit(character))) {
                    number += character;

                    size--;
                    i1++;
                    if(size>0) character = formula.charAt(i1);
                }
                size++;
                i1--;
                rezult.add(number);

                number="";


            } else {
                if (!character.equals(')')) {
                    rezult_stack.push(String.valueOf(character));

                } else {

                    String tmp = rezult_stack.peek();

                    while (!tmp.equals("(")) {
                        rezult.add(tmp);
                        rezult_stack.pop();
                        tmp = rezult_stack.peek();
                    }
                    rezult_stack.pop();

                }
            }
            size--;
            i1++;
        }
        while (!rezult_stack.isEmpty()) {
            String tmp = rezult_stack.peek();
            rezult.add(tmp);
            rezult_stack.pop();
        }
        return rezult;
    }

    /**
     * Метод меняет тип числа на int, а букву меняет на число, введенное пользователем
     * @param oper число или буква, требующая преобразования
     * @return измененная oper
     */
    private int convert(String oper) {
        Scanner scan = new Scanner(System.in);
        int oper_ = 0;
        Character character = oper.charAt(0);
        int tmp_z=1;
        int tmp_o=0;
        if(character.equals('-')) {
            tmp_z=-1;
            tmp_o=1;
            character = oper.charAt(1);
        }
        if (Character.isDigit(character)) {
            int tmp=1;
            for (int i = oper.length() - 1; i >= tmp_o; i--) {
                oper_ += (oper.charAt(i) - '0') * tmp;
                tmp = tmp * 10;
            }
            oper_=oper_*tmp_z;
        } else if (Character.isLetter(character)) {
            System.out.print("Введите неизвестную переменную " + oper + ":");
            oper_ = scan.nextInt();
        }
        return oper_;
    }

    /**
     * Метод вычисляет операцию
     * @param oper1 первый операнд в выражении
     * @param oper2 второй операнд в выражении
     * @param op оперции
     * @return int
     */
    private int calculate(String oper1, String oper2, String op) {
        //Scanner scan = new Scanner(System.in);
        int oper1_ = convert(oper1);
        int oper2_ = convert(oper2);
        int result = 0;
        switch (op) {
            case "+": {
                result = oper1_ + oper2_;
                break;
            }
            case "-": {
                result = oper1_ - oper2_;
                break;
            }
            case "*": {
                result = oper1_ * oper2_;
                break;
            }
            case "/": {
                result = oper1_ / oper2_;
                break;
            }
            default: {
                break;
            }
        }
        return result;
    }

    /**
     * Метод подсчитывает значение формулы
     * @return string
     */
    public String calculateFormula() {
        String word = "";
        Stack<String> q = new Stack<String>();
        Queue<String> tmp = new LinkedList<String>();

        tmp=postfixFormula();
        int result = 0;

        while (!tmp.isEmpty()) {
            word = tmp.peek();
            tmp.remove();
            Character character = word.charAt(0);
            if (Character.isLetter(character) || Character.isDigit(character) || (character.equals("-") && Character.isDigit(word.charAt(1)))) q.add(word);
            else {
                String oper2 = q.peek();
                q.pop();
                String oper1 = q.peek();
                q.pop();
                result = calculate(oper1, oper2, word);
                String res = Integer.toString(result);
                q.add(res);
            }
        }
        return q.peek();
    }


}
