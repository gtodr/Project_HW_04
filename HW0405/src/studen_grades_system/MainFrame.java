package studen_grades_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainFrame extends JFrame implements ActionListener{

    //按钮
    JButton addButton = new JButton("添加");
    JButton sortButton = new JButton("整理");

    //文本输入框
    JTextField nameJTextField=new JTextField(20);
    JTextField numJTextField=new JTextField(20);
    JTextField gradeJTextField=new JTextField(20);

//    double average=0.0;
//    double maxGrade=0.0;
//    double minGrade=0.0;

    public MainFrame(){
        //初始化界面
        initJFrame();

        //添加按钮
        initJButton();

        //添加文本输入框
        initJTextField();

        //显示界面
        this.setVisible(true);
    }

    private void initJTextField() {
        JLabel nameJlabel=new JLabel("姓名");
        JLabel numJlabel=new JLabel("学号");
        JLabel gradejlabel=new JLabel("成绩");
        nameJlabel.setBounds(20,30,50,20);
        numJlabel.setBounds(20,70,50,20);
        gradejlabel.setBounds(20,110,50,20);
        this.getContentPane().add(nameJlabel);
        this.getContentPane().add(numJlabel);
        this.getContentPane().add(gradejlabel);

//        JTextField nameJTextField=new JTextField(20);
//        JTextField numJTextField=new JTextField(20);
//        JTextField gradeJTextField=new JTextField(20);
        nameJTextField.setBounds(60,30,150,20);
        numJTextField.setBounds(60,70,150,20);
        gradeJTextField.setBounds(60,110,150,20);
        nameJTextField.addActionListener(this);
        numJTextField.addActionListener(this);
        gradeJTextField.addActionListener(this);
        this.getContentPane().add(nameJTextField);
        this.getContentPane().add(numJTextField);
        this.getContentPane().add(gradeJTextField);

        JLabel info1=new JLabel("添加:添加输入内容到\"data.txt\"中");
        JLabel info2=new JLabel("整理:整理已有数据并写入\"output.txt\"中");
        info1.setBounds(20,190,200,20);
        info2.setBounds(20,220,280,20);
        this.getContentPane().add(info1);
        this.getContentPane().add(info2);

        this.getContentPane().repaint();
    }

    private void initJButton() {

        addButton.setBounds(50,280,100,20);
        sortButton.setBounds(150,280,100,20);

        addButton.addActionListener(this);
        sortButton.addActionListener(this);

        this.getContentPane().add(addButton);
        this.getContentPane().add(sortButton);
    }

    private void initJFrame() {
        //设置长宽
        this.setSize(300,340);

        //标题
        this.setTitle("学生成绩管理系统");

        //居中
        this.setLocationRelativeTo(null);

        //设置关闭操作
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //布局
        this.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj==addButton){
            System.out.println("添加");

            try {
                FileWriter fileWriter= new FileWriter("/Users/dwayne/Desktop/CS/Java/CourseHW/Project_HW_04/HW0405/src/studen_grades_system/data.txt",true);
                BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);

                String name=nameJTextField.getText();
                String num=numJTextField.getText();
                String grade=gradeJTextField.getText();
                bufferedWriter.write(name+"\t"+num+"\t"+grade);
                bufferedWriter.newLine();

                bufferedWriter.close();
                fileWriter.close();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            nameJTextField.setText("");
            numJTextField.setText("");
            gradeJTextField.setText("");

        } else if (obj==sortButton) {
            System.out.println("整理");

            try {
                List<Student> students=readStudentsFromFile("/Users/dwayne/Desktop/CS/Java/CourseHW/Project_HW_04/HW0405/src/studen_grades_system/data.txt");
                //计算
                double average=calculateAverage(students);
                double maxGrade=calculateMaxGrade(students);
                double minGrade=calculateMinGrade(students);

                //排序
                Collections.sort(students);

                for (Student student:students){
                    System.out.println(student);
                }

                //将结果写入文件
//                FileWriter fileWriter=new FileWriter("/Users/dwayne/Desktop/CS/Java/CourseHW/Project_HW_04/HW0405/src/studen_grades_system/output.txt");
//                BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
//                bufferedWriter.write("平均成绩:"+average+"\t"+"最高分:"+maxGrade+"\t"+"最低分:"+minGrade);
                writeStudentsToFile(students,"/Users/dwayne/Desktop/CS/Java/CourseHW/Project_HW_04/HW0405/src/studen_grades_system/output.txt",average,maxGrade,minGrade);


            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    //从文件中读取学生成绩数据
    private static List<Student> readStudentsFromFile(String filename) throws IOException {
        List<Student> students=new ArrayList<>();
        BufferedReader reader =new BufferedReader(new FileReader(filename));

        String line;
        while ((line=reader.readLine())!=null){
            String[] parts=line.split("\\s");
            if (parts.length==3){
                String name=parts[0];
                String num=parts[1];
                double grade= Double.parseDouble(parts[2]);
                students.add(new Student(name,num,grade));
            }
        }
        reader.close();

        return students;
    }


    //计算平均数
    private static double calculateAverage(List<Student> students){
        double sum=0.0;
        for (Student student:students){
            sum+=student.getGrade();
        }
        double res=sum/students.size();
        return (Math.round(res*100)/100.0);
    }

    //计算最大值
    private static double calculateMaxGrade(List<Student> students){
        double maxGrade=-1.0;
        for (Student student:students){
            if (student.getGrade()>maxGrade){
                maxGrade=student.getGrade();
            }
        }

        return maxGrade;
    }

    //计算最小值
    private static double calculateMinGrade(List<Student> students){
        double minGrade=9999;
        for (Student student:students){
            if (student.getGrade()<minGrade){
                minGrade=student.getGrade();
            }
        }

        return minGrade;
    }

    //将成绩写入文件
    private static void writeStudentsToFile(List<Student> students,String filename, double average, double maxGrade, double minGrade) throws IOException {
        BufferedWriter writer=new BufferedWriter(new FileWriter(filename));
        writer.write("平均成绩:"+average+"\t"+"最高分:"+maxGrade+"\t"+"最低分:"+minGrade+"\n");
        for (Student student:students){
            writer.write(student.getName()+"\t"+student.getNum()+"\t"+student.getGrade());
            writer.write("\n");
        }

        writer.close();
    }
}
