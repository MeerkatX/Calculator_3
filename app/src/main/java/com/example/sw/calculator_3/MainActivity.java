package com.example.sw.calculator_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button[] btn = new Button[10];
    private EditText input;
    private Button div, mul, sub, sum, equ, sin, cos, del, lb, rb, c,point;
    public String str_old, str_new;
    public boolean flag = true;//控制输入true为正确，可以继续输入，false错误，输入锁定
    public boolean flagre = true;// 控制输入，true为重新输入，false为接着输入
    public double pi = 3.14;
    public boolean equals_flag = true;// 是否在按下=之后输入，true为之前，false为之后

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindId();
        setListener();
    }

    private void FindId() {
        input = (EditText) findViewById(R.id.input);
        btn[0] = (Button) findViewById(R.id.zero);
        btn[1] = (Button) findViewById(R.id.one);
        btn[2] = (Button) findViewById(R.id.two);
        btn[3] = (Button) findViewById(R.id.three);
        btn[4] = (Button) findViewById(R.id.four);
        btn[5] = (Button) findViewById(R.id.five);
        btn[6] = (Button) findViewById(R.id.six);
        btn[7] = (Button) findViewById(R.id.seven);
        btn[8] = (Button) findViewById(R.id.eight);
        btn[9] = (Button) findViewById(R.id.nine);
        div = (Button) findViewById(R.id.div);
        mul = (Button) findViewById(R.id.mul);
        sub = (Button) findViewById(R.id.sub);
        sum = (Button) findViewById(R.id.sum);
        cos = (Button) findViewById(R.id.cos);
        sin = (Button) findViewById(R.id.sin);
        del = (Button) findViewById(R.id.del);
        lb = (Button) findViewById(R.id.l);
        rb = (Button) findViewById(R.id.r);
        c = (Button) findViewById(R.id.c);
        equ = (Button) findViewById(R.id.equ);
        point=(Button)findViewById(R.id.point);
    }

    private void setListener() {
        for (int i = 0; i < 10; i++) {
            btn[i].setOnClickListener(setLis);
        }
        point.setOnClickListener(setLis);
        div.setOnClickListener(setLis);
        mul.setOnClickListener(setLis);
        sub.setOnClickListener(setLis);
        sum.setOnClickListener(setLis);
        cos.setOnClickListener(setLis);
        sin.setOnClickListener(setLis);
        del.setOnClickListener(setLis);
        rb.setOnClickListener(setLis);
        lb.setOnClickListener(setLis);
        c.setOnClickListener(setLis);
        equ.setOnClickListener(setLis);
    }

    String[] strcpy = new String[500];//主要用于计算小数点
    int str_i = 0;// strcpy指针
    private View.OnClickListener setLis = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String command = ((Button) v).getText().toString();
            String str = input.getText().toString();
            if (equals_flag == false && "0123456789.()sincos+-*/".indexOf(command) != -1) {
                if (right(str)) {
                    if ("+-×÷√^)".indexOf(command) != -1) {
                        for (int i = 0; i < str.length(); i++) {
                            strcpy[str_i] = String.valueOf(str.charAt(i));
                            str_i++;
                        }
                        flagre = false;
                    }
                } else {
                        input.setText("none");
                        flagre = true;
                        flag = true;
                    }
                    equals_flag = true;
            }
            if(str_i>0)
                check(strcpy[str_i-1],command);
            else if (str_i==0) {
                check("#", command);
            }
            if("0123456789.()sincos+-*/".indexOf(command) != -1 && flag){
                strcpy[str_i]=command;
                str_i++;
            }
            if ("0123456789.()sincos+-*/".indexOf(command) != -1
                    && flag) { // 共25个按键
                print(command);
            }
            else if (command.compareTo("del") == 0 && equals_flag){
                if (str.charAt(str.length() - 1) == 'n'|| str.charAt(str.length() - 1) == 's') {
                    if (str.length() > 3)
                        input.setText(str.substring(0, str.length() - 3));
                    else if (str.length() == 3) {
                        input.setText("0");
                        flagre = true;
                        str_i = 0;
                    }
                }
                // 依次删除一个字符
                else {
                    // 若之前输入的字符串合法则删除一个字符
                    if (right(str)) {
                        if (str.length() > 1)
                            input.setText(str.substring(0, str.length() - 1));
                        else if (str.length() == 1) {
                            input.setText("0");
                            flagre = true;
                            str_i = 0;
                        }
                    } else {
                        input.setText("0");
                        flagre = true;
                        str_i = 0;
                    }
                }
                if (input.getText().toString().compareTo("-") == 0
                        || equals_flag == false) {
                    input.setText("0");
                    flagre = true;
                    str_i = 0;
                }
                flag = true;
                if (str_i > 0)
                    str_i--;
            }else if (command.compareTo("del") == 0 && equals_flag == false) {
                // 将显示器内容设置为0
                input.setText("0");
                flagre = true;
                str_i = 0;
                flag = true;
                // 如果输入的是清除键
            } else if (command.compareTo("cl") == 0) {
                // 将显示器内容设置为0
                input.setText("0");
                // 重新输入标志置为true
                flagre = true;
                // 缓存命令位数清0
                str_i = 0;
                // 表明可以继续输入
                flag = true;
                // 表明输入=之前
                equals_flag = true;
                // 如果输入的是”MC“，则将存储器内容清0
            }
            // 如果输入的是=号，并且输入合法
            else if (command.compareTo("=") == 0 && flag && right(str)
                    && equals_flag) {
                str_i = 0;
                flag = false;
                equals_flag = false;
                // 保存原来算式样子
                str_old = str;
                // 替换算式中的运算符，便于计算
                str = str.replaceAll("sin", "s");
                str = str.replaceAll("cos", "c");
                // 重新输入标志设置true
                flagre = true;
                // 将-1*转换成-
                str_new = str.replaceAll("-", "-1*");
                // 计算算式结果
                new cal().process(str_new,input);
            }

            flag = true;
        }

    };

        private boolean right(String str) {
            int i;
            for (i = 0; i < str.length(); i++) {
                if (str.charAt(i) != '0' && str.charAt(i) != '1'
                        && str.charAt(i) != '2' && str.charAt(i) != '3'
                        && str.charAt(i) != '4' && str.charAt(i) != '5'
                        && str.charAt(i) != '6' && str.charAt(i) != '7'
                        && str.charAt(i) != '8' && str.charAt(i) != '9'
                        && str.charAt(i) != '.' && str.charAt(i) != '-'
                        && str.charAt(i) != '+' && str.charAt(i) != '*'
                        && str.charAt(i) != '/' && str.charAt(i) != 's'
                        && str.charAt(i) != 'i' && str.charAt(i) != 'n'
                        && str.charAt(i) != 'c' && str.charAt(i) != 'o'
                        && str.charAt(i) != '(' && str.charAt(i) != ')')
                    break;
            }
            if (i == str.length()) {
                return true;
            } else {
                return false;
            }
        }

        private void check(String str, String com) {
            boolean error = false;
            int i = 0, j = 0;
            //第一个字符错误判定
            if (str.compareTo("#") == 0 && (com.compareTo("/") == 0 || com.compareTo("*") == 0 || com.compareTo("+") == 0 || com.compareTo(")") == 0))
                error = true;
            else if (str.compareTo("#") != 0) {
                if (str.compareTo("(") == 0) {
                    i = 1;
                } else if (str.compareTo(")") == 0) {
                    i = 2;
                } else if (str.compareTo(".") == 0) {
                    i = 3;
                } else if ("0123456789".indexOf(str) != -1) {
                    i = 4;
                } else if ("+-*/".indexOf(str) != -1) {
                    i = 5;
                } else if ("sincos".indexOf(str) != -1) {
                    i = 6;
                }
                if (com.compareTo("(") == 0) {
                    j = 1;
                } else if (com.compareTo(")") == 0) {
                    j = 2;
                } else if (com.compareTo(".") == 0) {
                    j = 3;
                } else if ("0123456789".indexOf(com) != -1) {
                    j = 4;
                } else if ("+-*/".indexOf(com) != -1) {
                    j = 5;
                } else if ("sincos".indexOf(com) != -1) {
                    j = 6;
                }
                switch (i) {
                    case 1:
                        // 左括号后面直接接右括号,“+x÷”（负号“-”不算）,或者"√^"
                        if (j == 2
                                || (j == 5 && com.compareTo("-") != 0))
                            error = true;
                        break;
                    case 2:
                        // 右括号后面接左括号，数字，“+-x÷sin^...”
                        if (j == 1 || j == 3 || j == 4
                                || j == 6)
                            error = true;
                        break;
                    case 3:
                        // “.”后面接左括号或者“sincos...”
                        if (j == 1 || j == 6)
                            error = true;
                        // 连续输入两个“.”
                        if (j == 3)
                            error = true;
                        break;
                    case 4:
                        // 数字后面直接接左括号或者“sincos...”
                        if (j == 1 || j == 6)
                            error = true;
                        break;
                    case 5:
                        // “+-x÷”后面直接接右括号，“+-x÷√^”
                        if (j == 2 || j == 5)
                            error = true;
                        break;
                    case 6:
                        // “sincos...”后面直接接右括号“+-x÷√^”以及“sincos...”
                        if (j == 2 || j == 5
                                || j == 6)
                            error = true;
                        break;
                }
            }
            if (error == false && com.compareTo(".") == 0) {
                int point = 0;
                for (int k = 0; k < str_i; k++) {
                    // 若之前出现一个小数点点，则小数点计数加1
                    if (strcpy[k].compareTo(".") == 0) {
                        point++;
                    }
                    // 若出现以下几个运算符之一，小数点计数清零
                    if (strcpy[k].compareTo("sin") == 0
                            || strcpy[k].compareTo("cos") == 0
                            || strcpy[k].compareTo("/") == 0
                            || strcpy[k].compareTo("*") == 0
                            || strcpy[k].compareTo("-") == 0
                            || strcpy[k].compareTo("+") == 0
                            || strcpy[k].compareTo("(") == 0
                            || strcpy[k].compareTo(")") == 0) {
                        point = 0;
                    }
                }
                point++;
                // 若小数点计数大于1，表明小数点重复了
                if (point > 1) {
                    error=true;
                }
            }
            // 检测右括号是否匹配
            if (error == false && com.compareTo(")") == 0) {
                int right_bracket = 0;
                for (int k = 0; k < str_i; k++) {
                    // 如果出现一个左括号，则计数加1
                    if (strcpy[k].compareTo("(") == 0) {
                        right_bracket++;
                    }
                    // 如果出现一个右括号，则计数减1
                    if (strcpy[k].compareTo(")") == 0) {
                        right_bracket--;
                    }
                }
                // 如果右括号计数=0,表明没有响应的左括号与当前右括号匹配
                if (right_bracket == 0) {
                    error =true;
                }
            }
            // 检查输入=的合法性
            if (error == false && com.compareTo("=") == 0) {
                // 括号匹配数
                int bracket = 0;
                for (int k = 0; k < str_i; k++) {
                    if (strcpy[k].compareTo("(") == 0) {
                        bracket++;
                    }
                    if (strcpy[k].compareTo(")") == 0) {
                        bracket--;
                    }
                }
                // 若大于0，表明左括号还有未匹配的
                if (bracket > 0) {
                    error=true;
                } else if (bracket == 0) {
                    // 若前一个字符是以下之一，表明=号不合法
                    if ("sincos".indexOf(str) != -1) {
                        error=true;
                    }
                    // 若前一个字符是以下之一，表明=号不合法
                    if ("+-*/".indexOf(str) != -1) {
                        error = true;
                    }
                }
            }
            if (error)
                flag = false;
        }

        private void print(String str) {
            // 清屏后输出
            if (flagre) {
                input.setText(str);
            } else {
                input.append(str);
            }
            flagre = false;
        }
}
