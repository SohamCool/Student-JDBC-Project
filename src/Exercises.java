import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Exercises {
	private static Connection con = null;
	private static PreparedStatement pt = null;
	private static ResultSet rs = null;
	
	//1. Display list of all student
	public static void displayStudents() {
		ResultSet rs = null;
		try {
			con = DBConnection.getCon();
			pt = con.prepareStatement("select * from student");
			rs = pt.executeQuery();
			System.out.println("List of Student");
			while(rs.next()) {
				System.out.println("\nSno: "+rs.getString(1)+"\nSName: "+rs.getString(2)+"\nSDOB: "+rs.getString(3)+"\nSDOj: "+rs.getString(4));
			}
			rs.close();
			con.close();
			pt.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//2. Display list of all projects
	public static void displayProjects() {
		System.out.println("List of Project");
		try {
			con = DBConnection.getCon();
			pt = con.prepareStatement("select * from project");
			rs = pt.executeQuery();
			while(rs.next()) {
				System.out.println("\nPno: "+rs.getString(1)+"\nPName: "+rs.getString(2)+"\nPDur: "+rs.getString(3)+"\nPPlatform: "+rs.getString(4));
			}
			con.close();
			pt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//3.Display the number of students who are working on project “P01”.
	public static void studentWorkOnProject() {
		System.out.println("Students who are working on Project P01");
		try {
			con = DBConnection.getCon();
			pt = con.prepareStatement("select distinct student.st_no,student.st_name from student join studentproject on student.st_no = studentproject.st_no join project on studentproject.prj_no = project.prj_no where project.prj_no = ?");
			pt.setString(1, "P01");
			rs = pt.executeQuery();
			while(rs.next()) {
				System.out.println("\nSno: "+rs.getString(1)+"\nSName: "+rs.getString(2));
			}
			con.close();
			pt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//4. Display number of students who participated in more than one project.
	
	public static void studentMoreThanOneProject() {
		System.out.println("Students who participated in more than one project.");
		try {
			con = DBConnection.getCon();
			pt = con.prepareStatement("select distinct student.st_no,student.st_name from student join studentproject on student.st_no = studentproject.st_no group by st_no having count(studentproject.prj_no)>1");
			rs = pt.executeQuery();
			while(rs.next()) {
				System.out.println("\nSno: "+rs.getString(1)+"\nSName: "+rs.getString(2));
			}
			con.close();
			pt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//5. Find number of students who did not participate in any project.
	public static void studentNotParticipated() {
		System.out.println("Students who did not participate in any project.");
		try {
			con = DBConnection.getCon();
			pt = con.prepareStatement("select distinct s.st_no,s.st_name from student as s left join studentproject as sp on s.st_no = sp.st_no where sp.prj_no is null");
			rs = pt.executeQuery();
			while(rs.next()) {
				System.out.println("\nSno: "+rs.getString(1)+"\nSName: "+rs.getString(2));
			}
			con.close();
			pt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
//	6.	Display the information(no,name,age) of student  who made the project in java.
	public static void studentProjectInJava() {
		System.out.println("Students who made the project in java.");
		try {
			con = DBConnection.getCon();
			pt = con.prepareStatement("select distinct s.st_no,s.st_name, YEAR(CURRENT_DATE)-YEAR(s.st_dob) as Age from student as s join studentproject as sp on s.st_no = sp.st_no join project as p on sp.prj_no=p.prj_no where p.prj_platform=?");
			pt.setString(1, "JAVA");
			rs = pt.executeQuery();
			while(rs.next()) {
				System.out.println("\nSno: "+rs.getString(1)+"\nSName: "+rs.getString(2));
			}
			con.close();
			pt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
//	7.	Display the student information who is a programmer.
	public static void studentProgrammer() {
		System.out.println("Students who are programmers.");
		try {
			con = DBConnection.getCon();
			pt = con.prepareStatement("select distinct s.st_no,s.st_name, s.st_dob, s.st_doj from student as s left join studentproject as sp on s.st_no = sp.st_no where sp.designation=?");
			pt.setString(1, "PROGRAMMER");
			rs = pt.executeQuery();
			while(rs.next()) {
				System.out.println("\nSno: "+rs.getString(1)+"\nSName: "+rs.getString(2)+"\nSDOB: "+rs.getString(3)+"\nSDOJ: "+rs.getString(4));
			}
			con.close();
			pt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
//	8.	Display the student who played the max designation(e.g. manager,programmer) in the same project.
	public static void studentMaxDesignation() {
		System.out.println("Students who played the max designation in the same project.");
		try {
			con = DBConnection.getCon();
			pt = con.prepareStatement("select distinct s.st_no,s.st_name from student as s left join studentproject as sp on s.st_no = sp.st_no group by sp.st_no having count(sp.st_no)>1");
			rs = pt.executeQuery();
			while(rs.next()) {
				System.out.println("\nSno: "+rs.getString(1)+"\nSName: "+rs.getString(2));
			}
			con.close();
			pt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
//	9.	Display details of the youngest student.
	public static void youngestStudent() {
		System.out.println("\nYoungest Student details.");
		try {
			con = DBConnection.getCon();
			pt = con.prepareStatement("select distinct st_no,st_name,st_dob,st_doj from student where st_dob = (select max(st_dob) from student)");
			rs = pt.executeQuery();
			while(rs.next()) {
				System.out.println("\nSno: "+rs.getString(1)+"\nSName: "+rs.getString(2));
			}
			con.close();
			pt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
//	10.	Display the info of the student who participated in the project where total no of the student should be exact three.
	public static void threeStudentProject() {
		System.out.println("Project having Maximum 3 Students.");
		try {
			con = DBConnection.getCon();
			pt = con.prepareStatement("select s.st_no,s.st_name,s.st_dob,s.st_doj from student as s left join studentproject as sp on s.st_no = sp.st_no where sp.prj_no = (select sp.prj_no from studentproject as sp group by sp.prj_no having count(distinct sp.st_no)>2)");
			rs = pt.executeQuery();
			while(rs.next()) {
				System.out.println("\nSno: "+rs.getString(1)+"\nSName: "+rs.getString(2)+"\nSDOB: "+rs.getString(3)+"\nSDOJ: "+rs.getString(4));
			}
			con.close();
			pt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Display all students.\n2. Display all Projects.\n3. Students who are working on project P01."
				+ "\n4. Students who have participated in more than one projects.\n5. Students who are not participated in any of the Projects."
				+ "\n6. Students who made the project in java.\n7. Students who are programmers.\n8. Student who played the max designation in the same project.\n9. Show Youngest student details.\n10. Three students participated in same project.");
		int ch = sc.nextInt();
		switch(ch) {
		case 1:	displayStudents();
		break;
		case 2: displayProjects();
		break;
		case 3: studentWorkOnProject();
		break;
		case 4: studentMoreThanOneProject();
		break;
		case 5: studentNotParticipated();
		break;
		case 6: studentProjectInJava();
		break;
		case 7: studentProgrammer();
		break;
		case 8: studentMaxDesignation();
		break;
		case 9: youngestStudent();
		break;
		case 10: threeStudentProject();
		break;
		default: System.exit(0);
		}
		
	}

}
