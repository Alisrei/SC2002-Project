BTO Management System
Overview
This BTO (Build-To-Order) Management System is designed to simulate the real-world operations of HDB's flat application process in Singapore. The system supports three main user roles: Applicant, HDB Officer, and HDB Manager, each with distinct responsibilities and access levels.

Built with an Object-Oriented Programming (OOP) approach, the system encapsulates shared behavior and attributes through a hierarchical class structure while promoting code clarity, role segregation, and logical consistency.

Key Features
User Management
Handles user authentication, password changes, and user-type recognition.

All users inherit from a base User class which contains shared attributes: NRIC, name, password, age, and marital status.

BTO Project Management
Managed by HDBManager

Each project has attributes like project name, flat types, availability, and application period.

Application Handling
Applicants can apply for or withdraw from BTO projects.

HDB Managers can review and process applications.

Enquiries
Applicants can submit questions or inquiries.

HDB Officers and Managers can respond to inquiries.

Flat Booking
Post-application, HDB Officers manage flat bookings for successful applicants.

Object-Oriented Design Principles
Encapsulation
Each class is responsible for its own internal data and behavior. External access is provided only through well-defined methods.

Inheritance
The Applicant, HDBOfficer, and HDBManager classes inherit from a common User superclass, which contains shared attributes and methods.

Polymorphism
Some methods are overridden to exhibit different behaviors depending on the user's role. For example, HDB Officers can perform both applicant and officer actions due to their dual role capabilities.

We applied the Object-Oriented principles we learnt to improve clarity and maintainability.

Single Responsibility Principle (SRP)
We implemented this in our Application class, as it focuses solely on managing an applicant’s BTO application. This helped us to avoid mixing up logic with the user interface or the flat details – making the application class easier to test and debug.

Open/Closed Principle (OCP)
We used this when we implemented our User superclass with polymorphic behaviour in Applicant, HDBOfficer and HDBManager class. This allows us to add new user roles, but we do not have to modify the existing code. However, this required careful monitoring when coding the shared methods and overriding behaviours, but made extending this code in the future easier.

Liskov Substitution Principle (LSP)
This can be seen in our code where any User reference can be safely substituted by an Applicant, HDB Officer, or HDB Manager, with the code running without any hitches. This follows LSP by ensuring that role-based polymorphism works without breaking logic. 

Interface Segregation Principle (ISP)
Our code contains multiple interfaces that allow the subclasses to only make use of methods they are in need of. 

Dependency Inversion Principle (DIP)
Our code implemented this Principle by separating the input and output logic from the core code. The application and project code do not rely directly on how the file is formatted, which makes future enhancements very easy. 

Data Handling: A database would have made user data management more efficient. However, due to assignment constraints, we implemented file handling via CSVs and text files. While challenging, this requirement ultimately improved our problem-solving and design skills.

File Structure (Example)
bash
Copy
Edit
/bto-management-system
│
├── user.py               # Base User class and derived roles
├── bto_project.py        # Project creation and management
├── application.py        # Application submission and processing
├── enquiry.py            # Enquiry and response logic
├── booking.py            # Flat booking system
├── utils.py              # File I/O utilities
├── main.py               # Entry point and UI routing
├── data/
│   ├── users.csv
│   ├── projects.csv
│   └── applications.txt
└── README.md
Final Thoughts
This project challenged us to translate abstract OOP concepts into a realistic simulation of a public housing system. Despite design challenges and constraints like file-based storage, the experience reinforced good design practices, such as modularization, role-based access, and planning-before-coding.

