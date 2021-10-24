# Coding Assignment

## Running the app

* Clone the repository
* open console in the project directory
* run `mvn clean install`
* run `java -jar target/codingAssignment-0.0.1-SNAPSHOT.jar`
* the app will ask you to provide the path to the textfile. If no path will be provided then, by default it will
  read `logfile.txt` in `src/main/resources`. You can also replace the `logfile.txt` in this directory before running
  the app.
## HSQLDB
### To get access to the database go to [http://hsqldb.org](http://hsqldb.org/) and download HSQLDB and unzip it. Open the directory, go to the bin directory and execute `runManagerSwing.bat` file.
- Select type: `HSQL Database Engine Standalone`
- Driver: `org.hsqldb.jdbc.JDBCDriver`
- URL: `jdbc:hsqldb:file:«database/path?»` where <<database/path?>> should be pathtoappmaindirectory/db-data/mydatabase
- User: `SA`
- Password: 

