To design our BTO Management System, we began by identifying the three main user roles - Applicant, HDB Officer and HDB Manager. Using Object-Oriented thinking, we modeled each role as a separate class that inherits from a common User superclass to enforce the shared attributes such as NRIC, name, password, age and marital status to promote encapsulation. 
Initial Breakdown of Logical Components:
User Management: Handles authentication, password change, user type identification
BTO Project Management: Managed by HDBManager & contains attributes like project name, flat types, availability and application period
Application Handling: Applicant can apply and withdraw applications, while HDBManager reviews them
Enquiries: Applicant can ask questions, and HDBOfficer or HDBManager can respond
Flat Booking: Post-approval actions can be handled by HDBOfficer
We managed to incorporate a few Object-Oriented (OO) Thinking principles into our code logic when we were thinking of the different components of the project. 
OO Principles Used:
Encapsulation: Each class manages its own internal data and exposes methods
Inheritance: Applicant, HDBOfficer, and HDBManager inherit from User
Polymorphism: Officers act as both officers and applicants – our method design has some overridden methods to allow for certain methods to behave differently depending on the object’s role context.
We mapped real-world user cases like “Applicant applies for a flat” or “HDB Manager approves the application” to the classes we created. We did this for most of the test cases and roles that the guidelines stated, and this helped us to align each feature to a specific class and limit scope overlap. As for early visual models, we created flowcharts to help us plan the logic behind user login and role recognition of users, project visibility and filtering, as well as for the application and flat booking logic. These flowcharts ensured clarity before translating logic into code and helped spot potential logical pitfalls. 

2.2 Reflection on design trade-off
We debated whether to use separate methods for each user type’s functions or design polymorphic behaviour using method overriding. In the end, we prioritised clarity and simplicity over full-blown extensibility for this assignment scope. There was also discussion within my group on whether or not we should combine the classes of Applicant and HDBOfficer since HDB Officers inherit applicant capabilities. In the end, we opted to separate them for clearer responsibility segregation even if it meant duplicating some access logic. For this project, although using a database would have made user data management way easier, the requirement forced us to implement file reading and writing from CSVs and text files, both of which added more complexity for us. However, we felt like that requirement pushed us to think more and design better code, which helped us more in the end.
