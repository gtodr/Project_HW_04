package studen_grades_system;

public class Student implements Comparable<Student>{
    private String name;
    private String num;
    private double grade;

    public Student(String name, String num, double grade){
        this.name=name;
        this.num=num;
        this.grade=grade;
    }

    public String getName(){
        return name;
    }

    public String getNum(){
        return num;
    }

    public double getGrade(){
        return grade;
    }

    @Override
    public int compareTo(Student o) {
        return (int)(o.getGrade()-this.grade);
    }
}
