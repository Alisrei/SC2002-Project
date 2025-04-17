package SC2002_Assignment;

import java.util.Scanner;

public abstract class User {
    private String nric;// Format: S/T followed by 7 digits and a letter
    private String name;
    private String password;
    private int age;
    private boolean martialStatus;

    public User(String nric, String name, String password, int age, boolean isMarried) {
        if (!validateNric(nric)) {
            throw new IllegalArgumentException("Invalid NRIC format: " + nric);
        }

        this.nric = nric;
        this.name = name;
        this.password = password;
        this.age = age;
        this.martialStatus = isMarried;
    }

    private boolean validateNric(String nric) {
        return nric.matches("[ST]\\d{7}[A-Z]");
    }

    // Getters and setters
    public String getNric() {return nric;}
    public String getName() {return name;}
    public String getPassword() {return password;}
    public int getAge() {return age;}
    public boolean isMarried() {return martialStatus;}

    // user methods
    public void login(String IC,String Pass){
        if(IC == this.nric && Pass == this.password){
            System.out.println("Login succesful");
        } else if (IC == this.nric && Pass != this.password) {
            System.out.println("Incorrect Password, please try again.");
        }
        else {
            System.out.println("Incorrect Password or username, please try again.");
            //loop for incorrect details to be implemented outside of this class
        }

    }

    @Override
    public String toString() {
        return "User{" +
                "nric='" + nric + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", martialStatus=" + martialStatus +
                '}';
    }

    public void changePassword(){
        Scanner sc = new Scanner(System.in);
        if(this.password.equals("password")){
            boolean changed = false;
            while(!changed){
                System.out.println("Enter new password:");
                String newPass= sc.next();
                System.out.println("Enter new password again for confirmation:");
                String CFMPass = sc.next();
                if (newPass.equals(CFMPass)) {
                    this.password = newPass;
                    changed = true;
                }
                else {
                    System.out.println("Passwords do not match, please try again");
                }
                return;
            }
        }
        System.out.println("Enter current password:");
        String password = sc.nextLine();
        if (password.equals(this.password)){
            boolean changed = false;
            while(!changed){
                System.out.println("Enter new password:");
                String newPass = sc.next();
                System.out.println("Enter new password again for confirmation:");
                String CFMPass = sc.next();
                if (newPass.equals(CFMPass)) {
                    this.password = newPass;
                    changed = true;
                }
                else {
                    System.out.println("Passwords do not match, please try again");
                }
            }
        }
        else{
            System.out.println("Incorrect password, please try again.");
            //loop for incorrect details to be implemented outside of this class
        }
    }
}

