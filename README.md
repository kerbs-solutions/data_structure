# deprecated

Build the Library
Navigate to the data-structure folder and build the library:

cd /path/to/Kerbs-solutions/data-structure
mvn clean install
This installs the library to your local Maven repository (~/.m2/repository).

Import the Library in kerbs-informes-back

Add the Dependency
In the kerbs-informes-back/pom.xml, add the data-structure dependency:

<dependency>
     <groupId>org.kerbs.common</groupId>
    <artifactId>data_structure</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>

Build the other project
command to force maven reindexing for the other project: mvn dependency:purge-local-repository

--------------------------------------

Make Changes in data-structure

When you need to update data-structure, make your changes and bump the version in its pom.xml. For example:

<version>1.1.0</version>
Step 4.2: Rebuild and Install
Build and install the updated library:

cd /path/to/Kerbs-solutions/data-structure
mvn clean install
Step 4.3: Update kerbs-informes-back
Update the kerbs-informes-back/pom.xml dependency version to match the new version of data-structure:

<dependency>
    <groupId>com.kerbs</groupId>
    <artifactId>data-structure</artifactId>
    <version>1.1.0</version>
</dependency>
Rebuild kerbs-informes-back to use the updated library.

### ---------------------------------------------------------------

# REMOTE DEPLOYMENT 

run mvn deploy. everything is configured to be deployed in:

https://github.com/kerbs-solutions/data_structure/packages/
