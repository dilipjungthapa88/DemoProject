# DemoProject

To set up in local, please follow.

Please have Nodejs, java 1.8 and maven set up in your system.

To run the server, go to the server directory and open command line.
  Build: mvn clean install
  Run: mvn spring-boot:run
  
  Once running, you can test via POSTMAN:
  
 localhost:8080/demo-app/getCombo?phNbr=1234567897&pageNumber=1&itemsPerPage=10
  
 PS. This requires basic authentication [username: FOO, password: FOOPW] 
  
 To run the ui, go to the ui directory and open command line.
  Build: npm intall
  Run: npm run ng serve
  
  The web app can now be accessed at localhost:4200
