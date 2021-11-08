# Gateway Management Application

This application is to manage gateways. User can view list of gateways, add a new gateways with devices, update a gateways and all of its devices or delete a gateway.
Code is structured with 2 folders gateway-client and gateway-server

## Gateway-server
#### The application is built using SpringBoot, Java, H2 in memory database, Open API documentation 
Start the gateway-server application by running command below (Java 8 is required to be installed on the machine)
### `java -jar gateway-server/gatewaymanagment-0.0.1-SNAPSHOT.jar`

### Supported features
 - Create a gateway and its devices
 - Get list of gateways and devices
 - Get details of a gateway
 - Partial update a gateway: update gateway name, ip address, add new devices, update existing devices and delete devices in one call.
#### API document can be accessed at http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/gateway-controller
#### Database can be accessed at http://localhost:8080/h2
![Alt text](img/database.png?raw=true "H2 Database")
## Gateway-client
This is UI application built with React and material ui. Node is required to be installed on the machine 
To start the application,
### `npm install`
To install all dependencies needed to run the application

### `npm run start`
To start the application. The gateway-client application can be accessed at http://localhost:3000

### Features:
#### Gateways list:
![Alt text](img/AllGateways.png?raw=true "View all gateways")

#### Add a new gateway:
![Alt text](img/CreateGateway.png?raw=true "Create gateway")
Enter gateway information, add devices and then click Add gateway

#### View and edit a gateway
- Select gateway then click view details
![Alt text](img/SelectGateway.png?raw=true "Select a gateway")
- Gateway details
![Alt text](img/GatewayDetails.png?raw=true "Gateway Details")
Where user can change gateway information like name, ip address. Update existing devices, add new devices or delete devices
After provide all information, click Save button to update gateway details

#### Delete a gateway
Select gateway then click delete button
![Alt text](img/SelectGateway.png?raw=true "Select a gateway")





