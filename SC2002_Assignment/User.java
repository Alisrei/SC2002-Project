package SC2002_Assignment;

import java.util.Scanner;

/**
 * The {@code User} class represents a user with personal details such as NRIC, name, password, age, and marital status.
 * It provides methods to validate NRIC, change password, and retrieve user details.
 * This class is abstract and should be extended by other classes that require user functionality.
 * 
 * <p>The NRIC format should be validated before the user is created. The NRIC must begin with 'S' or 'T',
 * followed by 7 digits and a single uppercase letter (e.g., S1234567A).</p>
 * 
 * <p>It also supports password change functionality, where the user can update their password after providing 
 * the current password or based on an initial password ("password").</p>
 */
public abstract class User {
    
    private String nric; // Format: S/T followed by 7 digits and a letter
    private String name;
    private String password;
    private int age;
    private boolean martialStatus;

    /**
     * Constructs a new {@code User} object with the specified details.
     * 
     * @param nric The NRIC of the user, which must follow the format [S/T][7 digits][uppercase letter].
     * @param name The name of the user.
     * @param password The initial password for the user.
     * @param age The age of the user.
     * @param isMarried The marital status of the user.
     * @throws IllegalArgumentException if the NRIC format is invalid.
     */
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

    /**
     * Validates the format of the NRIC.
     * 
     * @param nric The NRIC to validate.
     * @return {@code true} if the NRIC matches the expected format, otherwise {@code false}.
     */
    private boolean validateNric(String nric) {
        return nric.matches("[ST]\\d{7}[A-Z]");
    }

    /**
     * Returns the NRIC of the user.
     * 
     * @return The user's NRIC.
     */
    public String getNric() {
        return nric;
    }

    /**
     * Returns the name of the user.
     * 
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the password of the user.
     * 
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the age of the user.
     * 
     * @return The user's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the marital status of the user.
     * 
     * @return {@code true} if the user is married, otherwise {@code false}.
     */
    public boolean isMarried() {
        return martialStatus;
    }

    /**
     * Allows the user to change their password. If the current password is set to the default value ("password"),
     * no old password is required to change it. Otherwise, the current password must be entered for verification.
     * If the new password is successfully confirmed, the password is updated.
     * 
     * <p>If the new passwords do not match, the user will be prompted to try again.</p>
     */
    public void changePassword() {
        Scanner sc = new Scanner(System.in);
        
        if (this.password.equals("password")) {
            boolean changed = false;
            while (!changed) {
                System.out.println("Enter new password:");
                String newPass = sc.next();
                System.out.println("Enter new password again for confirmation:");
                String CFMPass = sc.next();
                if (newPass.equals(CFMPass)) {
                    this.password = newPass;
                    changed = true;
                    System.out.println("Password successfully changed");
                } else {
                    System.out.println("Passwords do not match, please try again");
                }
            }
            return;
        }
        
        boolean correctPassword = false;
        while (!correctPassword) {
            System.out.println("Enter current password:");
            String password = sc.nextLine();
            if (password.equals(this.password)) {
                correctPassword = true;
                boolean changed = false;
                while (!changed) {
                    System.out.println("Enter new password:");
                    String newPass = sc.next();
                    System.out.println("Enter new password again for confirmation:");
                    String CFMPass = sc.next();
                    if (newPass.equals(CFMPass)) {
                        this.password = newPass;
                        changed = true;
                        System.out.println("Password successfully changed");
                    } else {
                        System.out.println("Passwords do not match, please try again");
                    }
                }
            } else {
                System.out.println("Incorrect password, please try again.");
            }
        }
    }

    /**
     * Returns a string representation of the user object, including the NRIC, name, password, age, and marital status.
     * 
     * @return A string representation of the user.
     */
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
}
