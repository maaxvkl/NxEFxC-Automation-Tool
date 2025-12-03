# NxEFxC-Automation-Tool

## Description
The NxEFxC-Automation-Tool is a Java-based algorithm to automate the fill in for the **NxEFxC.xlsm** Excel File

![Tool User Interface](path/to/screenshot1.png)

---

## The Issue
To generate a **.caf file (Controller Application File)** the datapoint values have to be set in the **NxEFxC.xlsm file** which will be exported from the Jabot Suite.
The NxEFxC.xlsm file contains 260 columns each column is a different datapoint value like:

- Unit
- Low Limit
- High Limit
- Signal
- Polarity
- Default Value

To fill in every cell in the file it takes hours every time every project by new! Above all if you not that experienced in the Metasys Environment this file can be overwhelming! 

*Example: Screenshot of an unprocessed Excel file*  
![Unprocessed Excel file](path/to/screenshot1.png)
![Unprocessed Excel file](path/to/screenshot2.png)

---

## The Solution
The **NxeFxC-Automation-Tool** automates this workflow:

- Algorithmically analyzes Excel files  
- Structures and describes the content  
- Generates a file ready for re-import  

*Example: Screenshot of the processed Excel file*  
![Processed Excel file](path/to/screenshot3.png)
![Processed Excel file](path/to/screenshot4.png)

---

## Tools & Technologies
The project uses the following technologies and libraries:

- **Java 22/Spring Boot** – main programming language  
- **Apache POI** – for reading and writing Excel files  
- **Angular/TypeScript** – for User Interface  
- **Gradle / Maven** – for project build and dependency management  

---


