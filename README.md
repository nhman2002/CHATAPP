# CHAT APP

This is a mini-project that constructing a Client-Server application using Java RMI (Remote Method Invocation). The project is a simple chat application where multiple clients can connect to a server and send messages to each other. In the context of this project, the server will be hosted by a VM instance on Google Cloud Platform (GCP) and the clients will be running on local machines. The project is designed to demonstrate the use of Java RMI for remote communication and to provide a basic understanding of how client-server architecture works.

## 1. Compile the programs:

Make sure that you are in the right folder in order to compile the programs.
```
javac -d ./classes ./src/*.java
```

## 2. Run the programs and testing:
Move to the classes folder:
```
cd ../classes
```

Run the Server:
```
java -cp ./classes ChatServerMain <port>
```
example:
```
java -cp ./classes ChatServerMain 3001
```


Run the client:
```
java -cp ./classes ChatClientMain <username> <hostname> <port>
```

You can specify the hostname as localhost or the external IP address of the server(in this context, it is your VM instance's external IP address). The port should be the same as the one used by the server.
example for localhost:
```
java -cp ./classes ChatClientMain Tony localhost 3001
```

example for external IP address:
```
java -cp ./classes ChatClientMain Tony 34.1.7.20 3001
```
### Note:
If you want to test the functionality of the program on your localhost, please find this line in the ChatServerMain.java file and comment it. This is for the purpose of specifying the hostname of the server running on the cloud environment. :
```
System.setProperty("java.rmi.server.hostname", "34.1.7.20");
```

Once you notice that the line above is commented and then you want to run the test on cloud environment, please uncomment it or you can specify that property by running this command when initializing the server via VM:
```
java -cp ./classes -Djava.rmi.server.hostname=34.1.7.20 ChatServerMain <port>
```

Example:
```
java -cp ./classes -Djava.rmi.server.hostname=34.1.7.20 ChatServerMain 3001
```


## 3. How to initialize the VM instance on GCP:
1. Go to the GCP console and create a new VM instance.
2. Choose the machine type and operating system that you want to use. For this project, we recommend using a Debian or Ubuntu instance with at least 1 vCPU and 1 GB of RAM.
3. Once the instance is created, connect to it using SSH.
4. Install Java Development Kit (JDK) on the instance. You can do this by running the following command:
```
sudo apt-get update
sudo apt-get install default-jdk
```
5. Once the JDK is installed, you can upload the project files to the instance using SCP or any other file transfer method.
6. After uploading the files, navigate to the project directory and compile the Java files using the command mentioned above.

### Note: 
It's is better that you acknowledge how to set up your VM's firewall rules in order to allow the incoming traffic on the port that you are using for your server(Notice that this is based on the current platform interface, so may be it could be change in the future).
- Search up "Network Security" on the search bar of the GCP platform.
- Navigate to the "Firewall policies" on the left-side which is an active menu bar.
- Create a new firewall rule and specify the port that you are using for your server. For example, if you are using port 3001, then you should specify that port in the firewall rule. 
    - Name: <your_rule_name>
    - Network: default
    - Priority: 1000
    - Direction of traffic: Ingress
    - Action on match: Allow
    - Targets: All instances in the network
    - Source filter: 0.0.0.0/0
    - Protocols and ports: tcp:3001 (or any other port that you are using for your server. For my version, i enabled all the protocols and ports so that i don't have to mined about the port that i am using.)
- Click on "Create" to create the firewall rule.
- Once the firewall rule is created, you should be able to see the Applicable instances list below that should have your VM instance in it.
- Go back to your VM instance and put in the tag of the rule you've just created into the "Network tags" section of your VM instance. This is to allow the incoming traffic on the port that you are using for your server.
- Click on "Save" to save the changes.