A working version of the app can be accessed at:

http://ec2-3-21-55-51.us-east-2.compute.amazonaws.com

To set up in local, please follow.

Please have Nodejs, java 1.8 and maven set up in your system.

To run the server, go to the server directory and in command line.
  
 1. Build: mvn clean install
 
 2. Run: mvn spring-boot:run
  
  Once running, you can test via POSTMAN:
  
 localhost:8080/demo-app/getCombo?phNbr=1234567897&pageNumber=1&itemsPerPage=10
  
 PS. This requires basic authentication [username: FOO, password: FOOPW] 
  
 To run the ui, go to the ui directory and open command line.
	  npm intall
  	ng serve   (do 'npm install -g @angular/cli' first if angular cli is not installed)
  
  The web app can now be accessed at localhost:4200
