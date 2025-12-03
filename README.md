# NxEFxC-Automation-Tool

## Description
The **NxEFxC-Automation-Tool** is a Java-based algorithm to automate the fill in for the **NxEFxC.xlsm** Excel File

![Tool User Interface](screenshots/toolUI.png)

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
Also you will get back a lot of errors while trying to import the Excel file back to Jabot if the values are not set correctly!

*Example: Screenshot of an unprocessed Excel file*  
![Unprocessed Excel file](screenshots/upf(1).png)
![Unprocessed Excel file](screenshots/upf(2).png)

---

## The Solution
The **NxeFxC-Automation-Tool** automates the fill in for the **NxEFxC.xlsm** file, while it efficiently sets the best values for each datapoint type and also only the really necessary.
It:
- Algorithmically analyzes the NxEFxC.xlsm **raw** file  
- Sets the correct values for each datapoint type
- Is also able to generate Trend Data for AnalogInputs and AnalogOutputs  
- Generates a file ready for re-import
  
In a large project with over 1,000 data points, this tool will save several hours of manual work.

*Example: Screenshot of the processed Excel file*  
![Processed Excel file](screenshots/pf(1).png)
![Processed Excel file](screenshots/pf(2).png)
*Example: Screenshot of the successful import back to Jabot*  
![Successful import back to Jabot](screenshots/imp.png)
After the successful import back to Jabot every datapoint has its correct values set and now the **Controller Application Files** can be generated and the programming can start!

---

## Tools & Technologies
The project uses the following technologies and libraries:

- **Java 22/Spring Boot** – main programming language  
- **Apache POI** – for reading and writing Excel files  
- **Angular/TypeScript** – for User Interface  
- **Gradle / Maven** – for project build and dependency management  

---

## Info
**For privacy and data protection reasons, some details in the screenshots cannot be shown.  
If you have any questions regarding the data or the workflow, feel free to contact me.**


