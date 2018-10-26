## Introduction
This program mines a given Java GitHub code repository, analyses all the commits in that repository and finds commits that have added or modified the parameter of the method. The application is build on the popular framework, repodriller, that helps developers on mining software repositories. The application is already run on two popular repositories, Crawljax and Roaster. The results are generated and given in files, namely, crawljax.csv and roaster.csv

## Installation & Configuration.
1. [Download the application](https://github.com/salmansherin29/Task-1/archive/master.zip)
2. Extract the downloaded contents into a directory.
3. Open [Netbeans](https://netbeans.org/) and open the project by navigating into File -> Open Project
4. After you open the project, Open the `MyStudy.java` file in "src/main/java/com/mycompany/mavenproject1/" by double clicking on that file from the Project explorer on the left side.
5. Change the Repository URl in the file on line 24 for example
6. `.in(GitRepository.singleProject("Path/to/repo/url"))`
7. and Change the Output CSV file directory in the line # 26 for example
8. `.process(new DevelopersVisitor(), new CSVFile("Path/to/output.csv"))`

## Running 
1. Right click on the project name in the left side project explorer and click Build with dependencies.
2. After that, Click on Run -> Run Project (repominer).

## Results
When the Project execute successfully, It will parse all the commits in the provided repository and compile a CSV file with all the functions with added/modified parameters.

##Testing
This application is already validated on two repositories, i.e. crawljax and roaster. The generated CSV files from both the repositories are included in this repository with the name [crawljax.csv](https://github.com/salmansherin29/Task-1/blob/master/crawljax.csv) and [roaster.csv](https://github.com/salmansherin29/Task-1/blob/master/roaster.csv) respectively.
 
